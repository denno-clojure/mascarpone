(ns screenscraping
  (:use net.cgrand.enlive-html)
  (:import java.net.URL))

; screen scraping
; retrieve url of images from the website below
(-> "http://www.penny-arcade.com/comic/" URL. html-resource 
  (select [:img]) first :attrs :src)