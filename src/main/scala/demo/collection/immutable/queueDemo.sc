import scala.collection.immutable.Queue

val q1 = Queue(1, 2, 3)
val q2 = q1.enqueue(4)
val tup1 = q2.dequeue
q1
q2