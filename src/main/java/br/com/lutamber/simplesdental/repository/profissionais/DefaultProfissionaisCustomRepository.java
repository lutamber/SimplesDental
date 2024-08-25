package br.com.lutamber.simplesdental.repository.profissionais;

import br.com.lutamber.simplesdental.entity.profissional.Profissional;
import br.com.lutamber.simplesdental.exception.InvalidFieldException;
import com.fasterxml.jackson.databind.JsonNode;
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
public class DefaultProfissionaisCustomRepository implements ProfissionaisCustomRepository {

    private final EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public ArrayNode customFindAll(String querySearch, final List<String> fields) {

        var objectMapper = new ObjectMapper();
        var node = objectMapper.createArrayNode();

        if(fields.isEmpty())
            return node;

        if(querySearch == null || querySearch.isEmpty())
            querySearch = "";

        String query = """
            SELECT p FROM Profissional p
            WHERE (
                LOWER(p.nome) LIKE CONCAT('%%', :querySearch, '%%')
                OR LOWER(p.cargo) LIKE CONCAT('%%', :querySearch, '%%')
            )
            AND p.deleted_date IS NULL
        """;


        var results = em.createQuery(query, Profissional.class)
            .setParameter("querySearch", querySearch.toLowerCase())
            .getResultList().stream().map(
                profissional -> convertResultListToCustomResult(objectMapper, fields, profissional)
            ).toList();

        node.addAll(results);
        return node;
    }

    private ObjectNode convertResultListToCustomResult(ObjectMapper objectMapper, List<String> fields, Profissional data) {
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
                throw new InvalidFieldException("Não foi possível acessar o campo '"+field+"' do profissional");
            }
        }

        return objectNode;
    }
}
