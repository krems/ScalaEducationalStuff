package helloworld

/**
 * Created by Java Student on 8/20/2014.
 */
trait Ord {
  def <(that: Any): Boolean
  def <=(that: Any): Boolean = this < that || this == that
  def >(that: Any): Boolean = !(this <= that)
  def >=(that: Any): Boolean = !(this < that)
}

class Date(y: Int, m: Int, d: Int) extends Ord {
  def year = y
  def month = m
  def day = d

  override def toString: String = {
    "Date: " + day + "-" + month + "-" + year
  }

  override def <(that: Any): Boolean = {
    if (!that.isInstanceOf[Date]) {
      error("Must be Date!")
    }
    val o = that.asInstanceOf[Date]
    year < o.year || (year == o.year && (month < o.month || (month == o.month && day < o.day)))
  }

  override def equals(that: Any): Boolean = {
    that.isInstanceOf[Date] && {
      val o = that.asInstanceOf[Date]
      year == o.year && month == o.month && day == o.day
    }
  }
}

object MainDate {
  def main (args: Array[String]) {
    val d = new Date(2014, 1, 30)
    val d1 = new Date(2014, 1, 31)
    val d2 = new Date(2014, 1, 30)
    val o = new Object
    println(d < d1)
    println(d equals d2)
    println(d equals o)
    d < o
    println("???")
  }
}
