(require '[labs.redis.core :as redis])

(def db (redis/client))

(redis/ping db)
; => <ReplyFuture <StatusReply "PONG">>

@(redis/ping db)
; => <StatusReply "PONG">

(redis/value @(redis/ping db))
; => "PONG"

@@(redis/ping db)
; => "PONG"

@(redis/set db "foo" "bar")
; => <StatusReply "OK">

@(redis/get db "foo")
; => <BulkReply@xxxx: #<byte[] [B@xxxx]>>

@@(redis/get db "foo")
; => #<byte[] [B@xxxx]>

(String. @@(redis/get db "foo"))
; => "bar"

(redis/->str @(redis/get db "foo"))
; => "bar"
