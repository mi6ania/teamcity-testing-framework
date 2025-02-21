package com.example.teamcity.api.spec;

import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.User;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


import java.nio.file.attribute.UserPrincipal;
import java.util.List;

public class Specifications {
    private static Specifications spec;

    private Specifications() {};

    public static Specifications getSpec() {
        if (spec == null){
            spec = new Specifications();
        }
        return spec;
    }

    private RequestSpecBuilder regBuilder() {
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setBaseUri("http://" + Config.getProperty("host")).build();
        reqBuilder.setContentType(ContentType.JSON);
        reqBuilder.setAccept(ContentType.JSON);
        reqBuilder.addFilters(List.of(new RequestLoggingFilter(), new ResponseLoggingFilter()));
        return reqBuilder;
        }

        public RequestSpecification unauthSpec() {
            return regBuilder().build();
        }

        public RequestSpecification authSpec(User user) {
            BasicAuthScheme basicAuthScheme = new BasicAuthScheme();
            basicAuthScheme.setUserName(user.getUser());
            basicAuthScheme.setPassword(user.getPassword());

            return regBuilder()
                    .setAuth(basicAuthScheme)
                    .build();

        }
}
