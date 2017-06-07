(ns spread-ui.system
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [config.core :as config]
            [spread-ui.server :as server]
            [clojure.string :as string]
            [clojure.tools.logging :as logging]))

(defn system-map []
  (component/system-map :server (server/new-server (get config/env :server-config))))

(def dependency-map
  ;; List systems inter-dependencies in either:
  ;;    {:key [:dependency1 :dependency2]} form
  ;; or
  ;;    {:key {:name-arg1 :dependency1
  ;;           :name-arg2 :dependency2}} form
  {})

(defn create-system
  []
  (component/system-using (system-map)
                          dependency-map))

(defn start
  "Performs side effects to initialize the system, acquire resources,
  and start it running. Returns an updated instance of the system."
  [system]
  (logging/info "Starting system")
  (component/start system))

(defn stop
  "Performs side effects to shut down the system and release its
  resources. Returns an updated instance of the system."
  [system]
  (logging/info "Stopping system")
  (component/stop system))

(defn -main [& args]
  (let [system (create-system)]
    (start system)))
