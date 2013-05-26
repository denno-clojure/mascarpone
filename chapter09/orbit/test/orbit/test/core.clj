(ns orbit.test.core
  (:require [orbit.core :as mkr]))

(defn application []
 (let [app (mkr/application :settings {}
                            )]))