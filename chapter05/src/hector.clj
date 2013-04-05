(ns clj-hector.test.core
  (:use [clj-hector.ddl] [clj-hector.core]))

(.setLevel (java.util.logging.Logger/getLogger "org.apache.cassandra.db.Memtable")
           java.util.logging.Level/WARNING)

(def my-cluster (cluster "Test Cluster" "127.0.0.1"))

(add-keyspace my-cluster 
              {:name "Keyspace"
               :strategy :simple
               :replication 1
               :column-families [{:name "a"}
                                 {:name "b" :comparator :long}]})

(add-column-family my-cluster  "Keyspace" {:name "c"})

(def ks (keyspace my-cluster "Keyspace"))

(put ks "a" "Paul" {"b" 50} :n-serializer :string :v-serializer :long)
(get-rows ks "a" ["Paul"] :n-serializer :string :v-serializer :long)

(drop-keyspace my-cluster "Keyspace")