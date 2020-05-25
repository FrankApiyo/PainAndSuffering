(ns xlson-reframe.handler
  (:require
   [reitit.ring :as reitit-ring]
   [xlson-reframe.middleware :refer [middleware]]
   [reitit.http.interceptors.multipart :as multipart]
   [hiccup.page :refer [include-js include-css html5]]
   [config.core :refer [env]]
   [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
   [clojure.java.io :as io]
   [cheshire.core :refer [generate-string]]
   [ring.util.response :refer [redirect]])
  (:import [java.io File]))
(def mount-target
  [:div#app
   [:h2 "Welcome to xlson-reframe"]
   [:p "please wait while Figwheel is waking up ..."]
   [:p "(Check the js console for hints if nothing exciting happens.)"]])

(defn head []
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css (if (env :dev) "/css/site.css" "/css/site.min.css"))])

(defn loading-page []
  (html5
   (head)
   [:body {:class "body-container"}
    mount-target
    (include-js "/js/app.js")]))

(def resource-path "/tmp/")

(defn file-path [path & [filename]]
  (java.net.URLDecoder/decode
   (str path File/separator filename)
   "utf-8"))

(defn upload-file
  "uploads a file to the target folder"
  [path {:keys [tempfile size filename]}]
  (with-open [in (io/input-stream tempfile)
              out (io/output-stream (file-path path filename))]
    (io/copy in out)))

(defn index-handler
  [request]
  (println "do we get to the index handler" request)
  (println "anti")
  {:status 200
   :headers {"Content-Type" "text/html"}
   :cookies {:anti-forgery-token (:anti-forgery-token request)
             :test-cookie (generate-string *anti-forgery-token*)}
   :body (loading-page)})


(def app
  (reitit-ring/ring-handler
   (reitit-ring/router
    [["/" {:get index-handler
           :post {:parameters {:multipart {:file multipart/temp-file-part}}
                  :handler (fn [{{{:keys [file]} :multipart :as m} :parameters}]
                             (println "multipart: " m)
                             (println "file" file)
                             (upload-file resource-path file)
                             (redirect "/"))}}]
     ["/email" {:post (fn [email] "ok")}]
     ["/files/:filename" {:get (fn [{{:keys [filename]} :path-params}])}]])
   (reitit-ring/routes
    (reitit-ring/create-resource-handler {:path "/" :root "/public"})
    (reitit-ring/create-default-handler))
   {:middleware middleware}))
