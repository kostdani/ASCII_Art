package Filters
import DataModel.Image
// Flips image horizontally
object HorizontalFlipFilter extends Filter {
  override def apply(img:Image): Image = {
    val newdata= (img.get_data().indices map {
      i=>{
        val w = i % img.get_width()
        val h = i / img.get_width()
        img.get_data()(h * img.get_width() + (img.get_width() - 1 - w))

      }
    }).toVector
    new Image(newdata,img.get_width())
  }
}