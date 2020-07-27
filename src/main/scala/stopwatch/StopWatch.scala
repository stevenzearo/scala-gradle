package stopwatch

/**
 * @author steve
 */
object StopWatch {
    def watch[T](function: () => Any): Long = {
        val start = System.currentTimeMillis()
        function.apply()
        val end = System.currentTimeMillis()
        end - start
    }
}
