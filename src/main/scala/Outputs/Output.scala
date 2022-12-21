package Outputs

// output is function with arguments for string to be exported and lambda for errors
trait Output extends ((String,String=>Unit)=>Unit)
