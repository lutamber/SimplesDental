package br.com.lutamber.simplesdental.dto;

import java.time.Instant;

public record CustomErrorDTO(
    Instant timestamp,
    String message
) {
}
