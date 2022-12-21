package ASCIITransformations

// class representing linear trnasformation table
class LinearTransformation(table:String) extends ASCIITransformation{
  def getTable:String=table
  override def apply(color:Double): Char ={
    table((color*table.length/256).toInt)
  }
}
