package mongo

import org.mongodb.scala.bson.{BsonArray, BsonDocument}

/**
 * @author steve
 */
abstract class MongoWrapper[T <: Any] {
    def get(bson: BsonDocument, key: String): T
}

object MongoWrapper {
    val intWrapper: MongoWrapper[Int] = IntMongoWrapper
    val longWrapper: MongoWrapper[Long] = LongMongoWrapper
    val doubleWrapper: MongoWrapper[Double] = DoubleMongoWrapper
    val booleanWrapper: MongoWrapper[Boolean] = BooleanMongoWrapper
    val dateTimeWrapper: MongoWrapper[Long] = DateTimeMongoWrapper
    val timeStampWrapper: MongoWrapper[Long] = TimeStampMongoWrapper
    val stringWrapper: MongoWrapper[String] = StringMongoWrapper
    val arrayMapper: MongoWrapper[BsonArray] = ArrayMongoWrapper
    val documentMapper: MongoWrapper[BsonDocument] = DocumentMongoWrapper
}

private abstract class BasicMongoWrapper[T <: AnyVal] extends MongoWrapper[T] {
    def get(bson: BsonDocument, key: String): T
}


private abstract class RefMongoWrapper[T <: AnyRef] extends MongoWrapper[T] {
    def get(bson: BsonDocument, key: String): T
}

private object IntMongoWrapper extends BasicMongoWrapper[Int] {
    def get(bson: BsonDocument, key: String): Int = {
        bson.getInt32(key).getValue
    }
}

private object LongMongoWrapper extends BasicMongoWrapper[Long] {
    override def get(bson: BsonDocument, key: String): Long = {
        bson.getInt64(key).getValue
    }
}

private object DoubleMongoWrapper extends BasicMongoWrapper[Double] {
    override def get(bson: BsonDocument, key: String): Double = {
        bson.getDouble(key).getValue
    }
}

private object BooleanMongoWrapper extends BasicMongoWrapper[Boolean] {
    override def get(bson: BsonDocument, key: String): Boolean = {
        bson.getBoolean(key).getValue
    }
}

private object DateTimeMongoWrapper extends BasicMongoWrapper[Long] {
    override def get(bson: BsonDocument, key: String): Long = {
        bson.getDateTime(key).getValue
    }
}

private object TimeStampMongoWrapper extends BasicMongoWrapper[Long] {
    override def get(bson: BsonDocument, key: String): Long = {
        bson.getTimestamp(key).getValue
    }
}

private object StringMongoWrapper extends RefMongoWrapper[String] {
    override def get(bson: BsonDocument, key: String): String = {
        bson.getString(key).getValue
    }
}

private object ArrayMongoWrapper extends RefMongoWrapper[BsonArray] {
    override def get(bson: BsonDocument, key: String): BsonArray = {
        bson.getArray(key)
    }
}

private object DocumentMongoWrapper extends RefMongoWrapper[BsonDocument] {
    override def get(bson: BsonDocument, key: String): BsonDocument = {
        bson.getDocument(key)
    }
}