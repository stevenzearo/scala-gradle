package mongo

import java.lang.reflect.Field

import mongo.exception.{NoCollectionAnnotationException, NoFieldAnnotationException, TypeMismatchException, UnsupportedMongoStructureException}
import org.bson.BsonType
import org.mongodb.scala.bson.{BsonArray, BsonDocument, BsonValue}

/**
 * @author steve
 */
object MongoMapper {
    def map[T](document: BsonDocument, clazz: Class[T]): Unit = {

        val collectionAnnotation = clazz.getDeclaredAnnotation(classOf[Collection])
        if (collectionAnnotation == null) throw new NoCollectionAnnotationException(s"mongo collection domain must has ${classOf[Collection].getName} annotation")
        val constructor = clazz.getDeclaredConstructor()
        val t = constructor.newInstance()


        clazz
        //        new T
    }

    /*
    * class A {
    * @MongoField
    *  field 1 (basic type)
    *
    * @MongoField
    * field 2 (array)
    *
    * @MongoField
    * field 3 (class)
    *
    * }
    * */
    def setFieldValue(t: Object, clazz: Class[_], document: BsonDocument): Unit = {
        val fieldClassMongoField = clazz.getDeclaredAnnotation(classOf[MongoField])
        if (fieldClassMongoField == null) {
            clazz.getDeclaredFields.foreach(field => {
                val mongoField = field.getDeclaredAnnotation(classOf[MongoField])
                if (mongoField == null) throw new NoFieldAnnotationException(s"mongo field must has ${classOf[MongoField].getName} annotation")
                val mongoWrapperOption = MongoWrapper.mongoTypeMap.get(fieldClassMongoField.mongoType())
                if (mongoWrapperOption.isEmpty) throw new UnknownBsonTypeException(s"Unknown bson type ${fieldClassMongoField.mongoType().name()}")
                val mongoWrapper = mongoWrapperOption.get
                val value = mongoWrapper.get(document, fieldClassMongoField.name())
                if (mongoField.mongoType() == BsonType.DOCUMENT) {
                    setFieldValue(field, field.getClass, value.asInstanceOf[BsonDocument])
                } else if (mongoField.mongoType() == BsonType.ARRAY) {
                    val bsonArray = value.asInstanceOf[BsonArray]
                    setFieldValueForArray(t, field, bsonArray)
                } else {
                    field.set(t, value)
                }
            })

        } else {

        }
    }

    def setFieldValueForArray(t: Object, field: Field, bsonArray: BsonArray): Unit = {
        // todo check type
        if (bsonArray.isEmpty) field.set(t, Array())
        val bsonValues = bsonArray.getValues
        val elemBsonType = bsonValues.get(0).getBsonType
        if (elemBsonType == BsonType.ARRAY) {
            throw new UnsupportedMongoStructureException("Unsupported mongo structure, while a BsonArray in a BsonArray")

        } else if (elemBsonType == BsonType.DOCUMENT) {
            val mongoWrapperOption = MongoWrapper.mongoTypeMap.get(elemBsonType)
            if (mongoWrapperOption.isEmpty) throw new UnknownBsonTypeException(s"Unknown bson type ${elemBsonType.name()}")
            // todo get field class -> new instance -> set value
            val fieldElemClass = field.getType.getGenericSuperclass.getClass
            val fieldValue = bsonValues.stream().map(bsonValue => {
                val fieldElemUnWrappedValue: Object = fieldElemClass.getDeclaredConstructor().newInstance().asInstanceOf[Object]
                setFieldValue(fieldElemUnWrappedValue, fieldElemClass, bsonValue.asDocument())
            }).toArray
            field.set(fieldValue, t)
        } else {
            val scalaArray = bsonArray.stream().map(bsonValue => bsonToScalaVal(bsonValue)).toArray()
            field.set(t, scalaArray)
        }
    }

    def bsonToScalaVal(bsonValue: BsonValue): Any = {
        val bsonType = bsonValue.getBsonType
        bsonType match {
            case BsonType.INT32 => bsonValue.asInt32().getValue
            case BsonType.INT64 => bsonValue.asInt64().getValue
            case BsonType.BOOLEAN => bsonValue.asBoolean().getValue
            case BsonType.DOUBLE => bsonValue.asDouble().getValue
            case BsonType.STRING => bsonValue.asString().getValue
            case BsonType.DATE_TIME => bsonValue.asDateTime().getValue
            case BsonType.TIMESTAMP => bsonValue.asTimestamp().getValue
            case BsonType.BINARY => bsonValue.asBinary().getData
            case BsonType.ARRAY => bsonValue.asArray().stream().map(bsonVal => bsonToScalaVal(bsonVal)).toArray()
            case _ => throw new TypeMismatchException(s"invalid bson type ${bsonType.name()}")
        }
    }

    def checkBasicClassType[T](field: Field, clazz: Class[T]): Unit = {
        if (!field.getDeclaringClass.isInstance(clazz)) throw new TypeMismatchException(s"filed type mismatch with ${clazz.getName}")
    }
}
