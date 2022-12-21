package Filters
import org.scalatest.FunSuite
import DataModel.Image
import Inputs.RandomInput
class InvertFilterTests extends FunSuite {
    test ("test invertion"){
      val img=new Image(Vector(255,100,0,25),2)
      val invimg=InvertFilter(img)
      assert(invimg.get_width()==img.get_width())
      assert(invimg.get_data()==Vector(0.0,155.0,255.0,230.0))
    }
    test ("random images inversion"){
      for (i <- 0 until 10) {
        val img = RandomInput(println)
        val invimg = InvertFilter(img)
        val sum = img.get_data().indices map {
          i => {
            img.get_data()(i) + invimg.get_data()(i)
          }
        }
        assert(sum.forall(_ == 255))
      }
    }
    test("twice inverion neutralisation"){
      for (i<- 0 until 10){
        val img= RandomInput(println)
        assert(img==InvertFilter(InvertFilter(img)))
      }
    }
}
