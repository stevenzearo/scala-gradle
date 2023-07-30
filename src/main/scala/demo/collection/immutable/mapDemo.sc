val m = Map("a" -> 1, "b" -> 2, "b" -> 3)
m + ("c" -> 3)
m + ("b" -> 4)
m - "a"
m.foreachEntry((k, v) => println(s"$k: $v"))
m