package code.snippet


import net.liftweb.http._
import net.liftweb.util._
import scala.xml._

object IfM extends DispatchSnippet {

  def dispatch: DispatchIt = {
    case "Prod" | "prod" => prod
    case _ => dev
  }


  def prod(n: NodeSeq): NodeSeq = {
    if (Props.mode != Props.RunModes.Production)
      NodeSeq.Empty
    else n
  }

  def dev(n: NodeSeq): NodeSeq = {
    if (Props.mode == Props.RunModes.Production)
      NodeSeq.Empty
    else n
  }
}

