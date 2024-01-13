package demo.basic

import demo.basic.Category._

/**
 * @author Steve Zou
 */
case class Cat(name: String, age: Int) extends Animal {
    override def run(): Unit = {
        println(s"Cat $name, has $age years old, but still running very cautiously!")
    }

    def eatFish(): Unit = {
        println(s"$name is eating a fish!")
    }

    override val category: Category = PET
}

object Cat {
    def stretch(cat: Cat): Unit = {
        println(s"${cat.name}, is stretching its body...")
    }

    implicit def toCat(a: Animal): Option[Cat] = {
        a match {
            case c: Cat => Option(c)
            case _ => Option.empty
        }
    }
}
