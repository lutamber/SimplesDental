package br.com.lutamber.simplesdental.dto.contatos;

import br.com.lutamber.simplesdental.entity.profissional.Cargo;

import java.time.LocalDate;

public record ContatoOutputDTO(
    String id,
    String nome,
    String contato,
    LocalDate created_date,
    String profissionalId
) {
}
