package demo.basic

import demo.basic.Category._

import scala.language.implicitConversions


trait AnimalSkill {
  var a = 1
  val category: Category

  def run(): Unit

  def hunting(): Unit = {
    category match {
      case PET => println("Pet no need hunting")
      case WILDLIFE => println("Wildlife need hunting for live")
    }
  }
}

abstract class Animal extends AnimalSkill
