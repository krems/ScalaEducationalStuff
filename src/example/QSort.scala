package example

/**
 * Created by Java Student on 8/21/2014.
 */
object QSort {
  def main (args: Array[String]) {
    val array: Array[Int] = Array.range(200, 0, -5)
    array.foreach((e) => print(e + " "))
    println()
    sort(array).foreach((e) => print(e + " "))
    println()
    array.foreach((e) => print(e + " "))
    println()
    sort_(array).foreach((e) => print(e + " "))
    println()
    array.foreach((e) => print(e + " "))
    println()
    qsort(array)
    array.foreach((e) => print(e + " "))
    var toSort: Array[Int] = Array[Int](100, 0, 4, 15, -2, 160, 99, -99, 23, 67, 1, 1, 0)
    toSort.foreach((e) => print(e + " "))
    println()
    toSort = sort(toSort)
    toSort.foreach((e) => print(e + " "))
    println()
  }

  def qsort(ar: Array[Int]): Unit = {

    def swap(lhs: Int, rhs: Int) = {
      val tmp = ar(rhs)
      ar(rhs) = ar(lhs)
      ar(lhs) = tmp
    }

    def sort(lhs: Int, rhs: Int): Unit = {
      val pivot = (ar(lhs) + ar(rhs) + ar((lhs + rhs) / 2)) / 3
      var l = lhs
      var r = rhs
      while (l < r) {
        while (ar(l) <= pivot) {
          l += 1
        }
        while (ar(r) >= pivot) {
          r -= 1
        }
        if (l < r) {
          swap(l, r)
          l += 1
          r -= 1
        }
      }
      if (lhs < r) {
        sort(lhs, r)
      }
      if (l < rhs) {
        sort(l, rhs)
      }
    }

    sort(0, ar.length - 1)
  }

  def sort(ar: Array[Int]): Array[Int] = {
    if (ar.length <= 1) {
      ar
    } else {
      val pivot = (ar(0) + ar(ar.length - 1) + ar(ar.length / 2)) / 3
      Array.concat(sort(ar.filter((e) => e < pivot)), ar.filter((e) => e == pivot), sort(ar.filter((e) => e > pivot)))
    }
  }

  def sort_(ar: Array[Int]): Array[Int] = {
    if (ar.length <= 1) {
      ar
    } else {
      val pivot = (ar(0) + ar(ar.length - 1) + ar(ar.length / 2)) / 3
      Array.concat(sort(ar filter (pivot >)), ar filter (pivot ==), sort(ar filter (pivot <)))
    }
  }
}
