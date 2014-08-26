package example

/**
 * Created by Java Student on 8/26/2014.
 */
object StackUser {
  def main(args: Array[String]) {
    println(EmptyMyStack.push(1).push(2).pop().top())
  }
}

abstract class MyStack[+A] {
  def push[B >: A](e: B): MyStack[B] = new NonEmptyMyStack[B](e, this)
  def pop(): MyStack[A]
  def top(): A
  def isEmpty: Boolean
}

object EmptyMyStack extends MyStack[Nothing] {

  override def top() = error("Empty!")

  override def pop() = error("Empty!")

  override def isEmpty = true
}

class NonEmptyMyStack[+A](e: A, rest: MyStack[A]) extends MyStack[A] {

  override def top() = e

  override def isEmpty = false

  override def pop() = rest
}
