package demo.basic

case class Book(val id: String, val isbn: String, val name: String, val author: String, val version: String) {
  override def toString: String = s"Book{id:$id, isbn:$isbn, name:$name, author$author, version:$version}"
}

object BookService {
  def updateVersion(book: Book, version: String): Book = {
    Book(id = book.id, isbn = book.isbn, name = book.name, author = book.author, version = version)
  }

  def main(args: Array[String]): Unit = {
    val b1 = Book("id-0001", "isbn-001", "A Greate Book", "Shakespeare", "version-0001")
    val b2 = BookService.updateVersion(b1, "version-0002")
    println(b1)
    println(b2)
    println(b1 == b2)
  }
}

