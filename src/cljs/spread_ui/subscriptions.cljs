(ns spread-ui.subscriptions
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :analysis-type
 (fn [db [_ params]]
   (get db :analysis-type)))

(re-frame/reg-sub
 :attribute
 (fn [db [_ params]]
   (get-in db [(:type params) (:attribute params)])))
