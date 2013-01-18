; https://github.com/davidsantiago/clojure-csv/blob/master/test/clojure_csv/test/core.clj

(import '[java.io StringReader])
(use 'clojure.java.io)
(use 'clojure-csv.core)

; dumb example
(parse-csv "a,b,c")

;
; Longer example on how to convert a CSV list of people
; to vcard format
;
(defn parse-line[o]
(let [
	tel_or_email (get o 3)
	is_email (re-find #"@" tel_or_email)
	]
	{
		:id (first o)
		:first (get o 1)
		:last (get o 2)
		:email (if is_email tel_or_email)
		:tel (if (not is_email) tel_or_email)
	}
	))

(use 'clostache.parser)

; template to convert a record to vcard format
; using only a few fields for this demonstration.
(defn vcard
  "Converts the intermal map representation into a format meeting the vCard specification"
  [record]
  (render "BEGIN:VCARD
VERSION:3.0
N:{{first}};{{last}};
{{#tel}}
TEL;TYPE=CELL;TYPE=PREF:{{tel}}
{{/tel}}
EMAIL;TYPE=PREF:{{email}}
END:VCARD
" record))

; now to the core of the work
; 1 line to parse the csv file
(def ppl (slurp "src/contacts.csv"))
; 1 line to convert each line to a vcard
(def vcards (map #(vcard (parse-line %)) (parse-csv ppl)))

(spit "contacts.vcf" (apply str vcards))