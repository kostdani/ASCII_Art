package Filters
import DataModel.Image
// Flipts image vertically
object VerticalFlipFilter extends Filter {
  override def apply(img:Image): Image = {
    val newdata= (img.get_data().indices map {
      i=>{
        val w = i % img.get_width()
        val h = i / img.get_width()
        img.get_data()(((img.get_data().length/img.get_width())-1-h)*img.get_width()+w)

      }
    }).toVector
    new Image(newdata,img.get_width())
  }
}