package MVC
import DataModel.{Image, NoImage}
import ASCIITransformations.{ASCIITransformation, PaulBourkeTransformation}
import Inputs.Input
import Outputs.Output
class Model() {
  private var error=""
  def setError(err:String): Unit = {
    error=err
    view(print)
  }
  def getError:String = {
    error
  }

  private var image:Image=NoImage
  def getImage: Image = {
    image
  }

  private var view:(String=>Unit)=>Unit= (_:String=>Unit)=>_
  def setView(f:(String=>Unit)=>Unit): Unit = {
    view=f
  }

  var transformation:ASCIITransformation=PaulBourkeTransformation
  def setTransform(transform: ASCIITransformation): Unit = {
    transformation=transform
  }
  def getTransform:ASCIITransformation = {
    transformation
  }
  def checkImage():
  Unit = {

    if (error.isEmpty)
      image match {
        case _: NoImage.type =>
          setError("No specified image")
          view(println)
        case _ =>
      }
  }

  def input(in:Input): Unit = {
    image match {
      case _:NoImage.type => image=in()
      case _ =>
        setError("Only one image is allowed")
        view(println)
    }
  }

  def output(out:Output): Unit = {
    checkImage()
    if(error.isEmpty)
      view(out)
  }
  def action(act: Image => Image): Unit = {
    checkImage()
    if(error.isEmpty)
      image = act(image)
  }
}