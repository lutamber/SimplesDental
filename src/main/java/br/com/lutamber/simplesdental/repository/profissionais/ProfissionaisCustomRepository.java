package br.com.lutamber.simplesdental.repository.profissionais;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.List;

public interface ProfissionaisCustomRepository {
    ArrayNode customFindAll(final String querySearch, final List<String> fields);
}
