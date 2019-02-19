package models;

import modelsDTO.EnvolventeDTO;
import services.EnvolventeService;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table
public class Envolvente implements Serializable {

    // Variáveis de Instância
    //================================================================

    private final EnvolventeService es = new EnvolventeService();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Long version;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private Double latitude;
    @Column(nullable = false)
    private Double longitude;

    // Construtores
    //================================================================

    public Envolvente() {
    }

    public Envolvente(String titulo, String descricao, Double latitude, Double longitude) {
        if (titulo == null || descricao == null || latitude == null || longitude == null) {
            throw new IllegalArgumentException();
        }
        if (es.existsOnDbByDescription(descricao)) {
            Envolvente t = es.getEnvolventeByDescription(descricao);
            this.titulo = t.titulo;
            this.descricao = t.descricao;
            this.latitude = t.latitude;
            this.longitude = t.longitude;
            this.id = t.id;
            this.version = t.version;
        } else {
            this.titulo = titulo;
            this.descricao = descricao;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    // Métodos
    //================================================================

    @Override
    public String toString() {
        return "Envolvente{" +
            "id=" + id +
            ", titulo='" + titulo + '\'' +
            ", descricao='" + descricao + '\'' +
            ", version=" + version +
            '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Envolvente other = (Envolvente) obj;
        return Objects.equals(this.titulo, other.titulo);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.titulo);
        return hash;
    }

    public EnvolventeDTO toDTO() {
        return new EnvolventeDTO(this.titulo, this.descricao, this.latitude, this.longitude);
    }


}
