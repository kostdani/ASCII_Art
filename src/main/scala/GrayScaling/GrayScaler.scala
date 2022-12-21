package GrayScaling

// Stating that grayscaler is just a function transforming integer pixel in the default RGB color model into double in range of 0-255
trait GrayScaler extends (Int => Double)