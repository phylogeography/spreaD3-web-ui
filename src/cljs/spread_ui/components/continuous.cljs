(ns spread-ui.components.continuous
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

;; handlers

(defn handle-tree-upload [evt]
  (let [target (.-currentTarget evt)
        js-file (-> target .-files (aget 0))]
    (do
      (re-frame/dispatch [:post-continuous-tree {:file js-file
                                                 :name (.-name js-file)}])
      (set! (.-value target) ""))))


(defn handle-coordinate-change [analysis-id coordinate-id evt]
  (re-frame/dispatch [:set-attribute {:type analysis-id
                                      :attribute coordinate-id
                                      :value (-> evt .-target .-value)}]))


(defn toggle-external-annotations [analysis-id evt]
  (let [attribute-id :external-annotations
        current-value  @(re-frame/subscribe [:attribute {:type analysis-id
                                                         :attribute attribute-id}])]
    (re-frame/dispatch [:set-attribute {:type analysis-id
                                        :attribute attribute-id
                                        :value (not current-value)}])))

;; views

(defn tree-button []
  (fn []
    [:label {:for "file-upload" :class "custom-file-upload"}
     [:i {:class "btn-upload"}] "Custom Upload"]
    [:input {:class "none"
             :id "file-upload"
             :type "file"
             :on-change #(handle-tree-upload %)}]))



(defn coordinates-chooser [analysis-id coordinate-id values]
  [:select {:on-change #(handle-coordinate-change analysis-id coordinate-id %)}
   (map 
    (fn [value]
      [:option {:key value :value value} value])
    values)])


(defn external-annotations-checkbox [analysis-id]
  [:input {:type "checkbox"
           :on-change #(toggle-external-annotations analysis-id %)}])

;; TODO: labels, styling
(defn continuous-analysis-component [id]
  (fn []    
    (let [y-coordinate-id :y-coordinate
          x-coordinate-id :x-coordinate
          attributes @(re-frame/subscribe [:attribute {:type id
                                                       :attribute :attributes}])
          y-coordinate @(re-frame/subscribe [:attribute {:type id
                                                         :attribute y-coordinate-id}])
          x-coordinate @(re-frame/subscribe [:attribute {:type id
                                                         :attribute x-coordinate-id}])]
      [:div
       [tree-button]
       (if attributes
         [:div
          [coordinates-chooser id y-coordinate-id attributes]
          [coordinates-chooser id x-coordinate-id attributes]])
       ;; TODO if both coords set display rest
       (if (and y-coordinate x-coordinate)

         [external-annotations-checkbox id]
         
         )])))
