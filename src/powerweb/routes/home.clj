(ns powerweb.routes.home
  (:require [compojure.core :refer :all]
            [powerweb.layout :as layout]
            [powerweb.util :as util]
            [powerweb.resources.powerstrip :as powerstrip]
            )
  )

(defn home-page []
  (layout/render
    "home.html" {:content (util/md->html "/md/docs.md")}))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/powerstrip" []
       (noir.response/content-type "application/hal+json"
                                   (powerstrip/get-all-powerstrips)))
  (GET "/powerstrip/:index" [ index :as {:keys [self-link]}]
       (noir.response/content-type "application/hal+json"
                                   (powerstrip/get-powerstrip index self-link)))
)
