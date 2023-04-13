package com.lgr.recommender

/**
 * @author Li Gengrun
 * @date 2023/3/23 16:11
 */


/**
 * 标签数据集
 * @param userId
 * @param movieId
 * @param tag
 * @param timeStamp
 */
case class Tag(userId: Int, movieId: Int, tag: String, timeStamp: Int)
