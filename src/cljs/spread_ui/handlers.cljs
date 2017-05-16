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
 :set-attributes
 (fn [db [_ params]]
   (assoc-in db [(:type params) :attributes] (:value params))))

(defn get-attributes
  []
  (ajax/GET
   (str "http://localhost:4000" "/continuous/attributes")
   {:response-format (ajax/json-response-format)
    :handler #(re-frame/dispatch [:set-attributes {:type :continuous
                                                   :value (js->clj %)}])
    :error-handler #(println %)}))

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
       :handler get-attributes
       :error-handler #(println %)}))
   db))
