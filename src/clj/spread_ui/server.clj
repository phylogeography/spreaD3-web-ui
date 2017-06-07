(ns spread-ui.server
  (:require [com.stuartsierra.component :as component]
            [config.core :as config]
            [clj-json.core :as json]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.format :as middleware-format]
            [ring.middleware.cors :as cors]
            [compojure.route :as route]
            [compojure.core :as compojure :refer [GET POST defroutes]]
            [ring.util.response :as response]
            [clojure.tools.logging :as logging]))

;; macros

(defmacro with-err-str
  "Evaluates exprs in a context in which *err* is bound to a fresh
  StringWriter.  Returns the string created by any nested printing
  calls."
  [& body]
  `(let [s# (new java.io.StringWriter)]
     (binding [*err* s#]
       ~@body
       (str s#))))

;; global variables

(def http-internal-server-error 500)
(def internal-server-error "Internal Server Error")

(defn make-response
  [http-status body]
  (-> (response/response body)
      (response/status http-status)))

(defn wrap-exception [handler]
  (fn [request]
    (try (handler request)
         (catch Exception e
           (let [stack-trace (with-err-str (.printStackTrace e))]
             (logging/error stack-trace)
             (make-response http-internal-server-error internal-server-error))))))

(defn wrap-json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defn serve-index [_]
  (response/resource-response "index.html" {:root "public"}))

(defn serve-favicon [_]
  (response/resource-response "favicon.ico" {:root "public"}))

(defn serve-config [_]    
  (json/generate-string (get config/env :server-config)))

(defroutes all-routes
  (GET "/" [] serve-index)
  (GET "/favicon.ico" [] serve-favicon)
  (GET "/config" [] serve-config)
  (route/resources "/")
  (route/not-found "Page not found"))

(def handlers
  (-> all-routes      
      wrap-exception
      (cors/wrap-cors :access-control-allow-origin [#".*"]
                      :access-control-allow-methods [:post :get])))

#_(defn create-app []
    handlers)

(defrecord Server [host port server]
  component/Lifecycle
  (start [this]
    (logging/info "Starting Server")
    (let [server (jetty/run-jetty handlers {:host host :port port :join? false})]
      (assoc this :server server)))
  (stop [this]
    (logging/info "Stopping Server")
    (.stop server)
    (assoc this :server nil)))

(defn new-server
  [{:keys [host port]}]
  (map->Server {:host host :port port}))
