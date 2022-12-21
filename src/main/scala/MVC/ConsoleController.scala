package MVC
import ASCIITransformations._
import Filters._
import GrayScaling.TutorialGrayScaler
import Inputs._
import Outputs._

class ConsoleController(args:Array[String],model:Model) {
  var i=0
// checks if n more arguments forward exist, sets error if not
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
    if(args.length==0)
      model.setError("No arguments provided")
    while(i<args.length  ){
      if(model.getError.nonEmpty)
        return ()
      args(i) match{
        case "--help" => model.setError("Usage as follows run --image \"../images/test-image.jpg\" --rotate +90 --scale 0.25 --invert --output-console\n"+
                                        "all commands are worked throu sequentialy one by one\n"+
                                        "possible commands:\n"+
                                        "--image path: loads image\n"+
                                        "--random-image: generates random image with dimensions 10-30\n"+
                                        "--table {bourke|bourke-small}:select one of bourke conversion tables\n"+
                                        "--custom-table seq: use seq as conversion table\n"+
                                        "--output-console: rites image out to console\n"+
                                        "--output-file path: rites image out to file path\n" +
                                        "--scale n: scale to n times\n" +
                                        "--invert: inverts image\n" +
                                        "--{horizontal|vertial}-flip: flips image\n" +
                                        "--help writes this help info out")
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
        case "--horizontal-flip" => model.action(HorizontalFlipFilter)
        case "--vertical-flip" => model.action(VerticalFlipFilter)
        case _ => {
          model.setError("Unknown keyword: " + args(i))
          return ()
        }
      }
      i+=1
    }
  }
}