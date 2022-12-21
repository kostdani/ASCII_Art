package Inputs
import DataModel.NoImage
import GrayScaling.TutorialGrayScaler
import org.scalatest.FunSuite

class FileInputTests extends FunSuite {
  test("wrong filepath"){
    var err:String=""
    val input=new FileInput("/",TutorialGrayScaler)
    val res=input((str:String)=>err=str)
    assert(res==NoImage && err== "Cannot read input file")
  }
}
