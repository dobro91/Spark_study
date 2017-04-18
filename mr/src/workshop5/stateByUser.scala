package workshop5

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import MRUtils.MRUtils.transformXmlToMap
import scala.collection.JavaConverters._
object stateByUser {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("State By User")
    val sc = new SparkContext(conf)
    
    if(args.length < 2){
      System.err.println("Usage : <in> <out>")
      System.exit(2)
    }
    
    val state =  Set( "AL", "AK", "AZ", "AR",
				"CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN",
				"IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS",
				"MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND",
				"OH", "OK", "OR", "PA", "RI", "SC", "SF", "TN", "TX", "UT",
				"VT", "VA", "WA", "WV", "WI", "WY" )
				
		val StateByUser = sc.textFile(args(0)).flatMap {
      transformXmlToMap(_).asScala.toMap.get("Location")
      .filter { _ != None }
      }
    .flatMap { _.toUpperCase().split("\\s") }
    .filter { state.contains(_) }
    .map { word => (word, 1) }
    .reduceByKey(_+_)
    .saveAsTextFile(args(1))
  }
}
