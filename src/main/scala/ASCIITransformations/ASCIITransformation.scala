package ASCIITransformations
// ASCII tranformation table is just a function transforming Double between 0 and 256 to char
trait ASCIITransformation extends (Double => Char)
