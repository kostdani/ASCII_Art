package Outputs
import org.scalatest.FunSuite

class FileOutputTests extends FunSuite {
  test("wrong filepath") {
    var err: String = ""
    val output = new FileOutput("/")
    val res = output("test", (str: String) => err = str)
    assert(err == "Cannot write to specified file: /")
  }
}