package mongo

import java.lang.reflect.Field

import mongo.exception.{NoCollectionAnnotationException, NoFieldAnnotationException, TypeMismatchException}
import org.bson.BsonType
import org.mongodb.scala.bson.BsonDocument

/**
 * @author steve
 */
object MongoMapper {
    def map[T](document: BsonDocument, clazz: Class[T]): Unit = {

        val collectionAnnotation = clazz.getDeclaredAnnotation(classOf[Collection])
        if (collectionAnnotation == null) throw new NoCollectionAnnotationException("mongo collection domain must has @Collection annotation")
        val constructor = clazz.getDeclaredConstructor()
        val t = constructor.newInstance()
        clazz.getDeclaredFields.foreach(field => {
            val FieldAnnotation = field.getDeclaredAnnotation(classOf[MongoField])
            if (field == null) throw new NoFieldAnnotationException("filed must has @Field annotation")
            val bsonType = FieldAnnotation.mongoType()

            bsonType
        })
        clazz
        //        new T
    }

    def setFieldValue[T](key: String, bsonType: BsonType, bsonDocument: BsonDocument, field: Field, t: T): Unit = {
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
