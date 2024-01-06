package com.xander.javaapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@SpringBootApplication
public class JavaAppApplication {
    @Value("${server.port}")
    private String port;

    private static final String welcomeMessage = """ 
            Welcome to the simple java-app!
            You can use following prefixes:
            /v1
            /v2
            /headers1
            /headers2
            /book
            /system
            /params?param1=p1&paramN=pN
            """;

    public static void main(String[] args) {
        SpringApplication.run(JavaAppApplication.class, args);
    }

    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/", request -> ServerResponse.ok().bodyValue(welcomeMessage))
                .GET("/v1", request -> ServerResponse.ok().bodyValue("Version: v1 : " + port + getRemoteAddress(request)))
                .GET("/v2", request -> ServerResponse.ok().bodyValue("Version: v2 : " + port + getRemoteAddress(request)))
                .GET("/headers1", request -> ServerResponse.ok().bodyValue(printCollection("headers 1", request.headers().asHttpHeaders())))
                .GET("/headers2", request -> ServerResponse.ok().bodyValue(printCollection("headers 2", request.headers().asHttpHeaders())))

                .GET("/system", request -> ServerResponse.ok().bodyValue(getBody(request)))
                .GET("/params", request -> ServerResponse.ok().bodyValue("This are your request params: " + request.queryParams()))
                .GET("/book", request -> ServerResponse.ok().bodyValue(List.of(
                        new Book("The Hobbit"),
                        new Book("The Lord of the Rings"),
                        new Book("His Dark Materials")
                )))
                .build();
    }

    public String getRemoteAddress(ServerRequest request) {
        StringBuilder builder = new StringBuilder();
        Optional<InetSocketAddress> remote = request.remoteAddress();
        remote.ifPresent(r -> {
            builder.append("\n\nRemote address: \n")
                    .append("\nAddress: ").append(r.getAddress())
                    .append("\nPort: ").append(r.getPort())
                    .append("\nHostName: ").append(r.getHostName())
                    .append("\nHostString: ").append(r.getHostString());
            builder.append("\n\ntotal: ").append(r);

        });
        return builder.toString();
    }

    private Object getBody(ServerRequest request) {
        return new StringBuilder("\nJava-App Version: " + port + "\n\n")
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