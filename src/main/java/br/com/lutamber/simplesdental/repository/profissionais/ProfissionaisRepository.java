package br.com.lutamber.simplesdental.repository.profissionais;

import br.com.lutamber.simplesdental.entity.profissional.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfissionaisRepository extends JpaRepository<Profissional, String> {
}
