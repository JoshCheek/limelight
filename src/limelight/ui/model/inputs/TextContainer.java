//- Copyright © 2008-2010 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.ui.model.inputs;

import limelight.styles.Style;
import limelight.util.Box;

import java.awt.*;

public interface TextContainer
{
  Style getStyle();

  Point getAbsoluteLocation();

  int getWidth();

  int getHeight();

  Box getBounds();

  boolean hasFocus();
}
