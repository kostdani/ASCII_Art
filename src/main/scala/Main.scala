package Main
import java.awt.image.BufferedImage
import java.io.{BufferedWriter, File, FileWriter}
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
object NoOutput extends Output {
  override def apply(v1: ArrayBuffer[Char], v2: Int): Unit = {
  }
}

object ConsoleOutput extends Output {
  override def apply(buf: ArrayBuffer[Char], width: Int): Unit = {
    var c = 0
    for (i <- buf.indices) {
      if (c == width) {
        c = 0
        print("\n")
      }
      print(buf(i))
      c += 1
    }
  }
}
class FileOutput(path:String) extends Output{
  override def apply(buf: ArrayBuffer[Char], width: Int): Unit = {
    val file = new File(path)
    val bw = new BufferedWriter(new FileWriter(file))
    var c = 0
    for (i <- buf.indices) {
      if (c == width) {
        c = 0
        bw.write("\n")
      }
      bw.write(buf(i))
      c += 1
    }

    bw.close()
  }

}
class MultipleOutput(outputs:ArrayBuffer[Output]) extends Output{
  override def apply(buf: ArrayBuffer[Char], w: Int): Unit = {
    for(i<-outputs.indices){
      outputs(i)(buf,w)
    }
  }
}

trait Program extends (()=>Unit){

}
class GoodProgram(loadImage: ImageLoader, grayScale: GrayScaler, transform: Transformation,output:Output,filter:Filter) extends Program{
  override def apply(): Unit = {
    val buf = scala.collection.mutable.ArrayBuffer.empty[Char]
    val image = loadImage()
    val w = image.getWidth
    val h = image.getHeight
    for (y <- 0 until h)
      for (x <- 0 until w)
        buf += transform(grayScale(image.getRGB(x, y)))
    val t=filter(buf,w)
    output(t._1, t._2)
  }
}
class ErrorProgram(error:String) extends Program{
  override def apply(): Unit = {
    println(error)
  }
}
object HelpProgram extends Program{
  override def apply(): Unit = {
    println("Usage as follows run --image \"../images/test-image.jpg\" --rotate +90 --scale 0.25 --invert --output-console")
  }
}

trait ConsoleUI extends (Array[String]=>Program)
object MyConsoleUI extends ConsoleUI{
  override def apply(args:Array[String]):Program={
    var imageLoader:ImageLoader=null
    var grayScaler:GrayScaler=TutorialGrayScaler
    var table:Transformation=PaulBourkeTransformation
    var outputs=scala.collection.mutable.ArrayBuffer.empty[Output]
    var filters =scala.collection.mutable.ArrayBuffer.empty[Filter]
    outputs+=NoOutput
    filters+=NoFilter
    var skip=0
    for (i<-args.indices){
      if(skip>0){
        skip-=1
      }else {
        args(i) match {
          case "--help" => return HelpProgram
          case "--image" => imageLoader = {
            skip+=1
            new FileImageLoader(args(i + 1))
          }
          case "--table" => {
            skip += 1
            args(i + 1) match {
              case "bourke" => table = PaulBourkeTransformation
              case "bourke-small" => table = ShortPaulBourkeTransformation
              case _ =>
            }
          }
          case "--custom-table" => {
            skip += 1
            table = new LinearTransformation(args(i + 1))
          }
          case "--output-console" => outputs += ConsoleOutput
          case "--output-file" => {
            skip += 1
            outputs += new FileOutput(args(i + 1))
          }
          case "--horizontal-flip" => filters += HorizontalFlipFilter
          case "--vertical-flip" => filters += VerticalFlipFilter
          case "--scale" => {
            skip += 1
            filters += new ScaleFilter(args(i + 1).toDouble)
          }

          case _ => {
            return new ErrorProgram("Unknown keyword: " + args(i))
          }
        }
      }
    }
    if(imageLoader==null)
      return new ErrorProgram("No input file specified")
    new GoodProgram(imageLoader,grayScaler,table,new MultipleOutput(outputs),new SerialFilter(filters))
  }
}

trait Filter extends ((ArrayBuffer[Char],Int)=>(ArrayBuffer[Char],Int))
class ScaleFilter(scale:Double) extends Filter {
  override def apply(arrayBuffer: ArrayBuffer[Char],width:Int): (ArrayBuffer[Char],Int) = {
    if (scale>1){
      val s=scale.ceil.toInt
      var buf=new ArrayBuffer[Char](arrayBuffer.length*s*s)
      for (i <- 0 until arrayBuffer.length*s*s){
        val w = (i % (width*s))/s
        val h = (i / (width*s))/s
        buf+=arrayBuffer(h*width+w)
      }
      (buf,width*s)
    }else{
      val s=(1/scale).toInt
      val height = arrayBuffer.length/width
      val h=(height/s).toInt
      val w=(width/s).toInt
      var buf = new ArrayBuffer[Char](h*w)
      for (i <- 0 until h*w) {
        val x = i % w
        val y:Int = i / w
        buf+= arrayBuffer((s * y * width) + (x * s))
      }
      (buf,w)
    }
  }
}
object HorizontalFlipFilter extends Filter {
  override def apply(arrayBuffer: ArrayBuffer[Char], width: Int): (ArrayBuffer[Char], Int) = {
    var buf = new ArrayBuffer[Char](arrayBuffer.length)
    for (i <-arrayBuffer.indices){
      val w=i%width
      val h=i/width
      buf+=arrayBuffer(h*width+(width-1-w))
    }
    (buf,width)
  }
}
object VerticalFlipFilter extends Filter {
  override def apply(arrayBuffer: ArrayBuffer[Char], width: Int): (ArrayBuffer[Char], Int) = {
    var buf = new ArrayBuffer[Char](arrayBuffer.length)
    for (i <- arrayBuffer.indices) {
      val w = i % width
      val h = i / width
      buf += arrayBuffer(((arrayBuffer.length/width)-1-h)*width+w)
    }
    (buf,width)
  }
}
class SerialFilter(filters:ArrayBuffer[Filter]) extends  Filter{
  override def apply(arrayBuffer: ArrayBuffer[Char], width: Int): (ArrayBuffer[Char], Int) = {
    var buf=arrayBuffer
    var w=width
    for (i<-filters.indices){
      val t=filters(i)(buf,w)
      buf=t._1
      w=t._2
    }
    (buf,w)
  }
}
object NoFilter extends Filter {
  override def apply(arrayBuffer: ArrayBuffer[Char], width: Int): (ArrayBuffer[Char], Int) = {
    (arrayBuffer, width)
  }
}



object Main {
  def main(args: Array[String]): Unit = {
    MyConsoleUI(args)()
  }
}
