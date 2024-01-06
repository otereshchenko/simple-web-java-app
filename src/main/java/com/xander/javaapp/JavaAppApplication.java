package com.xander.javaapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@SpringBootApplication
public class JavaAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaAppApplication.class, args);
    }

    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/", request -> ServerResponse.ok().bodyValue("Welcome to the simple java-app!\n You can use following prefixes:\n/system\n/params?param1=p1&paramN=pN\n/book"))
                .GET("/system", request -> ServerResponse.ok().bodyValue(getBody(request)))
                .GET("/params", request -> ServerResponse.ok().bodyValue("This are your request params: " + request.queryParams()))
                .GET("/book", request -> ServerResponse.ok().bodyValue(List.of(
                        new Book("The Hobbit"),
                        new Book("The Lord of the Rings"),
                        new Book("His Dark Materials")
                )))
                .build();
    }

    private Object getBody(ServerRequest request) {
        return new StringBuilder("\nJava-App Version: 1\n\n")
                .append("Current time:  \n").append(LocalDateTime.now()).append("\n\n")
                .append("Current request uri:  \n").append(request.uri()).append("\n\n*******\n\n")

                .append(printCollection("headers", request.headers().asHttpHeaders()))
                .append(printCollection("cookies", request.cookies()))
                .append(printCollection("system properties", System.getProperties()))
                ;
    }

    private <K, V> StringBuilder printCollection(String collectionName, Map<K, V> map) {
        StringBuilder builder = new StringBuilder(collectionName).append(": \n");
        map.forEach((key, value) -> builder.append(key).append("=").append(value).append("\n"));
        builder.append("\n\n");
        return builder;
    }
}

record Book(String title) {
}