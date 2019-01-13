package com.test.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.types._
import org.apache.spark.sql.SparkSession

object JsonRead {

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "D:/work/hadoop/")
    val conf = new SparkConf().setAppName("HelloKafka").setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .getOrCreate()
    import spark.implicits._

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

    val jsonStr="""({    "GUID":"9AAV102172",   "LegalName":"Asea Brown Boveri S.A. Turbocharging",   "LocalLegalName":"Asea Brown Boveri S.A. Turbocharging",   "ShortName":null,   "LocalShortName":null,   "Locale":null,   "ExternalIdentifiers":[   {  "Name":"BvD", "Value":null  }   ],   "FamilyTreeLevel":"Branch",   "ParentHeadquarter":null,   "ConsortiumParent":null,   "ConsortiumLevel":null,   "Comment":[     ],   "WebAddress":null,   "NoWebAddress":true,   "BusinessPartnerType":"Undesignated",   "AccountType":"Internal",   "AccountManagerEmail":null,   "TaxNumbers":[   {  "Name":"VAT number", "Number":"\n  ", "CountryCode":"ES", "Main":true  }   ],   "BusinessPartnerSegment":[     ],   "Channel":"ABBINT",   "ChannelIsDefault":false,   "ChannelChangeDate":"09/01/2019 13:42:03",   "IndustryUsage":"XY.1",   "IndustryUsageIsDefault":false,   "IndustryUsageChangeDate":"09/01/2019 13:42:03",   "Active":true,   "Successor":null,   "SuccessorReason":null,   "Address":[   {  "ABBLocationId":"9AAE100700", "Street":"Calle Ibarra y Cia s/n Darsena", "LocalStreet":"Calle Ibarra y Cia s/n Dársena", "Street2":null, "LocalStreet2":null, "Street3":null, "LocalStreet3":null,"City":"Las Palmas de Gran Canaria", "LocalCity":"Las Palmas de Gran Canaria", "Locale":null, "ZipCode":"35008", "StateCode":"35", "State":"Las Palmas", "LocalState":"Las Palmas", "CountryCode":"ES", "Country":"Spain", "Longitude":null, "Latitude":null, "NoGPS":false, "AddressType":"Legal"  },  {  "ABBLocationId":null, "Street":null, "LocalStreet":null, "Street2":null, "LocalStreet2":null, "Street3":null, "LocalStreet3":null, "City":null, "LocalCity":null, "Locale":null, "ZipCode":null, "StateCode":null, "State":null, "LocalState":null, "CountryCode":null, "Country":null, "Longitude":null, "Latitude":null, "NoGPS":false, "AddressType":"Postal"  }   ],   "IsInternal":true,   "GlobalHeadquarter":null,   "DomesticUltimateHeadquarter":"9AAV102172",   "ChangeDate":"09/01/2019 13:42:03",   "CreationDate":"15/11/2017 19:14:46",   "Status":"Globally Enriched",   "UnclearableReason":null,   "TelephoneNumber":[     ],   "FaxNumber":[     ],   "Email":[     ],   "InternalBusinessPartnerData":{   "AbacusCode":null,  "AbacusLegalName":null,  "CITCode":null,  "CITResponsible":null,  "OwnerCountry":null,  "OwningOrganization":null,  "ProductGroup":null,  "BusinessPartnerLevels":[  { "Level":"Ship To","Parent":"9AAV102190","Address":null }  ],  "AdditionalVATNumber":[    ]   }})"""
    //val df = spark.read.option("multiline", "true").schema(schema).json("D:\\work\\workspace\\SparkTest\\jsondata.txt")
    val df = spark.read.schema(schema).json(Seq(jsonStr.replaceAll("""^[(]+|[)]+$""","")).toDS)
    df.show()
    df.printSchema()
    //val string3="(Hello)"
    //print(string3.replaceAll("""^[(]+|[)]+$""",""))
    
  }
}