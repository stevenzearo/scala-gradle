package json

/**
 * @author steve
 */
class JSON {
    def toJSON[T](entity: T): String = {
        ""
    }

    def fromJSON[T](jsonStr: String, clazz: Class[T]): T = {
        val constructors = try clazz.getDeclaredConstructor() catch {
            case e: Exception if e.isInstanceOf[NoSuchMethodException] | e.isInstanceOf[SecurityException] => throw new NoPublicDefaultConstructorException(e)
        }
        constructors.newInstance()
    }
}