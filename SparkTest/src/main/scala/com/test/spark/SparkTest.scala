package com.test.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{ array, col, explode, lit, struct }
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window


object SparkTest {
  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "D:/work/hadoop/")
    val conf = new SparkConf().setAppName("SparkTest").setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .getOrCreate()

    val df = spark.read.option("header", "true").csv("D:/test/test.txt")
      
      //df.show()
    def transposeUDF(transDF: DataFrame, transBy: Seq[String]): DataFrame = {
      val (cols, types) = transDF.dtypes.filter { case (c, _) => !transBy.contains(c) }.unzip
      require(types.distinct.size == 1)

      val kvs = explode(array(
        cols.map(c => struct(lit(c).alias("column_name"), col(c).alias("column_value"))): _*))

      val byExprs = transBy.map(col(_))

      transDF
        .select(byExprs :+ kvs.alias("_kvs"): _*)
        .select(byExprs ++ Seq(col("_kvs.column_name"), col("_kvs.column_value")): _*)
    }

    val df_trans=transposeUDF(df, Seq("code"))
    
    
    val df_nn=df_trans.select(col("code").cast(org.apache.spark.sql.types.IntegerType),col("column_value").cast(org.apache.spark.sql.types.IntegerType)).filter(col("column_value").isNotNull).withColumn("column_name",concat(lit("col"),rank().over(Window.partitionBy("code").orderBy("column_value")))).sort(col("code"), col("column_value"))
    
    val result = df_nn.groupBy("code")
    .pivot("column_name")
    .agg(expr("coalesce(first(column_value))"))

    result.show()
    
  }
}