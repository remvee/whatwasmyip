(defproject whatwasmyip "0.1.0-SNAPSHOT"
  :main whatwasmyip.core
  :description "Record IP address on POST, return it on GET."
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [environ "1.0.0"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]]

  :uberjar-name "whatwasmyip.jar"
  :profiles {:uberjar {:aot :all}})
