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
   (let [content (:content params)
         form-data (doto
                       (js/FormData.)
                     (.append "treefile" content)
                     )]

     #_(-> form-data js->clj prn)

     #_(ajax/ajax-request
        {:uri (str "http://localhost:4000" "/continuous/tree")
         :method :post
         :params {:treefile content}
         ;;:headers {"content-Type" "multipart/form-data"}
         ;; :content-type "multipart/form-data"
         :handler #(println %1)
         :format (ajax/text-request-format)
         :response-format (ajax/json-response-format {:keywords? true})})
     
     #_(ajax/POST
        (str "http://localhost:4000" "/continuous/tree")
        {:multipart-params {:treefile content}
         :format (ajax/text-request-format)
         :handler #(println %1)
         :error-handler #(println %)})
     
     )
   db))
