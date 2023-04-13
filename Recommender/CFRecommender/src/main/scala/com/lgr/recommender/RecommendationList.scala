package com.lgr.recommender

/**
 * @author Li Gengrun
 * @date 2023/3/27 11:31
 */

/**
 * 单个用户推荐列表
 * @param userId
 * @param recs
 */
case class RecommendationList(userId:Int, recs:Seq[Recommendation])
