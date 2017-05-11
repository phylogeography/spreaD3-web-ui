(ns spread-ui.handlers
  (:require [re-frame.core :as re-frame]
            [clojure.walk :as walk]
            [clojure.string :as string]
            [ajax.core :as ajax]
            [spread-ui.db :as db])
  (:import [goog.net XhrIo]))

(re-frame/reg-event-db
 :initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 :set-analysis-type
 (fn [db [_ params]]
   (assoc db :analysis-type (:value params))))

(re-frame/reg-event-db
 :post-continuous-tree
 (fn [db [_ params]]
   (let [file (:file params)
         form-data (doto
                       (js/FormData.)
                     (.append "treefile" file))]
     
     (ajax/POST
      (str "http://localhost:4000" "/continuous/tree")
      {:body form-data
       :response-format (ajax/raw-response-format)
       :handler #(println %1)
       :error-handler #(println %)})
     
     )
   db))
