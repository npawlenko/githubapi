package com.example.github.client.dto;

import java.util.ArrayList;
import java.util.List;

public record GithubRepositoryDTO(String name, GithubOwnerDTO owner, Boolean fork, List<GithubBranchDTO> branches) {

	public GithubRepositoryDTO {
		if (branches == null) {
			branches = new ArrayList<>();
		}
	}
}
