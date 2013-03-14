; import clj
(use 'clj-xpath.core)

; slurp the remote document
(def xdoc (slurp "http://svn.apache.org/viewvc/commons/proper/lang/trunk/pom.xml?view=co&revision=1428371&content-type=text%2Fplain"))

; show the top element
(prn ($x:tag "/*" xdoc))

; show the developers
(prn ($x:text* "/project/developers/developer/name" xdoc))

; describe all the dependencies
(doseq [node ($x "/project/dependencies/dependency" xdoc)]
  (println (format "%s %s %s"
               ($x:text "./groupId"    node)
               ($x:text "./artifactId" node)
               ($x:text "./version"    node))))