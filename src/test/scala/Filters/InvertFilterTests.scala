package Filters
import org.scalatest.FunSuite
import DataModel.Image
import Inputs.RandomInput
class InvertFilterTests extends FunSuite {
    test("twice inverion neutralisation"){
      for (i<- 0 until 10){
        val img= RandomInput(println)
        assert(img==InvertFilter(InvertFilter(img)))
      }
    }
}
