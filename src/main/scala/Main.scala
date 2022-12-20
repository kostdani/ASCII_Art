package Main
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import scala.collection.mutable.ArrayBuffer
trait ImageLoader extends (()=> BufferedImage)
class FileImageLoader(path:String) extends ImageLoader{
  override def apply():BufferedImage={
    ImageIO.read(new File(path))
  }
}
trait GrayScaler extends (Int => Double)
object TutorialGrayScaler extends GrayScaler{
  override def apply (color:Int):Double={
    val red = (color & 0xff0000) / 65536
    val green = (color & 0xff00) / 256
    val blue = (color & 0xff)
    ((0.3 * red) + (0.59 * green) + (0.11 * blue))
  }
}
trait Transformation extends (Double => Char)
class LinearTransformation(table:String) extends Transformation{
  override def apply(color:Double): Char ={
    table((color*table.length/256).toInt)
  }
}
object PaulBourkeTransformation extends LinearTransformation("$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ")
object ShortPaulBourkeTransformation extends LinearTransformation("@%#*+=-:. ")

trait Output extends ((ArrayBuffer[Char],Int)=>Unit)
object ConsoleOutput extends Output {
  override def apply(buf: ArrayBuffer[Char], width: Int): Unit = {
    var c = 0
    for (i <- 0 until buf.length) {
      if (c == width) {
        c = 0
        print("\n")
      }
      print(buf(i))
      c += 1
    }
  }
}
trait ConsoleUI extends (Array[String]=>Program)
object MyConsoleUI extends ConsoleUI{
  override def apply(args:Array[String]):Program={
    var imageLoader:ImageLoader=new FileImageLoader("/home/user/image.png")
    var grayScaler:GrayScaler=TutorialGrayScaler
    var table:Transformation=PaulBourkeTransformation
    var output:Output=ConsoleOutput

    for (i<-0 until args.length){
      args(i) match {
        case "--image" => imageLoader=new FileImageLoader(args(i+1))
        case "--table" => args(i+1) match{
          case "bourke" => table=PaulBourkeTransformation
          case "bourke-small" => table=ShortPaulBourkeTransformation
        }
        case "--output-console" => output=ConsoleOutput
        case _ =>
      }
    }
    new Program(imageLoader,grayScaler,table,output)
  }
}
class Program(loadImage: ImageLoader, grayScale: GrayScaler, transform: Transformation,output:Output){
  def run(): Unit = {
    val buf = scala.collection.mutable.ArrayBuffer.empty[Char]
    val image = loadImage()
    val w = image.getWidth
    val h = image.getHeight
    for (y <- 0 until h)
      for (x <- 0 until w)
        buf += transform(grayScale(image.getRGB(x, y)))
    output(buf, w)
  }
}


object Main {
  def main(args: Array[String]): Unit = {
    MyConsoleUI(args).run()
  }
}
