package br.com.lutamber.simplesdental.api;

import br.com.lutamber.simplesdental.dto.CustomErrorDTO;
import br.com.lutamber.simplesdental.dto.contatos.ContatoCreateDTO;
import br.com.lutamber.simplesdental.dto.contatos.ContatoOutputDTO;
import br.com.lutamber.simplesdental.dto.contatos.ContatoUpdateDTO;
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

@Tag(name = "Contatos")
public interface APIContatos {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista todos os contatos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso!", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ContatoOutputDTO.class))),
        }),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorDTO.class))
        }),
    })
    ResponseEntity<?> listAll(
        @RequestParam(required = false) String q,
        @RequestParam(required = false) List<String> fields
    );

    @GetMapping(path = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retorna um contato pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contato encontrado com sucesso!"),
        @ApiResponse(responseCode = "404", description = "Contato não encontrado.", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorDTO.class))
        }),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorDTO.class))
        })
    })
    ResponseEntity<?> findById(@PathVariable String id);

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove um contato pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Contato removido com sucesso!"),
        @ApiResponse(responseCode = "404", description = "Contato não encontrado.", content = {
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
    @Operation(summary = "Atualiza um contato pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contato atualizado com sucesso!"),
        @ApiResponse(responseCode = "404", description = "Contato não encontrado.", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorDTO.class))
        }),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorDTO.class))
        }),
    })
    ResponseEntity<?> update(@PathVariable String id, @RequestBody ContatoUpdateDTO body);

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Insere um novo contato")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Contato criado com sucesso!"),
        @ApiResponse(responseCode = "404", description = "Contato não encontrado.", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorDTO.class))
        }),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorDTO.class))
        }),
    })
    ResponseEntity<?> insert(@RequestBody ContatoCreateDTO body);

}
