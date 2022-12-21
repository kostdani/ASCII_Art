package Filters
import DataModel.Image
// inversion filter
object InvertFilter extends Filter {
  override def apply(img: Image): Image = {
    new Image(img.get_data().map((x:Double)=>255-x),img.get_width())
  }
}
