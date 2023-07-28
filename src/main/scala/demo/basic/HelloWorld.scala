package demo.basic

;

/**
 * @author Steve Zou
 */

object HelloWorld {
  def main(args: Array[String]): Unit = {
    val d = Dog("Doggy", 5)
    d.run()
    val c = Cat("Tom", 6)
    c.run()
    Cat.stretch(c)
    c.hunting()
  }
}
