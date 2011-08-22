(ns limelight.clojure.proxy-map-spec
  (:use
    [speclj.core])
  (:import
    [limelight.clojure ProxyMap]))

(describe "PersistentJavaMap"

  (with java-map (limelight.util.OptionsMap.))
  (with proxy-map (ProxyMap. @java-map))

  (it "can retrieve values"
    (.put @java-map "foo" "bar")
    (should= "bar" (@proxy-map "foo")))

  (it "uses string value of keywords to retrieve values"
    (.put @java-map "foo" "bar")
    (should= "bar" (:foo @proxy-map)))

  (it "uses default value when key is missing"
    (should= :default (:blah @proxy-map :default)))

  (it "can add values"
    (let [new-map (assoc @proxy-map "foo" "bar")]
      (should= "bar" (:foo @proxy-map))
      (should= "bar" (@proxy-map "foo"))))

  (it "throws if adding a value already keyed"
    (.put @java-map "foo" "bar")
    (should-throw Exception "Key already present"
      (.assocEx @proxy-map :foo "value")))

  (it "knows if a key is present"
    (.put @java-map "foo" "bar")
    (should= true (contains? @proxy-map :foo))
    (should= false (contains? @proxy-map :blah)))

  (it "counts entries"
    (should= 0 (count @proxy-map))
    (.put @java-map "foo" "bar")
    (should= 1 (count @proxy-map)))

  (it "detects emptiness"
    (should= true (empty? @proxy-map))
    (.put @java-map "foo" "bar")
    (should= false (empty? @proxy-map)))

  (it "dissociates values"
    (.put @java-map "foo" "bar")
    (let [removed (dissoc @proxy-map :foo)]
      (should-not= nil removed)
      (should= nil (:foo removed))))
  )

(run-specs)