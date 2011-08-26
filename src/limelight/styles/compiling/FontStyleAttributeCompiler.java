//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.styles.compiling;

import limelight.styles.abstrstyling.StyleCompiler;
import limelight.styles.abstrstyling.StyleValue;
import limelight.styles.values.SimpleFontStyleValue;

public class FontStyleAttributeCompiler extends StyleCompiler
{
  public StyleValue compile(Object value)
  {
    String lowerCase = stringify(value).toLowerCase().trim();

    if("plain".equals(lowerCase))
      return new SimpleFontStyleValue("plain");
    else
    {
      String[] tokens = lowerCase.split(" ");
      for(String token : tokens)
      {
        if(!"bold".equals(token) && !"italic".equals(token))
          throw makeError(value);
      }
      return new SimpleFontStyleValue(lowerCase);
    }

  }
}
