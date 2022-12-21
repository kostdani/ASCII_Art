package ASCIITransformations

import org.scalatest.FunSuite

class LinearTransformationTests extends FunSuite{
  test("replace to one character") {
    val rand = new scala.util.Random
    val char = rand.nextPrintableChar()
    val vec:Vector[Double]=(0 to rand.between(10,20) map {
      _ => rand.nextDouble() * 256
    }).toVector

    val lintransform=new LinearTransformation(char.toString)
    val newvec=vec.map(lintransform)
    assert(newvec.forall(_==char))
  }
}
