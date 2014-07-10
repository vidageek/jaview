package jaview

import java.util.concurrent.atomic.AtomicInteger

sealed trait Expression {
  def scalaCode : String
}

object ViewNumber {

  private val number = new AtomicInteger(0)

  def apply() = number.incrementAndGet()

}

case class Root(viewType : String, content : Expression*) extends Expression {
  val className = s"jaview_generated_View${ViewNumber()}"

  def scalaCode =
    s"""
		class $className extends ($typeDef => String) {
				
				def apply$viewType : String = {

						val result = new StringBuilder()
						
						${content.map(_.scalaCode).mkString("\n\n")}

						result.toString
				}
		} 		
		scala.reflect.classTag[$className].runtimeClass
		"""

  def typeDef = {
    var res = "("
    var openBrackets = 0
    var beforeColon = true

    viewType.dropRight(1).tail.foreach {
      case ' ' =>
      case '[' =>
        openBrackets += 1
        res += '['
      case ']' =>
        openBrackets -= 1
        res += ']'
      case ':' => beforeColon = false
      case ',' if openBrackets == 0 =>
        beforeColon = true
        res += ','
      case c if beforeColon =>
      case c => res += c
    }
    res + ')'
  }
}
case class Tag(name : String) extends Expression {
  def scalaCode = s"""result.append("<").append(\"$name\").append(">"); """
}
case class Text(content : String) extends Expression {
  def scalaCode = "result.append(" + "\"\"\"" + content + "\"\"\"" + ");"
}

case class Variable(name : String) extends Expression {
  def scalaCode = s"result.append($name.toString());"
}

case class Fold(variable : String, varName : String, content : Expression*) extends Expression {

  def scalaCode = s"""
            $variable.foldLeft(result) { 
							case (result, $varName) =>
								 	${content.map(_.scalaCode).mkString("\n\n")}
  								result
            }
            """
}