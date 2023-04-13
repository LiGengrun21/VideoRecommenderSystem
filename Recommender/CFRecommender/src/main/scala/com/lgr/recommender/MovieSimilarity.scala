package com.lgr.recommender

/**
 * @author Li Gengrun
 * @date 2023/3/27 11:34
 */

/**
 * 电影相似度
 * @param movieId
 * @param recs
 */
case class MovieSimilarity(movieId:Int, recs:Seq[Recommendation])
