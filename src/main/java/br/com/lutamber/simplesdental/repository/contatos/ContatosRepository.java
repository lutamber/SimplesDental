package br.com.lutamber.simplesdental.repository.contatos;

import br.com.lutamber.simplesdental.entity.contato.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatosRepository extends JpaRepository<Contato, String> {

}
