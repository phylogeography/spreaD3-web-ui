(ns spread-ui.components.continuous
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [cljs.core.async :refer [put! chan <! >!]])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))

(defn handle-file-reads
  [evt]
  (let [file (-> evt .-target .-result)]
    (re-frame/dispatch [:post-continuous-tree {:file file}])))

(defn handle-tree-upload [evt]
  (let [target (.-currentTarget evt)
        file (-> target .-files (aget 0))
        reader (js/FileReader.)]

    (re-frame/dispatch [:post-continuous-tree {:file file
                                               :name (.-name file)}])
    
    (set! (.-value target) "")
    #_(set! (.-onload reader) #(handle-file-reads %))
    #_(.readAsText reader file)))

(defn tree-button []
  (fn []
    [:label {:for "file-upload" :class "custom-file-upload"}
     [:i {:class "btn-upload"}] "Custom Upload"]
    [:input {:class "none"
             :id "file-upload"
             :type "file"
             :on-change #(handle-tree-upload %)}]
    
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
