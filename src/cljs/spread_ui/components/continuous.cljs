(ns spread-ui.components.continuous
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(defn handle-tree-upload [evt]
  (println "click")
  )

(defn tree-button []
  (fn []
    [:button {:class "btn-upload"
              :type "button"
              :on-click #(handle-tree-upload %)}
     "Load tree file"]))

(defn continuous-analysis-component []
  (fn []    
    [:p "CONTINUOUS"]
    ;; upload-button
    [tree-button]
    
    ))
