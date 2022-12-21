package Inputs
import DataModel.NoImage
import GrayScaling.TutorialGrayScaler
import org.scalatest.FunSuite

class RandomInputTests extends FunSuite {
  test("generating random images"){
    for (i<- 0 until 10){
      val img=RandomInput(println)
      assert(img.get_data().length%img.get_width()==0)
      assert(img.get_data().forall(x=>(x>=0&&x<256)))
    }
  }
}
