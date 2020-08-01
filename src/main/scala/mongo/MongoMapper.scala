package mongo

import java.lang.reflect.Field

import mongo.exception.{NoCollectionAnnotationException, NoFieldAnnotationException, TypeMismatchException}
import org.bson.BsonType
import org.mongodb.scala.bson.{BsonArray, BsonDocument}

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

                }
            })

        } else {

        }
    }

    def mapFieldValue[T](key: String, bsonType: BsonType, bsonDocument: BsonDocument, field: Field, t: T): Unit = {
        bsonType match {
            case BsonType.INT32 => {
                checkClassType(field, classOf[Int])
                field.set(t, MongoWrapper.intWrapper.get(bsonDocument, key))
            }
            case BsonType.INT64 => {
                checkClassType(field, classOf[Long])
                field.set(t, MongoWrapper.longWrapper.get(bsonDocument, key))
            }
            case BsonType.BOOLEAN => {
                checkClassType(field, classOf[Boolean])
                field.set(t, MongoWrapper.booleanWrapper.get(bsonDocument, key))
            }
            case BsonType.DOUBLE => {
                checkClassType(field, classOf[Double])
                field.set(t, MongoWrapper.doubleWrapper.get(bsonDocument, key))
            }
            case BsonType.DATE_TIME => {
                checkClassType(field, classOf[Long])
                field.set(t, MongoWrapper.dateTimeWrapper.get(bsonDocument, key))
            }
            case BsonType.TIMESTAMP => {
                checkClassType(field, classOf[Long])
                field.set(t, MongoWrapper.timeStampWrapper.get(bsonDocument, key))
            }
            case BsonType.STRING => {
                checkClassType(field, classOf[String])
                field.set(t, MongoWrapper.stringWrapper.get(bsonDocument, key))
            }
            case BsonType.ARRAY => {
                // todo
                checkClassType(field, classOf[Seq[_]])
                val bsonArray = MongoWrapper.arrayMapper.get(bsonDocument, key)
            }
            case BsonType.DOCUMENT => {
                // todo
                checkClassType(field, classOf[Object])
                field.set(t, MongoWrapper.documentMapper.get(bsonDocument, key))
            }
            case _ => {

            }
        }
    }

    def checkClassType[T](field: Field, clazz: Class[T]): Unit = {
        if (!field.getDeclaringClass.isInstance(clazz)) throw new TypeMismatchException(s"filed type mismatch with ${clazz.getName}")
    }
}
