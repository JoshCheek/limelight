package limelight.styles.attributes;

import org.junit.Before;
import org.junit.Test;

public class BottomRightBorderWidthAttributeTest extends AbstractStyleAttributeTest
{
  @Before
  public void setUp() throws Exception
  {
    attribute = new BottomRightBorderWidthAttribute();
  }

  @Test
  public void shouldCreation() throws Exception
  {
    assertEquals("Bottom Right Border Width", attribute.getName());
    assertEquals("pixels", attribute.getCompiler().type);
    assertEquals("0", attribute.getDefaultValue().toString());
  }
  
  @Test
  public void shouldStyleChanged() throws Exception
  {
    checkBorderChange();
  }
}
