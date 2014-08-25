package example

/**
 * Created by Java Student on 8/25/2014.
 */
object Set {

  def main(args: Array[String]) {
    var set: IntSet = EmptySet
    set = set incl 5
    set = set incl 7
    set = set incl 8
    set = set incl 10
    set = set incl -2
    set = set incl 3
    set = set incl 0
    set = set incl -10
    var sett: IntSet = EmptySet
    sett = sett incl 9
    sett = sett incl -2
    sett = sett incl 10
    sett = sett incl 4

    println(set contains 0)
    println(set contains 9)

    set = sett union set
    println(set contains 0)
    println(set contains 9)
    println(set contains -2)

    var interSet = EmptySet incl 0
    interSet = interSet incl 100

    interSet = interSet intersect set
    println(set contains 0)
    println(set contains 9)
    println(set contains 100)

    println(set contains 0)
    set = set excl 0
    println(set contains 0)
  }
}

trait IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(set: IntSet): IntSet
  def intersect(set: IntSet): IntSet
  def isEmpty(): Boolean
  def excl(x: Int): IntSet
}

class NonEmptySet(elem: Int, left: IntSet, right: IntSet) extends IntSet {
  override def incl(x: Int): IntSet = {
    if (x < elem) {
     new NonEmptySet(elem, left incl x, right)
    } else if (x > elem) {
      new NonEmptySet(elem, left, right incl x)
    } else {
      this
    }
  }

  override def contains(x: Int): Boolean = {
    if (x < elem) {
      left contains x
    } else if (x > elem) {
      right contains x
    } else {
      true
    }
  }

  override def union(set: IntSet): IntSet = {
    if (set.contains(elem)) {
      left.union(right.union(set))
    } else {
      union(set incl elem)
    }
  }

  override def intersect(set: IntSet): IntSet = {
    if (set contains elem) {
      left.intersect(set).union(right intersect set) incl elem
    } else {
      left.intersect(set).union(right intersect set)
    }
  }

  override def isEmpty(): Boolean = false

  override def excl(x: Int): IntSet = {
    if (x == elem) {
      left union right
    } else if (x < elem) {
      left excl x
    } else {
      right excl x
    }
  }
}

object EmptySet extends IntSet {
  override def incl(x: Int): IntSet = new NonEmptySet(x, EmptySet, EmptySet)

  override def contains(x: Int): Boolean = false

  override def union(set: IntSet): IntSet = set

  override def intersect(set: IntSet): IntSet = EmptySet

  override def isEmpty(): Boolean = true

  override def excl(x: Int): IntSet = EmptySet
}
