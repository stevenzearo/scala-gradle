package mongo

import org.mongodb.scala.bson.BsonDocument

/**
 * @author steve
 */
trait Collection[T] {
    def find(bsonDocument: BsonDocument): Seq[T]
}
