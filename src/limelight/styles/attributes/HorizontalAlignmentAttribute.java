package limelight.styles.attributes;

import limelight.styles.StyleAttribute;
import limelight.styles.abstrstyling.StyleValue;
import limelight.ui.model.ChangeablePanel;

public class HorizontalAlignmentAttribute extends StyleAttribute
{
  public HorizontalAlignmentAttribute()
  {
    super("Horizontal Alignment", "horizontal alignment", "left");
  }

  @Override
  public void applyChange(ChangeablePanel panel, StyleValue value)
  {
    expireCache(panel);
    panel.getTextAccessor().markAsNeedingLayout();
    panel.markAsNeedingLayout();
  }
}
