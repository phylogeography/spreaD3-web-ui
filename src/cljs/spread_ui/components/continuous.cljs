(ns spread-ui.components.continuous
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(defn handle-tree-upload [evt]
  (println "click")
  )

;; https://mrmcc3.github.io/post/csv-with-clojurescript/

;; (defn upload-button [file-name]
;;   [:span.upload-label
;;    [:label
;;     [:input.hidden-xs-up 
;;      {:type "file" :accept ".csv" :on-change #(prn %)}]
;;     [:i.fa.fa-upload.fa-lg]
;;     #_(or file-name "click here to upload and render csv...")]
;;    (when file-name 
;;      [:i.fa.fa-times {:on-click #(prn %)}])])

;; http://jsfiddle.net/4cwpLvae/

(defn tree-button []
  (fn []

    [:label {:for "file-upload" :class "custom-file-upload"}
     [:i {:class "btn-upload"}] "Custom Upload"
     ]
    [:input {:class "none" :id "file-upload" :type "file"}]
    
    #_ [:input {:class "btn-upload"
                :type "file"
                :on-change #(handle-tree-upload %)}]
    ))

(defn continuous-analysis-component []
  (fn []    
    [:p "CONTINUOUS"]
    #_    [upload-button]
    [tree-button]
    
    ))
