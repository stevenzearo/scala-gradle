
val ls = List(1, 2, 3, 4)
val ls2 = ls.updated(0, 2)
ls.head
ls.tail
ls.take(1)
val ls3 = ls.reverse
val sum = ls.sum
val product = ls.product
ls
ls.head
ls.tail
ls.appended()
val ls4 = 1::2::3::Nil
ls4.head
val ls5 = 4::ls4
val ls6 = 6::Nil ::: ls4
ls6.drop(1)
ls6.dropRight(1)