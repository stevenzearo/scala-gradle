package mongo

import java.time.ZonedDateTime

import com.mongodb.ConnectionString
import conf.Conf
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.{MongoClient, MongoClientSettings, MongoCollection, ReadPreference}

import scala.io.StdIn

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
        testCollection.countDocuments()
            .subscribe(count => {
            println("----------------->>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<----------------------")
            println(s"total count: $count")
        })
        testCollection
            .find(new BsonDocument())
            .collect()
            .subscribe(results => {
                val now = ZonedDateTime.now()
                println(s"size: ${results.size}")
                results.foreach(v => println(v.getString("_id")))
                println(results.mkString)
            })
        val str: String = StdIn.readLine() // for waiting mongodb client thread get result
    }
}
