(ns clj-todoist.core
  (:require [clj-http.client :as client])
  (:use [cheshire.core]))

; base url of the API
(def base "https://todoist.com/API/")

; store the token in here 
(def token (ref ""))

; call the api through JSON
(defn call
	([method] (call method {}))
	([method params]
	(let 
		[answer (:body (client/post (str base method)
		{:form-params (assoc params :token @token)
		 :as :json}))]
	(if (= method "login")
		(dosync (ref-set token (answer :api_token))))
		answer)))

(def todo-methods	
["login"
"getTimezones"
"register"
"updateUser" 
"getProjects"
"getProject"
"addProject"
"updateProject"
"updateProjectOrders"
"deleteProject"
"archiveProject"
"unarchiveProject"
"getLabels"
"addLabel"
"updateLabel"
"updateLabelColor"
"deleteLabel"
"getUncompletedItems"
"getAllCompletedItems"
"getCompletedItems"
"getItemsById"
"addItem"
"updateItem"
"updateOrders"
"moveItems"
"updateRecurringDate"
"deleteItems"
"completeItems"
"uncompleteItems"
"addNote"
"updateNote"
"deleteNote"
"getNotes"
"query"])

(doseq [m todo-methods] (intern *ns* (symbol m) (fn [params] (call m params))))