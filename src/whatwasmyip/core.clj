(ns whatwasmyip.core
  (:require [environ.core :refer [env]]
            [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))

(defonce addresses-atom (atom {}))

(defn app [{:keys [headers request-method remote-addr uri] :as req}]
  (case request-method
    :get
    {:status 200, :body (get @addresses-atom uri)}

    :post
    (let [remote-addr (get headers "x-forwarded-for" remote-addr)]
      (swap! addresses-atom assoc uri remote-addr)
      {:status 200})

    {:status 404}))

(defn -main [& args]
  (let [file (get env :addresses-file "/tmp/addresses.edn")
        port (Long/parseLong (get env :port "8080"))]
    (reset! addresses-atom
            (try (read-string (slurp file)) (catch Exception _ {})))
    (add-watch addresses-atom nil
               #(try (spit file (pr-str %4)) (catch Exception _)))

    (run-jetty #'app {:port port, :join? false})))
