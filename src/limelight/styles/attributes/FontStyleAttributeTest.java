//- Copyright © 2008-2010 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.styles.attributes;

import org.junit.Before;
import org.junit.Test;

public class FontStyleAttributeTest extends AbstractStyleAttributeTest
{
  @Before
  public void setUp() throws Exception
  {
    attribute = new FontStyleAttribute();
  }

  @Test
  public void shouldCreation() throws Exception
  {
    assertEquals("Font Style", attribute.getName());
    assertEquals("font style", attribute.getCompiler().type);
    assertEquals("plain", attribute.getDefaultValue().toString());
  }

  @Test
  public void shouldApplyChanges() throws Exception
  {
    checkFontChange();
  }
}
