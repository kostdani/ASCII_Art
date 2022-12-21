package Outputs

import java.io.{BufferedWriter, File, FileWriter}

class FileOutput(path:String) extends Output{
  override def apply(str: String,errl:String=>Unit): Unit = {
    try {
      val file = new File(path)
      val bw = new BufferedWriter(new FileWriter(file))
      bw.write(str)
      bw.close()
    }catch{
      case _:Throwable => errl("Cannot write to specified file: "+path)
    }
  }
}