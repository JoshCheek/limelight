//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.styles.attributes;

import limelight.styles.compiling.DimensionAttributeCompiler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BackgroundImageFillStrategyAttributeTest extends Assert
{
  private BackgroundImageFillStrategyAttribute attribute;

  @Before
  public void setUp() throws Exception
  {
    attribute = new BackgroundImageFillStrategyAttribute();
  }

  @Test
  public void shouldCreation() throws Exception
  {
    assertEquals("Background Image Fill Strategy", attribute.getName());
    assertEquals("fill strategy", attribute.getCompiler().type);
    assertEquals("repeat", attribute.getDefaultValue().toString());
  }
}
