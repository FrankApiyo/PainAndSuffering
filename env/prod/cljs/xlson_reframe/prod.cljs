(ns xlson-reframe.prod
  (:require [xlson-reframe.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
