package workshop6

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import MRUtils.MRUtils.transformXmlToMap
import scala.collection.JavaConverters._
object reputation {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("reputation 10")
    val sc = new SparkContext(conf)
    
    if(args.length < 2){
      System.err.println("Usage : <in> <out>")
      System.exit(2)
    }
    
    val rep = sc.textFile(args(0)).flatMap { x => val row = transformXmlToMap(x).asScala.toMap
      Map(row.get("Id") -> row.get("Reputation").map { Integer.parseInt(_) })
    }.sortBy(_._2, false).take(10)
    val rep10 = sc.parallelize(rep)
    rep10.saveAsTextFile(args(1))
  }
}