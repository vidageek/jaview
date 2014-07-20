package jaview

import java.util.concurrent.atomic.AtomicInteger

trait Expression {
  def scalaCode : String
}

object IncNumber {

  private val number = new AtomicInteger(0)

  def apply() = number.incrementAndGet()

}

object TempVar {
  def apply() = s"var${IncNumber()}"
}

case class Root(viewType : String, content : Expression*) extends Expression {

  val className = s"jaview_generated_View${IncNumber()}"

  def scalaCode =
    s"""
		class $className(cachedJaview: ${classOf[CachedJaview].getName}) extends ($typeDef => String) {
				
				private def printIfNotUnit(result: StringBuilder, value: Any) {
						result.append(if (value == ()) "" else value.toString) 
				} 


				def apply$viewType : String = {
		
						${JaviewConfig.imports.mkString("\n")}
		
						val cache = cachedJaview
				
						val result = new StringBuilder()

						${JaviewConfig.pluginStart.mkString("\n")}

						
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
case class Tag(name : String, selfClosing : Boolean, attributes : Expression*) extends Expression {
  def scalaCode =
    s"""result.append("<").append(\"$name\");
		${attributes.map(_.scalaCode).mkString("\n\n")}
		result.append("${if (selfClosing) "/" else ""}>"); """
}

case class Attribute(name : String, content : Expression*) extends Expression {
  def scalaCode =
    if (content.size == 0) s"""result.append(" $name");"""
    else s"""result.append(\" $name=\\"\");
			${content.map(_.scalaCode).mkString("\n")}
  	result.append("\\""); """
}

case class Text(content : String) extends Expression {
  def scalaCode = "result.append(" + "\"\"\"" + content + "\"\"\"" + ");"
}

case class CodeSnippet(name : String) extends Expression {
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

case class Code(block : String) extends Expression {

  def scalaCode = s"printIfNotUnit(result, {$block});"

}

case class Raw(content : Expression*) extends Expression {
  def scalaCode = content.map(_.scalaCode).mkString("")
}

case class EscapedChar(char : String) extends Expression {
  def scalaCode = s"""result.append("$char"); """
}