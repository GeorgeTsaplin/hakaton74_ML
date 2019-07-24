package com.latitude55.bookkeeper.service
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.sql.SparkSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookkeeperService {

  @Autowired private[bookkeeper] val sc:JavaSparkContext = null

  def getPredictions: Array[(Int,Int)] = {
    import org.apache.spark.sql.types.{StructType, LongType, IntegerType, StringType}
    import org.apache.spark.sql.functions.collect_list

    val spark = SparkSession.builder.config(sc.getConf).getOrCreate()

    val ds1SchemaTyped = new StructType()
      .add("id", LongType)
      .add("Год рождения", IntegerType)
      .add("Город", StringType)
      .add("Семейное положение", StringType)
      .add("Род деятельности", StringType)
      .add("Пол", StringType)
      .add("Наличие Вод. удостоверения", IntegerType)
      .add("Количество автомобилей", IntegerType)
      .add("Количество детей", IntegerType)

    val ds1 = spark.read
      .format("csv")
      .option("header", "true")
      .option("sep",";")
      .option("mode", "DROPMALFORMED")
      .option("inferSchema", "true")
      .option("encoding","windows-1251")
      .schema(ds1SchemaTyped)
      .load("hdfs:///localhost//data//dataset1.csv")

    val ds2SchemaTyped = new StructType()
      .add("Услуга", StringType)
      .add("ID_пользователя", LongType)
      .add("ID_услуги", LongType)

    val ds2 = spark.read
      .format("csv")
      .option("header", "true")
      .option("sep",";")
      .option("mode", "DROPMALFORMED")
      .option("inferSchema", "true")
      .option("encoding","windows-1251")
      .schema(ds2SchemaTyped)
      .load("hdfs:///localhost//data//dataset2.csv")

    val user_services = ds2.select("ID_пользователя", "ID_услуги")
    val users_services_grouped = user_services.groupBy("ID_пользователя").agg(collect_list("ID_услуги").as("Услуги"))
    // val users_services_grouped = df.withColumn("Услуги", collect_list("ID_услуги").over(Window.partitionBy("ID_пользователя").orderBy("ID_услуги")))

    val users = ds1.join(users_services_grouped,ds1("id") === users_services_grouped("ID_пользователя")).drop("ID_пользователя").orderBy("id")

    Array((42,42))
  }
}
