
case class Track(id: Int, title: String, year: Int)
val tracks = List(Track(1, "Tet", 2006), Track(2, "Testss2", 2005), Track(3, "Test3", 2004), Track(4, "st4", 2003), Track(5, "Aestsss5", 2002))
val titles = tracks.map(_.title)
val top3 = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)

def greet(name: String): String = 
    s"hello, $name!"


def describe(n: Int): String = n match
    case 0 => "zero"
    case n if n > 0 => "plus"
    case _ => "minus"


def parse(s: String): String = {
    s.toIntOption.map(x => s"number $x").getOrElse("no number")
}


    
@main def run(): Unit = {
    println(greet("Markus"))
    println(describe(9))
    println(describe(0))
    println(describe(-1))
    println(parse("9"))
    println(parse("no"))

    println(titles.filter(_.length > 5))
    println(titles.map(_.toUpperCase))
    println(titles.groupBy(_.take(1)))

    println(top3.sum)
    println(top3.sum.toDouble / top3.length)
    println(top3.sortBy(x => -x).take(3))
}