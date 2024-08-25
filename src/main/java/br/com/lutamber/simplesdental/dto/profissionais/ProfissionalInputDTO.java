package br.com.lutamber.simplesdental.dto.profissionais;

import br.com.lutamber.simplesdental.entity.profissional.Cargo;
import br.com.lutamber.simplesdental.entity.profissional.Profissional;

import java.time.LocalDate;

public record ProfissionalInputDTO(
    String nome,
    Cargo cargo,
    LocalDate nascimento
) {
}
