package helloworld

/**
 * Created by Java Student on 8/20/2014.
 */
abstract class Tree
case class Sum(l: Tree, r: Tree) extends Tree
case class Var(x: String) extends Tree
case class Const(v: Int) extends Tree

object Main {
  type Environment = String => Int
  def main(args: Array[String]) {
    val exp = Sum(Sum(Var("x"), Var("x")), Sum(Const(7), Var("y")))
    val env: Environment = {case "x" => 5 case "y" => 7}
    println("Expression " + exp)
    println("Evaluation with x = 5, y = 7")
    println(eval(exp, env))
    println("Derive relative to x\n" + derive(exp, "x"))
    println("Derive relative to y\n" + derive(exp, "y"))
  }

  def eval(t: Tree, env: Environment): Int = t match {
    case Sum(l, r) => eval(l, env) + eval(r, env)
    case Var(x) => env(x)
    case Const(v) => v
  }

  def derive(t: Tree, v: String): Const = t match {
    case Sum(l, r) => sum(derive(l, v), derive(r, v))
    case Var(x) if v == x => Const(1)
    case _ => Const(0)
  }

  def sum(l: Const, r: Const): Const = {
      if (l.v == 0) {
        if (r.v == 0) {
          Const(0)
        }
        Const(1)
      }
    Const(1)
  }
}

