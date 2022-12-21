package Inputs
import DataModel.Image

// Input is function suplied with error function and returning image
abstract class Input extends ((String=>Unit)=>Image)
