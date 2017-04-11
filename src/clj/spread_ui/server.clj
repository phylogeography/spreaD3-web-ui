(ns spread-ui.server
  (:require [clj-json.core :as json]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.format :refer [wrap-restful-format]]
            [ring.middleware.cors :as cors]
            [compojure.route :as route]
            [compojure.core :refer [GET POST routes defroutes context]]
            [ring.util.response :as response]
            [clojure.repl :refer [pst]]
            [environ.core :refer [env]]
            [clojure.tools.logging :as logging]))
 
(def http-internal-server-error 500)
(def internal-server-error "Internal Server Error")

(defn make-response
  [http-status body]
  (-> (response/response body)
      (response/status http-status)))

(defmacro with-err-str
  "Evaluates exprs in a context in which *err* is bound to a fresh
  StringWriter.  Returns the string created by any nested printing
  calls."
  [& body]
  `(let [s# (new java.io.StringWriter)]
     (binding [*err* s#]
       ~@body
       (str s#))))

(defn wrap-exception [handler]
  (fn [request]
    (try (handler request)
         (catch Exception e
           (let [stack-trace (with-err-str (pst e 36))]
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
  (json/generate-string (get env :server-config)))

(defroutes all-routes
  (GET "/" [] serve-index)
  (GET "/favicon.ico" [] serve-favicon)
  (GET "/config" [] serve-config)
  (route/resources "/")
  (route/not-found "Page not found"))

(def app-handlers
  (-> all-routes      
      wrap-exception
      (cors/wrap-cors :access-control-allow-origin [#".*"]
                      :access-control-allow-methods [:post :get])))

(defn create-app []
  app-handlers)

(defn create-and-start
  [handlers server-config]
  (let [port (:port server-config) host (:host server-config)]
    (jetty/run-jetty handlers {:port port :join? false :host host})))

(defn stop
  [server-connection]
  (.stop server-connection))
