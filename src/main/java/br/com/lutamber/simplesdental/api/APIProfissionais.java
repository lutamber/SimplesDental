package br.com.lutamber.simplesdental.api;

import br.com.lutamber.simplesdental.dto.CustomErrorDTO;
import br.com.lutamber.simplesdental.dto.profissionais.ProfissionalInputDTO;
import br.com.lutamber.simplesdental.dto.profissionais.ProfissionalOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Profissionais")
public interface APIProfissionais {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista todos os profissionais")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso!", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ProfissionalOutputDTO.class))),
        }),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorDTO.class))
        }),
    })
    ResponseEntity<?> listAll(
        @RequestParam(required = false) String q,
        @RequestParam(required = false) List<String> fields);

    @GetMapping(path = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retorna um profissional pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profissional encontrado com sucesso!"),
        @ApiResponse(responseCode = "404", description = "Profissional não encontrado.", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorDTO.class))
        }),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorDTO.class))
        })
    })
    ResponseEntity<?> findById(@PathVariable String id);

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove um profissional pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Profissional removido com sucesso!"),
        @ApiResponse(responseCode = "404", description = "Profissional não encontrado.", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorDTO.class))
        }),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorDTO.class))
        }),
    })
    void deleteById(@PathVariable String id);

    @PutMapping(path = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualiza um profissional pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profissional atualizado com sucesso!"),
        @ApiResponse(responseCode = "404", description = "Profissional não encontrado.", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorDTO.class))
        }),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorDTO.class))
        }),
    })
    ResponseEntity<?> update(@PathVariable String id, @RequestBody ProfissionalInputDTO body);

    @PostMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Insere um novo profissional")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Profissional criado com sucesso!"),
        @ApiResponse(responseCode = "404", description = "Profissional não encontrado.", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorDTO.class))
        }),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorDTO.class))
        }),
    })
    ResponseEntity<?> insert(@RequestBody ProfissionalInputDTO body);

}
