package br.com.lutamber.simplesdental.dto.contatos;

public record ContatoCreateDTO(
    String nome,
    String contato,
    String profissionalId
) {
}
