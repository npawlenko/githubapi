package com.example.github.mapper;

import com.example.github.client.dto.GithubBranchDTO;
import com.example.github.resource.dto.BranchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta-cdi")
public interface BranchMapper {
	@Mapping(target = "lastCommitSha", source = "commit.sha")
	BranchDTO toDomainDTO(GithubBranchDTO branch);
}
