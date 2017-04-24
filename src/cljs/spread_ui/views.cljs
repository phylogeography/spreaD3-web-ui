(ns spread-ui.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [clojure.string :refer [capitalize]]
            [spread-ui.components.chooser :refer [chooser]]))

(defn main-panel []
  (let [analysis-chooser :analysis-chooser]
    (fn []
      [:div {:class "container"}
       [:div {:class "header"} "SpreaD3"]
       [:div {:class "innerContainer"}
        [:div {:class "rowDisplay margin-top"}
         [:p {:class "label"} "Select analysis type:"]]
        [:div {:class "rowDisplay"} [(chooser analysis-chooser)]]
        [:div {:class "rowDisplay"} [:hr {:class "hline"}]]
        ]
       [:div {:class "footer"} "footer"]])))
