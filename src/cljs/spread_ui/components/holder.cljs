(ns spread-ui.components.holder
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [spread-ui.components.continuous :refer [continuous-analysis-component]]))

(defn holder [id]
  (fn []    
    (let [analysis-type @(re-frame/subscribe [:analysis-type])]
      [:div {:class "holder"
             :key id}
       (cond
         (= analysis-type "MCC tree with CONTINUOUS traits")
         [continuous-analysis-component]
         (= analysis-type "MCC tree with DISCRETE traits")
         [:p "DISCRETE"]
         :else nil)])))
