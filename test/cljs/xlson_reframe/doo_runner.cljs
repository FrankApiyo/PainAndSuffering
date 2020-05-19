(ns xlson-reframe.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [xlson-reframe.core-test]))

(doo-tests 'xlson-reframe.core-test)
