package com.lgr.recommender

/**
 * @author Li Gengrun
 * @date 2023/3/23 16:12
 */

/**
 * mongoDB的配置信息
 * @param uri 连接
 * @param db  对应数据库
 */
case class MongoConfig(uri:String, db:String)
