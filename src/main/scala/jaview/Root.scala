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
  def scalaCode =
    s"""
		package jaview.generated 
		
		class View${ViewNumber()} extends ($typeDef => String) {
				
				def apply$viewType : String = {
						val result = new StringBuilder()
						
						${content.map(_.scalaCode).mkString("\n\n")}

						result.toString
				}
		} 		
		"""

  def typeDef = {
    var res = "("
    var openBrackets = 0
    var beforeColon = true
    viewType.tail.foreach {
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
    res
  }
}
case class Tag(name : String) extends Expression {
  def scalaCode = s"""result.append("<").append(\"$name\").append(">"); """
}
case class Text(content : String) extends Expression {
  def scalaCode = "result.append(" + "\"\"\"" + content + "\"\"\"" + ");"
}
