(ns association-facts-kotoba-parity-test
  "The EMF catalog in .cljc and in .kotoba, field by field.

  This repository is the one member of the assoc family whose `.cljc` exposes a
  flat `catalog` vector with `by-kind`/`by-topic`/`quoted`/`coverage`, rather
  than the slug-keyed map and `spec-basis` the other 41 share. So the generated
  test the siblings use does not fit, and this one is written against the API
  that is actually here. The Kotoba module is generated the same way -- from
  `data/datascript-tx.edn` -- because the data shape IS shared even where the
  Clojure surface is not.

  Both sides are readings of the same data, so the risk is transcription, not
  logic: a wrong URL, a dropped field, a topic that lost its entry. Every field
  of all 7 entries is compared.

  `:association-rule/topic` is a SET. A set has no order and `topic` is indexed
  by position, so the port uses the order the data file writes; the assertion
  compares against that written order rather than against `seq` on a set."
  (:require [clojure.test :refer [deftest is testing]]
            [association.facts :as facts]
            [kotoba.compiler.core :as compiler]
            [kotoba.kir :as ir]))

(def ^:private source (slurp "src/association_facts.kotoba"))
(def ^:private kir (:kir (compiler/compile-source source :js-kotoba-v1)))
(defn- call [f & args] (ir/execute kir f (vec args)))
(defn- present [option] (when (second option) (nth option 2)))

(def ^:private slug "emf")
(def ^:private entries (vec facts/catalog))
(def ^:private topic-order [["governance" "mortgage-lending"] ["governance" "membership"] ["market-statistics" "mortgage-lending"] ["market-statistics" "mortgage-lending"] ["advocacy" "regulation"] ["covered-bonds" "funding"] ["covered-bonds" "regulation" "equivalence"]])
(def ^:private fields
  ["id" "title" "association" "isic" "country" "kind" "url" "url-provenance"
   "established-date" "retrieved-at"])
(def ^:private kw->field
  {"id" :association-rule/id "title" :association-rule/title
   "association" :association-rule/association "isic" :association-rule/isic
   "country" :association-rule/country "kind" :association-rule/kind
   "url" :association-rule/url "url-provenance" :association-rule/url-provenance
   "established-date" :association-rule/established-date
   "retrieved-at" :association-rule/retrieved-at})

(deftest the-fixture-reads-a-real-catalog
  (is (= 7 (count entries)))
  (is (= (count entries) (count topic-order))))

(deftest every-field-of-every-entry-is-transcribed
  (is (= (count entries) (call 'entry-count slug)))
  (doseq [[i entry] (map-indexed vector entries)]
    (doseq [f fields]
      (testing (str "entry " i " / " f)
        (let [expected (get entry (kw->field f))
              expected (cond (keyword? expected) (name expected)
                             (nil? expected) nil
                             :else expected)]
          (is (= expected (present (call 'entry-field slug i f)))))))))

(deftest topics-are-complete-and-in-the-order-the-port-chose
  (doseq [[i names] (map-indexed vector topic-order)]
    (testing (str "entry " i)
      (is (= (count names) (call 'topic-count slug i)))
      (is (= (set names)
             (set (map name (:association-rule/topic (nth entries i)))))
          "the written order must name exactly the set the cljc holds")
      (doseq [[t nm] (map-indexed vector names)]
        (is (= nm (present (call 'topic slug i t))))))))

(deftest by-topic-answers-the-same-entries
  ;; `facts/by-topic` here takes only a topic -- there is no slug argument in
  ;; this repository's API -- so the comparison is against that.
  (doseq [names topic-order t names]
    (testing t
      (let [cljc (mapv :association-rule/id (facts/by-topic (keyword t)))]
        (is (= (count cljc) (call 'by-topic-count slug t)))
        (is (= (first cljc) (present (call 'by-topic-id slug t 0)))))))
  (is (zero? (call 'by-topic-count slug "no-such-topic"))))

(deftest an-unknown-association-is-covered-by-nothing
  (doseq [other ["zzz" ""]]
    (is (false? (call 'association-covered? other)))
    (is (zero? (call 'entry-count other)))
    (is (nil? (present (call 'entry-field other 0 "id"))))
    (is (nil? (present (call 'coverage-note other))))))

(deftest the-module-compiles-for-every-target-it-claims
  (doseq [target [:js-kotoba-v1 :wasm32-kotoba-v1 :x86_64-kotoba-v1 :aarch64-kotoba-v1]]
    (testing (name target)
      (is (some? (compiler/compile-source source target {}))))))
