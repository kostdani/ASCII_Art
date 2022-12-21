package Filters
import org.scalatest.FunSuite
import DataModel.Image
import Inputs.RandomInput
class FlipFiltersTests extends FunSuite {
  test("twice horizontal flip neutralisation"){
    for (i<- 0 until 10){
      val img= RandomInput(println)
      assert(img==HorizontalFlipFilter(HorizontalFlipFilter(img)))
    }
  }
  test("twice vertical flip neutralisation") {
    for (i <- 0 until 10) {
      val img = RandomInput(println)
      assert(img == VerticalFlipFilter(VerticalFlipFilter(img)))
    }
  }
}