package GrayScaling

// grayscale function from tutorial
object TutorialGrayScaler extends GrayScaler{
  override def apply (color:Int):Double={
    val red = (color & 0xff0000) / 65536
    val green = (color & 0xff00) / 256
    val blue = (color & 0xff)
    ((0.3 * red) + (0.59 * green) + (0.11 * blue))
  }
}
