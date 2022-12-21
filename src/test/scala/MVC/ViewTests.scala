package MVC

import ASCIITransformations.PaulBourkeTransformation
import DataModel.{Image, NoImage}
import org.scalatest.FunSuite

class ViewTests extends FunSuite {
  def testtransform(d:Double): Char = {
    ('0'+d.toInt).toChar
  }
  test("Image to string transformation testing"){
    var res=""
    val model = TestsFriendlyModel
    model.setTransform(testtransform)
    val view=new View(model)

    model.setImage(new Image(Vector(1,2,3,4,5,6,7,8,9),3))
    view((str:String,errl:String=>Unit)=>{res=str})
    assert("123\n456\n789\n"==res)
  }
  test("View empty image") {
    var res = ""

    val model = TestsFriendlyModel
    model.setTransform(testtransform)
    val view = new View(model)

    model.setImage(NoImage)
    view((str: String, errl: String => Unit) => {
      res = str
    })
    assert("" == res)
  }

}
