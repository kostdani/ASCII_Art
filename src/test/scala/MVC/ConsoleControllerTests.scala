package MVC
import ASCIITransformations.{LinearTransformation, PaulBourkeTransformation, ShortPaulBourkeTransformation}
import DataModel.Image
import Filters.{Filter, HorizontalFlipFilter, InvertFilter, ScaleFilter, VerticalFlipFilter}
import GrayScaling.TutorialGrayScaler
import Inputs.{FileInput, Input, RandomInput}
import Outputs.{ConsoleOutput, FileOutput, Output}
import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer
object TestsFriendlyModel extends Model{
  def setError(err: String): Unit = {
    error = err
  }
  def setImage(img:Image): Unit = {
    image=img
  }
  val inputs=ArrayBuffer.empty[(String => Unit) => Image]
  val outputs = ArrayBuffer.empty[(String, String => Unit) => Unit]
  val filters = ArrayBuffer.empty[ Image => Image]
  override def input(in: (String => Unit) => Image): Unit = {
    inputs+=in
  }

  override def output(out: (String, String => Unit) => Unit): Unit = {
    outputs+=out
  }

  override def action(act: Image => Image): Unit = {
    filters+=act
  }
}
class ConsoleControllerTests extends FunSuite {

  test ("All commands"){
    val dict=Vector(
      ( "--image test ", new FileInput("test", TutorialGrayScaler)),
      ("--random-image ", RandomInput),
      ("--table bourke ", PaulBourkeTransformation),
      ("--table short-bourke ", ShortPaulBourkeTransformation),
      ("--custom-table ABC ", new LinearTransformation("ABC")),
      ("--output-console ", ConsoleOutput),
      ("--output-file file ", new FileOutput("file")),
      ("--scale 1 ", new ScaleFilter(1)),
      ("--invert ", InvertFilter),
      ("--horizontal-flip ", HorizontalFlipFilter),
      ("--vertical-flip ", VerticalFlipFilter),
    )
    val model = TestsFriendlyModel
    val controller=new ConsoleController((dict.map {
      i=>i._1
    }).mkString.split(" "),model)
    controller.run()
    assert(model.inputs.length==2)
    assert(model.outputs.length == 2)
    assert(model.filters.length == 4)
  }
}
