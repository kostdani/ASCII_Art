import MVC._
object Main {
  def main(args: Array[String]): Unit = {
    val model=new ModelImpl
    val view=new View(model)
    val controller=new ConsoleController(args,model)
    model.setView(view)
    controller.run()
  }
}

