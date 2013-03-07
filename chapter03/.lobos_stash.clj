
(create h2 (table :users (varchar :first 100) (varchar :last 100)))

(l/drop (table :users))

(l/create h2 (table :users (varchar :first 100) (varchar :last 100)))

(l/drop (table :users))

(l/create
 h2
 (table
  :users
  (integer :id :auto-inc :primary-key)
  (varchar :first 100)
  (varchar :last 100)))

(l/create
 (table
  :users2
  (varchar :name 100 :unique)
  (check :name (> (length :name) 1))))

(l/drop (table :users2))
