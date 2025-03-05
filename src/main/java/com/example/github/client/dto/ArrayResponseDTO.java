package com.example.github.client.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.List;

public record ArrayResponseDTO<T>(@JsonValue List<T> list) {
}
