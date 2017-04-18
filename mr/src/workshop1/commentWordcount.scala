package workshop1

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import MRUtils.MRUtils.transformXmlToMap
import scala.collection.JavaConverters._
import org.apache.commons.lang.StringEscapeUtils;

object commentWordcount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("comment Word count")
    val sc = new SparkContext(conf)
    
    if(args.length != 2){
      Console.err.println("Usage: Comment Word Count <in> <out>")
      System.exit(1)
    }
    
    val comment = sc.textFile(args(0)).flatMap { x => transformXmlToMap(x).asScala.toMap.get("Text") }
    .flatMap { line => StringEscapeUtils.escapeHtml(line.toLowerCase())
      .replaceAll("'", "")
      .replaceAll("[^a-zA-Z]", " ")
      .split("\\s") }
    .map { word => (word, 1) }
    .reduceByKey(_ + _).sortBy(_._2, false)    
    .saveAsTextFile(args(1))
    
    sc.stop
    
  }
  
}