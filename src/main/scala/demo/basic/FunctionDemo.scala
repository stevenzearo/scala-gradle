package demo.basic


/**
 * @author Steve Zou
 */
object FunctionDemo {
  def reduce(a: Int, b: Int, op: (Int, Int) => Int): Int = op(a, b)

  def opChain(a: Int, b: Int, ops: (Int, Int) => Int*): Int = {
    assert(ops.nonEmpty)
    var res: Int = a
    for (op <- ops) {
      res = op(res, b)
    }
    res
  }

  def opChainCombine(ops: (Int, Int) => Int*): Option[(Int, Int) => Int] = {
    if (ops.isEmpty) return Option.empty[(Int, Int) => Int]
    Option(ops.reduce((op1: (Int, Int) => Int, op2: (Int, Int) => Int) => { (a: Int, b: Int) => op1(a, op2(a, b)) }))
  }

  def fib(n: Int): Int = if (n <= 1) 1 else fib(n - 1) + fib(n - 2)

  def main(args: Array[String]): Unit = {
    val op1: (Int, Int) => Int = (x, y) => x + y
    val result = reduce(2, 3, op1)
    println(result)

    val op2: (Int, Int) => Int = (x, y) => x * y
    val result2 = reduce(2, 3, op2)
    println(result2)

    val result3 = opChain(2, 3, op1, op2) // (2+3)*3
    println(result3)

    val chain = opChainCombine(op1, op2) // 2 + (2 * 3)
    if (chain.nonEmpty) {
      val op3 = chain.get
      val res4 = op3(2, 3)
      println(res4)
    }

    println(fib(10))
  }
}
