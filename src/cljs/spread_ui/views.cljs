(ns spread-ui.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [clojure.string :refer [capitalize]]))

(defn main-panel []
  (let []
    (fn []
      [:div {:class "container"}
       [:div {:class "header"} "Spread"]
       [:div {:class "innerContainer"}
        [:div {:class "rowDisplay margin-top"} [:p "SPREAD"]]

        ]
       [:div {:class "footer"} "footer"]])))
