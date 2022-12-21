package Outputs
import org.scalatest.FunSuite

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
    val res = output("testing Message", (str: String) => err = str)
    assert(scala.io.Source.fromFile("tmp.txt").mkString=="testing Message")

  }
}