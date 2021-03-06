package limelight.styles.attributes;

import limelight.styles.StyleAttribute;
import limelight.styles.abstrstyling.StyleValue;
import limelight.ui.model.ChangeablePanel;

public class HeightAttribute extends StyleAttribute
{
  public HeightAttribute()
  {
    super("Height", "dimension", "auto");
  }
  
  @Override
  public void applyChange(ChangeablePanel panel, StyleValue value)
  {
    handleDimensionChange(panel);
  }
}
