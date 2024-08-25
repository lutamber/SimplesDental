package br.com.lutamber.simplesdental.service.profissionais;

import br.com.lutamber.simplesdental.dto.profissionais.ProfissionalInputDTO;
import br.com.lutamber.simplesdental.dto.profissionais.ProfissionalOutputDTO;
import br.com.lutamber.simplesdental.entity.profissional.Profissional;
import br.com.lutamber.simplesdental.exception.InvalidFieldException;
import br.com.lutamber.simplesdental.exception.ResourceNotFoundException;
import br.com.lutamber.simplesdental.repository.profissionais.ProfissionaisCustomRepository;
import br.com.lutamber.simplesdental.repository.profissionais.ProfissionaisRepository;
import com.fasterxml.jackson.databind.node.ArrayNode;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class DefaultProfissionalService implements ProfissionalService {

    public static final String NOME_INVALIDO = "Nome inválido";
    public static final String DATA_NASCIMENTO_INVALIDA = "Data de nascimento inválida";
    public static final String PROFISSIONAL_NAO_ENCONTRADO = "Profissional não encontrado.";
    private final String[]enabledFields = {"id", "nome", "cargo", "nascimento", "created_date"};

    private final ProfissionaisRepository profissionaisRepository;
    private final ProfissionaisCustomRepository profissionaisCustomRepository;

    @Override
    @Transactional
    public ProfissionalOutputDTO create(final ProfissionalInputDTO profissional) {
        this.validateDTO(profissional);

        var entity = this.copyDtoToEntity(profissional);
        entity = this.profissionaisRepository.save(entity);

        return this.entityToDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfissionalOutputDTO findById(final String id) {
        var entity = this.profissionaisRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException(PROFISSIONAL_NAO_ENCONTRADO)
        );
        return this.entityToDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ArrayNode findAll(final String query, List<String> fields) {
        if(fields == null || fields.isEmpty())
            fields = List.of(this.enabledFields);
        else
            fields.removeIf(field -> Arrays.stream(enabledFields).noneMatch(field::equalsIgnoreCase));

        return this.profissionaisCustomRepository.customFindAll(query, fields);
    }

    @Override
    @Transactional
    public ProfissionalOutputDTO update(final String id, final ProfissionalInputDTO profissional) {
        try {
            var entity = this.profissionaisRepository.getReferenceById(id);
            this.copyDtoToEntity(profissional, entity);
            entity = this.profissionaisRepository.save(entity);
            return this.entityToDto(entity);
        }
        catch(EntityNotFoundException e) {
            throw new ResourceNotFoundException(PROFISSIONAL_NAO_ENCONTRADO);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(final String id) {
        var entity = this.profissionaisRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException(PROFISSIONAL_NAO_ENCONTRADO)
        );
        entity.setDeleted_date(LocalDate.now());
        this.profissionaisRepository.save(entity);
    }

    private Profissional copyDtoToEntity(ProfissionalInputDTO dto, Profissional entity) {
        entity.setNome(dto.nome());
        entity.setNascimento(dto.nascimento());
        entity.setCargo(dto.cargo());
        return entity;
    }

    private Profissional copyDtoToEntity(ProfissionalInputDTO dto) {
        var entity = new Profissional();
        entity.setNome(dto.nome());
        entity.setNascimento(dto.nascimento());
        entity.setCargo(dto.cargo());
        entity.setCreated_date(LocalDate.now());
        return entity;
    }

    private ProfissionalOutputDTO entityToDto(Profissional profissional) {
        return new ProfissionalOutputDTO(
            profissional.getId(),
            profissional.getNome(),
            profissional.getCargo(),
            profissional.getNascimento(),
            profissional.getCreated_date()
        );
    }

    private void validateDTO(ProfissionalInputDTO profissional) {
        var name = profissional.nome();
        var nascimento = profissional.nascimento();

        if(name.isEmpty() || name.isBlank())
            throw new InvalidFieldException(NOME_INVALIDO);
        if(nascimento == null || nascimento.isBefore(LocalDate.now()))
            throw new InvalidFieldException(DATA_NASCIMENTO_INVALIDA);
    }
}
