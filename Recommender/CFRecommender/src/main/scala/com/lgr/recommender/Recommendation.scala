package com.lgr.recommender

/**
 * @author Li Gengrun
 * @date 2023/3/27 11:29
 */

/**
 * 单个推荐对象
 * @param movieId
 * @param score
 */
case class Recommendation(movieId:Int, score:Double)
