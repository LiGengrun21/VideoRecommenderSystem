package com.lgr.recommender

import org.apache.spark.SparkConf
import org.apache.spark.mllib.recommendation.{ALS, Rating}
import org.apache.spark.sql.SparkSession

/**
 * @author Li Gengrun
 * @date 2023/3/27 11:17
 */
object CFRecommender {

  val MONGODB_RATING_COLLECTION = "Rating" //评分表
  val REC_RESULT="RecommendationResults"   //每个用户的推荐列表
  val MOVIE_SIMILARITY="MovieSimilarity"   //电影相似度表
  val USER_MAX_RECOMMENDATION=20           //每个用户可以得到的最多推荐数量

  def main(args: Array[String]): Unit = {
//    val config = Map(
//      "spark.cores" -> "local[*]", //本地多线程，尽可能多
//      "mongo.uri" -> "mongodb://localhost:27017/recommender",
//      "mongo.db" -> "recommender"
//    )

    val sparkConfig = new SparkConf().setAppName("CFRecommender").setMaster("local[*]")
    val spark = SparkSession.builder().config(sparkConfig).getOrCreate()
    // 在对 DataFrame 和 Dataset 进行操作许多操作都需要这个包进行支持
    import spark.implicits._
    //隐式参数，因为每次都要传
    implicit val mongoConfig = MongoConfig("mongodb://localhost:27017/recommender","recommender")

    /**
     * 加载数据
     */
    //评分的RDD
    val ratingRDD=spark.read
      .option("uri",mongoConfig.uri)
      .option("collection",MONGODB_RATING_COLLECTION)
      .format("com.mongodb.spark.sql.DefaultSource")
      .load()
      .as[Ratings]  //转为dataset
      .rdd          //转为RDD格式
      .map(rating=>(rating.userId, rating.movieId, rating.score)) //去除timestamp
      .cache()      //持久化到内存

    //用户的RDD
    val userRDD=ratingRDD.map(_._1).distinct() //从ratingRDD中拿到第一个元素，然后去重，就得到了用户id
    //电影的RDD
    val movieRDD=ratingRDD.map(_._2).distinct() //不从movie表里读取的原因：有的电影没有评分记录，所以在算法里不会被推荐，加进去没有意义

    /**
     * 模型训练
     */
    //Rating是机器学习库的一个类，用于推荐模型中，所以要转成这个特定的类
    val trainData=ratingRDD.map(x=>Rating(x._1,x._2,x._3))
    //先根据经验设定参数，最后根据CEEvaluation的运行结果填写最优参数
    val (rank, iteration, lambda) = (200, 5, 0.1)
    //调用算法
    val model = ALS.train(trainData, rank, iteration, lambda)

    /**
     * 预测评分，从而得到个性化推荐列表
     */
    //计算电影和用户的笛卡尔积，得到一个空评分矩阵
    val userMovie=userRDD.cartesian(movieRDD)
    //得到一个RDD列表，每个元素都是Rating类型
    val predictRatings=model.predict(userMovie)

    val recommendationResults=predictRatings
      .filter(_.rating>0)     //过滤，只取评分大于0
      .map(rating=>(rating.user,(rating.product,rating.rating)))
      .groupByKey()
      .map{
        case (userId, recommendation)=>RecommendationList(userId, recommendation.toList.sortWith(_._2>_._2).take(USER_MAX_RECOMMENDATION).map(x=>Recommendation(x._1,x._2)))
      }
      .toDF()   //转成df格式，以便存放到mongodb

    //将协同过滤的推荐结果写入数据库
    recommendationResults.write
      .option("uri",mongoConfig.uri)
      .option("collection",REC_RESULT)
      .mode("overwrite")
      .format("com.mongodb.spark.sql.DefaultSource")
      .save()

    /**
     * 计算相似度矩阵
     *
     * 用于实时推荐（不一定会实现在本系统）
     */

    spark.stop()
  }
}
