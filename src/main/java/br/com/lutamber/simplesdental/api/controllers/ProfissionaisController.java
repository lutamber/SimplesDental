package br.com.lutamber.simplesdental.api.controllers;

import br.com.lutamber.simplesdental.api.APIProfissionais;
import br.com.lutamber.simplesdental.dto.profissionais.ProfissionalInputDTO;
import br.com.lutamber.simplesdental.dto.profissionais.ProfissionalOutputDTO;
import br.com.lutamber.simplesdental.service.profissionais.ProfissionalService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/profissionais", produces = "application/json")
public class ProfissionaisController  implements APIProfissionais {

    private final ProfissionalService profissionalService;

    @Override
    public ResponseEntity<ArrayNode> listAll(String q, List<String> fields
    ) {
        return ResponseEntity.ok().body(this.profissionalService.findAll(q, fields));
    }

    @Override
    public ResponseEntity<ProfissionalOutputDTO> findById(String id) {
        return ResponseEntity.ok().body(this.profissionalService.findById(id));
    }

    @Override
    public void deleteById(String id) {
        this.profissionalService.deleteById(id);
    }

    @Override
    public ResponseEntity<ProfissionalOutputDTO> update(String id, ProfissionalInputDTO body) {
        return ResponseEntity.ok().body(this.profissionalService.update(id, body));
    }

    @Override
    public ResponseEntity<ProfissionalOutputDTO> insert(ProfissionalInputDTO body) {
        return ResponseEntity.ok().body(this.profissionalService.create(body));
    }
}
