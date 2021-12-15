package sun.dfs.pj.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

@Configuration
public class WebMvcConfig  implements WebMvcConfigurer {
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("120.27.128.225", 9200, "http"))
        );
        return client;
    }
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //允许上传的文件最大值
        DataSize dataSize = DataSize.ofGigabytes(2);
        factory.setMaxFileSize(dataSize); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(dataSize);
        return factory.createMultipartConfig();
    }
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        //设置允许跨域的路径
//        registry.addMapping("/**")
//                //设置允许跨域请求的域名
//                .allowedOrigins("*")
//                //是否允许证书 不再默认开启
//                .allowCredentials(true)
//                //设置允许的方法
//                .allowedMethods("*")
//                //跨域允许时间
//                .maxAge(3600);
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        //设置允许跨域的路径
//        registry.addMapping("/**")
//                //设置允许跨域请求的域名
//                .allowedOrigins("*")
//                //是否允许证书 不再默认开启
//                .allowCredentials(true)
//                //设置允许的方法
//                .allowedMethods("*")
//                //跨域允许时间
//                .maxAge(3600);
        registry.addMapping("/**")
                //http://192.168.137.202:8080/
//                .allowedOrigins("http://localhost:8080")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                //是否允许证书 不再默认开启
                .allowCredentials(true)
                //设置允许的方法
                .allowedMethods("*")
                //跨域允许时间
                .maxAge(3600);
        ;
    }
}
