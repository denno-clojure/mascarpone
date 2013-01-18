(use 'clostache.parser)

; simple replacement
(render "Hello, {{name}}!" {:name "Felix"})

; replace using arrays
(render "<ul>{{#names}}<li>{{.}}</li>{{/names}}</ul>" {:names ["Felix" "Jenny"]})

; simple file based templating. this looks in the classpath, so no need for src/..
(render-resource "sample.mustache" {:name "Michael"})