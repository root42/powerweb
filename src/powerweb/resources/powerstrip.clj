(ns powerweb.resources.powerstrip
  (:require noir.response))

(use 'noir.response)

(defn get-all-powerstrips []
  (json
   {
    :_links 
    {
     :self "http://test"
     }
    }
   )
  )

(defn get-powerstrip [ index self-link ] 
  (json
   {
    :_links
    {
     :self self-link
     }
    :index index
    }
   )
  )
