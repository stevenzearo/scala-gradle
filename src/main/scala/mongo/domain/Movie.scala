package mongo.domain

/**
 * @author steve
 */
class Movie {
    var id: String = _
    var title: String = _
    var movieType: String = _
    var year: Int = _

    override def toString: String = s"\\{_id: ${id}, title: $title, movieType: $movieType, year: $year\\}"
}
