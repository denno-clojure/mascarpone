(require '[clj-todoist.core :as todo])

(todo/login {:email "nico@cloudstudio.co.jp" :password "12345"})

; list my projects
(todo/getProjects {})

; get info about a project
(todo/getProject {:project_id 100008867})

; get uncompleted tasks
(todo/getUncompletedItems {:project_id 100008867})

; get a task id
(def task-id ( 
	(todo/addItem {:project_id 100008867 :content "test item"})
	:id))

; complete a task
(todo/completeItems {:ids (str "[" task-id "]")})