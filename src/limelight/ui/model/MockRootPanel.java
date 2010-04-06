//- Copyright © 2008-2010 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.ui.model;

import limelight.styles.RichStyle;
import limelight.ui.Panel;
import java.awt.*;
import java.util.Collection;
import java.util.Map;

public class MockRootPanel extends MockPropablePanel implements RootPanel
{

  public void setFrame(PropFrame frame)
  {
  }

  public boolean hasPanelsNeedingLayout()
  {
    return false;
  }

  public boolean hasDirtyRegions()
  {
    return false;
  }

  public void getAndClearPanelsNeedingLayout(Collection<Panel> panelBuffer)
  {
  }

  public void getAndClearDirtyRegions(Collection<Rectangle> regionBuffer)
  {
  }

  public void addDirtyRegion(Rectangle bounds)
  {
  }

  public Map<String, RichStyle> getStylesStore()
  {
    return null;
  }
}