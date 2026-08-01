(ns association.facts
  "Industry-association source catalog for the European Mortgage Federation
  (EMF) — the first ISIC 6492 (other credit granting) member of the
  `cloud-itonami-assoc-*` family, and the first supranational one.

  READ-ONLY reference/archive, like every sibling: no Advisor ⊣ Governor node,
  nothing proposed, nothing actuated. It records WHERE an official industry
  source lives so a consumer (e.g. `mortgage-registry`, or
  `cloud-itonami-isic-6492`'s Credit Governor) can cite it instead of
  inventing one.

  HONESTY BOUNDARY, stated up front because this catalog is weaker than its
  Japanese siblings and must not be read as equally strong: the EMF publishes
  market statistics, position papers and covered-bond industry documents. This
  catalog does NOT currently hold an EMF self-regulatory CODE. The
  EMF/consumer-organisation \"European Agreement on a Voluntary Code of
  Conduct on Pre-contractual Information for Home Loans\" (the instrument the
  Mortgage Credit Directive's ESIS descends from) was NOT located on hypo.org
  in the 2026-08-01 session and is therefore not asserted here. Do not cite
  this repository as evidence that the EMF has or lacks such a code.

  `:url-provenance` distinguishes three strengths of evidence, deliberately:
    :official-association-site        — the page was fetched and quoted
    :official-association-site-index  — the link is listed on a fetched page,
                                        but that target was NOT itself fetched
    :official-association-site-file   — the file URL resolved and downloaded,
                                        but its text was NOT extracted"
  (:require [clojure.string :as str]))

(def association "emf")

(def catalog
  "Vector of association-source entries. Shape matches every sibling
  `association.facts/catalog`."
  [{:association-rule/id "emf.identity-and-mandate"
    :association-rule/title "About EMF — mandate of the European Mortgage Federation"
    :association-rule/association association
    :association-rule/isic "6492"
    :association-rule/country "EU"
    :association-rule/kind :association-mandate
    :association-rule/url "https://hypo.org/about-emf"
    :association-rule/url-provenance :official-association-site
    :association-rule/established-date "1967"
    :association-rule/retrieved-at "2026-08-01"
    :association-rule/topic [:governance :mortgage-lending]
    :association-rule/quote
    "Established in 1967, the European Mortgage Federation (EMF) is the voice of the European mortgage industry on the retail side of the business, representing the interests of mortgage lenders at European level."}

   {:association-rule/id "emf.full-membership-eligibility"
    :association-rule/title "EMF full membership eligibility rule"
    :association-rule/association association
    :association-rule/isic "6492"
    :association-rule/country "EU"
    :association-rule/kind :membership-rule
    :association-rule/url "https://hypo.org/emf"
    :association-rule/url-provenance :official-association-site
    :association-rule/retrieved-at "2026-08-01"
    :association-rule/topic [:governance :membership]
    :association-rule/quote
    "Full membership is open to associations, groups and organisations which are either licensed or represent credit institutions licensed in the European Union (EU), the European Economic Area (EEA) or in a country that is in accession negotiations with the European Union."
    :association-rule/note
    "The fetched About page also stated \"the EMF has 13 Full Members across 11 EU Member States as well as a number of Observer Members.\" The member list itself (https://hypo.org/emf-members) was NOT fetched, so no member association is named in this catalog."}

   {:association-rule/id "emf.hypostat"
    :association-rule/title "EMF Hypostat — flagship statistical publication on European mortgage markets"
    :association-rule/association association
    :association-rule/isic "6492"
    :association-rule/country "EU"
    :association-rule/kind :statistical-publication
    :association-rule/url "https://hypo.org/emf-hypostat"
    :association-rule/url-provenance :official-association-site-index
    :association-rule/retrieved-at "2026-08-01"
    :association-rule/topic [:market-statistics :mortgage-lending]
    :association-rule/edition-url "https://hypo.org/sites/default/files/2025-09/EMF_Hypostat_2025.pdf"
    :association-rule/edition-provenance :official-association-site-file
    :association-rule/note
    "The 2025 edition URL resolved and downloaded (4 MB PDF) but its text could NOT be extracted in this session, so nothing is quoted from inside it — neither its coverage, its country count nor its figures."}

   {:association-rule/id "emf.quarterly-review"
    :association-rule/title "EMF Quarterly Review of European Mortgage Markets"
    :association-rule/association association
    :association-rule/isic "6492"
    :association-rule/country "EU"
    :association-rule/kind :market-review
    :association-rule/url "https://hypo.org/quarterly-review"
    :association-rule/url-provenance :official-association-site-index
    :association-rule/retrieved-at "2026-08-01"
    :association-rule/topic [:market-statistics :mortgage-lending]}

   {:association-rule/id "emf.position-papers"
    :association-rule/title "EMF-ECBC Position Papers"
    :association-rule/association association
    :association-rule/isic "6492"
    :association-rule/country "EU"
    :association-rule/kind :position-paper-series
    :association-rule/url "https://hypo.org/position-papers"
    :association-rule/url-provenance :official-association-site-index
    :association-rule/retrieved-at "2026-08-01"
    :association-rule/topic [:advocacy :regulation]}

   {:association-rule/id "ecbc.fact-book"
    :association-rule/title "ECBC Fact Book & Statistics (European Covered Bond Council)"
    :association-rule/association association
    :association-rule/isic "6492"
    :association-rule/country "EU"
    :association-rule/kind :statistical-publication
    :association-rule/url "https://hypo.org/ecbc-fact-book"
    :association-rule/url-provenance :official-association-site-index
    :association-rule/retrieved-at "2026-08-01"
    :association-rule/topic [:covered-bonds :funding]
    :association-rule/note
    "Covered-bond funding is the wholesale side of the same mortgage lending this family's ISIC 6492 covers; it is catalogued here because the EMF and the ECBC are co-located and jointly published, not because covered bonds are a retail mortgage instrument."}

   {:association-rule/id "ecbc.global-concept-note-third-country-equivalence"
    :association-rule/title "ECBC Global Concept Note on Third Country Equivalence for Covered Bonds"
    :association-rule/association association
    :association-rule/isic "6492"
    :association-rule/country "EU"
    :association-rule/kind :industry-concept-note
    :association-rule/url "https://hypo.org/ecbc-global-concept-note-third-country-equivalence-covered-bonds"
    :association-rule/url-provenance :official-association-site-index
    :association-rule/retrieved-at "2026-08-01"
    :association-rule/topic [:covered-bonds :regulation :equivalence]}])

(def not-catalogued
  "What this catalog deliberately does NOT contain, as data. A reader must be
  able to see the boundary without reading prose."
  [{:item "EMF/consumer-organisation European Agreement on a Voluntary Code of Conduct on Pre-contractual Information for Home Loans"
    :reason "not located on hypo.org in the 2026-08-01 session; asserting it from memory would fabricate a URL"}
   {:item "the 13 EMF Full Member associations by name"
    :reason "https://hypo.org/emf-members was not fetched"}
   {:item "Energy Efficient Mortgage Label (EEML) and its Harmonised Disclosure Template"
    :reason "referenced in a fetched EMF-ECBC response PDF but its own site was not fetched"}
   {:item "any figure from inside Hypostat 2025"
    :reason "PDF downloaded but text extraction failed"}])

(defn by-kind [kind]
  (vec (filter #(= kind (:association-rule/kind %)) catalog)))

(defn by-topic [topic]
  (vec (filter #(some #{topic} (:association-rule/topic %)) catalog)))

(defn quoted
  "Entries that carry a verbatim quote from a page that was actually fetched.
  Everything else is a located link, not a read source."
  []
  (vec (filter :association-rule/quote catalog)))

(defn coverage []
  {:association association
   :entries (count catalog)
   :fetched-and-quoted (count (quoted))
   :listed-but-not-fetched (count (filter #(= :official-association-site-index
                                              (:association-rule/url-provenance %))
                                          catalog))
   :known-gaps (count not-catalogued)
   :note (str "Located sources, not a complete rule set. "
              (count (quoted)) " of " (count catalog)
              " entries were fetched and quoted; the rest are links listed on a "
              "fetched page. Known gaps: "
              (str/join "; " (map :item not-catalogued)) ".")})
