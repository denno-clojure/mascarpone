(use 'clj-mixpanel.core)

(def api-token "33776931148b353b108c86cecfc8aa29")

(notify api-token "Signed Up" {:distinct-id (generate-uuid) :extra-data "hello"})