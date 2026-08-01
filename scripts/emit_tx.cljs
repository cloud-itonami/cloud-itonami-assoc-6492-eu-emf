#!/usr/bin/env nbb
;; scripts/emit_tx.cljs — project `association.facts/catalog` into DataScript
;; tx-data at data/datascript-tx.edn. GENERATED FILE: never hand-edit
;; data/datascript-tx.edn, edit src/association/facts.cljc and re-run:
;;
;;   nbb --classpath src scripts/emit_tx.cljs

(ns emit-tx
  (:require [clojure.string :as str]
            [association.facts :as facts]
            ["fs" :as fs]))

(def header
  (str ";; data/datascript-tx.edn — GENERATED from src/association/facts.cljc by\n"
       ";; scripts/emit_tx.cljs. Do not hand-edit: edit the catalog and re-run\n"
       ";;   nbb --classpath src scripts/emit_tx.cljs\n"
       ";;\n"
       ";; " (count facts/catalog) " located source(s), of which "
       (count (facts/quoted)) " were fetched and quoted. The remainder are\n"
       ";; links listed on a fetched page but not themselves fetched — see\n"
       ";; :association-rule/url-provenance. Known gaps live in\n"
       ";; `association.facts/not-catalogued`, not in this file.\n\n"))

(fs/writeFileSync "data/datascript-tx.edn"
                  (str header (str/join "\n" (map pr-str facts/catalog)) "\n"))

(println "wrote data/datascript-tx.edn:" (count facts/catalog) "entities")
