(ns orbit.test.monkey2
  (:use cortex.world)
  (:import com.jme3.math.Vector3f)
  (:import com.jme3.material.Material)
  (:import com.jme3.scene.Geometry)
  (:import com.jme3.math.ColorRGBA)
  (:import com.jme3.app.SimpleApplication)
  (:import com.jme3.scene.shape.Box))

(def cube (Box. Vector3f/ZERO 1 1 1))

(def geom (Geometry. "Box" cube))

(def mat (Material. (asset-manager) "Common/MatDefs/Misc/Unshaded.j3md"))

(.setColor mat "Color" ColorRGBA/Blue)

(.setMaterial geom mat)

(defn simple-app []
  (doto
      (proxy [SimpleApplication] []
        (simpleInitApp
         []
         ;; Don't take control of the mouse
         (org.lwjgl.input.Mouse/setGrabbed false)
         (.attachChild (.getRootNode this) geom)))
    ;; don't show a menu to change options.
    (.setShowSettings false)
    (.setPauseOnLostFocus false)
    (.setSettings *app-settings*)))