package br.com.lutamber.simplesdental.service.contatos;

import br.com.lutamber.simplesdental.dto.contatos.ContatoCreateDTO;
import br.com.lutamber.simplesdental.dto.contatos.ContatoOutputDTO;
import br.com.lutamber.simplesdental.dto.contatos.ContatoUpdateDTO;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.List;

/**
 * Contrato de métodos que o service deve implementar para persistencia
 */
public interface ContatoService {

    ContatoOutputDTO create(ContatoCreateDTO contato);

    ContatoOutputDTO findById(String id);

    ArrayNode findAll(String query, List<String> fields);

    ContatoOutputDTO update(String id, ContatoUpdateDTO contato);

    void deleteById(String id);

}
