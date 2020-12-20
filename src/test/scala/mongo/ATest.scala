package mongo

import java.lang.reflect.ParameterizedType

/**
 * @author steve
 */
object ATest {
    def main(args: Array[String]): Unit = {
        val array = Array[TestOBJ]()
        val clazz = array.getClass.getGenericSuperclass.getClass
        println(clazz)
        val fieldType = classOf[TestOBJ].getDeclaredFields.head.getType.getCanonicalName
        println(fieldType)
    }
}
