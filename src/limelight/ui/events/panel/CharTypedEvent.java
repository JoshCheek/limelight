//- Copyright © 2008-2010 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.ui.events.panel;

public class CharTypedEvent extends ModifiableEvent
{
  private char c;

  public CharTypedEvent(int modifiers, char c)
  {
    super(modifiers);
    this.c = c;
  }

  public char getChar()
  {
    return c;
  }

  @Override
  public String toString()
  {
    return super.toString() + " char=" + (int)c + "/" + c;
  }
}