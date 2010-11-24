//- Copyright © 2008-2010 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.ui.model.inputs;

import limelight.ui.MockPanel;
import limelight.util.Box;

import java.awt.*;

public class MockTextContainer extends MockPanel implements TextContainer
{
  public Box bounds;
  public boolean cursorOn;

  public MockTextContainer()
  {
  }

  public MockTextContainer(Box bounds)
  {
    this.bounds = bounds;
  }

  public Point getAbsoluteLocation()
  {
    return bounds.getLocation();
  }

  public int getWidth()
  {
    return bounds.width;
  }

  public int getHeight()
  {
    return bounds.height;
  }

  public Box getBounds()
  {
    return bounds;
  }

  public boolean isCursorOn()
  {
    return cursorOn;
  }

  public boolean hasFocus()
  {
    return false;
  }
}
