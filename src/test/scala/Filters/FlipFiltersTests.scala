package Filters
import org.scalatest.FunSuite
import DataModel.Image
import Inputs.RandomInput
class FlipFiltersTests extends FunSuite {
  test("Horizontal flip"){

    val img = new Image(Vector(255, 100, 0, 25), 2)
    val invimg = HorizontalFlipFilter(img)
    assert(invimg.get_width() == img.get_width())
    assert(invimg.get_data() == Vector(100.0, 255.0, 25.0, 0.0))
  }
  test("Vertical flip") {

    val img = new Image(Vector(255, 100, 0, 25), 2)
    val invimg = VerticalFlipFilter(img)
    assert(invimg.get_width() == img.get_width())
    assert(invimg.get_data() == Vector(0.0, 25.0, 255.0, 100.0))
  }
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