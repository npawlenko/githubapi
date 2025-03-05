package com.example.github.resource;

import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractResource {

	protected <T, R> Response getResponse(List<T> repos, Function<T, R> mappingFunction) {
		return Response.ok(repos.stream().map(mappingFunction)).build();
	}
}
