# cloud-itonami-assoc-6492-eu-emf

Industry source catalog for the **European Mortgage Federation** (EMF, jointly
branded EMF-ECBC) — the **first ISIC 6492** (other credit granting) member of
the `cloud-itonami-assoc-*` compliance-fact family, and the **first
supranational** one, alongside
[`cloud-itonami-assoc-6810-jpn-recaj`](https://github.com/cloud-itonami/cloud-itonami-assoc-6810-jpn-recaj),
[`cloud-itonami-assoc-6419-jpn-zenginkyo`](https://github.com/cloud-itonami/cloud-itonami-assoc-6419-jpn-zenginkyo),
[`cloud-itonami-assoc-6419-deu-bankenverband`](https://github.com/cloud-itonami/cloud-itonami-assoc-6419-deu-bankenverband),
[`cloud-itonami-assoc-6419-fra-fbf`](https://github.com/cloud-itonami/cloud-itonami-assoc-6419-fra-fbf)
and the rest. Prior family ADR: `com-junkawasaki/root` ADR-2607141700.
This repository's ADR: `90-docs/adr/2608011200-mortgage-registry-procedure-support-organizations.edn`.

Consumed by [`mortgage-registry`](https://github.com/cloud-itonami/mortgage-registry),
which holds the per-jurisdiction mortgage procedure / public-support /
organization planes and joins to this family on `(:isic, :country)`.

## Scope

A **read-only reference/archive** catalog — not an Advisor ⊣ Governor actor.
It records *where an official industry source lives* so a consumer can cite it
instead of inventing one.

> "Established in 1967, the European Mortgage Federation (EMF) is the voice of
> the European mortgage industry on the retail side of the business,
> representing the interests of mortgage lenders at European level."
> — [hypo.org/about-emf](https://hypo.org/about-emf), retrieved 2026-08-01

## Read this before citing it: this catalog is weaker than its siblings

The Japanese siblings catalogue **self-regulatory codes** (定款, 自主行動計画).
**This one does not.** The EMF publishes market statistics, position papers and
covered-bond industry documents; the EMF/consumer-organisation *European
Agreement on a Voluntary Code of Conduct on Pre-contractual Information for
Home Loans* — the instrument the Mortgage Credit Directive's ESIS descends from
— **was not located on hypo.org in the 2026-08-01 session and is therefore not
asserted here.** Do not cite this repository as evidence that the EMF has, or
lacks, such a code.

`:url-provenance` grades every entry by evidence strength, so a located link
can never be mistaken for a read source:

| value | meaning | entries |
|---|---|---|
| `:official-association-site` | the page was fetched **and quoted** | 2 |
| `:official-association-site-index` | listed on a fetched page, target **not** fetched | 5 |
| `:official-association-site-file` | file URL resolved and downloaded, text **not** extracted | Hypostat 2025 edition |

`association.facts/not-catalogued` enumerates the known gaps **as data** (4
entries), and a test fails if that list is ever emptied. `(facts/coverage)`
returns the same boundary as a map. A further test asserts
`(facts/by-kind :self-regulatory-code)` is empty — if a code is ever added,
that test forces the README claim above to be rewritten in the same commit.

## The "EU" country code

Every sibling is country-level (iso3). The EMF is supranational, so
`:association-rule/country` holds **`"EU"`** — the ISO 3166-1 *exceptionally
reserved* code, not an iso3 country. The deviation is recorded in
`organization.edn` `:country-code-note` and asserted uniformly by a test
rather than silently mixed with iso3 values. Membership spans **13 Full Members
across 11 EU Member States** as of the retrieved source; the member list itself
was not fetched, so **no member association is named in this catalog**.

## Layout

```
src/association/facts.cljc   catalog + not-catalogued + query fns (canonical)
organization.edn             real-world identity (no personal names)
schema/association-rule.edn  DataScript schema (sibling shape + 4 evidence attrs)
data/datascript-tx.edn       GENERATED projection — never hand-edit
scripts/emit_tx.cljs         regenerates data/ from src/ (nbb)
test/association/facts_test.cljc  honesty invariants
run-tests.cljs               nbb test entry point
```

```bash
nbb --classpath src:test run-tests.cljs   # 7 tests, 69 assertions
nbb --classpath src scripts/emit_tx.cljs  # regenerate data/datascript-tx.edn
```

## Licence

AGPL-3.0-or-later.
