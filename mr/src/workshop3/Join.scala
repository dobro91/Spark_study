package workshop3

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import MRUtils.MRUtils.transformXmlToMap
import scala.collection.JavaConverters._
import org.apache.spark.rdd.RDD
object Join {
	def main(args: Array[String]): Unit = {
			val conf = new SparkConf().setAppName("Join User and Comments")
					val sc = new SparkContext(conf)
					if (args.length < 3) {
						println("Usage: Join <user> <comment> <joinType> <output>")
						System.exit(1)
					}
					val User = sc.textFile(args(0)).flatMap { x =>
					val key = transformXmlToMap(x).asScala.toMap.get("Id")
					List(key -> x).filter(x => x._1 != None) }
					val Comment = sc.textFile(args(1)).flatMap { x => 
					val key = transformXmlToMap(x).asScala.toMap.get("UserId")
					List(key -> x).filter(x => x._1 != None) }
					
					if(args(2) == "inner"){
						val result = User.join(Comment).saveAsTextFile(args(3))
					}else if(args(2) == "left"){
						val result = User.leftOuterJoin(Comment).saveAsTextFile(args(3))
					}else if(args(2) == "right"){
						val result = User.rightOuterJoin(Comment).saveAsTextFile(args(3))
					}

	}
}