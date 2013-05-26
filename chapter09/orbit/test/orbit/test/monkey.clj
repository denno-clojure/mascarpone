(ns orbit.test.monkey
  (:import [com.jme3 app.SimpleApplication
                     system.AppSettings
                     system.JmeSystem
                     material.Material
                     scene.Geometry
                     scene.shape.Box
                     math.Vector3f
                     math.ColorRGBA]))
 
(def desktop-cfg (.getResource (.getContextClassLoader (Thread/currentThread))
                   "com/jme3/asset/Desktop.cfg"))
 
(def assetManager (JmeSystem/newAssetManager desktop-cfg))
 
(def ^:dynamic *app-settings* (doto (AppSettings. true)
                                (.setFullscreen false)
                                (.setTitle "jme_clj")))
 
(def app (proxy [SimpleApplication] []
  (simpleInitApp []
    (org.lwjgl.input.Mouse/setGrabbed false)
    (let [b (Box. Vector3f/ZERO 1 1 1)
          geom (Geometry. "Box" b)
          mat (Material. assetManager
                         "Common/MatDefs/Misc/Unshaded.j3md")]
      (.setColor mat "Color" ColorRGBA/Blue)
      (.setMaterial geom mat)
      (doto (.getRootNode this) (.attachChild geom))))))
 
(defn -main [& args]
  (doto app
    (.setShowSettings false)
    (.setPauseOnLostFocus false)
    (.setSettings *app-settings*)
    (.start)))