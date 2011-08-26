//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.styles.attributes;

import limelight.styles.compiling.DimensionAttributeCompiler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FloatAttributeTest extends Assert
{
  private FloatAttribute attribute;

  @Before
  public void setUp() throws Exception
  {
    attribute = new FloatAttribute();
  }

  @Test
  public void shouldCreation() throws Exception
  {
    assertEquals("Float", attribute.getName());
    assertEquals("on/off", attribute.getCompiler().type);
    assertEquals("off", attribute.getDefaultValue().toString());
  }
}
