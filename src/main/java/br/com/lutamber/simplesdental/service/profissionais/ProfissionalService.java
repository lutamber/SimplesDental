package br.com.lutamber.simplesdental.service.profissionais;

import br.com.lutamber.simplesdental.dto.profissionais.ProfissionalInputDTO;
import br.com.lutamber.simplesdental.dto.profissionais.ProfissionalOutputDTO;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.List;

/**
 * Contrato de métodos que o service deve implementar para persistencia
 */
public interface ProfissionalService {

    ProfissionalOutputDTO create(ProfissionalInputDTO profissional);

    ProfissionalOutputDTO findById(String id);

    ArrayNode findAll(String query, List<String> fields);

    ProfissionalOutputDTO update(String id, ProfissionalInputDTO profissional);

    void deleteById(String id);

}
