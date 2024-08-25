package br.com.lutamber.simplesdental.service.contatos;

import br.com.lutamber.simplesdental.dto.contatos.ContatoCreateDTO;
import br.com.lutamber.simplesdental.dto.contatos.ContatoUpdateDTO;
import br.com.lutamber.simplesdental.entity.profissional.Cargo;
import br.com.lutamber.simplesdental.entity.profissional.Profissional;
import br.com.lutamber.simplesdental.exception.InvalidFieldException;
import br.com.lutamber.simplesdental.exception.ResourceNotFoundException;
import br.com.lutamber.simplesdental.repository.contatos.ContatosCustomRepository;
import br.com.lutamber.simplesdental.repository.contatos.ContatosRepository;
import br.com.lutamber.simplesdental.repository.contatos.DefaultContatosCustomRepository;
import br.com.lutamber.simplesdental.repository.profissionais.ProfissionaisRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.ArrayList;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContatoServiceTest {

    @Autowired
    private ContatosRepository contatosRepository;
    @Autowired
    private ProfissionaisRepository profissionaisRepository;

    private ContatosCustomRepository contatosCustomRepository;
    private ContatoService contatoService;

    @Autowired
    private EntityManager em;

    private String professionalId;

    @BeforeAll
    public void setUp() {
        this.contatosCustomRepository = new DefaultContatosCustomRepository(this.em);
        this.contatoService = new DefaultContatoService(this.contatosRepository, this.contatosCustomRepository, this.profissionaisRepository);

        var profissional = new Profissional();
        profissional.setNome("John Doe");
        profissional.setCargo(Cargo.Desenvolvedor);
        profissional.setCreated_date(LocalDate.now());
        profissional.setNascimento(LocalDate.now().minusYears(20));

        var output = this.profissionaisRepository.save(profissional);
        this.professionalId = output.getId();
    }

    @BeforeEach
    public void each() {
        this.contatosRepository.deleteAll();
    }

    @AfterAll
    public void stop() {
        this.contatosRepository.deleteAll();
        this.profissionaisRepository.deleteAll();
    }

    @Test
    public void givenAValidContact_whenCallToCreateContact_thenReturnCreated() {

        var contactName = "Phone";
        var contact = "00 0000-0000";

        var dto = new ContatoCreateDTO(
            contactName,
            contact,
            this.professionalId
        );

        var aContact = this.contatoService.create(dto);

        Assertions.assertEquals(contactName, aContact.nome());
        Assertions.assertEquals(contact, aContact.contato());
        Assertions.assertNotNull(aContact.id());
        Assertions.assertNotNull(aContact.created_date());
        Assertions.assertEquals(LocalDate.now(), aContact.created_date());
        Assertions.assertEquals(this.professionalId, aContact.profissionalId());
    }

    @Test
    public void givenAInvalidEmptyName_whenCallToCreateContact_thenThrowsException() {
        var contactName = " ";
        var contact = "00 0000-0000";

        var dto = new ContatoCreateDTO(
            contactName,
            contact,
            this.professionalId
        );

        Assertions.assertThrows(InvalidFieldException.class,
            () -> this.contatoService.create(dto)
        );
    }

    @Test
    public void givenAInvalidBornDate_whenCallToCreateContact_thenThrowsException() {
        var contactName = "Phone";
        var contact = " ";

        var dto = new ContatoCreateDTO(
            contactName,
            contact,
            this.professionalId
        );

        Assertions.assertThrows(InvalidFieldException.class,
            () -> this.contatoService.create(dto)
        );
    }

    @Test
    public void givenAValidId_whenCallToFindContactById_thenReturnContact() {
        var contactName = "Phone";
        var contact = "00 0000-0000";

        var dto = new ContatoCreateDTO(
            contactName,
            contact,
            this.professionalId
        );

        var aContact = this.contatoService.create(dto);

        Assertions.assertNotNull(aContact.id());
        Assertions.assertEquals(this.professionalId, aContact.profissionalId());

        var outputContact = this.contatoService.findById(aContact.id());

        Assertions.assertNotNull(aContact.id());
        Assertions.assertEquals(contactName, outputContact.nome());
        Assertions.assertEquals(contact, outputContact.contato());
        Assertions.assertEquals(this.professionalId, outputContact.profissionalId());
    }

    @Test
    public void givenAInvalidId_whenCallToFindContactById_thenThrowsException() {

        Assertions.assertThrows(ResourceNotFoundException.class,
            () -> this.contatoService.findById("invalid")
        );
    }

    @Test
    public void givenNoQueryStr_whenCallToListContact_thenReturnContact() {
        var contactName = "Phone";
        var contact = "00 0000-0000";

        var dto = new ContatoCreateDTO(
            contactName,
            contact,
            this.professionalId
        );

        var aContact = this.contatoService.create(dto);

        Assertions.assertNotNull(aContact.id());
        Assertions.assertEquals(this.professionalId, aContact.profissionalId());

        var finded = this.contatoService.findAll("", null);

        Assertions.assertNotNull(finded);
        Assertions.assertEquals(1, finded.size());
        Assertions.assertEquals(finded.get(0).get("id").asText(), aContact.id());
        Assertions.assertEquals(finded.get(0).get("nome").asText(), contactName);
        Assertions.assertEquals(finded.get(0).get("contato").asText(), contact);
    }

    @Test
    public void givenQueryStr_whenCallToListContact_thenReturnContact() {
        var contactName = "Phone";
        var contact = "00 0000-0000";

        var dto = new ContatoCreateDTO(
            contactName,
            contact,
            this.professionalId
        );

        var aContact = this.contatoService.create(dto);

        Assertions.assertNotNull(aContact.id());
        Assertions.assertEquals(this.professionalId, aContact.profissionalId());

        var finded = this.contatoService.findAll("Pho", null);

        Assertions.assertNotNull(finded);
        Assertions.assertEquals(1, finded.size());
        Assertions.assertEquals(finded.get(0).get("id").asText(), aContact.id());
        Assertions.assertEquals(finded.get(0).get("nome").asText(), contactName);
        Assertions.assertEquals(finded.get(0).get("contato").asText(), contact);
    }

    @Test
    public void givenFieldList_whenCallToListContact_thenReturnFieldList() {
        var contactName = "Phone";
        var contact = "00 0000-0000";

        ArrayList<String> fields = new ArrayList<>();
        fields.add("id");
        fields.add("nome");

        var dto = new ContatoCreateDTO(
            contactName,
            contact,
            this.professionalId
        );

        var aContact = this.contatoService.create(dto);

        Assertions.assertNotNull(aContact.id());
        Assertions.assertEquals(this.professionalId, aContact.profissionalId());

        var finded = this.contatoService.findAll("", fields);

        Assertions.assertNotNull(finded);
        Assertions.assertEquals(1, finded.size());
        Assertions.assertEquals(finded.get(0).get("id").asText(), aContact.id());
        Assertions.assertEquals(finded.get(0).get("nome").asText(), aContact.nome());
        Assertions.assertNull(finded.get(0).get("contato"));
        Assertions.assertNull(finded.get(0).get("created_date"));
        Assertions.assertEquals(finded.get(0).size(), 2);
    }

    @Test
    public void givenInvalidId_whenCallToUpdateContact_thenThrowsException() {
        var contactName = "Phone";
        var contact = "00 0000-0000";

        var updateName = "Smartphone";

        var insertDto = new ContatoCreateDTO(
            contactName,
            contact,
            this.professionalId
        );
        var updateDto = new ContatoUpdateDTO(
            updateName,
            contact
        );

        var output = this.contatoService.create(insertDto);

        Assertions.assertNotNull(output.id());

        Assertions.assertThrows(ResourceNotFoundException.class,
            () -> this.contatoService.update("invalid", updateDto)
        );
    }

    @Test
    public void givenValidId_whenCallToUpdateContact_thenReturnUpdatedContact() {
        var contactName = "Phone";
        var contact = "00 0000-0000";

        var updateName = "Smartphone";

        var insertDto = new ContatoCreateDTO(
            contactName,
            contact,
            this.professionalId
        );
        var updateDto = new ContatoUpdateDTO(
            updateName,
            contact
        );

        var output = this.contatoService.create(insertDto);

        Assertions.assertNotNull(output.id());

        var updatedDto = this.contatoService.update(output.id(), updateDto);

        Assertions.assertNotNull(updatedDto.id());
        Assertions.assertEquals(updatedDto.id(), output.id());
        Assertions.assertEquals(updatedDto.nome(), updateName);
        Assertions.assertEquals(updatedDto.created_date(), output.created_date());
    }

    @Test
    public void givenValidId_whenCallToDeleteContact_thenReturnVoid() {
        var contactName = "Phone";
        var contact = "00 0000-0000";

        var dto = new ContatoCreateDTO(
            contactName,
            contact,
            this.professionalId
        );

        var aContact = this.contatoService.create(dto);

        Assertions.assertNotNull(aContact.id());
        Assertions.assertEquals(this.professionalId, aContact.profissionalId());

        Assertions.assertDoesNotThrow(() -> this.contatoService.deleteById(aContact.id()));
    }

    @Test
    public void givenInvalidId_whenCallToDeleteContact_thenThrowsException() {
        var contactName = "Phone";
        var contact = "00 0000-0000";

        var dto = new ContatoCreateDTO(
            contactName,
            contact,
            this.professionalId
        );

        var aContact = this.contatoService.create(dto);

        Assertions.assertNotNull(aContact.id());
        Assertions.assertEquals(this.professionalId, aContact.profissionalId());

        Assertions.assertThrows(ResourceNotFoundException.class,
            () -> this.contatoService.deleteById("invalid")
        );
    }
}
