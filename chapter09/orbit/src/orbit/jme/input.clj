(ns orbit.jme.input
  (:require (orbit [util :as util]))
  (:import (com.jme3.input.controls
             ActionListener
             AnalogListener
             JoyAxisTrigger
             JoyButtonTrigger
             KeyTrigger
             MouseAxisTrigger
             MouseButtonTrigger
             Trigger)
           (com.jme3.input
             InputManager
             JoyInput KeyInput MouseInput)))

(defn input-manager
  "Convert argument to an InputManager."
  {:arglists '([app] [input-manager])}
  [im]
  (if (instance? InputManager im)
    im
    (.getInputManager
      ^com.jme3.app.Application im)))

(defmacro ki {:private true} [ch]
  (symbol (str "KeyInput/KEY_" ch)))

(defn key-trigger
  "Create a KeyTrigger.
  
  ch is a string denoting the key trigger
  to create.
  
  Possible arguments:
  
  Alphanumerical:
   A-Z
   0-9
   
  Arrow keys:
   up
   down
   left
   right
   
  Keys above arrows:
   delete
   end
   home
   insert
   page down
   page up
   pause
   scroll lock
   
  Mode keys:
   lalt
   ralt
   lcontrol
   rcontrol
   lmeta (\"windows\" key)
   rmeta
   lshift
   rshift
  
  Numpad:
   num + space + 0-9
    Example: \"num 3\"
   num add
   num comma
   num enter
   num equals
   num lock
  "
  [ch]
  (let [ch  (cond
              (string? ch) ch
              (keyword? ch) (name ch)
              (char? ch) (str ch)
              :else (util/convert-err ch))
        ch (.toLowerCase ^String ch)]
    (KeyTrigger.
      (case ch
        ; alphanumerical
        "a" (ki \A)
        "b" (ki \B)
        "c" (ki \C)
        "d" (ki \D)
        "e" (ki \E)
        "f" (ki \F)
        "g" (ki \G)
        "h" (ki \H)
        "i" (ki \I)
        "j" (ki \J)
        "k" (ki \K)
        "l" (ki \L)
        "m" (ki \M)
        "n" (ki \N)
        "o" (ki \O)
        "p" (ki \P)
        "q" (ki \Q)
        "r" (ki \R)
        "s" (ki \S)
        "t" (ki \T)
        "u" (ki \U)
        "v" (ki \V)
        "w" (ki \W)
        "x" (ki \X)
        "y" (ki \Y)
        "z" (ki \Z)
        "0" (ki \0)
        "1" (ki \1)
        "2" (ki \2)
        "3" (ki \3)
        "4" (ki \4)
        "5" (ki \5)
        "6" (ki \6)
        "7" (ki \7)
        "8" (ki \8)
        "9" (ki \9)
        ; keys above arrows
        "escape" (ki "ESCAPE")
        "insert" (ki "INSERT")
        "home" (ki "HOME")
        "delete" (ki "DELETE")
        "end" (ki "END")
        "scroll lock" (ki "SCROLL")
        "pause" (ki "PAUSE")
        "page down" (ki "PGDN")
        "page up" (ki "PGUP")
        ; arrow keys
        "up" (ki "UP")
        "down" (ki "DOWN")
        "left" (ki "LEFT")
        "right" (ki "RIGHT")
        ; mode keys
        "lalt" (ki "LMENU")
        "ralt" (ki "RMENU")
        "lcontrol" (ki "LCONTROL")
        "rcontrol" (ki "RCONTROL")
        "lmeta" (ki "LMETA")
        "rmeta" (ki "RMETA")
        "lshift" (ki "LSHIFT")
        "rshift" (ki "RSHIFT")
        ; numpad keys
        "num 0" (ki "NUMPAD0")
        "num 1" (ki "NUMPAD1")
        "num 2" (ki "NUMPAD2")
        "num 3" (ki "NUMPAD3")
        "num 4" (ki "NUMPAD4")
        "num 5" (ki "NUMPAD5")
        "num 6" (ki "NUMPAD6")
        "num 7" (ki "NUMPAD7")
        "num 8" (ki "NUMPAD8")
        "num 9" (ki "NUMPAD9")
        "num add" (ki "ADD")
        "num comma" (ki "NUMPADCOMMA")
        "num enter" (ki "NUMPADENTER")
        "num equals" (ki "NUMPADEQUALS")
        "num lock" (ki "NUMLOCK")
        ; function keys
        "F1" (ki "F1")
        "F2" (ki "F2")
        "F3" (ki "F3")
        "F4" (ki "F4")
        "F5" (ki "F5")
        "F6" (ki "F6")
        "F7" (ki "F7")
        "F8" (ki "F8")
        "F9" (ki "F9")
        "F10" (ki "F10")
        "F11" (ki "F11")
        "F12" (ki "F12")
        "F13" (ki "F13")
        "F14" (ki "F14")
        "F15" (ki "F15")
        ))))

(defn mouse-axis-trigger [axis direction]
  (MouseAxisTrigger.
    (case axis
      :x MouseInput/AXIS_X
      :y MouseInput/AXIS_Y
      :wheel MouseInput/AXIS_WHEEL)
    (case direction
      :positive false
      :negative true)))

(defn mouse-button-trigger [button]
  (MouseButtonTrigger.
    (case button
      :left MouseInput/BUTTON_LEFT
      :right MouseInput/BUTTON_RIGHT
      :middle MouseInput/BUTTON_MIDDLE)))

(defn mouse-trigger
  "Create either:
   MouseAxisTrigger
   MouseButtonTrigger.
  
  The first argument denotes the type,
  and can be any of:
   :axis for MouseAxisTrigger.
         Then axis is :x, :y or :wheel
         and direction is :positive
         or :negative.
         
   :button for MouseButtonTrigger.
           Then button is one of
           :left :right :middle.
  "
  {:arglists '([:axis axis direction]
               [:button button])}
  [type & args]
  (case type
    :axis (apply mouse-axis-trigger args)
    :button (apply mouse-button-trigger args)
    (util/arg-err
      "type must be one of: :axis :button,"
      "but got instead:" type)))

(defn trigger
  "Create a Trigger.
  
  If argument is a Trigger, it is returned.
  
  If the argument is a string (key), a key-trigger
  is created. See orbit.input/key-trigger for details.
  
  If a sequence is provided, the first element designates
  the type. Type can be any of: :key :mouse. The args
  are then applied to either orbit.input/key-trigger
  or orbit.input/mouse-trigger, respectively.
  "
  {:arglists '([trigger]
               [key]
               [[type & args]])}
  [tr]
  (cond
    (instance? Trigger tr) tr
    (or (string? tr)
        (keyword? tr)) (key-trigger tr)
    (sequential? tr)
    (let [[type v :as args] tr]
      (case type
        :key (key-trigger v)
        :mouse (apply mouse-trigger (rest args))
        (util/arg-err
          (str "first element must be one of: "
               ":key :mouse, but got instead: "
               type))))
    :else (util/arg-err
            "incorrect argument to orbit.input/trigger,"
            "see docstring for usage.")))

(defn add-input-mappings
  "Add input mappings to the input manager
  (or the input manager of the app).
  
  mappings is a map where the keys denote
  which mapping should be triggered. It should
  match the keys provided to
  orbit.input/add-input-listeners.
  
  The values should be a list of objects that
  can be converted to a Trigger by orbit.input/trigger.
  
  Example mappings:
  {:forward [\"up\" \"w\"]
   :backward [\"down\" \"s\"]
   :attack [\"space\"]
   :zoom-out [[:mouse :axis :wheel :positive]]
   :zoom-in [[:mouse :axis :wheel :negative]]
   }
  
  See orbit.input/add-input-listeners for
  the corresponding listeners.
  "
  {:arglists '([app mappings]
               [input-manager mappings])}
  [im mappings]
  (let [im ^InputManager (input-manager im)]
    (doseq [[k triggers] mappings]
      (if-not (keyword? k)
        (util/arg-err
          "mapping key must be a keyword. Got:" (pr-str k)))
      (.addMapping im (name k)
                   (into-array Trigger (map trigger triggers))))))

(defn action-listener
  "Create an ActionListener.
  
  on-action should be a function taking three arguments:
  
   action - The triggered action, useful if triggered
            by multiple actions.
  
   pressed? - True when the event is \"activated\" and
              false when \"deactivated\". For example,
              if it is triggered by a keypress, pressed?
              will be true when the key is pressed down,
              and will be false when the key is released.
              (note that keys fire repeatedly when they
               are pressed).
   
   tpf - Time per frame.
  "
  [on-action]
  (reify ActionListener
    (onAction [this action pressed? tpf]
      (on-action (keyword action)
                 pressed? tpf))))

(defn analog-listener
  "Create an AnalogListener.
  
  on-analog should be a function taking three arguments:
  
   action - The triggered action, useful if triggered
            by multiple actions.
            
   value - Value of the axis, goes from 0 to 1.
   
   tpf - Time per frame.
  "
  [on-analog]
  (reify AnalogListener
    (onAnalog [this action value tpf]
      (on-analog (keyword action)
                 value tpf))))

(defn add-listeners
  ""
  {:arglists '([app listeners]
               [input-manager listeners])}
  [im listeners]
  (let [im ^InputManager (input-manager im)]
    (doseq [[mappings listener] listeners]
      (.addListener im listener
                    (into-array String (map name mappings))))))

(defn- add-listeners-helper
  [im listeners interface into-listener]
  (add-listeners
    im (for [[mappings listener] listeners]
         [mappings
          (cond
            (instance? interface listener)
            listener
            (fn? listener) (into-listener listener))])))

(defn add-action-listeners
  "Add action listeners to input manager
  (or the input manager of app).
  
  listeners should be a map
  where each key is either a single keyword,
  or a list of keywords. Each keyword denotes
  which event to listen to (provided to
  orbit.input/add-input-mappings).
  
  Values should either be an instance of ActionListener
  or a function. See orbit.input/action-listener for
  more info on such functions.
  "
  {:arglists '([app listeners]
               [input-manager listeners])}
  [im listeners]
  (add-listeners-helper
    im listeners
    ActionListener
    action-listener))

(defn add-analog-listeners
  "Add analog listeners to input manager
  (or the input manager of app).
  
  listeners should be a map
  where each key is either a single keyword,
  or a list of keywords. Each keyword denotes
  which event to listen to (provided to
  orbit.input/add-input-mappings).
  
  Values should either be an instance of AnalogListener
  or a function. See orbit.input/analog-listener for
  more info on such functions.
  "
  {:arglists '([app listeners]
               [input-manager listeners])}
  [im listeners]
  (add-listeners-helper
    im listeners
    AnalogListener
    analog-listener))
