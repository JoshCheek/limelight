#- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
#- Limelight and all included source files are distributed under terms of the MIT License.

def open
  puts "Curtains.open"
  scene.remove(self)
end

on_mouse_clicked do
  open
end

