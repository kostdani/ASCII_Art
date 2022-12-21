package MVC

class View(model:Model) extends ((String=>Unit)=>Unit) {
  override def apply(write: String=>Unit): Unit =  {
    if(model.getError.isEmpty)
      write(model.getImage.to_string(model.getTransform))
    else
      write(model.getError)
  }
}