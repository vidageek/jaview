package net.vidageek.jaview

import org.specs2.mutable.Specification
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class RootSpec extends Specification {

  "parse view-type with no parameter" in {
    Root("()").typeDef must_== "()"
  }

  "parse view-type with single parameter with generic type" in {
    Root("(a: Option[String])").typeDef must_== "(Option[String])"
  }

  "parse view-type with single parameter with 2 generic types" in {
    Root("(a: Map[String, Int])").typeDef must_== "(Map[String,Int])"
  }

  "parse view-type with single parameter with nested generic types" in {
    Root("(a: List[Set[String]])").typeDef must_== "(List[Set[String]])"
  }

  "parse view-type with single parameter with multiple nested generic types" in {
    Root("(a: List[Map[String,Int]])").typeDef must_== "(List[Map[String,Int]])"
  }

  "parse view-type with parameters" in {
    Root("(a: String, b: scala.Option[Int])").typeDef must_== "(String,scala.Option[Int])"
  }

}