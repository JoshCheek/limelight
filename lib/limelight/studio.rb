require 'limelight/producer'

module Limelight

  class Studio

    include Limelight::UI::Api::Studio

    class << self

      def install
        Context.instance.studio = instance  
      end

      def instance
        @studio = self.new if @studio.nil?
        return @studio
      end

    end

    def open(production)
      producer = Producer.new(production)
      producer.open
    end

  end

end