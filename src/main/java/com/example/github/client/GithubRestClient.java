package com.example.github.client;

import com.example.github.client.annotation.GithubEndpoint;
import com.example.github.client.dto.ArrayResponseDTO;
import com.example.github.client.dto.GithubBranchDTO;
import com.example.github.client.dto.GithubRepositoryDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://api.github.com")
public interface GithubRestClient {

	@GET
	@Path("/users/{userName}/repos")
	@GithubEndpoint
	Uni<ArrayResponseDTO<GithubRepositoryDTO>> getRepositories(@PathParam("userName") String userName);

	@GET
	@Path("/repos/{userName}/{repositoryName}/branches")
	@GithubEndpoint
	Uni<ArrayResponseDTO<GithubBranchDTO>> getBranches(@PathParam("userName") String userName,
		@PathParam("repositoryName") String repositoryName);
}
