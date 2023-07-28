package demo.basic

/**
 * @author Steve Zou
 */

object PartialFunctionDemo {
  def main(args: Array[String]): Unit = {
    val f: PartialFunction[Int, Int] = {
      case x if x >= 3 => x * x
      case x if x <= -3 => x * 2
    }

    val r1 = f(3)
    println(r1) // output: 9
    val r2 = f(-3)
    println(r2) // output: -6
    val r3 = f(2)
    println(r3) // MatchError
  }
}