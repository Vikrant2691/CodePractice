package com.test.spark

import org.apache.spark.SparkConf
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies._
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.HasOffsetRanges
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming._
import java.util.Calendar
import java.text.SimpleDateFormat
import org.apache.spark.sql.SaveMode
import org.apache.spark.TaskContext
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.types._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object KafkaConnect {
  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "D:/work/hadoop/")
    val conf = new SparkConf().setAppName("HelloKafka").setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .getOrCreate()
    import spark.implicits._
    val ssc = new StreamingContext(sc, Seconds(10))

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "test-consumer-group",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean))

    val topics = Array("test")
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      Subscribe[String, String](topics, kafkaParams))

    /*val jsonString=stream.map(record=>(record.value()))
   val df = spark.read.json(Seq(jsonString.toString()).toDS)
   df.show(truncate=false)*/

    val schema = StructType(List(
      StructField("GUID", StringType, true),
      StructField("LegalName", StringType, true),
      StructField("LocalLegalName", StringType, true),
      StructField("ShortName", StringType, true),
      StructField("LocalShortName", StringType, true),
      StructField("Locale", StringType, true),
      StructField("ExternalIdentifiers", ArrayType(StructType(List(StructField("Name", StringType, true), StructField("Value", StringType, true))), true), true),
      StructField("FamilyTreeLevel", StringType, true),
      StructField("ParentHeadquarter", StringType, true),
      StructField("ConsortiumParent", StringType, true),
      StructField("ConsortiumLevel", StringType, true),
      StructField("Comment", ArrayType(StringType, true), true),
      StructField("WebAddress", StringType, true),
      StructField("NoWebAddress", BooleanType, true),
      StructField("BusinessPartnerType", StringType, true),
      StructField("AccountType", StringType, true),
      StructField("AccountManagerEmail", StringType, true),
      StructField("TaxNumbers", ArrayType(StructType(List(
        StructField("CountryCode", StringType, true),
        StructField("Main", BooleanType, true),
        StructField("Name", StringType, true),
        StructField("Number", StringType, true))), true), true),
      StructField("BusinessPartnerSegment", ArrayType(StringType, true), true),
      StructField("Channel", StringType, true),
      StructField("ChannelIsDefault", BooleanType, true),
      StructField("ChannelChangeDate", StringType, true),
      StructField("IndustryUsage", StringType, true),
      StructField("IndustryUsageIsDefault", BooleanType, true),
      StructField("IndustryUsageChangeDate", StringType, true),
      StructField("Active", BooleanType, true),
      StructField("Successor", StringType, true),
      StructField("SuccessorReason", StringType, true),
      StructField("Address", ArrayType(StructType(List(
        StructField("ABBLocationId", StringType, true),
        StructField("Street", StringType, true),
        StructField("LocalStreet", StringType, true),
        StructField("Street2", StringType, true),
        StructField("LocalStreet2", StringType, true),
        StructField("Street3", StringType, true),
        StructField("LocalStreet3", StringType, true),
        StructField("City", StringType, true),
        StructField("LocalCity", StringType, true),
        StructField("Locale", StringType, true),
        StructField("ZipCode", StringType, true),
        StructField("StateCode", StringType, true),
        StructField("State", StringType, true),
        StructField("LocalState", StringType, true),
        StructField("CountryCode", StringType, true),
        StructField("Country", StringType, true),
        StructField("Longitude", StringType, true),
        StructField("Latitude", StringType, true),
        StructField("NoGPS", BooleanType, true),
        StructField("AddressType", StringType, true))), true), true),
      StructField("IsInternal", BooleanType, true),
      StructField("GlobalHeadquarter", StringType, true),
      StructField("DomesticUltimateHeadquarter", StringType, true),
      StructField("ChangeDate", StringType, true),
      StructField("CreationDate", StringType, true),
      StructField("Status", StringType, true),
      StructField("UnclearableReason", StringType, true),
      StructField("TelephoneNumber", ArrayType(StringType, true), true),
      StructField("FaxNumber", ArrayType(StringType, true), true),
      StructField("Email", ArrayType(StringType, true), true),
      StructField("InternalBusinessPartnerData", StructType(List(
        StructField("AbacusCode", StringType, true),
        StructField("AbacusLegalName", StringType, true),
        StructField("AdditionalVATNumber", ArrayType(StringType, true), true),
        StructField("BusinessPartnerLevels", ArrayType(StructType(List(
          StructField("Address", StringType, true),
          StructField("Level", StringType, true),
          StructField("Parent", StringType, true))), true), true),
        StructField("CITCode", StringType, true),
        StructField("CITResponsible", StringType, true),
        StructField("OwnerCountry", StringType, true),
        StructField("OwningOrganization", StringType, true),
        StructField("ProductGroup", StringType, true))), true)))

    val now = Calendar.getInstance.getTime
    val format = new SimpleDateFormat("yyyyMMddHHmmss")
    val date = format.format(now)
    //val buffer = new List<ConsumerRecord<String,String>>;

    spark.sql("set spark.sql.orc.impl=native")

    stream.foreachRDD { rddRaw =>
      val offsetRanges = rddRaw.asInstanceOf[HasOffsetRanges].offsetRanges

      rddRaw.foreachPartition { iter =>
        val o: OffsetRange = offsetRanges(TaskContext.get.partitionId)
        println(s"${o.topic} ${o.partition} ${o.fromOffset} ${o.untilOffset}")
      }
      
      val rdd = rddRaw.map(_.value().replaceAll("""^[(]+|[)]+$""",""))
      val df = spark.read.option("multiline", "true").schema(schema).json(rdd.toDS())
      val df_final=df.withColumn("LoadDate", lit(current_timestamp()))
      
      //df_final.select(col("GUID"),col("LegalName"),col("LocalLegalName"),col("LoadDate"),col("ChangeDate")).write.mode(SaveMode.Append).csv("D:/work/workspace/SparkTest/data/csvdata" + date)
      df_final.write.mode(SaveMode.Append).orc("D:/work/workspace/SparkTest/data/jsondata" + date)
      df_final.show(100)
      
      //stream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
    }

    ssc.start()
    ssc.awaitTermination()

  }
}