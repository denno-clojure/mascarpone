(use 'serial-port)

; list ports
(list-ports)

; open the port
(def port (open "/dev/rdisk0"))

; display whats coming through
(on-byte port #(println %))

; bytes
(byte-array [(byte 0) (byte 1) (byte 2)])
; write
(write-int-seq port bytes)

; close 
(close port)