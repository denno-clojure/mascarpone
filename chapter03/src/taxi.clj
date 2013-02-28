(use 'clj-webdriver.taxi)
(require '[clj-webdriver.core :as web])

(set-driver! {:browser :firefox})
; at the time of writing, I had to manually open a new window
; in the browser itself

; list of other drivers
; 
; (def ^{:doc "Map of keywords to available WebDriver classes."}
;   webdriver-drivers
;   {:firefox FirefoxDriver
;    :ie InternetExplorerDriver
;    :chrome ChromeDriver
;    ;; :opera OperaDriver
;    :htmlunit HtmlUnitDriver})

; go to github
(to "https://github.com")
(click "a[href*='login']")

(input-text "#login_field" "your_username")
(-> "#password"
  (input-text "your_password")
  submit)
(quit)