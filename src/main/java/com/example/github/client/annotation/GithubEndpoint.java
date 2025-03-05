package com.example.github.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ClientHeaderParam(name = "Accept", value = "application/json")
@ClientHeaderParam(name = "X-Github-Api-Version", value = "2022-11-28")
@ClientHeaderParam(name = "User-Agent", value = "GithubAPIClient")
public @interface GithubEndpoint {
}
