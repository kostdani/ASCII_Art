package Inputs
import DataModel.{Image, NoImage}
import GrayScaling.GrayScaler

import java.io.File
import javax.imageio.ImageIO
// Using imageio and given grayscaler to get grayscale image
class FileInput(path:String, grayscale:GrayScaler) extends Input {
  override def apply(errl:String=>Unit):Image={
    try {
      val file = new File(path)
      val buf = scala.collection.mutable.ArrayBuffer.empty[Double]
      val bufimg = ImageIO.read(file)
      val w = bufimg.getWidth
      val h = bufimg.getHeight
      for (y <- 0 until h)
        for (x <- 0 until w)
          buf += grayscale(bufimg.getRGB(x, y))
      new Image(buf.toVector, w)
    }
    catch {
      case _:Throwable => {
        errl("Cannot read input file")
        NoImage
      }
    }
  }
}