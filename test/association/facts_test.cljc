(ns association.facts-test
  "Honesty invariants. These do not check that the EMF's documents say what
  the catalog says they say — only a fetch can do that. They check that the
  catalog cannot present a located link as a read source, and cannot hide the
  boundary of what it does not contain."
  (:require [clojure.string :as str]
            [association.facts :as facts]
            #?(:clj [clojure.test :refer [deftest is testing]]
               :cljs [cljs.test :refer [deftest is testing]])))

(def valid-provenance
  #{:official-association-site
    :official-association-site-index
    :official-association-site-file})

(deftest every-entry-is-identified-and-sourced
  (doseq [entry facts/catalog]
    (testing (:association-rule/id entry)
      (is (seq (:association-rule/id entry)))
      (is (seq (:association-rule/title entry)))
      (is (= "emf" (:association-rule/association entry)))
      (is (= "6492" (:association-rule/isic entry)))
      (is (str/starts-with? (:association-rule/url entry) "https://hypo.org/")
          "every source URL must be on the association's own domain")
      (is (contains? valid-provenance (:association-rule/url-provenance entry))
          "evidence strength must be one of the three declared levels")
      (is (re-matches #"\d{4}-\d{2}-\d{2}" (str (:association-rule/retrieved-at entry)))))))

(deftest quotes-only-come-from-fetched-pages
  (doseq [entry facts/catalog]
    (when (:association-rule/quote entry)
      (testing (:association-rule/id entry)
        (is (= :official-association-site (:association-rule/url-provenance entry))
            "a verbatim quote may only be attached to a page that was actually fetched")))))

(deftest ids-are-unique
  (let [ids (map :association-rule/id facts/catalog)]
    (is (= (count ids) (count (set ids))))))

(deftest supranational-country-code-is-explicit
  (testing "EU is used deliberately, and uniformly — never silently mixed with iso3"
    (is (every? #(= "EU" (:association-rule/country %)) facts/catalog))))

(deftest the-boundary-is-data-not-prose
  (is (seq facts/not-catalogued)
      "the catalog must enumerate what it does NOT contain")
  (doseq [gap facts/not-catalogued]
    (is (seq (:item gap)))
    (is (seq (:reason gap)) "a gap must say WHY it is a gap")))

(deftest coverage-does-not-overstate
  (let [c (facts/coverage)]
    (is (= (count facts/catalog) (:entries c)))
    (is (= (count (facts/quoted)) (:fetched-and-quoted c)))
    (is (< (:fetched-and-quoted c) (:entries c))
        "if this ever equals :entries, the note below must be rewritten too")
    (is (str/includes? (:note c) "not a complete rule set"))))

(deftest query-helpers-work
  (is (seq (facts/by-kind :statistical-publication)))
  (is (seq (facts/by-topic :mortgage-lending)))
  (is (empty? (facts/by-kind :self-regulatory-code))
      "this catalog holds no self-regulatory code — see the ns docstring"))
