package com.ncia.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Wordcount {
  def main(args: Array[String]) {
  
 val conf = new SparkConf().setAppName("WordCount")
 val sc = new SparkContext(conf)
 
 if (args.length < 2) {
 println("Usage: ScalaWordCount <input> <output>")
 System.exit(1)
 }
 val rawData = sc.textFile(args(0))
  
 val words = rawData.flatMap(line => line.split(" "))
  
 val wordCount = words.map(word => (word, 1)).reduceByKey(_ + _)
  
 wordCount.saveAsTextFile(args(1))
 
 sc.stop
 }
}