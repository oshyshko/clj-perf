(defproject clj-perf "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"

  :dependencies [[org.clojure/clojure "1.3.0"]]

  :profiles {:1.3 {}
             :1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]}
             :1.5 {:dependencies [[org.clojure/clojure "1.5.0-master-SNAPSHOT"]]}}

  :aliases {"run-all" ["with-profile" "1.3:1.4" "run"]}

  :main clj-perf.core

  ;:warn-on-reflection true
  )
