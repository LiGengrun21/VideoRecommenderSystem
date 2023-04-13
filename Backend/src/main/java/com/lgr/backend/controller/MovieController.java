package com.lgr.backend.controller;

import com.lgr.backend.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Li Gengrun
 * @date 2023/4/4 10:40
 */
@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;
}
