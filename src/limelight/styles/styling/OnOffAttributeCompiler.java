package limelight.styles.styling;

import limelight.styles.abstrstyling.StyleAttributeCompiler;
import limelight.styles.abstrstyling.StyleAttribute;

public class OnOffAttributeCompiler extends StyleAttributeCompiler
{
  public StyleAttribute compile(Object value)
  {
    String lowerCaseValue = value.toString().toLowerCase();
    if("on".equals(lowerCaseValue))
      return new SimpleOnOffAttribute(true);
    else if("off".equals(lowerCaseValue))
      return new SimpleOnOffAttribute(false);
    else
      throw makeError(value);
  }
}
