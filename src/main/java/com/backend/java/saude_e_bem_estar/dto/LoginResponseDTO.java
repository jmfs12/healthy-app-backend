package com.backend.java.saude_e_bem_estar.dto;

public record LoginResponseDTO(
    String token,
    String email
) {}
