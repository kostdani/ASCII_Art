package ASCIITransformations

import org.scalatest.FunSuite

class PaulBourkeTransformationTests extends FunSuite {
  test("Test Paul Burke transformation"){

    val table = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. "
    for (i <- table.indices) {
      assert(table(i) == PaulBourkeTransformation(((i+0.5) / table.length) * 256))
    }
  }
  test("Test Short Paul Burke transformation") {

    val table = "@%#*+=-:. "
    for (i <- table.indices) {
      assert(table(i) == ShortPaulBourkeTransformation(((i + 0.5) / table.length) * 256))
    }
  }
}
