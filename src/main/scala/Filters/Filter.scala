package Filters
import DataModel.Image

// Stating that filter is just function converting image to image
trait Filter extends (Image=>Image)

