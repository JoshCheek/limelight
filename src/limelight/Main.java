//- Copyright 2008 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight;

import limelight.audio.RealAudioPlayer;
import limelight.background.AnimationLoop;
import limelight.background.CacheCleanerLoop;
import limelight.background.PanelPainterLoop;
import limelight.caching.TimedCache;
import limelight.io.Downloader;
import limelight.io.FileUtil;
import limelight.io.TempDirectory;
import limelight.styles.styling.RealStyleAttributeCompilerFactory;
import limelight.ui.Panel;
import limelight.ui.model.AlertFrameManager;
import limelight.ui.model.InertFrameManager;
import org.jruby.Ruby;
import org.jruby.RubyInstanceConfig;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Main
{
  static
  {
    RealStyleAttributeCompilerFactory.install();
  }

  private RubyInstanceConfig config;
  private Ruby runtime;
  private boolean contextIsConfigured;
  private Context context;
  public static boolean startupProvided;

  public static void main(String[] args) throws Exception
  {
    new Main().run(args);
  }

  public static void initializeContext()
  {
    new Main().configureContext();
  }

  public static void initializeTestContext()
  {
    new Main().configureContext();
  }

  public void run(String[] args) throws Exception
  {
    try
    {
      context = Context.instance();
      boolean usingStartupNotifications = context.isOsx() && context.runningAsApp;
      if(usingStartupNotifications)
        StartupListener.register();
      configureSystemProperties();

      processArgs(args);
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      runtime = Ruby.newInstance(config);
      configureContext();
      startJrubyRuntime();

      if(!usingStartupNotifications)
        Context.instance().studio.open(getStartupProduction());
      else if(!startupProvided)
        StartupListener.instance.startupPerformed(getStartupProduction());
    }
    catch(Throwable e)
    {
      handleError(e);
    }
  }

  public static void handleError(Throwable e)
  {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintWriter writer = new PrintWriter(byteArrayOutputStream);
    e.printStackTrace(writer);
    writer.flush();
    JOptionPane.showMessageDialog(new JFrame(), new String(byteArrayOutputStream.toByteArray()), "Limelight Error", JOptionPane.WARNING_MESSAGE);
  }

  private void startJrubyRuntime()
  {
    StringBuffer startupRuby = new StringBuffer();
    startupRuby.append("require '").append(FileUtil.pathTo(context.limelightHome, "lib", "init")).append("'").append("\n");
    startupRuby.append("require 'limelight/studio'").append("\n");
    startupRuby.append("Limelight::Studio.install").append("\n");

    ByteArrayInputStream input = new ByteArrayInputStream(startupRuby.toString().getBytes());
    runtime.runFromMain(input, "limelight_pseudo_main");
  }

  private String getStartupProduction()
  {
    String productionName = context.limelightHome + "/productions/startup";
    if(productionProvided())
      productionName = config.getScriptFileName();
    return productionName;
  }

  private boolean productionProvided()
  {
    return config.getScriptFileName() != null;
  }

  private void processArgs(String[] args)
  {
    config = new RubyInstanceConfig();
    config.processArguments(args);
  }

  private void configureSystemProperties()
  {
//    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty("jruby.base", "");
    System.setProperty("jruby.home", context.limelightHome + "/jruby");
    System.setProperty("jruby.lib", context.limelightHome + "/jruby/lib");
    if(context.isWindows())
    {
      System.setProperty("jruby.shell", "cmd.exe");
      System.setProperty("jruby.script", "jruby.bat org.jruby.Main");
    }
    else
    {
      System.setProperty("jruby.shell", "/bin/sh");
      System.setProperty("jruby.script", "jruby");
    }
  }

  public void configureContext()
  {
//VerboseRepaintManager.install();
    if(contextIsConfigured)
      return;
    Context context = Context.instance();

    context.keyboardFocusManager = new KeyboardFocusManager().installed();
    context.tempDirectory = new TempDirectory();
    context.downloader = new Downloader(context.tempDirectory);
    context.frameManager = new AlertFrameManager();
    context.audioPlayer = new RealAudioPlayer();

    context.bufferedImageCache = new TimedCache<Panel, BufferedImage>(1);
    context.bufferedImagePool = new BufferedImagePool(1);

    context.panelPanter = new PanelPainterLoop().started();
    context.animationLoop = new AnimationLoop().started();
    context.cacheCleaner = new CacheCleanerLoop().started();

    contextIsConfigured = true;
  }

  public void configureTestContext()
  {
    if(contextIsConfigured)
      return;
    Context context = Context.instance();

    context.keyboardFocusManager = new KeyboardFocusManager().installed();
    context.tempDirectory = new TempDirectory();
    context.downloader = new Downloader(context.tempDirectory);
    context.frameManager = new InertFrameManager();
    context.audioPlayer = new RealAudioPlayer();

    context.bufferedImageCache = new TimedCache<Panel, BufferedImage>(1);
    context.bufferedImagePool = new BufferedImagePool(1);

    context.panelPanter = new PanelPainterLoop();
    context.animationLoop = new AnimationLoop();
    context.cacheCleaner = new CacheCleanerLoop();

    contextIsConfigured = true;
  }

}