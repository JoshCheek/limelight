#- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
#- Limelight and all included source files are distributed under terms of the MIT License.

require 'limelight/player'
require 'limelight/string'

module Limelight
  module Builtin
    module Players

      Dir.glob(File.expand_path(File.dirname(__FILE__) + "/players/*.rb")).each do |file|
        module_name = File.basename(file)[0...-3].camalized
        content = IO.read(file)
        player = Limelight::Player.new
        player.module_eval(content, file)
        self.const_set(module_name, player)
      end

    end
  end
end