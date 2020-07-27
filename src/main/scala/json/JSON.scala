package json

/**
 * @author steve
 */
object JSON {
    def toJSON[T](entity: T): String = {
        ""
    }

    def fromJSON[T](jsonStr: String): T = {
        val constructors = try classOf[T].getDeclaredConstructor() catch {
            case e: NoSuchMethodException | SecurityException => throw new NoPublicDefaultConstructorException(e)
        }
        constructors.newInstance()
    }
}
