package com.example.github.resource;

import com.example.github.mapper.RepositoryMapper;
import com.example.github.service.GithubRepositoryService;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/github")
@Produces(MediaType.APPLICATION_JSON)
public class GithubRepositoryResource extends AbstractResource {

	private final GithubRepositoryService githubRepositoryService;
	private final RepositoryMapper repositoryMapper;

	public GithubRepositoryResource(GithubRepositoryService githubRepositoryService,
		RepositoryMapper repositoryMapper) {
		this.githubRepositoryService = githubRepositoryService;
		this.repositoryMapper = repositoryMapper;
	}

	@GET
	@Path("/repos/{userName}")
	public Uni<Response> getNotForkedUserRepos(@PathParam("userName") String userName) {
		return githubRepositoryService.getNotForkedRepositories(userName)
			.collect().asList()
			.onItem()
			.transform(repositories -> getResponse(repositories, repositoryMapper::toDomainDTO));
	}
}
