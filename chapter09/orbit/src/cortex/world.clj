(ns cortex.world
  "World Creation, abstraction over jme3's input system, and REPL
  driven exception handling"
  {:author "Robert McIntyre"}
  
  (:import com.aurellem.capture.IsoTimer)

  (:import com.jme3.math.Vector3f)
  (:import com.jme3.scene.Node)
  (:import com.jme3.system.AppSettings)
  (:import com.jme3.system.JmeSystem)
  (:import com.jme3.input.KeyInput)
  (:import com.jme3.input.controls.KeyTrigger)
  (:import com.jme3.input.controls.MouseButtonTrigger)
  (:import com.jme3.input.InputManager)
  (:import com.jme3.bullet.BulletAppState)
  (:import com.jme3.shadow.BasicShadowRenderer)
  (:import com.jme3.app.SimpleApplication)
  (:import com.jme3.input.controls.ActionListener)
  (:import com.jme3.renderer.queue.RenderQueue$ShadowMode)
  (:import org.lwjgl.input.Mouse)
  (:import com.aurellem.capture.AurellemSystemDelegate))

(in-ns 'cortex.world)

(def ^:dynamic *app-settings*
  "These settings control how the game is displayed on the screen for
   debugging purposes.  Use binding forms to change this if desired.
   Full-screen mode does not work on some computers."
  (doto (AppSettings. true)
    (.setFullscreen false)
    (.setTitle "Aurellem.")
    ;; The "Send" AudioRenderer supports simulated hearing.
    (.setAudioRenderer "Send")))    

(defn asset-manager
  "returns a new, configured assetManager" []
  (JmeSystem/newAssetManager
   (.getResource
    (.getContextClassLoader (Thread/currentThread))
    "com/jme3/asset/Desktop.cfg")))

	(in-ns 'cortex.world)

	(defmacro no-exceptions
	  "Sweet relief like I never knew."
	  [& forms]
	  `(try ~@forms (catch Exception e# (.printStackTrace e#))))

	(defn thread-exception-removal
	  "Exceptions thrown in the graphics rendering thread generally cause
	  the entire REPL to crash! It is good to suppress them while trying
	  things out to shorten the debug loop."
	  []
	  (.setUncaughtExceptionHandler
	   (Thread/currentThread)
	   (proxy [Thread$UncaughtExceptionHandler] []
	     (uncaughtException
	       [thread thrown]
	       (println "uncaught-exception thrown in " thread)
	       (println (.getMessage thrown))))))

		(in-ns 'cortex.world)

		(defn static-integer?
		  "does the field represent a static integer constant?"
		  [#^java.lang.reflect.Field field]
		  (and (java.lang.reflect.Modifier/isStatic (.getModifiers field))
		       (integer? (.get field nil))))

		(defn integer-constants [class]
		  (filter static-integer? (.getFields class)))

		(defn constant-map
		  "Takes a class and creates a map of the static constant integer
		  fields with their names.  This helps with C wrappers where they have
		  just defined a bunch of integer constants instead of enums"
		  [class]
		     (let [integer-fields (integer-constants class)]
		       (into (sorted-map)
		             (zipmap (map #(.get % nil) integer-fields)
		                     (map #(.getName %) integer-fields)))))
		(alter-var-root #'constant-map memoize)

		(defn all-keys
		  "Uses reflection to generate a map of string names to jme3 trigger
		  objects, which govern input from the keyboard and mouse"
		  []
		  (let [inputs (constant-map KeyInput)]
		    (assoc
		        (zipmap (map (fn [field]
		                       (.toLowerCase (.replaceAll field "_" "-"))) (vals inputs))
		                (map (fn [val] (KeyTrigger. val)) (keys inputs)))
		      ;;explicitly add mouse controls
		      "mouse-left" (MouseButtonTrigger. 0)
		      "mouse-middle" (MouseButtonTrigger. 2)
		      "mouse-right" (MouseButtonTrigger. 1))))

		(defn initialize-inputs
		  "Establish key-bindings for a particular virtual world."
		  [game  input-manager key-map]
		  (doall
		   (map (fn [[name trigger]]
		          (.addMapping
		           ^InputManager input-manager
		           name (into-array (class trigger)
		                            [trigger]))) key-map))
		  (doall
		   (map (fn [name] 
		          (.addListener
		           ^InputManager input-manager game
		           (into-array String [name]))) (keys key-map))))
		
				(in-ns 'cortex.world)

				(defn no-op
				  "Takes any number of arguments and does nothing."
				  [& _])

				(defn traverse
				  "apply f to every non-node, deeply"
				  [f node]
				  (if (isa? (class node) Node)
				    (dorun (map (partial traverse f) (.getChildren node)))
				    (f node)))

				(defn world
				  "the =world= function takes care of the details of initializing a
				  SimpleApplication.

				   ***** Arguments:

				   - root-node : a com.jme3.scene.Node object which contains all of
				       the objects that should be in the simulation.

				   - key-map : a map from strings describing keys to functions that
				       should be executed whenever that key is pressed.
				       the functions should take a SimpleApplication object and a
				       boolean value.  The SimpleApplication is the current simulation
				       that is running, and the boolean is true if the key is being
				       pressed, and false if it is being released. As an example,

				       {\"key-j\" (fn [game value] (if value (println \"key j pressed\")))}

				       is a valid key-map which will cause the simulation to print a
				       message whenever the 'j' key on the keyboard is pressed.

				   - setup-fn : a function that takes a SimpleApplication object. It
				       is called once when initializing the simulation. Use it to
				       create things like lights, change the gravity, initialize debug
				       nodes, etc.

				   - update-fn : this function takes a SimpleApplication object and a
				       float and is called every frame of the simulation.  The float
				       tells how many seconds is has been since the last frame was
				       rendered, according to whatever clock jme is currently
				       using. The default is to use IsoTimer which will result in this
				       value always being the same.
				  "
				  [root-node key-map setup-fn update-fn]
				  (let [physics-manager (BulletAppState.)]
				    (JmeSystem/setSystemDelegate (AurellemSystemDelegate.))
				    (doto
				        (proxy [SimpleApplication ActionListener] []
				          (simpleInitApp
				            []
				            (no-exceptions
				             ;; allow AI entities as much time as they need to think.
				             (.setTimer this (IsoTimer. 60))
				             (.setFrustumFar (.getCamera this) 300)
				             ;; Create default key-map.
				             (initialize-inputs this (.getInputManager this) (all-keys))
				             ;; Don't take control of the mouse
				             (org.lwjgl.input.Mouse/setGrabbed false)
				             ;; add all objects to the world
				             (.attachChild (.getRootNode this) root-node)
				             ;; enable physics
				             ;; add a physics manager
				             (.attach (.getStateManager this) physics-manager)
				             (.setGravity (.getPhysicsSpace physics-manager) 
				                          (Vector3f. 0 -9.81 0))
				             ;; go through every object and add it to the physics
				             ;; manager if relevant.
				             ;;(traverse (fn [geom]
				             ;;            (dorun
				             ;;             (for [n (range (.getNumControls geom))]
				             ;;               (do
				             ;;                 (cortex.util/println-repl
				             ;;                  "adding " (.getControl geom n))
				             ;;                 (.add (.getPhysicsSpace physics-manager)
				             ;;                       (.getControl geom n))))))
				             ;;          (.getRootNode this))
				             ;; call the supplied setup-fn
				             ;; simpler !
				             (.addAll (.getPhysicsSpace physics-manager) root-node)
				             (if setup-fn
				               (setup-fn this))))
				          (simpleUpdate
				            [tpf]
				            (no-exceptions
				             (update-fn this tpf))) 
				          (onAction
				            [binding value tpf]
				            ;; whenever a key is pressed, call the function returned
				            ;; from key-map.
				            (no-exceptions
				             (if-let [react (key-map binding)]
				               (react this value)))))
				      ;; don't show a menu to change options.      
				      (.setShowSettings false)
				      ;; continue running simulation even if the window has lost
				      ;; focus.
				      (.setPauseOnLostFocus false)
				      (.setSettings *app-settings*))))