package MVC
import ASCIITransformations._
import Filters._
import GrayScaling.TutorialGrayScaler
import Inputs._
import Outputs._

class ConsoleController(args:Array[String],model:Model) {
  var i=0

  def forward(n: Int): Boolean = {

    if (i + n >= args.length) {
      model.setError("Wrong command expected more arguments")
      false
    } else {
      i += n
      true
    }
  }
  def run(): Unit = {
    while(i<args.length  ){
      args(i) match{
        case "--image" =>
          if (!forward(1)) {
            return
          }
          model.input(new FileInput(args(i),TutorialGrayScaler))
        case "--random-image" => {
          model.input(RandomInput)
        }
        case "--table" => {
          if (!forward(1)) return
          args(i) match {
            case "bourke" => model.setTransform(PaulBourkeTransformation)
            case "bourke-small" => model.setTransform(ShortPaulBourkeTransformation)
            case _ =>
          }
        }
        case "--custom-table" => {
          if (!forward(1)) return
          model.setTransform( new LinearTransformation(args(i)))
        }
        case "--output-console" => model.output(ConsoleOutput)
        case "--output-file" => {
          if (!forward(1)) return
          model.output(new FileOutput(args(i)))
        }
        case "--invert" => model.action(InvertFilter)
        case "--scale" => {
          if (!forward(1)) return
          model.action(new ScaleFilter(args(i).toDouble))
        }
        case _ => {
          model.setError("Unknown keyword: " + args(i))
          return ()
        }
      }
      i+=1
    }
  }
}