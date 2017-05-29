(ns spread-ui.components.holder
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [spread-ui.components.continuous :refer [continuous-analysis-component]]))

(defn holder []
  (fn []    
    (let [continuous-analysis-id :continuous
          analysis-type @(re-frame/subscribe [:analysis-type])]
      [:div {:class "holder"}
       (cond
         (= analysis-type "MCC tree with CONTINUOUS traits")
         [(continuous-analysis-component continuous-analysis-id)]
         (= analysis-type "MCC tree with DISCRETE traits")
         [:p "DISCRETE"]
         :else nil)])))
