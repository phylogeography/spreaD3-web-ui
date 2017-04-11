(ns spread-ui.handlers
  (:require [re-frame.core :as re-frame]
            [clojure.walk :as walk]
            [clojure.string :as string]
            [ajax.core :refer [GET POST ajax-request detect-response-format text-request-format text-response-format]]
            [spread-ui.db :as db])
  (:import [goog.net XhrIo]))

 
