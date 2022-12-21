package MVC

import ASCIITransformations.{ASCIITransformation, PaulBourkeTransformation}
import DataModel.{Image, NoImage}

trait Model {
    var error=""
    def setError(err:String)
    def getError:String = {
      error
    }

    var image:Image=NoImage
    def getImage: Image = {
      image
    }

    var view:((String,String=>Unit)=>Unit)=>Unit= (_:((String,String=>Unit)=>Unit)=>Unit)=>_
    def setView(f:((String,String=>Unit)=>Unit)=>Unit): Unit = {
      view=f
    }

    var transformation:ASCIITransformation=PaulBourkeTransformation
    def setTransform(transform: ASCIITransformation): Unit
    def getTransform:ASCIITransformation = {
      transformation
    }

    // supplied with function whose argument is error function uses this function to get image
    def input(in:(String=>Unit)=>Image)
    // supplied with function whose arguments are string and eror function uses this function to write out image
    def output(out:(String,String=>Unit)=>Unit)
    // called with function transforming image to image aplies this ufunction to stored image and stores new image
    def action(act: Image => Image)
  }

