package example

/**
 * Created by Java Student on 8/25/2014.
 */
object Set {

  def main(args: Array[String]) {
    var set: MySet[Num] = new EmptySet[Num]
    set = set incl Num(5)
    set = set incl Num(7)
    set = set incl Num(8)
    set = set incl Num(10)
    set = set incl Num(-2)
    set = set incl Num(3)
    set = set incl Num(0)
    set = set incl Num(-10)
    var sett: MySet[Num] = new EmptySet[Num]
    sett = sett incl Num(9)
    sett = sett incl Num(-2)
    sett = sett incl Num(10)
    sett = sett incl Num(4)

    println(set contains Num(0))
    println(set contains Num(9))

    set = sett union set
    println(set contains Num(0))
    println(set contains Num(9))
    println(set contains Num(-2))

    var interSet = new EmptySet[Num] incl Num(0)
    interSet = interSet incl Num(100)

    interSet = interSet intersect set
    println(set contains Num(0))
    println(set contains Num(9))
    println(set contains Num(100))

    println(set contains Num(0))
    set = set excl Num(0)
    println(set contains Num(0))
  }
}

trait MySet[A <: Ordered[A]] {
  def incl(x: A): MySet[A]
  def contains(x: A): Boolean
  def union(set: MySet[A]): MySet[A]
  def intersect(set: MySet[A]): MySet[A]
  def isEmpty(): Boolean
  def excl(x: A): MySet[A]
}

class NonEmptySet[A <: Ordered[A]](elem: A, left: MySet[A], right: MySet[A]) extends MySet[A] {
  override def incl(x: A): MySet[A] = {
    if (x < elem) {
     new NonEmptySet(elem, left incl x, right)
    } else if (x > elem) {
      new NonEmptySet(elem, left, right incl x)
    } else {
      this
    }
  }

  override def contains(x: A): Boolean = {
    if (x < elem) {
      left contains x
    } else if (x > elem) {
      right contains x
    } else {
      true
    }
  }

  override def union(set: MySet[A]): MySet[A] = {
    if (set.contains(elem)) {
      left.union(right.union(set))
    } else {
      union(set incl elem)
    }
  }

  override def intersect(set: MySet[A]): MySet[A] = {
    if (set contains elem) {
      left.intersect(set).union(right intersect set) incl elem
    } else {
      left.intersect(set).union(right intersect set)
    }
  }

  override def isEmpty(): Boolean = false

  override def excl(x: A): MySet[A] = {
    if (x == elem) {
      left union right
    } else if (x < elem) {
      left excl x
    } else {
      right excl x
    }
  }
}

class EmptySet[A <: Ordered[A]] extends MySet[A] {
  override def incl(x: A): MySet[A] = new NonEmptySet(x, new EmptySet[A], new EmptySet[A])

  override def contains(x: A): Boolean = false

  override def union(set: MySet[A]): MySet[A] = set

  override def intersect(set: MySet[A]): MySet[A] = new EmptySet[A]

  override def isEmpty(): Boolean = true

  override def excl(x: A): MySet[A] = new EmptySet[A]
}

case class Num(value: Double) extends Ordered[Num] {
  override def compare(that: Num): Int = {
    if (value < that.value) -1
    else if (value > that.value) 1
    else 0
  }
}
