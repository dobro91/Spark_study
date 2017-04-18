package workshop4

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import MRUtils.MRUtils.transformXmlToMap
import scala.collection.JavaConverters._

object DistinctUser {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Distinct User")
    val sc = new SparkContext(conf)
    
    if(args.length < 2){
      System.err.println("Usage : <in> <out>")
      System.exit(2)
    }
    
    val distinctUser = sc.textFile(args(0)).flatMap { transformXmlToMap(_).asScala.toMap
      .get("UserId")
      .filter { _ != None } 
    }.distinct().saveAsTextFile(args(1))
  }
}