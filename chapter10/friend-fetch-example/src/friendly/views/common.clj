(ns friendly.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-js include-css html5]]))

(defpartial layout [& content]
            (html5
              [:head
               [:title "friendly"]
			   (include-css "http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css")
               (include-js "/js/main.js")]
              [:body {:onload "friendly.client.setuser()"}
               [:div.row
    			[:div.span4]
				[:div.span8
                [:div#wrapper
                 content]]]]))
