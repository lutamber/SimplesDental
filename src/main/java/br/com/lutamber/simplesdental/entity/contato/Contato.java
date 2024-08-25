package br.com.lutamber.simplesdental.entity.contato;

import br.com.lutamber.simplesdental.entity.profissional.Profissional;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contatos")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Setter
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String nome;

    @Setter
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String contato;

    @Setter
    @Column(nullable = false, columnDefinition = "date")
    private LocalDate created_date;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;

    public Contato(String nome, String contato, LocalDate created_date) {
        this.nome = nome;
        this.contato = contato;
        this.created_date = created_date;
    }
}
