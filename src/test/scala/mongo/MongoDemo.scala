package mongo

import com.mongodb.ConnectionString
import conf.Conf
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.{MongoClient, MongoClientSettings, MongoCollection, ReadPreference}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * @author steve
 */
object MongoDemo {
    def main(args: Array[String]): Unit = {
        Conf.loadProperties("conf.properties")
        val mongoURI: String = Conf.getProperty("mongo.uri")
        val connectionString = new ConnectionString(mongoURI)
        val settings = MongoClientSettings
            .builder()
            .applyConnectionString(connectionString)
            .readPreference(ReadPreference.primary())
            .build()
        val mongoClient: MongoClient = MongoClient.apply(settings)
        val testDatabase = mongoClient.getDatabase("video")
        println(testDatabase.name)

        val testCollection: MongoCollection[BsonDocument] =
            testDatabase.getCollection[BsonDocument]("my_movies")
        println(testCollection.namespace)

        val future = testCollection.find(new BsonDocument()).collect().toFuture()
        val documents = Await.result(future, Duration(3, "s"))

        println(s"size: ${documents.size}")
        documents.foreach(v => println(v.getString("_id")))
        println(documents.mkString)
    }
}
