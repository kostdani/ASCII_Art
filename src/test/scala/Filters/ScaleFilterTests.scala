package Filters
import org.scalatest.FunSuite
import DataModel.Image
import Inputs.RandomInput
class ScaleFilterTests extends FunSuite {

  test("scale 2 scale 0.5 neutralisation"){
    for (i<- 0 until 10){
      val img= RandomInput(println)
      val shrink=new ScaleFilter(0.5)
      val stretch=new ScaleFilter(2)
      assert(img==shrink(stretch(img)))
    }
  }
  test("Scale up 1 pixel"){
    for (i <- 0 until 10) {
      val rand = new scala.util.Random
      val rngnumber=rand.nextDouble() * 256
      val data: Vector[Double] = (0 until 1 map {
        _ => rngnumber }).toVector
      val img = new Image(data, 1)
      val scalenumber=rand.between(5,10)
      val scaler=new ScaleFilter(scalenumber)
      val scaledimg= scaler(img)
      assert(scaledimg.get_data().forall(_==rngnumber))
      assert(scaledimg.get_width()==scalenumber && scaledimg.get_data().length==scalenumber*scalenumber)
    }
  }
}
