package com.lgr.recommender

import com.mongodb.casbah.commons.MongoDBObject
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}
import com.mongodb.casbah.{MongoClient, MongoClientURI}
import com.mongodb.spark.MongoSpark

/**
 * @author Li Gengrun
 * @date 2023/3/23 16:07
 */
///**
// * ES的配置信息
// * @param httpHosts
// * @param transportHosts
// * @param index
// * @param clusterName
// */
//case class ESConfig(httpHosts:String, transportHosts:String, index:String, clusterName:String)

object DataLoader {
  //路径常量
  val MOVIE_DATA_PATH = "D:\\IdeaProjects\\VideoRecommenderSystem\\Recommender\\DataLoader\\src\\main\\resources\\movies.csv"
  val RATING_DATA_PATH = "D:\\IdeaProjects\\VideoRecommenderSystem\\Recommender\\DataLoader\\src\\main\\resources\\ratings.csv"
  val TAG_DATA_PATH = "D:\\IdeaProjects\\VideoRecommenderSystem\\Recommender\\DataLoader\\src\\main\\resources\\tags.csv"
  val MONGODB_MOVIE_COLLECTION = "Movie"
  val MONGODB_RATING_COLLECTION = "Rating"
  val MONGODB_TAG_COLLECTION = "Tag"
  val ES_MOVIE_INDEX = "Movie"

  def main(args: Array[String]): Unit = {
//    val config = Map(
//      "spark.cores" -> "local[*]", //本地多线程，尽可能多
//      "mongo.uri" -> "mongodb://localhost:27017/recommender",
//      "mongo.db" -> "recommender",
//      "es.httpHosts" -> "localhost:9200",
//      "es.transportHosts" -> "localhost:9300",
//      "es.index" -> "recommender",
//      "es.cluster.name" -> "elasticsearch"
//    )

    //创建sparkConfig，从config里取spark.cores
    val sparkConfig = new SparkConf().setAppName("DataLoader").setMaster("local[*]")

    //创建sparkSession
    val spark = SparkSession.builder().config(sparkConfig).getOrCreate()

    // 在对 DataFrame 和 Dataset 进行操作许多操作都需要这个包进行支持
    import spark.implicits._

    //加载电影数据
    val movieRDD = spark.sparkContext.textFile(MOVIE_DATA_PATH)
    //将 MovieRDD 装换为 DataFrame
    val movieDF = movieRDD.map(item =>{
      val attr = item.split("\\^")
      Movie(attr(0).toInt,attr(1).trim,attr(2).trim,attr(3).trim,attr(4).trim, attr(5).trim,attr(6).trim,attr(7).trim,attr(8).trim,attr(9).trim)
    }).toDF()

    //加载评分数据
    val ratingRDD=spark.sparkContext.textFile(RATING_DATA_PATH)
    //将 ratingRDD 转换为 DataFrame
    val ratingDF = ratingRDD.map(item => {
      val attr = item.split(",")
      Ratings(attr(0).toInt,attr(1).toInt,attr(2).toDouble,attr(3).toInt)
    }).toDF()

    //加载标签数据
    val tagRDD = spark.sparkContext.textFile(TAG_DATA_PATH)
    //将 tagRDD 装换为 DataFrame
    val tagDF = tagRDD.map(item => {
      val attr = item.split(",")
      Tag(attr(0).toInt,attr(1).toInt,attr(2).trim,attr(3).toInt)
    }).toDF()

    //隐式参数，因为每次都要传
    implicit val mongoConfig = MongoConfig("mongodb://localhost:27017/recommender","recommender")
    //将数据保存到mongoDB
    storeToMongoDB(movieDF, ratingDF, tagDF)

    //将数据保存到ES
    storeToES()

    //关闭sparkSession
    spark.stop()
  }

  //传入隐式参数
  def storeToMongoDB(movieDF: DataFrame, ratingDF:DataFrame, tagDF:DataFrame)(implicit mongoConfig: MongoConfig): Unit ={

    //新建一个到 MongoDB 的连接
    val mongoClient = MongoClient(MongoClientURI(mongoConfig.uri))

    /**
     * dropCollection被弃用
     * 解决方法：将mongodb版本从6.0.5降低到3.4.24
     */
    //将三个旧表删除
    mongoClient(mongoConfig.db)(MONGODB_MOVIE_COLLECTION).dropCollection()
    mongoClient(mongoConfig.db)(MONGODB_TAG_COLLECTION).dropCollection()
    mongoClient(mongoConfig.db)(MONGODB_RATING_COLLECTION).dropCollection()

    //将DF数据写入表中
    /**
     * 将df数据导入mongodb的过程出现重大bug：无法识别com.mongodb.spark.sql.DefaultSource
     * 解决方法：将mongo-spark-connector的版本降低为2.4.0
     */
    movieDF.write
      .option("uri",mongoConfig.uri)                 //哪个数据库
      .option("collection",MONGODB_MOVIE_COLLECTION) //哪张表
      .mode("overwrite")
      .format("com.mongodb.spark.sql.DefaultSource")       //将spark的数据结构写入mongodb
      .save()
    ratingDF.write
      .option("uri",mongoConfig.uri)                 //哪个数据库
      .option("collection",MONGODB_RATING_COLLECTION) //哪张表
      .mode("overwrite")
      .format("com.mongodb.spark.sql.DefaultSource")       //将spark的数据结构写入mongodb
      .save()
    tagDF.write
      .option("uri",mongoConfig.uri)                 //哪个数据库
      .option("collection",MONGODB_TAG_COLLECTION)   //哪张表
      .mode("overwrite")
      .format("com.mongodb.spark.sql.DefaultSource")       //将spark的数据结构写入mongodb
      .save()

    //建立索引
    mongoClient(mongoConfig.db)(MONGODB_MOVIE_COLLECTION).createIndex(MongoDBObject("movieId" -> 1))
    mongoClient(mongoConfig.db)(MONGODB_RATING_COLLECTION).createIndex(MongoDBObject("userId" -> 1))
    mongoClient(mongoConfig.db)(MONGODB_RATING_COLLECTION).createIndex(MongoDBObject("movieId" -> 1))
    mongoClient(mongoConfig.db)(MONGODB_TAG_COLLECTION).createIndex(MongoDBObject("userId" -> 1))
    mongoClient(mongoConfig.db)(MONGODB_TAG_COLLECTION).createIndex(MongoDBObject("movieId" -> 1))

    //关闭 MongoDB 的连接
    mongoClient.close()
  }

  def storeToES(): Unit ={

  }
}
