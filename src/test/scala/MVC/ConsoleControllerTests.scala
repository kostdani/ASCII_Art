package MVC
import org.scalatest.FunSuite

class ConsoleControllerTests extends FunSuite {

  test ("random sequnce of commands commands"){
    val dict=Vector(
      "--image test ",
      "--random-image ",
      "--table bourke ",
      "--table bourke-small ",
      "--custom-table ABC ",
      "--output-console ",
      "--output-file file ",
      "--scale 1.0 ",
      "--invert ",
      "--horizontal-flip ",
      "--vertical-flip "
    )
    val model = TestsFriendlyModel
    val rand = new scala.util.Random
    val origstr=(0 until 50 map{
      _ => dict(rand.between(0,dict.length))
    }).mkString
    val controller = new ConsoleController(origstr.split(" "), model)
    controller.run()
    assert(model.str==origstr)
  }
}
