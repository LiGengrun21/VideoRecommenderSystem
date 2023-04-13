package com.lgr.recommender

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

import java.text.SimpleDateFormat
import java.util.Date

/**
 * @author Li Gengrun
 * @date 2023/3/24 15:55
 */
object CommonRecommender {

  //基本表
  val MONGODB_MOVIE_COLLECTION = "Movie"
  val MONGODB_RATING_COLLECTION = "Rating"

  /**
   * 统计数据表（非个性化推荐）
   */
  //最多观看的
  val MOST_VIEWED="MostViewed"
  //最近最多观看的
  val RECENTLY_MOST_VIEWED="RecentlyMostViewed"
  //评价最高的
  val TOP_RATED="TopRated"

  def main(args: Array[String]): Unit = {
//    val config = Map(
//      "spark.cores" -> "local[*]", //本地多线程，尽可能多
//      "mongo.uri" -> "mongodb://localhost:27017/recommender",
//      "mongo.db" -> "recommender"
//    )

    //创建sparkConfig，从config里取spark.cores
    val sparkConfig = new SparkConf().setAppName("CommonRecommender").setMaster("local[*]")

    //创建sparkSession
    val spark = SparkSession.builder().config(sparkConfig).getOrCreate()

    // 在对 DataFrame 和 Dataset 进行操作许多操作都需要这个包进行支持
    import spark.implicits._

    //隐式参数，因为每次都要传
    implicit val mongoConfig = MongoConfig("mongodb://localhost:27017/recommender","recommender")

    //加载数据
    val ratingDF=spark.read
      .option("uri",mongoConfig.uri)
      .option("collection",MONGODB_RATING_COLLECTION)
      .format("com.mongodb.spark.sql.DefaultSource")
      .load()
      .as[Ratings]  //转为dataset
      .toDF()       //转为dataframe
    val movieDF=spark.read
      .option("uri",mongoConfig.uri)
      .option("collection",MONGODB_MOVIE_COLLECTION)
      .format("com.mongodb.spark.sql.DefaultSource")
      .load()
      .as[Movie]  //转为dataset
      .toDF()     //转为dataframe

    //建立一张视图存放df数据
    ratingDF.createOrReplaceTempView("ratings")

    /**
     * 最多观看的电影
     * 数据格式：movieId,count
     */
    //sql语句
    val mostViewedDF=spark.sql("select movieId,count(movieId) as count from ratings group by movieId")
    //写入mongodb
    storeDFtoMongoDB(mostViewedDF,MOST_VIEWED)

    /**
     * 最近最多观看的电影
     */
    //格式化日期
    val simpleDateFormat=new SimpleDateFormat("yyyyMM")
    //注册一个udf，用于将时间戳转化为指定格式
    spark.udf.register("changeDate",(x:Int)=>simpleDateFormat.format(new Date(x*1000L)).toInt)
    //转化时间，并生成一个临时视图，包括电影id、评分、格式化时间
    val ratingTmp=spark.sql("select movieId,score,changeDate(timestamp) as stdDate from ratings")
    ratingTmp.createOrReplaceTempView("ratingTmp")
    //以月份为单位分组
    val mostRecentlyViewedDF=spark.sql("select movieId, count(movieId) as count, stdDate from ratingTmp group by stdDate, movieId order by stdDate desc, count desc")
    //写入mongodb
    storeDFtoMongoDB(mostRecentlyViewedDF,RECENTLY_MOST_VIEWED)

    /**
     * 评分最高的电影
     */
    val topRatedDF=spark.sql("select movieId, avg(score) as average from ratings group by movieId order by average desc")
    storeDFtoMongoDB(topRatedDF,TOP_RATED)

    spark.stop()
  }

  /**
   * 将统计数据存入mongodb
   *
   * 参数传入df数据和表名
   * 隐式传入mongodb的配置
   */
  def storeDFtoMongoDB(df:DataFrame,collectionName:String)(implicit mongoConfig: MongoConfig): Unit ={
    df.write
      .option("uri",mongoConfig.uri)
      .option("collection",collectionName)
      .mode("overwrite")
      .format("com.mongodb.spark.sql.DefaultSource")
      .save()
  }
}
