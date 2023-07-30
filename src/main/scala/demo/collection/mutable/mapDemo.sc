import scala.collection.mutable

val m = mutable.HashMap[String, Int]("a" -> 1, "b" -> 3, "b" -> 2)
m.put("c", 3)
m
m += ("d" -> 4)
m
m.remove("d")
m
m.get("a")
m.addAll(Array("e" -> 7, "f" -> 9))
m.foreachEntry((k, v) => println(s"$k: $v"))