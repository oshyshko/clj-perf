(ns clj-perf.core)

(defn -main
[& args]

(println)
(println "Version: " (clojure-version))

(def mm 10000)

(def str-keys (map str (range mm)))
(def m (zipmap str-keys (range mm)))
(time (dotimes [i mm] (doseq [k str-keys] (m k))))

(def kw-keys (map #(keyword (str %)) (range mm)))
(def m (zipmap kw-keys (range mm)))
(time (dotimes [i mm] (doseq [k kw-keys] (m k))))

(def sym-keys (map #(symbol (str %)) (range mm)))
(def m (zipmap sym-keys (range mm)))
(time (dotimes [i mm] (doseq [k sym-keys] (m k))))

(def vec-keys (map (comp (juxt keyword symbol identity) str) (range mm)))
(def m (zipmap vec-keys (range mm)))
(time (dotimes [i mm] (doseq [k vec-keys] (m k))))

(println))
