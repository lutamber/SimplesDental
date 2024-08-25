package br.com.lutamber.simplesdental.entity.profissional;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "profissionais")
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Setter
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String nome;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private Cargo cargo;

    @Setter
    @Column(nullable = false, columnDefinition = "date")
    private LocalDate nascimento;

    @Setter
    @Column(nullable = false, columnDefinition = "date")
    private LocalDate created_date;

    @Setter
    @Column(columnDefinition = "date")
    private LocalDate deleted_date;

    public Profissional(String nome, Cargo cargo, LocalDate nascimento, LocalDate created_date) {
        this.nome = nome;
        this.cargo = cargo;
        this.nascimento = nascimento;
        this.created_date = created_date;
    }
}
