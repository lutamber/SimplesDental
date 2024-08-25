package br.com.lutamber.simplesdental.api.controllers;

import br.com.lutamber.simplesdental.api.APIContatos;
import br.com.lutamber.simplesdental.dto.contatos.ContatoCreateDTO;
import br.com.lutamber.simplesdental.dto.contatos.ContatoOutputDTO;
import br.com.lutamber.simplesdental.dto.contatos.ContatoUpdateDTO;
import br.com.lutamber.simplesdental.service.contatos.ContatoService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/contatos", produces = "application/json")
public class ContatosController implements APIContatos {

    private final ContatoService contatoService;

    @Override
    public ResponseEntity<ArrayNode> listAll(String q, List<String> fields) {
        return ResponseEntity.ok(this.contatoService.findAll(q, fields));
    }

    @Override
    public ResponseEntity<ContatoOutputDTO> findById(String id) {
        return ResponseEntity.ok(this.contatoService.findById(id));
    }

    @Override
    public void deleteById(String id) {
        this.contatoService.deleteById(id);
    }

    @Override
    public ResponseEntity<ContatoOutputDTO> update(String id, ContatoUpdateDTO body) {
        return ResponseEntity.ok(this.contatoService.update(id, body));
    }

    @Override
    public ResponseEntity<ContatoOutputDTO> insert(ContatoCreateDTO body) {
        return ResponseEntity.ok(this.contatoService.create(body));
    }
}
