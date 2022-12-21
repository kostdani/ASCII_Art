package Inputs
import DataModel.Image
// generaate random image with random size
object RandomInput extends Input {
  override def apply(errl:String=>Unit):Image={
    val rand = new scala.util.Random
    val w=rand.between(10,50)
    val h=rand.between(10,50)
    val buf=0 until w*h map {
      _ => rand.between(0, 256).toDouble
    }

    new Image(buf.toVector,w)
  }
}
