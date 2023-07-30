import scala.collection.mutable

val s = mutable.HashSet(2, 3, 3, 5)
s.add(2)
s
s.add(4)
s
s.remove(5)
s