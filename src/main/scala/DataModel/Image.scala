package DataModel
// class representing grayscale image as vector of doubles in interval 0-255 and width
class Image(data:Vector[Double],width:Int) {
  override def equals(o:Any): Boolean = {
    o match {
      case img: Image => img.get_data() == data && img.get_width() == width
      case _ => false
    }
  }
  //getter and setter for data
  def get_data(): Vector[Double] = {
    data
  }
  def get_width(): Int = {
    width
  }


}


