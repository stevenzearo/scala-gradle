package conf

import java.io.{File, FileNotFoundException}
import java.util.Properties

import scala.io.Source

/**
 * @author steve
 */
class Conf {
    private var filePath: String = _
    private val conf: Properties = new Properties()

    def this(filePath: String) {
        this()
        val file: File = Conf.getFile(filePath)
        this.filePath = filePath
        conf.load(Source.fromFile(file).bufferedReader())
    }
}

object Conf {
    private var filePath: String = _
    private val conf: Properties = new Properties()

    def loadProperties(source: String): Unit = {
        val file: File = getFile(this.getClass.getClassLoader.getResource(source).getPath)
        this.filePath = source
        conf.load(Source.fromFile(file).bufferedReader())
    }

    def getProperty(key: String): String = conf.getProperty(key)

    private def getFile(filePath: String): File = {
        if (!filePath.endsWith(".properties")) throw new NotPropertiesFileException(s"$filePath is not a properties file")
        val file: File = new File(filePath)
        if (!file.exists()) throw new FileNotFoundException()
        file
    }
}
