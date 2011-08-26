//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.ui.model;

import limelight.ui.Panel;

public interface Layout
{
  void doLayout(Panel panel);
  boolean overides(Layout other);

  void doLayout(Panel panel, boolean topLevel);
}
