package mongo

import java.text.SimpleDateFormat

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

class IntMongoWrapper extends BasicMongoWrapper {
    override def get(bson: BsonDocument)(key: String): Int = {
        bson.getInt32(key).getValue
    }
}

class LongMongoWrapper extends BasicMongoWrapper {
    override def get(bson: BsonDocument)(key: String): Long = {
        bson.getInt64(key).getValue
    }
}

class DoubleMongoWrapper extends BasicMongoWrapper {
    override def get(bson: BsonDocument)(key: String): Double = {
        bson.getDouble(key).getValue
    }
}

class BooleanMongoWrapper extends BasicMongoWrapper {
    override def get(bson: BsonDocument)(key: String): Boolean = {
        bson.getBoolean(key).getValue
    }
}

class StringMongoWrapper extends RefMongoWrapper {
    override def get(bson: BsonDocument)(key: String): String = {
        bson.getString(key).getValue
    }
}

class DateTimeMongoWrapper(timeFormattedPattern: String) extends RefMongoWrapper {
    override def get(bson: BsonDocument)(key: String): String = {
        val value = bson.getDateTime(key).getValue
        val format = new SimpleDateFormat(timeFormattedPattern)
        format.format(value)
    }
}

class ArrayMongoWrapper extends RefMongoWrapper {
    override def get(bson: BsonDocument)(key: String): BsonArray = {
        bson.getArray(key)
    }
}

class DocumentMongoWrapper extends RefMongoWrapper {
    override def get(bson: BsonDocument)(key: String): BsonDocument = {
        bson.getDocument(key)
    }
}