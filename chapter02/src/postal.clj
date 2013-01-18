(use 'postal.core)

; REPLACE WITH YOUR GMAIL USER NAME AND PASSWORD !
(send-message ^{	:host "smtp.gmail.com"
                    :user "<fakeuser>"
                    :pass "<fakepassword>"
                    :ssl :yes!!!11
                }
                {	:from "hellonico@gmail.com"
                    :to "hellonico@gmail.com"
                    :subject "Hi!"
                    :body [ 
                    	{:content "<html>新橋</html>" :type "text/html; charset=utf-8"}
                        {:type :attachment :content (java.io.File. "src/mail.txt")}
                        {	:type :inline 
                        	:content (java.io.File. "src/mail.txt") 
                        	:content-type "application/text"}
					]})