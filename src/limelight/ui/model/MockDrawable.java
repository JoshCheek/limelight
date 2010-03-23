package limelight.ui.model;

import java.awt.*;

public class MockDrawable implements Drawable
{
  public Graphics2D drawnGraphics2D;
  public int drawnX;
  public int drawnY;
  public int drawnWidth;
  public int drawnHeight;

  public void draw(Graphics2D graphics2D, int x, int y, int scaledWidth, int scaledHeight)
  {
    this.drawnGraphics2D = graphics2D;
    this.drawnX = x;
    this.drawnY = y;
    this.drawnWidth = scaledWidth;
    this.drawnHeight = scaledHeight;
  }
}
