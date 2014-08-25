package example

/**
 * Created by Java Student on 8/25/2014.
 */
object Matching {
  def main(args: Array[String]) {
    var tree: IntTree = insert(insert(insert(insert(EmptyTree, 4), 5), 3), -2)
    println(contains(tree, 3))
    println(contains(tree, -2))
    println(contains(tree, 4))
    println(contains(tree, 5))
    println(contains(tree, 0))
  }

  def contains(t: IntTree, v: Int): Boolean = t match {
    case EmptyTree => false
    case Node(elem, left, right) => if (elem == v) true else if (v < elem) contains(left, v) else contains(right, v)
  }

  def insert(t: IntTree, v: Int): IntTree = t match {
    case EmptyTree => Node(v, EmptyTree, EmptyTree)
    case Node(elem, left, right) => if (v < elem) Node(elem, insert(left, v), right) else if (v > elem) Node(elem, left, insert(right, v)) else t
  }
}

abstract class IntTree
case object EmptyTree extends IntTree
case class Node(elem: Int, left: IntTree, right: IntTree) extends IntTree
