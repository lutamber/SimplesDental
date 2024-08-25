package br.com.lutamber.simplesdental.repository.contatos;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.List;

public interface ContatosCustomRepository {
    ArrayNode customFindAll(final String querySearch, final List<String> fields);
}
