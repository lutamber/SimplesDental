package br.com.lutamber.simplesdental.dto.profissionais;

import br.com.lutamber.simplesdental.entity.profissional.Cargo;
import br.com.lutamber.simplesdental.entity.profissional.Profissional;

import java.time.LocalDate;

public record ProfissionalOutputDTO(
    String id,
    String nome,
    Cargo cargo,
    LocalDate nascimento,
    LocalDate created_date
) {

}
