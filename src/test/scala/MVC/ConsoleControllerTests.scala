package MVC
import ASCIITransformations.{ASCIITransformation, LinearTransformation, PaulBourkeTransformation, ShortPaulBourkeTransformation}
import DataModel.Image
import Filters.{Filter, HorizontalFlipFilter, InvertFilter, ScaleFilter, VerticalFlipFilter}
import GrayScaling.TutorialGrayScaler
import Inputs.{FileInput, Input, RandomInput}
import Outputs.{ConsoleOutput, FileOutput, Output}
import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer
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
