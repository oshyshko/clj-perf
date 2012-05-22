# clj-perf

A small demo project that shows performance degradation of PersistentHashMap
between Clojure versions 1.3 and 1.4.

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

Running this code with Clojure 1.3 and 1.4 yields some interesting output (depends on your machine).

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



It seems that the new PersistentHashMap.valAt(...) from 1.4 eats more CPU.
According to profiler report it spends more cycles in own time (method body, 3208ms --> 3917ms)
+ further calls to Uitl.hasheq(...) (900ms --> 2684ms).

These numbers are relative and will depend on actual configuration but
there is a recurring pattern that the code runs longer with Clojure 1.4 and more CPU agressive.


## License

Copyright © 2012 Oleksandr Shyshko

Distributed under the Eclipse Public License, the same as Clojure.
