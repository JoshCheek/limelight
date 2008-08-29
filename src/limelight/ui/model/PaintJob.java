package limelight.ui.model;

import limelight.Context;
import limelight.BufferedImagePool;
import limelight.caching.Cache;
import limelight.styles.Style;
import limelight.ui.Panel;
import limelight.util.Box;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PaintJob
{
  private Box clip;
  private BufferedImage buffer;
  private Graphics2D rootGraphics;
  private Composite composite;
  private Cache<Panel, BufferedImage> bufferCache;
  private BufferedImagePool bufferedImagePool;

  public PaintJob(Box clip)
  {
    bufferCache = Context.instance().bufferedImageCache;
    bufferedImagePool = Context.instance().bufferedImagePool;
    this.clip = clip;
    buffer = bufferedImagePool.acquire(clip.getSize());
    rootGraphics = buffer.createGraphics();
    rootGraphics.setBackground(Color.white);
    rootGraphics.clearRect(0, 0, clip.width, clip.height);
    composite = rootGraphics.getComposite();
  }

  public void paint(Panel panel)
  {
    Box panelBounds = panel.getAbsoluteBounds();
    int x = panelBounds.x - clip.x;
    int y = panelBounds.y - clip.y;

    Graphics2D graphics = (Graphics2D) rootGraphics.create(x, y, panel.getWidth(), panel.getHeight());
    paint(panel, graphics);
    graphics.dispose();
    rootGraphics.dispose();
  }

  public void paint(Panel panel, Graphics2D graphics)
  {
    applyAlphaCompositeFor(panel, graphics);
    paintClipFor(panel, graphics);
    restoreComposite(graphics);
    paintChildren(panel, graphics);
  }

  public Box getClip()
  {
    return clip;
  }

  public BufferedImage getBuffer()
  {
    return buffer;
  }

  public Graphics2D getGraphics()
  {
    return rootGraphics;
  }

  public boolean panelIsInClip(Panel panel)
  {
    Box panelClip = panel.getAbsoluteBounds();
    return clip.intersects(panelClip);
  }

  public void paintClipFor(Panel panel, Graphics2D graphics)
  {
    if(panel.canBeBuffered())
    {
      BufferedImage panelBuffer = bufferCache.retrieve(panel);
      if(shouldBuildBufferFor(panel, panelBuffer))
      {
        bufferedImagePool.recycle(panelBuffer);
        panelBuffer = buildBufferFor(panel);
      }
      graphics.drawImage(panelBuffer, 0, 0, null);
    }
    else
      panel.paintOn(graphics);
  }

  public void applyAlphaCompositeFor(Panel panel, Graphics2D graphics)
  {
    Style style = panel.getStyle();
    int alphaPercentage = style.asInt(style.getTransparency());
    if(alphaPercentage > 0)
    {
      float alpha = 1.0f - (alphaPercentage / 100.0f);
      Composite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
      graphics.setComposite(alphaComposite);
    }
  }

  public void restoreComposite(Graphics2D graphics)
  {
    graphics.setComposite(composite);
  }

  public void paintChildren(Panel panel, Graphics2D graphics)
  {
    if(panel.hasChildren())
    {
      Box innards = panel.getBoxInsidePadding();
      graphics.clipRect(innards.x, innards.y, innards.width, innards.height);
      for(Panel child : panel.getChildren())
        if(!child.isFloater())
          paintChild(graphics, child);
      for(Panel child : panel.getChildren())
        if(child.isFloater())
          paintChild(graphics, child);
    }
  }

  private void paintChild(Graphics2D graphics, Panel child)
  {
    if(panelIsInClip(child))
    {
      Graphics2D childGraphics = (Graphics2D) graphics.create(child.getX(), child.getY(), child.getWidth(), child.getHeight());
      paint(child, childGraphics);
      childGraphics.dispose();
    }
  }

  public void applyTo(Graphics graphics)
  {
    if(graphics != null)
    {
      graphics.drawImage(buffer, clip.x, clip.y, null);
      Toolkit.getDefaultToolkit().sync(); // required to sync display on some systems according "Killer Game Programming"
    }
  }

  public void substituteGraphics(Graphics2D graphics)
  {
    this.rootGraphics = graphics;
  }

  public boolean shouldBuildBufferFor(Panel panel, BufferedImage buffer)
  {
    if(buffer == null)
      return true;
    Style style = panel.getStyle();
    if(panel.getWidth() != buffer.getWidth() || panel.getHeight() != buffer.getHeight())
      return true;
    else if(style.changed() && !(style.getChangedCount() == 1 && style.changed(Style.TRANSPARENCY)))
      return true;
    else
      return false;
  }

  public BufferedImage buildBufferFor(Panel panel)
  {
    BufferedImage buffer = bufferedImagePool.acquire(new Dimension(panel.getWidth(), panel.getHeight()));
    Graphics2D graphics = buffer.createGraphics();
    graphics.setBackground(new Color(0, 0, 0, 0));
    graphics.clearRect(0, 0, panel.getWidth(), panel.getHeight());
    panel.paintOn(graphics);
    graphics.dispose();
    panel.getStyle().flushChanges(); //TODO Maybe called redundantly because Panel.paintOn() should also flushChanges. 
    bufferCache.cache(panel, buffer);
    return buffer;
  }

  public void dispose()
  {
    Context.instance().bufferedImagePool.recycle(buffer);
  }
}
