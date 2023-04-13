package com.lgr.recommender

/**
 * @author Li Gengrun
 * @date 2023/3/23 16:10
 */

/**
 * 电影数据集
 * @param movieId
 * @param name
 * @param description
 * @param duration
 * @param releaseDate
 * @param shootDate
 * @param language
 * @param genre
 * @param actor
 * @param director
 */
case class Movie(movieId: Int, name: String, description: String, duration: String, releaseDate: String, shootDate: String, language: String, genre: String, actor: String, director: String)

/**
 * 定义三个样例类
 * 样例类：快速定义一个用于保存数据的类，类似 Java POJO
 * Scala会自动创建伴生对象，不用 new 就可以创建方法
 */