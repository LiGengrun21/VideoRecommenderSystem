package com.lgr.recommender

/**
 * @author Li Gengrun
 * @date 2023/3/23 16:11
 */

/**
 * 评分数据集
 * @param userId
 * @param movieId
 * @param score
 * @param timeStamp
 */
case class Ratings(userId: Int, movieId: Int, score: Double, timeStamp: Int)

