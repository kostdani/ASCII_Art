package DataModel
// class representing grayscale image as vector of doubles in interval 0-255 and width
class Image(data:Vector[Double],width:Int) {
  //getter and setter for data
  def get_data(): Vector[Double] = {
    data
  }
  def get_width(): Int = {
    width
  }
  // given function for replacing doubles with characters generates string with \n in proper places
  def to_string(transform: Double =>Char):String = {
    var res=""
    if (width == 0)
      return res
    var from=0
    var to=width
    while(to<=data.length) {
      res += data.slice(from, to).map(transform).mkString+"\n"
      from=to
      to+=width
    }
    res
  }

}


