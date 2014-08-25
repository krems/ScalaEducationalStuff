package example

/**
 * Created by Java Student on 8/25/2014.
 */
object Lang {
  def main(args: Array[String]) {
    println(new Pred(new Pred(Zero)) - new Succ(Zero) + Zero + new Succ(new Succ(new Succ(Zero))) - new Pred(new Pred(new Pred(Zero))))
  }
}

abstract class Integer {
  def isPositive: Boolean
  def negate: Integer
  def isZero: Boolean
  def predecessor: Integer
  def successor: Integer
  def + (that: Integer): Integer
  def - (that: Integer): Integer
  def value(result: Int):Int
}

object Zero extends Integer {
  override def isZero: Boolean = true
  override def predecessor: Integer = Zero
  override def successor: Integer = Zero
  override def + (that: Integer): Integer = that
  override def - (that: Integer): Integer = that.negate
  override def isPositive: Boolean = false
  override def negate: Integer = Zero
  override def toString: String = "0"

  override def value(result: Int): Int = result
}

class Succ(x: Integer) extends Integer {
  override def isZero: Boolean = false
  override def predecessor: Integer = x
  override def successor: Integer = new Succ(this)
  override def + (that: Integer): Integer = {
    if (that.isZero) {
      this
    } else if (that.isPositive) {
      x + that.successor
    } else {
      x + that.predecessor
    }
  }
  override def - (that: Integer): Integer =  {
    if (that.isZero) {
      this
    } else if (that.isPositive) {
      x - that.predecessor
    } else {
      successor - that.predecessor
    }
  }
  override def isPositive: Boolean = true
  override def negate: Integer = negate0(x, new Pred(Zero))
  private def negate0(n: Integer, result: Integer): Integer = {
    if (n.isZero) {
      this
    } else {
      negate0(n - new Succ(Zero), new Pred(result))
    }
  }
  override def toString: String = {
    String valueOf value(1)
  }

  override def value(result: Int): Int = {
    if (this.isZero) {
      result
    } else {
      x value(result + 1)
    }
  }
}

class Pred(x: Integer) extends Integer {
  override def isZero: Boolean = false
  override def predecessor: Integer = x
  override def successor: Integer = new Pred(this)
  override def + (that: Integer): Integer = {
    if (that.isZero) {
      this
    } else if (that.isPositive) {
      x + that.predecessor
    } else {
      successor + that.predecessor
    }
  }
  override def - (that: Integer): Integer = {
    if (that.isZero) {
      this
    } else if (that.isPositive) {
      successor - that.predecessor
    } else {
      x - that.predecessor
    }
  }
  override def isPositive: Boolean = false
  override def negate: Integer = negate0(x, new Succ(Zero))
  private def negate0(n: Integer, result: Integer): Integer = {
    if (n.isZero) {
      this
    } else {
      negate0(n + new Succ(Zero), new Succ(result))
    }
  }
  override def toString: String = {
    String valueOf (x value(-1))
  }

  def value(result: Int):Int = {
    if (this.isZero) {
      result
    } else {
      x value(result - 1)
    }
  }
}
