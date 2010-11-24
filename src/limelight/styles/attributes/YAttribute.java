//- Copyright © 2008-2010 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.styles.attributes;

import limelight.styles.StyleAttribute;
import limelight.styles.abstrstyling.StyleValue;
import limelight.ui.model.ChangeablePanel;
import limelight.ui.model.FloaterLayout;

public class YAttribute extends StyleAttribute
{
  public YAttribute()
  {
    super("Y", "y-coordinate", "0");
  }

  @Override
  public void applyChange(ChangeablePanel panel, StyleValue value)
  {
    panel.markAsNeedingLayout(FloaterLayout.instance);
  }
}
