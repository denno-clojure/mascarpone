(ns orbit.jme.network
  (:require [orbit.util :as util]
            [clojure.edn :as edn])
  (:import (com.jme3.network
             Client Server
             HostedConnection
             Network ConnectionListener
             ClientStateListener
             MessageListener)))

(com.jme3.network.serializing.Serializer/registerClass
  orbit.StringMessage)

(defn- string-message-listener [f]
  (reify MessageListener
    (messageReceived [this source str-message]
      (let [message (.getMessage str-message)
            obj (try (edn/read-string message)
                  (catch Exception e
                    (throw (Exception. (str "Failed to read "
                                            (pr-str message))))))]
        (f source obj)))))

(defn string-message [obj]
  (orbit.StringMessage. (pr-str obj)))

(defn connection-listener [which on-connect on-disconnect]
  (case which
    :server (reify ConnectionListener
              (connectionAdded [this server connection]
                (on-connect server connection))
              (connectionRemoved [this server connection]
                (on-disconnect server connection)))
    :client (reify ClientStateListener
              (clientConnected [this client]
                (on-connect client))
              (clientDisconnected [this client info]
                (on-disconnect client info)))
    ))

(defn server
  "Create a Server.
  
  Options:
   :on-connect  A function taking two arguments:
                this server and a HostedConnection.
   
   :on-disconnect  Same as :on-connect.
   
   :on-message  A function taking three arguments:
                this server, the HostedConnection and the message.
   
   :port  The port used.
          Default: 8080
   
   :tcp-port  The port to use for tcp messages.
              If :tcp-port and :udp-port is specified,
              they override :port.
   
   :udp-port  See :tcp-port.
  "
  {:arglists '([& options])}
  [& {:as args}]
  (let [{:keys [port tcp-port udp-port
                on-message on-connect on-disconnect]
         :or {on-connect (fn [& _])
              on-disconnect (fn [& _])
              on-message (fn [& _])
              port 8080}
         } args
        [tcp-port udp-port] (if (and tcp-port udp-port)
                              [tcp-port udp-port]
                              [port port])
        s (if udp-port
            (Network/createServer tcp-port udp-port)
            (Network/createServer tcp-port))
        ]
    (.addConnectionListener
      s (connection-listener :server on-connect on-disconnect))
    (.addMessageListener
      s (string-message-listener (partial on-message s)))
  s))

(defn client
  "Create a Client.
  
  Options:
   :host  The host to connect to.
          Default: \"localhost\"
   
   :on-connect  A function taking one argument: this client.
   
   :on-disconnect  Same as :on-connect.
   
   :on-message  A function taking two arguments:
                this client and the message.
   
   :port  The port used for connecting.
          Default: 8080
  "
  [& {:as args}]
  (let [{:keys [host port on-connect on-disconnect
                on-message]
         :or {on-connect (fn [& _])
              on-disconnect (fn [& _])
              on-message (fn [& _])
              host "localhost"
              port 8080}} args
        c (Network/connectToServer host port)]
    (.addClientStateListener
      c (connection-listener :client on-connect on-disconnect))
    (.addMessageListener
      c (string-message-listener on-message))
    c))

(defn send!
  "Send message.
  
  The first argument should be of type:
   Client
   HostedConnection
   Server
  
  The message can be any clojure datastructure.
  "
  {:arglists '([client message]
               [connection message]
               [server message])}
  [obj message]
  (let [msg (string-message message)]
    (cond
      (instance? Client obj)
        (.send ^Client obj msg)
      (instance? HostedConnection obj)
        (.send ^HostedConnection obj msg)
      (instance? Server obj)
        (.broadcast ^Server obj msg)
      :else (util/convert-err obj))))

(defn close!
  ([obj] (.close obj))
  ([obj info] (.close obj info)))
