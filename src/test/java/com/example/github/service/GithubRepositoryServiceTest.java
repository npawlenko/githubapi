package com.example.github.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.github.client.GithubRestClient;
import com.example.github.client.dto.ArrayResponseDTO;
import com.example.github.client.dto.GithubOwnerDTO;
import com.example.github.client.dto.GithubRepositoryDTO;
import io.smallrye.mutiny.Uni;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GithubRepositoryServiceTest {

	@Mock
	GithubRestClient githubRestClient;

	@InjectMocks
	GithubRepositoryService githubRepositoryService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetNotForkedRepositories_FiltersForksCorrectly() {
		List<GithubRepositoryDTO> repositories = List.of(
			new GithubRepositoryDTO("repo1", new GithubOwnerDTO("user1"), false,
				Collections.emptyList()),
			new GithubRepositoryDTO("repo2", new GithubOwnerDTO("user1"), true,
				Collections.emptyList()),
			new GithubRepositoryDTO("repo3", new GithubOwnerDTO("user1"), false,
				Collections.emptyList())
		);

		when(githubRestClient.getRepositories("testuser"))
			.thenReturn(Uni.createFrom().item(new ArrayResponseDTO<>(repositories)));

		when(githubRestClient.getBranches(any(), any()))
			.thenReturn(Uni.createFrom().item(new ArrayResponseDTO<>(List.of())));

		List<GithubRepositoryDTO> filteredRepositories = githubRepositoryService
			.getNotForkedRepositories("testuser")
			.collect().asList()
			.await().indefinitely();

		assertNotNull(filteredRepositories);
		assertEquals(2, filteredRepositories.size());
		assertTrue(filteredRepositories.stream().noneMatch(GithubRepositoryDTO::fork));
	}
}