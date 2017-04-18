package workshop3

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import MRUtils.MRUtils.transformXmlToMap
import scala.collection.JavaConverters._
import org.apache.spark.sql._

object JoinDF {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Join Using DataFrame")
    val sc = new SparkContext(conf)
    val spark: SparkSession = SparkSession.builder().appName("Join Dataframe").getOrCreate()
    import spark.implicits._
    
    if(args.length < 4){
      System.err.println("usage : <user> <comment> <join type> <out>")
      System.exit(2)
    }
    
    val users = sc.textFile(args(0)).map { line => transformXmlToMap(line).asScala.toMap }
  }
}