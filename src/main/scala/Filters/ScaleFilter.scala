package Filters
import DataModel.Image
// scaling filter
class ScaleFilter(scale:Double) extends Filter {
  // Shrink image by s times
  def scaleUp(img:Image,s:Int):Image ={
    val newdata = 0 until img.get_data().length * s * s map {
      i => {
        val w = (i % (img.get_width() * s)) / s
        val h = (i / (img.get_width() * s)) / s
        img.get_data()(h * img.get_width() + w)
      }
    }
    new Image(newdata.toVector, img.get_width() * s)
  }
  // Stretch image by s times
  def scaleDown(img:Image,s:Int):Image = {
    val height = img.get_data().length / img.get_width()
    val h = height / s
    val w = img.get_width() / s
    val newdata = 0 until h * w map {
      i => {
        val x = i % w
        val y: Int = i / w
        img.get_data()((s * y * img.get_width()) + (x * s))

      }
    }
    new Image(newdata.toVector, w)
  }
  override def apply(img: Image):Image = {
    if (scale>1)
      scaleUp(img,scale.ceil.toInt)
    else
      scaleDown(img,(1/scale).toInt)
  }
}
