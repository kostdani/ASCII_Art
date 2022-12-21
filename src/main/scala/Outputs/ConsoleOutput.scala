package Outputs

object ConsoleOutput extends Output{
  override def apply(str: String): Unit = {
    print(str)
  }
}

