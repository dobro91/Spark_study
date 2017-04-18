package workshop2

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import MRUtils.MRUtils.transformXmlToMap
import scala.collection.JavaConverters._

object CommentLength {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Comment Length")
    val sc = new SparkContext(conf)
    
    if(args.length < 2){
      System.err.println("Usage : <in> <out>") 
    }
    
    val Comment = sc.textFile(args(0)).flatMap { transformXmlToMap(_)
      .asScala
      .toMap
      .get("Text")
      .filter { _ != None } }
      .map { _.length() }
      .mean()
      println("===================================================================")
      println(":::::::::::::::::::::::::" +  Comment + ":::::::::::::::::::::::::")
      println("===================================================================")
      
  }
}