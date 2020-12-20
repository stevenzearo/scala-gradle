package mongo.exception

/**
 * @author steve
 */
class UnsupportedMongoStructureException extends Exception {
    private var msg: String = _

    def this(msg: String) {
        this()
        this.msg = msg
    }

    def this(throwable: Throwable) {
        this()
        this.initCause(throwable)
    }

    def this(msg: String, throwable: Throwable) {
        this(throwable)
        this.msg = msg
    }

    override def getMessage: String = if (this.msg == null) super.getMessage else msg
}