package Outputs
// Just prints string to console
object ConsoleOutput extends Output{
  override def apply(str: String,errl:String=>Unit): Unit = {
    println(str)
  }
}

