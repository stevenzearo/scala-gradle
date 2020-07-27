package mongo
import org.mongodb.scala.bson.BsonDocument

/**
 * @author steve
 */
class CollectionImpl[T] extends Collection[T] {


    override def find(bsonDocument: BsonDocument): Seq[T] = {
        null
    }
}
