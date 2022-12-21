package MVC
class View(model:Model) extends (((String,String=>Unit)=>Unit)=>Unit) {
  // given function for replacing doubles with characters generates string with \n each w chars
  def placeNewlines(str:String,w:Int):String={
    var res = ""
    var from = 0
    var to = w
    while (to <= str.length) {
      res += str.slice (from, to) + "\n"
      from = to
      to += w
    }
    res
  }
  // if model is ok writes out image if there is error writes out error
  override def apply(write: (String,String=>Unit)=>Unit): Unit =  {
    if(model.getError.isEmpty) {
      if(model.getImage.get_data().nonEmpty) {
        val towrite = placeNewlines(model.getImage.get_data().map(model.getTransform).mkString, model.getImage.get_width())
        write(towrite, model.setError)
      }
    } else
      write(model.getError,model.setError)
  }
}