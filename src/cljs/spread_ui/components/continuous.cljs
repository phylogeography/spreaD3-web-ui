(ns spread-ui.components.continuous
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            #_[cljs.core.async :refer [put! chan <! >!]])
  #_(:require-macros [cljs.core.async.macros :refer [go go-loop]]))

#_(defn handle-file-reads
    [evt]
    (let [file (-> evt .-target .-result)]
      (re-frame/dispatch [:post-continuous-tree {:file file}])))

(defn handle-tree-upload [evt]
  (let [target (.-currentTarget evt)
        js-file (-> target .-files (aget 0))]
    (do
      (re-frame/dispatch [:post-continuous-tree {:file js-file
                                                 :name (.-name js-file)}])
      (set! (.-value target) ""))))

(defn tree-button []
  (fn []
    [:label {:for "file-upload" :class "custom-file-upload"}
     [:i {:class "btn-upload"}] "Custom Upload"]
    [:input {:class "none"
             :id "file-upload"
             :type "file"
             :on-change #(handle-tree-upload %)}]))

;; TODO: dispatch :set-coordinate
(defn chooser [id values]
  [:select {:key id
            :on-change #(prn %)}
   (map 
    (fn [value]
      [:option {:key value :value value} value])
    values)])

;; TODO: labels, styling
(defn continuous-analysis-component [id]
  (fn []    
    (let [attributes @(re-frame/subscribe [:attributes {:type id}])]
      [:div
       [tree-button]
       (if attributes
         [:div
          [chooser :y-coordinate attributes]
          [chooser :x-coordinate attributes]]

         )

       ])))
