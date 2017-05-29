(ns spread-ui.components.chooser
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(defn handle-selection-change [evt]
  (let [selection (-> evt .-target .-value)]
    (re-frame/dispatch [:set-analysis-type {:value selection}])))

(defn chooser []
  (fn []    
    [:div {:class "chooser"}
     [:select {:on-change #(handle-selection-change %)}
      [:option {:value "MCC tree with CONTINUOUS traits"} "MCC tree with CONTINUOUS traits"]
      [:option {:value "MCC tree with DISCRETE traits"} "MCC tree with DISCRETE traits"]]]))
