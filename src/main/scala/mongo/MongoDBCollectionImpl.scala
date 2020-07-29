package mongo

import org.mongodb.scala.MongoCollection
import org.mongodb.scala.bson.BsonDocument

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * @author steve
 */
class MongoDBCollectionImpl[T](collection: MongoCollection[BsonDocument]) extends MongoDBCollection[T] {

    override def find(bsonDocument: BsonDocument): Seq[T] = find(bsonDocument, 0, 0)

    override def find(bsonDocument: BsonDocument, skip: Int, limit: Int): Seq[T] = {
        var res: Seq[T] = Nil
        val future = collection.find(bsonDocument).skip(skip).limit(limit).collect().toFuture()
        val documents = Await.result(future, Duration(3, "s"))
//        documents.foreach(result)
        null
    }
}
