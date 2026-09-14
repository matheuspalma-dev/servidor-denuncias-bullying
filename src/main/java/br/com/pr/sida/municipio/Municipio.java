package br.com.pr.sida.municipio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "municipios")
@Getter
@Setter
public class Municipio {
    @Id
    @Column(name = "codigo_ibge", nullable = false)
    private Long codigoIbge;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "uf", nullable = false, length = 2)
    private String uf;
}
