(use 'clodiuno.core)
(use 'clodiuno.firmata)

(def board (arduino :firmata "/dev/tty.usbmodemfa131"))

(pin-mode board 13 OUTPUT)
(digital-write board 13 HIGH)
(digital-write board 13 LOW)

(close board)