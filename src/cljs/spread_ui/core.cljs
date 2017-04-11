(ns spread-ui.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [re-frisk.core :refer [enable-re-frisk!]]
            [spread-ui.handlers]
            [spread-ui.subscriptions]
            [spread-ui.views :as views]))

(def debug?
  ^boolean js/goog.DEBUG)

(defn dev-setup []
  (when debug?
    (enable-console-print!)
    (enable-re-frisk!)
    (println "Starting in dev mode")))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (dev-setup)
  ;;(re-frame/dispatch-sync [:initialize-db])
 ;; (re-frame/dispatch [:get-config])
  (mount-root))
