package mongo

import org.mongodb.scala.bson.BsonDocument

/**
 * @author steve
 */
trait MongoDBCollection[T] {
    var notCompleted: Boolean = false
    def find(bsonDocument: BsonDocument): Seq[T]
    def find(bsonDocument: BsonDocument, skip: Int, limit: Int): Seq[T]
}
