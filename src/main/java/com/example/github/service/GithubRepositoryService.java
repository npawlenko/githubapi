package com.example.github.service;

import com.example.github.client.GithubRestClient;
import com.example.github.client.dto.GithubBranchDTO;
import com.example.github.client.dto.GithubRepositoryDTO;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class GithubRepositoryService {

	private final GithubRestClient githubRestClient;

	public GithubRepositoryService(@RestClient GithubRestClient githubRestClient) {
		this.githubRestClient = githubRestClient;
	}

	public Multi<GithubRepositoryDTO> getNotForkedRepositories(String userName) {
		return getRepositories(userName)
			.filter(repository -> !repository.fork())
			.onItem()
			.transformToUniAndMerge(this::addRepositoryBranches);
	}

	private Multi<GithubRepositoryDTO> getRepositories(String userName) {
		return githubRestClient.getRepositories(userName)
			.onItem()
			.transformToMulti(arrayResponse -> Multi.createFrom().iterable(arrayResponse.list()));
	}

	private Uni<GithubRepositoryDTO> addRepositoryBranches(GithubRepositoryDTO repository) {
		return getBranches(repository)
			.collect().asList()
			.onItem().transform(branches -> {
				repository.branches().addAll(branches);
				return repository;
			});
	}

	private Multi<GithubBranchDTO> getBranches(GithubRepositoryDTO repository) {
		return githubRestClient.getBranches(repository.owner().login(), repository.name())
			.onItem().transformToMulti(
				arrayResponse -> Multi.createFrom().iterable(arrayResponse.list()));
	}
}
