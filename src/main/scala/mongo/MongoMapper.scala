package mongo

import java.lang.reflect.Field

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

                field.set(t, MongoWrapper.intWrapper.get(bsonDocument, key))
            }
            case BsonType.INT64 => {
                LongMongoWrapper.get(bsonDocument, key)
            }
            case BsonType.BOOLEAN => {
                BooleanMongoWrapper.get(bsonDocument, key)
            }
            case BsonType.DOUBLE => {

            }
            case BsonType.DATE_TIME => {

            }
            case BsonType.TIMESTAMP => {

            }
            case BsonType.STRING => {

            }
            case BsonType.ARRAY => {
                // todo
            }
            case BsonType.DOCUMENT => {
                // todo
            }
            case _ => {

            }
        }
    }

    def checkClassType[T](field: Field, clazz: Class[T]): Unit = {
        if (!field.getDeclaringClass.isInstanceOf[Class[T]]) throw new TypeMismatchException(s"filed type mismatch with ${clazz.getName}")
    }
}
