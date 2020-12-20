package stopwatch

/**
 * @author steve
 */
object StopWatchTest {
    def main(args: Array[String]): Unit = {
        val execNano = StopWatch.watch(() => {
            Thread.sleep(1000)
        })
        println(execNano)
    }
}
