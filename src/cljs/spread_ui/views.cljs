(ns spread-ui.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [clojure.string :refer [capitalize]]
            [spread-ui.components.chooser :refer [chooser]]
            [spread-ui.components.holder :refer [holder]]))

(defn main-panel []
  (let [analysis-chooser :analysis-chooser
        analysis-holder :analysis-holder]
    (fn []
      [:div {:class "container"}
       [:div {:class "header"} "SpreaD3"]
       [:div {:class "innerContainer"}
        [:div {:class "rowDisplay margin-top"}
         [:p {:class "label"} "Select analysis type:"]]
        [:div {:class "rowDisplay"} [(chooser analysis-chooser)]]
        [:div {:class "rowDisplay"} [:hr {:class "hline"}]]
        [:div {:class "rowDisplay"} [(holder analysis-chooser)]]
        ]
       [:div {:class "footer"} "footer"]])))
