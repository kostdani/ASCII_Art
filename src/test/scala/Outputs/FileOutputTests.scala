package Outputs
import org.scalatest.FunSuite

import java.io.File

class FileOutputTests extends FunSuite {
  test("wrong filepath") {
    var err: String = ""
    val output = new FileOutput("/")
    val res = output("test", (str: String) => err = str)
    assert(err == "Cannot write to specified file: /")
  }
  test("test writing out to file") {
    var err: String = ""
    val output = new FileOutput("tmp.txt")
    val res = output("test", (str: String) => err = str)
    val file=new File("tmp.txt")
    assert(scala.io.Source.fromFile("tmp.txt").mkString=="test")
    file.delete()

  }
}