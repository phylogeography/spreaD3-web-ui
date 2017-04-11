(ns spread-ui.system
  (:gen-class)
  (:require [spread-ui.server :as server]
            [clojure.string :as string]
            [clojure.tools.logging :as logging]
            [environ.core :refer [env]]))

(defn create-system
  "Returns a new instance of the whole application. No side effects."
  []
  (logging/info "Initializing spread-ui...")
  {:handlers (server/create-app)
   :server-config (get env :server-config)})

(defn start-server [system]
  (server/create-and-start (:handlers system)
                           (:server-config system)))

(defn start
  "Performs side effects to initialize the system, acquire resources,
  and start it running. Returns an updated instance of the system."
  [system]
  (logging/info "Starting spread-ui...")
  (into system {:server-connection (start-server system)}))

(defn stop-server [system]
  (when (:server-connection system)
    (server/stop (:server-connection system)))
  (dissoc system :server-connection))

(defn stop
  "Performs side effects to shut down the system and release its
  resources. Returns an updated instance of the system."
  [system]
  (logging/info "Stopping spread-ui...")
  (-> system (stop-server)))

(defn -main [& args]
  (let [system (create-system)]
    (start system)
    (logging/info "Systems functional")))
