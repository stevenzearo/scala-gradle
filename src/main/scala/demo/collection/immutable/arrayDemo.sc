val arr = new Array[Int](4)
arr.update(0, 9)
val arr2 = arr :+ 1 // append
arr
val arr3 = 2 +: arr2 // prepend
val arr4 = arr :++ Array(1, 2, 3) // appendAll
val arr5 = arr ++: Array(1, 2, 3) // prependAll
arr5(6)
arr5(7)
