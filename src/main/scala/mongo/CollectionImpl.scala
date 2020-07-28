package mongo

import org.mongodb.scala.MongoCollection
import org.mongodb.scala.bson.BsonDocument

/**
 * @author steve
 */
class CollectionImpl[T](collection: MongoCollection[BsonDocument]) extends Collection[T] {

    override def find(bsonDocument: BsonDocument): Seq[T] = find(bsonDocument, 0, 0)

    override def find(bsonDocument: BsonDocument, skip: Int, limit: Int): Seq[T] = {
        null
    }

    def findAsync(bsonDocument: BsonDocument, skip: Int, limit: Int): Seq[T] = {
        var res: Seq[T] = Nil
        res :+ null


        res
    }

}
