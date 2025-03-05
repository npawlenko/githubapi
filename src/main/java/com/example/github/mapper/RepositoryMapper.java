package com.example.github.mapper;

import com.example.github.client.dto.GithubRepositoryDTO;
import com.example.github.resource.dto.RepositoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta-cdi", uses = BranchMapper.class)
public interface RepositoryMapper {
	@Mapping(target = "ownerLogin", source = "owner.login")
	RepositoryDTO toDomainDTO(GithubRepositoryDTO repo);
}
