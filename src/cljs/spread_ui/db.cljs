(ns spread-ui.db)

;; In debug mode backend server runs on a separate port
;; needs to be hardcoded
;; In prod mode all urls are relative (backend servers the frontend)
(def backend-uri (atom nil))

(def default-db
  {:analysis-type "MCC tree with CONTINUOUS traits"    
   :continuous {:attributes nil
                :y-coordinate nil
                :x-coordinate nil
                :external-annotations nil
                :hpd-level nil
                :mrsd nil
                :timescale-multiplier nil}
   :discrete {}})
