package MVC
import DataModel.{Image, NoImage}
import ASCIITransformations.{ASCIITransformation, PaulBourkeTransformation}
import Inputs.Input
import Outputs.Output
class ModelImpl() extends Model {
  def setError(err:String): Unit = {
    error=err
    view((s:String,e:String=>Unit)=>println(s))
  }

  def setTransform(transform: ASCIITransformation): Unit = {
    transformation = transform
  }
  // supplied with function whose argument is error function uses this function to get image
  def input(in:(String=>Unit)=>Image): Unit = {
    image match {
      case _:NoImage.type => image=in(setError)
      case _ =>
        setError("Only one image is allowed")
    }
  }
  // supplied with function whose arguments are string and eror function uses this function to write out image
  def output(out:(String,String=>Unit)=>Unit): Unit = {
    image match {
      case _: NoImage.type =>
        setError("No specified image. Cannot export it")
      case _ => view(out)
    }
  }
  // called with function transforming image to image aplies this ufunction to stored image and stores new image
  def action(act: Image => Image): Unit = {

    image match {
      case _: NoImage.type =>
        setError("No specified image. Cannot apply filter")
      case _ => image=act(image)
    }
  }
}