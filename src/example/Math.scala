package example

/**
 * Created by Java Student on 8/22/2014.
 */
object Math {
  def main(args: Array[String]) {
    val x = sqrt(9)
    println(x + " x^2 = " + x * x)
    println(gcd(52, 48))
    println(factorial(5))
    println(factorial1(5))
    println(sum((x) => x * x)(0, 3))
    println(sum1((x) => x * x)(0, 3))
    println(product((x) => x * x)(1, 2))
    println(product1((x) => x * x)(1, 2))
    println(operate((a, b) => a * b)((x) => x * x)(1, 2))
  }

  def sqrt(x: Double): Double = {
    def sqrtIter(guess: Double): Double = {
      if (isGoodEnough(guess)) {
        guess
      } else {
        sqrtIter(improve(guess))
      }
    }

    def isGoodEnough(guess: Double): Boolean = {
      abs(guess * guess - x) < x * 0.0001
    }

    def improve(guess: Double): Double = {
      (x / guess + guess) / 2
    }

    def abs(x: Double): Double = {
      if (x < 0.0) {
        x * -1.0
      } else {
        x
      }
    }
    sqrtIter(1.0)
  }

  def gcd(lhs: Int, rhs: Int): Int = {
    if (rhs == 0) {
      lhs
    } else {
      gcd(rhs, lhs % rhs)
    }
  }

  def factorial(n: Int): Int = {
    def fact(n: Int, m: Int): Int = {
      if (n == 1) {
        m
      } else {
        fact(n - 1, n * m)
      }
    }
    fact(n, 1)
  }

  def sum(f: Int => Int)(a: Int, b: Int): Int = {
    def iter(a: Int, result: Int): Int = {
      if (a > b) result else iter(a + 1, f(a) + result)
    }
    iter(a, 0)
  }

  def product(f: Int => Int)(a: Int, b: Int): Int  = {
    def iter(a: Int, result: Int): Int = {
      if (a > b) result else iter(a + 1, f(a) * result)
    }
    iter(a, 1)
  }

  def sum1(f: Int => Int)(a: Int, b: Int): Int =
    if (a > b) 0 else f(a) + sum1(f)(a + 1, b)

  def product1(f: Int => Int)(a: Int, b: Int): Int =
    if (a > b) 1 else f(a) * product1(f)(a + 1, b)

  def factorial1(n: Int): Int = {
    product1((x) => x)(2, n)
  }

  def operate(g: (Int, Int) => Int)(f: Int => Int)(a:Int, b: Int): Int = {
    if (a > b) 1 else g(f(a), operate(g)(f)(a + 1, b))
  }
}
