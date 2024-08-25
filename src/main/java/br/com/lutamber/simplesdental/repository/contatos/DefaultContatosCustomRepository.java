package br.com.lutamber.simplesdental.repository.contatos;

import br.com.lutamber.simplesdental.entity.contato.Contato;
import br.com.lutamber.simplesdental.exception.InvalidFieldException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class DefaultContatosCustomRepository implements ContatosCustomRepository {

    private final EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public ArrayNode customFindAll(String querySearch, List<String> fields) {
        var objectMapper = new ObjectMapper();
        var node = objectMapper.createArrayNode();

        if(fields.isEmpty())
            return node;

        if(querySearch == null || querySearch.isEmpty())
            querySearch = "";

        String query = """
            SELECT c FROM Contato c
            WHERE (
                LOWER(c.nome) LIKE CONCAT('%%', :querySearch, '%%')
                OR LOWER(c.contato) LIKE CONCAT('%%', :querySearch, '%%')
            )
        """;

        var results = em.createQuery(query, Contato.class)
            .setParameter("querySearch", querySearch.toLowerCase())
            .getResultList().stream().map(
                contato -> convertResultListToCustomResult(objectMapper, fields, contato)
            ).toList();

        node.addAll(results);
        return node;
    }

    private ObjectNode convertResultListToCustomResult(ObjectMapper objectMapper, List<String> fields, Contato data) {
        var iteratorFields = fields.iterator();

        var objectNode = objectMapper.createObjectNode();

        while (iteratorFields.hasNext()) {
            var field = iteratorFields.next();
            try {
                var value = FieldUtils.readField(data, field, true);
                if(value != null)
                    objectNode.put(field, value.toString());
                else
                    objectNode.put(field, "");
            }
            catch(IllegalAccessException e)
            {
                throw new InvalidFieldException("Não foi possível acessar o campo '" + field + "' do contato");
            }
        }

        return objectNode;
    }
}
