(ns limelight.prop-building-spec
  (:use
    [speclj.core]
    [limelight.common]
    [limelight.prop-building]
    [limelight.scene :only (new-scene)]
    [limelight.production :only (new-production)]))

(defn illuminate [scene]
  (.setProduction @(.peer scene) (.peer (new-production (limelight.model.FakeProduction. "Mock Production"))))
  (.illuminate @(.peer scene)))

(describe "prop-building"

  (it "builds with no props"
    (let [scene (build-props (new-scene {}) "" "props.rb")]
      (should= 0 (count (child-props scene)))))

  (it "with one prop"
    (let [scene (build-props (new-scene {}) "[:one]" "props.rb")]
      (should= 1 (count (child-props scene)))))

  (it "with two prop"
    (let [scene (build-props (new-scene {}) "[:one][:two]" "props.rb")]
      (should= 2 (count (child-props scene)))))

  (it "nested props"
    (let [scene (build-props (new-scene {}) "[:one [:two]]" "props.rb")]
      (should= 1 (count (child-props scene)))
      (should= 1 (count (child-props (first (child-props scene)))))))

  (it "with options and a child prop"
    (let [scene (build-props (new-scene {}) "[:one {:text \"Number ONE!\"} [:two]]" "props.rb")]
      (should= 1 (count (child-props scene)))
      (should= 1 (count (child-props (first (child-props scene)))))))

  (it "with two children"
    (let [scene (build-props (new-scene {}) "[:one {:text \"Number ONE!\"} [:two] [:three]]" "props.rb")]
      (should= 2 (count (child-props (first (child-props scene)))))))

  (it "with illumination"
    (let [scene (build-props (new-scene {}) "[:one {:text \"Number ONE!\"} [:two]]" "props.rb")]
      (illuminate scene)
      (should= "one" (.getName @(.peer (first (child-props scene)))))
      (should= "Number ONE!" (.getText @(.peer (first (child-props scene)))))))

  (it "with dynamic code"
    (let [scene (build-props (new-scene {}) "[:one (for [name [:two :three]] [name])]" "props.rb")]
      (should= 2 (count (child-props (first (child-props scene)))))))

  (it "adds the props in the right order"
    (let [scene (build-props (new-scene {}) "[:one][:two][:three]" "props.rb")]
      (illuminate scene)
      (should= "one" (prop-name (first (child-props scene))))
      (should= "two" (prop-name (second (child-props scene))))
      (should= "three" (prop-name (nth (child-props scene) 2)))))

  )

(run-specs)
