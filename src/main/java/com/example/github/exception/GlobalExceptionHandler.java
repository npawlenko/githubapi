package com.example.github.exception;

import com.example.github.provider.MessageProvider;
import com.example.github.resource.dto.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<ClientWebApplicationException> {

	private final MessageProvider messageProvider;

	public GlobalExceptionHandler(MessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}

	@Override
	public Response toResponse(ClientWebApplicationException e) {
		int githubResponseStatus = e.getResponse().getStatus();
		int status = e.getResponse().getStatus() == 404 ? 404 : 500;
		String message = getGithubErrorMessage(githubResponseStatus);
		return Response.status(status)
			.entity(new ErrorResponse(status, message))
			.build();
	}

	private String getGithubErrorMessage(int githubResponseStatus) {
		return messageProvider.getMessageOrDefault("github.error." + githubResponseStatus,
			"github.error.default");
	}
}
