(ns orbit.test.tree
  (:use orbit.tree))

(defn test1 []
  (vec->style
    [:#id1 :a 3 :b 4
     [:#id2 :b 4]
     [:#id1 :a 4]]))

(defn test2 []
  (let [s (into-style
            [:#id1 :a 3 :b 4]
            [:#id1 :a 4]
            [:type :bla "hello"]
            [:type :bla "blabal"]
            [:.class2 :bla "3" :a 3])]
    (select-style-with-classes s ["class1" "class2"])
    s
    (select-style s :type)))

(defn test3 []
  (let [s (into-style
            [:type :a 3]
            [:.class1 :b 4]
            [:.class2 :a 4])
        n (vec->node
            [:type.class1.class2]
            #(do % (gensym)))]
    (style! n s)))
