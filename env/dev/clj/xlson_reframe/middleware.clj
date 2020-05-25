(ns xlson-reframe.middleware
  (:require
   [ring.middleware.content-type :refer [wrap-content-type]]
   [ring.middleware.params :refer [wrap-params]]
   [ring.middleware.cookies :refer [wrap-cookies]]
   [prone.middleware :refer [wrap-exceptions]]
   [ring.middleware.session.memory :as memory]
   [ring.middleware.reload :refer [wrap-reload]]
   [ring.middleware.multipart-params :refer [wrap-multipart-params]]
   [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
   [ring.middleware.session :refer [wrap-session]]
   [ring.middleware.params :refer [wrap-params]]
   [ring.middleware.session.cookie :refer [cookie-store]]
   [ring.middleware.defaults :refer [site-defaults wrap-defaults]]))

(def store (memory/memory-store))

(def middleware
  [#(wrap-defaults % site-defaults)
   wrap-exceptions
   #(wrap-session % {:cookie-attrs {:max-age 3600}
                  :store (cookie-store {:key "ahY9poQuaghahc7I"})})])
