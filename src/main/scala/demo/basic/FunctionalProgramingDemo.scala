package demo.basic

case class StudentScore(id: String,
                        studentId: String,
                        courseId: String,
                        classId: String,
                        score: Double) {
    override def toString: String =
        s"StudentScore:{id: ${id}, studentId:${studentId}, courseId:${courseId}, score: ${score}}"
}

object StudentScore {
    def avgScore(studentScores: Array[StudentScore]): Double = {
        studentScores.map[Double](s => s.score).reduce((s1, s2) => (s1 + s2) / 2)
    }

    def updateScore(studentScore: StudentScore, newScore: Double): StudentScore = {
        StudentScore(studentScore.id, studentScore.studentId, studentScore.courseId, studentScore.classId, newScore)
    }
}

object FunctionalProgramingDemo {
    def main(args: Array[String]): Unit = {
        val s1 = StudentScore("id-0001", "student-0001", "course-0001", "class-0001", 83.5)
        val s2 = StudentScore("id-0002", "student-0002", "course-0001", "class-0001", 82.0)
        val s3 = StudentScore("id-0002", "student-0003", "course-0001", "class-0001", 81.0)
        val scores = Array(s1, s2, s3)
        val avgScore = StudentScore.avgScore(scores)
        println(avgScore)
        val s3New = StudentScore.updateScore(s3, 79.5)
        println(s3)
        println(s3New)
        /*
        81.875
        StudentScore:{id: id-0002, studentId:student-0003, courseId:course-0001, score: 81.0}
        StudentScore:{id: id-0002, studentId:student-0003, courseId:course-0001, score: 79.5}
        * */
    }
}