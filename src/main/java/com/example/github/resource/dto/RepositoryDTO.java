package com.example.github.resource.dto;

import java.util.List;

public record RepositoryDTO(String name, String ownerLogin, List<BranchDTO> branches) {

}
