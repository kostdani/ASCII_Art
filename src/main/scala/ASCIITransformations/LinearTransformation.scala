package ASCIITransformations

// class representing linear trnasformation table
class LinearTransformation(table:String) extends ASCIITransformation{
  override def apply(color:Double): Char ={
    table((color*table.length/256).toInt)
  }
}
