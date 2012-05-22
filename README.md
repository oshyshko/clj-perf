# clj-perf

It seems there is a 30-40% performance degradation of PersistentHashMap.valAt(...) in Clojure 1.4.
Possibly due to references to new CPU-hungry implementation of Util.hasheq(...).

Consider the following snippet:

```clj
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
```

Running this code with Clojure 1.3 and 1.4 yields some interesting output (actual numbers depend on your machine).

```
$ lein run-all

...

Version:  1.3.0
"Elapsed time: 3549.974 msecs"
"Elapsed time: 3613.319 msecs"
"Elapsed time: 3350.744 msecs"

...

Version:  1.4.0
"Elapsed time: 6007.356 msecs"
"Elapsed time: 5926.243 msecs"
"Elapsed time: 5756.16 msecs"
```

Playing with a profiler leads to PersistentHashMap.

Clojure 1.3
![image](https://github.com/oshyshko/clj-perf/raw/master/doc/clj_13.png)

Clojure 1.4
![image](https://github.com/oshyshko/clj-perf/raw/master/doc/clj_14.png)

It seems that the new PersistentHashMap.valAt(...) from 1.4 eats more CPU.
According to profiler report it spends more cycles in own time **3208ms --> 3917ms**, plus,
further calls to new Util.hasheq(...) **900ms --> 2684ms**.

## License

Copyright © 2012 Oleksandr Shyshko

Distributed under the Eclipse Public License, the same as Clojure.
