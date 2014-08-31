(ns powerweb.middleware
  (:require [taoensso.timbre :as timbre]
            [selmer.parser :as parser]
            [environ.core :refer [env]]
            [selmer.middleware :refer [wrap-error-page]]
            [noir-exception.core
              :refer [wrap-internal-error wrap-exceptions]]))

(defn log-request [handler]
  (fn [req]
    (timbre/debug req)
    (handler req)))

(defn wrap-request-add-self-link [handler]
  (fn add-self-link [{:keys [scheme server-name server-port uri] :as request}]
    (let [link (str (name scheme) "://" server-name ":" server-port uri)]
      (handler (assoc request :self-link link))))
  )

(def development-middleware
  [log-request
   wrap-request-add-self-link
   wrap-error-page
   wrap-exceptions])

(def production-middleware
  [#(wrap-internal-error % :log (fn [e] (timbre/error e)))])

(defn load-middleware []
  (concat (when (env :dev) development-middleware)
          production-middleware))
