package demo.collection

/**
 * @author Steve Zou
 */
object WordCountDemo {
  def main(args: Array[String]): Unit = {
    val words = Array("hello   world", "hello howard", "how are you", "are you ok", "you looks good today", "how do you do today")
    Ordered.orderingToOrdered()
    Ordering.String.reversed()
    val wordsCount = words.map(_.split("\\s+"))
      .flatMap(_.iterator)
      .map((_, 1))
      .groupBy(_._1)
      .map(wordFreq => (wordFreq._1, wordFreq._2.map(_._2).sum))
      .toArray
      .sortBy(-_._2)
    wordsCount.foreach(tup => println(s"word: ${tup._1}, count:${tup._2}"))
    /*
    word: you, count:4
    word: are, count:2
    word: how, count:2
    word: hello, count:2
    word: today, count:2
    word: do, count:2
    word: looks, count:1
    word: ok, count:1
    word: good, count:1
    word: world, count:1
    word: howard, count:1
    * */
  }
}
