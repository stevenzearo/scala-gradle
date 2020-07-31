package mongo

import org.bson.BsonType
import org.mongodb.scala.bson.{BsonArray, BsonDocument, BsonValue}

/**
 * @author steve
 */
abstract class MongoWrapper[T <: Any] {
    def get(bson: BsonDocument, key: String): T = {
        val t: BsonValue = getAsBsonValue(bson, key)
        convert(t)
    }

    def getAsBsonValue(bson: BsonDocument, key: String): BsonValue = {
        val bsonValue = bson.get(key)
        checkType(bsonValue)
        bsonValue
    }

    def convert(bsonValue: BsonValue): T

    def checkType(bsonValue: BsonValue): Unit
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
    val mongoTypeMap: Map[BsonType, MongoWrapper[_]] = Map(
        BsonType.INT32 -> IntMongoWrapper,
        BsonType.INT64 -> LongMongoWrapper,
        BsonType.BOOLEAN -> BooleanMongoWrapper,
        BsonType.DOUBLE -> DoubleMongoWrapper,
        BsonType.DATE_TIME -> DateTimeMongoWrapper,
        BsonType.TIMESTAMP -> TimeStampMongoWrapper,
        BsonType.STRING -> StringMongoWrapper,
        BsonType.DOCUMENT -> DocumentMongoWrapper,
        BsonType.ARRAY -> ArrayMongoWrapper
    )
}

private abstract class BasicMongoWrapper[T <: AnyVal] extends MongoWrapper[T] {
}


private abstract class RefMongoWrapper[T <: AnyRef] extends MongoWrapper[T] {
}

private object IntMongoWrapper extends BasicMongoWrapper[Int] {
    override def convert(bsonValue: BsonValue): Int = {
        checkType(bsonValue)
        bsonValue.asInt32().getValue
    }

    override def checkType(bsonValue: BsonValue): Unit = {
        if (!bsonValue.isInt32) throw new BsonTypeConvertException(s"can not convert ${bsonValue.getBsonType} to Int")
    }
}

private object LongMongoWrapper extends BasicMongoWrapper[Long] {
    override def convert(bsonValue: BsonValue): Long = {
        checkType(bsonValue)
        bsonValue.asInt64().getValue
    }

    override def checkType(bsonValue: BsonValue): Unit = {
        if (!bsonValue.isInt64) throw new BsonTypeConvertException(s"can not convert ${bsonValue.getBsonType} to Long")
    }
}

private object DoubleMongoWrapper extends BasicMongoWrapper[Double] {
    override def convert(bsonValue: BsonValue): Double = {
        checkType(bsonValue)
        bsonValue.asInt64().getValue
    }

    override def checkType(bsonValue: BsonValue): Unit = {
        if (!bsonValue.isDouble) throw new BsonTypeConvertException(s"can not convert ${bsonValue.getBsonType} to Double")
    }
}

private object BooleanMongoWrapper extends BasicMongoWrapper[Boolean] {
    override def convert(bsonValue: BsonValue): Boolean = {
        checkType(bsonValue)
        bsonValue.asBoolean().getValue
    }

    override def checkType(bsonValue: BsonValue): Unit = {
        if (!bsonValue.isBoolean) throw new BsonTypeConvertException(s"can not convert ${bsonValue.getBsonType} to Boolean")
    }
}

private object DateTimeMongoWrapper extends BasicMongoWrapper[Long] {
    override def convert(bsonValue: BsonValue): Long = {
        checkType(bsonValue)
        bsonValue.asDateTime().getValue
    }

    override def checkType(bsonValue: BsonValue): Unit = {
        if (!bsonValue.isDateTime) throw new BsonTypeConvertException(s"can not convert ${bsonValue.getBsonType} to DateTime")
    }
}

private object TimeStampMongoWrapper extends BasicMongoWrapper[Long] {
    override def convert(bsonValue: BsonValue): Long = {
        checkType(bsonValue)
        bsonValue.asDateTime().getValue
    }

    override def checkType(bsonValue: BsonValue): Unit = {
        if (!bsonValue.isDateTime) throw new BsonTypeConvertException(s"can not convert ${bsonValue.getBsonType} to TimeStamp")
    }
}

private object StringMongoWrapper extends RefMongoWrapper[String] {
    override def convert(bsonValue: BsonValue): String = {
        checkType(bsonValue)
        bsonValue.asString().getValue
    }

    override def checkType(bsonValue: BsonValue): Unit = {
        if (!bsonValue.isString) throw new BsonTypeConvertException(s"can not convert ${bsonValue.getBsonType} to String")
    }
}

private object ArrayMongoWrapper extends RefMongoWrapper[BsonArray] {
    override def convert(bsonValue: BsonValue): BsonArray = {
        checkType(bsonValue)
        bsonValue.asArray()
    }

    override def checkType(bsonValue: BsonValue): Unit = {
        if (!bsonValue.isDateTime) throw new BsonTypeConvertException(s"can not convert ${bsonValue.getBsonType} to ${BsonType.ARRAY}")
    }
}

private object DocumentMongoWrapper extends RefMongoWrapper[BsonDocument] {
    override def convert(bsonValue: BsonValue): BsonDocument = {
        checkType(bsonValue)
        bsonValue.asDocument()
    }

    override def checkType(bsonValue: BsonValue): Unit = {
        if (!bsonValue.isDateTime) throw new BsonTypeConvertException(s"can not convert ${bsonValue.getBsonType} to ${BsonType.DOCUMENT}")
    }
}