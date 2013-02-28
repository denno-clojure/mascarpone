(use 'clj-webdriver.taxi)
(require '[clj-webdriver.core :as web])

; define a driver
(def driver (new-driver {:browser :firefox}))
(to driver "http://japantimes.co.jp")

; take a screenshot
(web/get-screenshot driver :file)

; remember if you do not want to wait for loading of a page:
(future (to driver "http://www.google.co.jp"))