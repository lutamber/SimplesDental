package br.com.lutamber.simplesdental.service.contatos;

import br.com.lutamber.simplesdental.dto.contatos.ContatoCreateDTO;
import br.com.lutamber.simplesdental.dto.contatos.ContatoOutputDTO;
import br.com.lutamber.simplesdental.dto.contatos.ContatoUpdateDTO;
import br.com.lutamber.simplesdental.dto.profissionais.ProfissionalInputDTO;
import br.com.lutamber.simplesdental.dto.profissionais.ProfissionalOutputDTO;
import br.com.lutamber.simplesdental.entity.contato.Contato;
import br.com.lutamber.simplesdental.entity.profissional.Profissional;
import br.com.lutamber.simplesdental.exception.InvalidFieldException;
import br.com.lutamber.simplesdental.exception.ResourceNotFoundException;
import br.com.lutamber.simplesdental.repository.contatos.ContatosCustomRepository;
import br.com.lutamber.simplesdental.repository.contatos.ContatosRepository;
import br.com.lutamber.simplesdental.repository.profissionais.ProfissionaisCustomRepository;
import br.com.lutamber.simplesdental.repository.profissionais.ProfissionaisRepository;
import com.fasterxml.jackson.databind.node.ArrayNode;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultContatoService implements ContatoService {

    public static final String NOME_INVALIDO = "Nome inválido";
    public static final String CONTATO_INVALIDO = "Contato inválido";
    public static final String PROFISSIONAL_INVALIDO = "Profissional informado não foi encontrado.";
    public static final String CONTATO_NAO_ENCONTRADO = "Contato não encontrado.";

    private final ContatosRepository contatoRepository;
    private final ContatosCustomRepository contatosCustomRepository;

    private final ProfissionaisRepository profissionaisRepository;

    private final String[]enabledFields = {"id", "nome", "contato", "created_date"};

    @Override
    public ContatoOutputDTO create(ContatoCreateDTO contato) {
        this.validateDTO(contato);

        var profissional = this.profissionaisRepository.findById(contato.profissionalId()).orElseThrow(
            () -> new ResourceNotFoundException(PROFISSIONAL_INVALIDO)
        );

        var entity = this.copyDtoToEntity(contato);
        entity.setProfissional(profissional);

        var output = this.contatoRepository.save(entity);
        return this.entityToDto(output);
    }

    @Override
    public ContatoOutputDTO findById(String id) {
        var entity = this.contatoRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException(CONTATO_NAO_ENCONTRADO)
        );
        return entityToDto(entity);
    }

    @Override
    public ArrayNode findAll(String query, List<String> fields) {

        if(fields == null || fields.isEmpty())
            fields = List.of(this.enabledFields);
        else
            fields.removeIf(field -> Arrays.stream(enabledFields).noneMatch(field::equalsIgnoreCase));

        return this.contatosCustomRepository.customFindAll(query, fields);
    }

    @Override
    public ContatoOutputDTO update(String id, ContatoUpdateDTO contato) {
        try {
            var reference = this.contatoRepository.getReferenceById(id);
            this.copyDtoToEntity(contato, reference);
            this.contatoRepository.save(reference);
            return this.entityToDto(reference);
        }
        catch(EntityNotFoundException e) {
            throw new ResourceNotFoundException(CONTATO_NAO_ENCONTRADO);
        }
    }

    @Override
    public void deleteById(String id) {
        if(!this.contatoRepository.existsById(id))
            throw new ResourceNotFoundException(CONTATO_NAO_ENCONTRADO);

        this.contatoRepository.deleteById(id);
    }

    private Contato copyDtoToEntity(ContatoUpdateDTO dto, Contato entity) {
        entity.setNome(dto.nome());
        entity.setContato(dto.contato());
        return entity;
    }

    private Contato copyDtoToEntity(ContatoCreateDTO dto) {
        var entity = new Contato();
        entity.setNome(dto.nome());
        entity.setContato(dto.contato());
        entity.setCreated_date(LocalDate.now());

        return entity;
    }

    private ContatoOutputDTO entityToDto(Contato contato) {
        return new ContatoOutputDTO(
            contato.getId(),
            contato.getNome(),
            contato.getContato(),
            contato.getCreated_date(),
            contato.getProfissional().getId()
        );
    }

    private void validateDTO(ContatoCreateDTO contato) {
        var profissionalId = contato.profissionalId();
        var name = contato.nome();
        var contatoInfo = contato.contato();

        if(profissionalId == null || profissionalId.isEmpty())
            throw new InvalidFieldException(PROFISSIONAL_INVALIDO);
        if(name.isEmpty() || name.isBlank())
            throw new InvalidFieldException(NOME_INVALIDO);
        if(contatoInfo.isEmpty() || contatoInfo.isBlank())
            throw new InvalidFieldException(CONTATO_INVALIDO);
    }
}
