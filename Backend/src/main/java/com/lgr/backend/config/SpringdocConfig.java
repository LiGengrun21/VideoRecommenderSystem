package com.lgr.backend.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringdocConfig {

        /**
         * SpringDoc 标题、描述、版本等信息配置
         *
         * @return openApi 配置信息
         */
        @Bean
        public OpenAPI springDocOpenAPI() {
                return new OpenAPI().info(new Info()
                                .title("视频推荐系统 API")
                                .description("接口文档说明")
                                .version("v0.0.1-SNAPSHOT"))
                        .externalDocs(new ExternalDocumentation()
                                .description("Github项目地址")
                                .url("https://github.com/LiGengrun21/VideoRecommenderSystem"));
        }
}
