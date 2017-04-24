(ns spread-ui.components.chooser
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]))


(defn chooser [id]
  (fn []    
    [:div {:class "mainselection"}
     [:select {:key id}
      [:option "MCC tree with CONTINUOUS traits"]
      [:option "MCC tree with DISCRETE traits"]]]))
