package com.lgr.backend.controller;

import com.lgr.backend.model.collection.Movie;
import com.lgr.backend.service.MovieService;
import com.lgr.backend.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Li Gengrun
 * @date 2023/4/4 10:40
 */
@Tag(name="Movie",description = "视频模块")
@RestController
@RequestMapping("/movie")
@CrossOrigin("*")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Operation(summary = "电影模糊搜索", description = "通过视频（电影）名称搜索")
    @ResponseBody
    @GetMapping("/search")
    public Result searchMovies(@RequestParam("movieName") String string){
        return movieService.searchMovies(string);
    }

    @Operation(summary = "根据ID获取电影信息",description = "在搜索页面和首页里点击电影，跳转到电影详情页，使用本接口获取改电影的数据,然后展示")
    @ResponseBody
    @GetMapping("/info")
    public Result getMovieInfo(int movieId){
        return movieService.getMovieInfo(movieId);
    }

    @Operation(summary = "获取个性化推荐结果",description = "返回20部电影")
    @ResponseBody
    @GetMapping("/recommendation/cf")
    public Result getCFRec(@RequestParam("userId") int userId){
        return movieService.getCFRec(userId);
    }

    @Operation(summary = "获取最多评价的推荐结果",description = "返回6部电影")
    @ResponseBody
    @GetMapping("/recommendation/mostViewed")
    public Result getMostViewedRec(){
        return movieService.getMostViewedRec();
    }

//    @Operation(summary = "获取最近最多评价的推荐结果",description = "在首页使用")
//    @ResponseBody
//    @GetMapping("/recommendation/recentlyMostViewed")
//    public Result getRecentlyMostViewedRec(){
//        return movieService.getRecentlyMostViewedRec();
//    }

    @Operation(summary = "获取最高评分的推荐结果",description = "返回6部电影")
    @ResponseBody
    @GetMapping("/recommendation/topRated")
    public Result getTopRatedRec(){
        return movieService.getTopRatedRec();
    }

    @Operation(summary = "管理员添加一个新电影",description = "视频管理功能")
    @ResponseBody
    @PostMapping
    public Result add(Movie movie){
        return movieService.add(movie);
    }

//    @Operation(summary = "逻辑删除一个电影",description = "视频管理功能")
//    @ResponseBody
//    @DeleteMapping
//    public Result delete(int movieId){
//        return movieService.logicDelete(movieId);
//    }

    @Operation(summary = "管理员更新一个电影",description = "视频管理功能")
    @ResponseBody
    @PutMapping
    public Result update(Movie movie){
        return movieService.update(movie);
    }

    @Operation(summary = "管理员获取电影列表",description = "视频管理功能")
    @ResponseBody
    @GetMapping("/list")
    public Result getMovieList(){
        return movieService.getMovieList();
    }

}
