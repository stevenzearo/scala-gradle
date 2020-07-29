package mongo

import org.bson.BsonInt32
import org.mongodb.scala.bson.BsonDocument

/**
 * @author steve
 */
object MongoWrapperTest {
    def main(args: Array[String]): Unit = {
        val value: Int = MongoWrapper.intWrapper.get(new BsonDocument().append("a", new BsonInt32(12)), "a")
        println(value)
    }
}
