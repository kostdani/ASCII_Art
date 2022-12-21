package MVC

import ASCIITransformations.{ASCIITransformation, LinearTransformation, PaulBourkeTransformation, ShortPaulBourkeTransformation}
import DataModel.Image
import Filters.{HorizontalFlipFilter, InvertFilter, ScaleFilter, VerticalFlipFilter}
import Inputs.{FileInput, RandomInput}
import Outputs.{ConsoleOutput, FileOutput}

import scala.collection.mutable.ArrayBuffer

// model with some changed methods for convinient testing
object TestsFriendlyModel extends Model{
  var str = ""
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
    in match {
      case fi:FileInput => str+="--image "+fi.getPath+" "
      case r:RandomInput.type => str+="--random-image "
    }
  }

  override def output(out: (String, String => Unit) => Unit): Unit = {
    out match {
      case fo: FileOutput => str += "--output-file " + fo.getPath+" "
      case c:ConsoleOutput.type => str += "--output-console "
    }
  }

  override def action(act: Image => Image): Unit = {
    act match {
      case sf: ScaleFilter => str += "--scale " + sf.getScale +" "
      case in: InvertFilter.type  => str+="--invert "
      case hf: HorizontalFlipFilter.type => str += "--horizontal-flip "
      case vf: VerticalFlipFilter.type => str += "--vertical-flip "
    }
  }

  override def setTransform(transform: ASCIITransformation): Unit = {
    transformation=transform
    transform match{
      case pb:PaulBourkeTransformation.type => str+= "--table bourke "
      case pb: ShortPaulBourkeTransformation.type => str += "--table bourke-small "
      case pb: LinearTransformation => str += "--custom-table "+pb.getTable+" "
      case _=>
    }
  }
}