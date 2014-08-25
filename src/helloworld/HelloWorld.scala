package helloworld

import java.util.Locale
import java.text.DateFormat._

/**
* Created by Java Student on 8/19/2014.
*/
object HelloWorld {
  def main (args: Array[String]) {
    val now = new java.util.Date
    val dformat = getDateInstance(LONG, Locale.CANADA)
    println(dformat format now)
    println("Hello World!")
    printArray(Array {3})
    new MyThread start()
    oncePerSecond(() => {println("Aaaa")})
    type Environment = String => Int
  }

  def printStuff() {
    println("Stuff")
  }

  def printArray (a: Array[Int]) {
    println(a.length)
  }

  def oncePerSecond(callback: () => Unit) {
    while (true) {
      callback()
      Thread sleep 1000
    }
  }
}
class MyThread extends Thread {
  override def run() {
    println("Hi!")
  }
}
