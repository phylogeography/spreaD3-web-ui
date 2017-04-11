(ns spread-ui.browser
  (:require [doo.runner :refer-macros [doo-tests]]
            [spread-ui.handlers-tests]))

(enable-console-print!)
(doo-tests 'spread-ui.handlers-tests)
