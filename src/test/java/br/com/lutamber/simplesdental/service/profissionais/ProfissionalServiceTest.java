package br.com.lutamber.simplesdental.service.profissionais;

import br.com.lutamber.simplesdental.dto.profissionais.ProfissionalInputDTO;
import br.com.lutamber.simplesdental.entity.profissional.Cargo;
import br.com.lutamber.simplesdental.exception.InvalidFieldException;
import br.com.lutamber.simplesdental.exception.ResourceNotFoundException;
import br.com.lutamber.simplesdental.repository.profissionais.DefaultProfissionaisCustomRepository;
import br.com.lutamber.simplesdental.repository.profissionais.ProfissionaisCustomRepository;
import br.com.lutamber.simplesdental.repository.profissionais.ProfissionaisRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.ArrayList;

@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
public class ProfissionalServiceTest {

    @Autowired
    private ProfissionaisRepository profissionalRepository;

    private ProfissionaisCustomRepository profissionaisCustomRepository;
    private ProfissionalService profissionalService;

    @Autowired
    private EntityManager em;

    @BeforeAll
    public void setUp() {
        this.profissionaisCustomRepository = new DefaultProfissionaisCustomRepository(this.em);
        this.profissionalService = new DefaultProfissionalService(this.profissionalRepository, this.profissionaisCustomRepository);
    }

    @BeforeEach
    public void tearDown() {
        this.profissionalRepository.deleteAll();
    }

    @Test
    public void givenAValidProfessional_whenCallToCreateProfessional_thenReturnCreated() {

        var professionalName = "John Doe";
        var professionalPosition = Cargo.Desenvolvedor;
        var professionalBornDate = LocalDate.now().minusYears(20);

        var dto = new ProfissionalInputDTO(
            professionalName,
            professionalPosition,
            professionalBornDate
        );

        var aProfessional = this.profissionalService.create(dto);

        Assertions.assertEquals(professionalName, aProfessional.nome());
        Assertions.assertEquals(professionalPosition, aProfessional.cargo());
        Assertions.assertEquals(professionalBornDate, aProfessional.nascimento());
        Assertions.assertNotNull(aProfessional.id());
        Assertions.assertNotNull(aProfessional.created_date());
        Assertions.assertEquals(aProfessional.created_date(), LocalDate.now());
    }

    @Test
    public void givenAInvalidPosition_whenCallToCreateProfessional_thenThrowsException() {
        var professionalName = "John Doe";
        var professionalBornDate = LocalDate.now().minusYears(20);

        var dto = new ProfissionalInputDTO(
            professionalName,
            null,
            professionalBornDate
        );

        Assertions.assertThrows(DataIntegrityViolationException.class,
            () -> this.profissionalService.create(dto)
        );
    }

    @Test
    public void givenAInvalidEmptyName_whenCallToCreateProfessional_thenThrowsException() {

        var professionalName = " ";
        var professionalPosition = Cargo.Desenvolvedor;
        var professionalBornDate = LocalDate.now().minusYears(20);

        var dto = new ProfissionalInputDTO(
            professionalName,
            professionalPosition,
            professionalBornDate
        );

        Assertions.assertThrows(InvalidFieldException.class,
            () -> this.profissionalService.create(dto)
        );
    }

    @Test
    public void givenAInvalidBornDate_whenCallToCreateProfessional_thenThrowsException() {

        var professionalName = "John Doe";
        var professionalPosition = Cargo.Desenvolvedor;
        var professionalBornDate = LocalDate.now().plusDays(1);

        var dto = new ProfissionalInputDTO(
            professionalName,
            professionalPosition,
            professionalBornDate
        );

        Assertions.assertThrows(InvalidFieldException.class,
            () -> this.profissionalService.create(dto)
        );
    }

    @Test
    public void givenAValidId_whenCallToFindProfessionalById_thenReturnProfessional() {
        var professionalName = "John Doe";
        var professionalPosition = Cargo.Desenvolvedor;
        var professionalBornDate = LocalDate.now().minusYears(20);

        var dto = new ProfissionalInputDTO(
            professionalName,
            professionalPosition,
            professionalBornDate
        );

        var aProfessional = this.profissionalService.create(dto);

        Assertions.assertNotNull(aProfessional.id());

        var finded = this.profissionalService.findById(aProfessional.id());

        Assertions.assertNotNull(finded);
        Assertions.assertEquals(aProfessional.id(), finded.id());
        Assertions.assertEquals(aProfessional.nome(), finded.nome());
        Assertions.assertEquals(aProfessional.cargo(), finded.cargo());
        Assertions.assertEquals(aProfessional.nascimento(), finded.nascimento());
        Assertions.assertEquals(aProfessional.created_date(), finded.created_date());
        Assertions.assertEquals(aProfessional.nascimento(), professionalBornDate);
    }

    @Test
    public void givenAInvalidId_whenCallToFindProfessionalById_thenThrowsException() {

        Assertions.assertThrows(ResourceNotFoundException.class,
            () -> this.profissionalService.findById("invalid")
        );
    }

    @Test
    public void givenNoQueryStr_whenCallToListProfessional_thenReturnProfessional() {
        var professionalName = "John Doe";
        var professionalPosition = Cargo.Desenvolvedor;
        var professionalBornDate = LocalDate.now().minusYears(20);

        var dto = new ProfissionalInputDTO(
            professionalName,
            professionalPosition,
            professionalBornDate
        );

        var aProfessional = this.profissionalService.create(dto);

        Assertions.assertNotNull(aProfessional.id());

        var finded = this.profissionalService.findAll("", null);

        Assertions.assertNotNull(finded);
        Assertions.assertEquals(1, finded.size());
        Assertions.assertEquals(finded.get(0).get("id").asText(), aProfessional.id());
        Assertions.assertEquals(finded.get(0).get("nome").asText(), professionalName);
        Assertions.assertEquals(finded.get(0).get("cargo").asText(), professionalPosition.toString());
        Assertions.assertEquals(finded.get(0).get("nascimento").asText(), professionalBornDate.toString());
    }

    @Test
    public void givenQueryStr_whenCallToListProfessional_thenReturnProfessional() {
        var professionalName = "John Doe";
        var professionalPosition = Cargo.Desenvolvedor;
        var professionalBornDate = LocalDate.now().minusYears(20);

        var dto = new ProfissionalInputDTO(
            professionalName,
            professionalPosition,
            professionalBornDate
        );

        var aProfessional = this.profissionalService.create(dto);

        Assertions.assertNotNull(aProfessional.id());

        var finded = this.profissionalService.findAll("John", null);

        Assertions.assertNotNull(finded);
        Assertions.assertEquals(1, finded.size());
        Assertions.assertEquals(finded.get(0).get("id").asText(), aProfessional.id());
        Assertions.assertEquals(finded.get(0).get("nome").asText(), professionalName);
        Assertions.assertEquals(finded.get(0).get("cargo").asText(), professionalPosition.toString());
        Assertions.assertEquals(finded.get(0).get("nascimento").asText(), professionalBornDate.toString());
    }

    @Test
    public void givenFieldList_whenCallToListProfessional_thenReturnFieldList() {
        var professionalName = "John Doe";
        var professionalPosition = Cargo.Desenvolvedor;
        var professionalBornDate = LocalDate.now().minusYears(20);

        ArrayList<String> fields = new ArrayList<>();
        fields.add("id");
        fields.add("nome");

        var dto = new ProfissionalInputDTO(
            professionalName,
            professionalPosition,
            professionalBornDate
        );

        var aProfessional = this.profissionalService.create(dto);

        Assertions.assertNotNull(aProfessional.id());

        var finded = this.profissionalService.findAll("", fields);

        Assertions.assertNotNull(finded);
        Assertions.assertEquals(1, finded.size());
        Assertions.assertEquals(finded.get(0).get("id").asText(), aProfessional.id());
        Assertions.assertEquals(finded.get(0).get("nome").asText(), professionalName);
        Assertions.assertNull(finded.get(0).get("cargo"));
        Assertions.assertNull(finded.get(0).get("nascimento"));
        Assertions.assertEquals(finded.get(0).size(), 2);
    }

    @Test
    public void givenInvalidId_whenCallToUpdateProfessional_thenThrowsException() {
        var professionalName = "John Doe";
        var professionalPosition = Cargo.Desenvolvedor;
        var professionalBornDate = LocalDate.now().minusYears(20);

        var professionalUpdateName = "Jane Doe";

        var insertDto = new ProfissionalInputDTO(
            professionalName,
            professionalPosition,
            professionalBornDate
        );
        var updateDto = new ProfissionalInputDTO(
            professionalUpdateName,
            professionalPosition,
            professionalBornDate
        );

        var aProfessional = this.profissionalService.create(insertDto);

        Assertions.assertNotNull(aProfessional.id());

        Assertions.assertThrows(ResourceNotFoundException.class,
            () -> this.profissionalService.update("invalid", updateDto)
        );
    }

    @Test
    public void givenValidId_whenCallToUpdateProfessional_thenReturnUpdatedProfessional() {
        var professionalName = "John Doe";
        var professionalPosition = Cargo.Desenvolvedor;
        var professionalBornDate = LocalDate.now().minusYears(20);

        var professionalUpdateName = "Jane Doe";

        var insertDto = new ProfissionalInputDTO(
            professionalName,
            professionalPosition,
            professionalBornDate
        );
        var updateDto = new ProfissionalInputDTO(
            professionalUpdateName,
            professionalPosition,
            professionalBornDate
        );

        var aProfessional = this.profissionalService.create(insertDto);

        Assertions.assertNotNull(aProfessional.id());

        var updatedDto = this.profissionalService.update(aProfessional.id(), updateDto);

        Assertions.assertNotNull(updatedDto.id());
        Assertions.assertEquals(updatedDto.id(), aProfessional.id());
        Assertions.assertEquals(updatedDto.nome(), professionalUpdateName);
        Assertions.assertEquals(updatedDto.cargo(), professionalPosition);
        Assertions.assertEquals(updatedDto.nascimento(), professionalBornDate);
        Assertions.assertEquals(updatedDto.created_date(), aProfessional.created_date());
    }

    @Test
    public void givenValidId_whenCallToDeleteProfessional_thenReturnVoid() {
        var professionalName = "John Doe";
        var professionalPosition = Cargo.Desenvolvedor;
        var professionalBornDate = LocalDate.now().minusYears(20);

        var insertDto = new ProfissionalInputDTO(
            professionalName,
            professionalPosition,
            professionalBornDate
        );

        var aProfessional = this.profissionalService.create(insertDto);

        Assertions.assertNotNull(aProfessional.id());

        Assertions.assertDoesNotThrow(() -> this.profissionalService.deleteById(aProfessional.id()));
    }

    @Test
    public void givenInvalidId_whenCallToDeleteProfessional_thenThrowsException() {
        var professionalName = "John Doe";
        var professionalPosition = Cargo.Desenvolvedor;
        var professionalBornDate = LocalDate.now().minusYears(20);

        var insertDto = new ProfissionalInputDTO(
            professionalName,
            professionalPosition,
            professionalBornDate
        );

        var aProfessional = this.profissionalService.create(insertDto);

        Assertions.assertNotNull(aProfessional.id());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> this.profissionalService.deleteById("invalid"));
    }
}
