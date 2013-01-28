(use 'lamina.core 'lamina.viz)

; defines a channel. A place that receives events
(def ch (channel))

; apply an increment method for each value entering the channel
(map* inc ch)

; add some values to the channel
(enqueue ch 1 2 3)

; use graphviz to view the graph
(view-graph ch)