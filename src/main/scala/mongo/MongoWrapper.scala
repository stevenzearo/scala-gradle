package mongo

import org.mongodb.scala.bson.{BsonArray, BsonDocument}

/**
 * @author steve
 */
abstract class MongoWrapper {
    def get(bson: BsonDocument)(key: String): Any
}

abstract class BasicMongoWrapper extends MongoWrapper {
    def get(bson: BsonDocument)(key: String): AnyVal
}


abstract class RefMongoWrapper extends MongoWrapper {
    def get(bson: BsonDocument)(key: String): AnyRef
}

object IntMongoWrapper extends BasicMongoWrapper {
    override def get(bson: BsonDocument)(key: String): Int = {
        bson.getInt32(key).getValue
    }
}

object LongMongoWrapper extends BasicMongoWrapper {
    override def get(bson: BsonDocument)(key: String): Long = {
        bson.getInt64(key).getValue
    }
}

object DoubleMongoWrapper extends BasicMongoWrapper {
    override def get(bson: BsonDocument)(key: String): Double = {
        bson.getDouble(key).getValue
    }
}

object BooleanMongoWrapper extends BasicMongoWrapper {
    override def get(bson: BsonDocument)(key: String): Boolean = {
        bson.getBoolean(key).getValue
    }
}

object DateTimeMongoWrapper extends BasicMongoWrapper {
    override def get(bson: BsonDocument)(key: String): Long = {
        bson.getDateTime(key).getValue
    }
}

object TimeStampMongoWrapper extends BasicMongoWrapper {
    override def get(bson: BsonDocument)(key: String): Long = {
        bson.getTimestamp(key).getValue
    }
}

object StringMongoWrapper extends RefMongoWrapper {
    override def get(bson: BsonDocument)(key: String): String = {
        bson.getString(key).getValue
    }
}

object ArrayMongoWrapper extends RefMongoWrapper {
    override def get(bson: BsonDocument)(key: String): BsonArray = {
        bson.getArray(key)
    }
}

object DocumentMongoWrapper extends RefMongoWrapper {
    override def get(bson: BsonDocument)(key: String): BsonDocument = {
        bson.getDocument(key)
    }
}