package demo.basic

/**
 * @author Steve Zou
 */

object PartialAppliedFunctionDemo {
  def reduce(a: Int, b: Int, op: (Int, Int) => Int): Int = op(a, b)

  def reduceV2(a: Int, b: Int, op: (Int, Int) => Int)(op2: Int => Int): Int = op2(reduce(a, b, op))

  def curringReduce(a: Int)(b: Int)(op: (Int, Int) => Int): Int = op(a, b)

  def main(args: Array[String]): Unit = {
    val op = (a: Int, b: Int) => a * b
    val f1: Int => ((Int, Int) => Int) => Int = curringReduce(1)
    val f2: ((Int, Int) => Int) => Int = f1(2)
    val res2: Int = f2(op)
    println(res2) // 1 * 2, output: 2

    val f3: (Int => Int) => Int = reduceV2(1, 2, op)
    val res3 = f3((a: Int) => a * 2)
    println(res3) // (1 * 2) * 2, output: 4
  }
}