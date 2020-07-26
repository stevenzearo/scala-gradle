package mongo

/**
  * @author steve
  */
class TestOBJ(val _id: String, val name: String) {
  override def toString: String = s"\\{_id:${_id}, name:${name}\\}"
}
object TestOBJ {
  def apply(_id: String, name: String): TestOBJ = new TestOBJ(_id, name)

  def unapply(arg: TestOBJ): Option[(String, String)] =
    if (arg == null) Option.empty else Option(("", ""))
}
