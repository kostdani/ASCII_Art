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
    val model = new TestsFriendlyModel
    val rand = new scala.util.Random
    val origstr=(0 until 50 map{
      _ => dict(rand.between(0,dict.length))
    }).mkString
    val controller = new ConsoleController(origstr.split(" "), model)
    controller.run()
    assert(model.str==origstr)
  }
  test("Testing help command") {
    val model = new TestsFriendlyModel()
    val controller = new ConsoleController(Array("--help"), model)
    controller.run()
    assert(model.getError== "Usage as follows run --image \"../images/test-image.jpg\" --rotate +90 --scale 0.25 --invert --output-console\n" +
      "all commands are worked throu sequentialy one by one\n" +
      "possible commands:\n" +
      "--image path: loads image\n" +
      "--random-image: generates random image with dimensions 10-30\n" +
      "--table {bourke|bourke-small}:select one of bourke conversion tables\n" +
      "--custom-table seq: use seq as conversion table\n" +
      "--output-console: rites image out to console\n" +
      "--output-file path: rites image out to file path\n" +
      "--scale n: scale to n times\n" +
      "--invert: inverts image\n" +
      "--{horizontal|vertial}-flip: flips image\n" +
      "--help writes this help info out")

  }
}
