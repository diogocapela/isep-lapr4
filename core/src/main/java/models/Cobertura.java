package models;

import adapters.Exporter;
import modelsDTO.CoberturaDTO;
import services.CoberturaService;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
public class Cobertura implements Serializable, Exporter {

    // Variáveis de Instância
    //================================================================

    @Transient
    private final CoberturaService cs = new CoberturaService();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Long version;
    @Column(nullable = false, unique = true)
    private String titulo;
    @Column(nullable = false)
    private String descricao;

    // Construtores
    //================================================================

    public Cobertura() {
    }

    public Cobertura(String titulo, String descricao) {
        if (titulo == null || descricao == null) {
            throw new IllegalArgumentException();
        }
        if (cs.existsOnDbByTitle(titulo)) {
            Cobertura t = cs.getCoberturaByTitle(titulo);
            this.titulo = t.titulo;
            this.descricao = t.descricao;
            this.id = t.id;
            this.version = t.version;
        } else {
            this.titulo = titulo;
            this.descricao = descricao;
        }
    }

    // Métodos
    //================================================================

    @Override
    public String toString() {
        return "Cobertura{" +
            "id=" + id +
            ", titulo='" + titulo + '\'' +
            ", descricao='" + descricao + '\'' +
            ", version=" + version +
            '}';
    }

    @Override
    public boolean equals(Object obj) {
        return this.id == ((Cobertura) obj).id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.titulo);
        return hash;
    }

    @Override
    public List<String[]> toExporterDTO() {
        List<String[]> result = new ArrayList<>();
        String[] tituloArray = {titulo, descricao};
        result.add(tituloArray);
        return result;
    }

    public CoberturaDTO toDTO() {
        return new CoberturaDTO(this.titulo, this.descricao);
    }
}
