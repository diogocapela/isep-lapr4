package models.avaliacao;

import javafx.util.Pair;
import models.Cobertura;
import modelsDTO.AvaliacaoDTO;
import modelsDTO.CoberturaDTO;
import modelsDTO.ObjetoSeguroDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
public class ObjetoSeguro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Version
    private Long version;
    private String descricao;
    private Double latitude;
    private Double longitude;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Cobertura> coberturas;
    @Embedded
    @Column(nullable = true)
    private Avaliacao avaliacao;

    private String fundamentacao;

    // Construtores
    //================================================================
    public ObjetoSeguro() {
        this.descricao = "";
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.coberturas = new ArrayList<>();
        this.avaliacao = null;
        this.fundamentacao = null;
    }

    public ObjetoSeguro(String descricao, Double latitude, Double longitude, List<Cobertura> coberturas) {
        this.descricao = descricao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.coberturas = new ArrayList<>(coberturas);
        this.avaliacao = null;
        this.fundamentacao = null;
    }

    // Métodos
    //================================================================
    public void copyAttributes(ObjetoSeguro os2) {
        this.descricao = os2.descricao;
        this.latitude = os2.latitude;
        this.longitude = os2.longitude;
        this.coberturas = os2.coberturas;
    }

    public void setFundamentacao(String fundamentacao) {
        this.fundamentacao = fundamentacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public List<Cobertura> getCoberturas() {
        return coberturas;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public Pair<Double, Double> coordenadas() {
        return new Pair<>(this.latitude, this.longitude);
    }

    public List<Cobertura> coberturasDoObjeto() {
        return new ArrayList<>(this.coberturas);
    }

    public void atribuirFundamentacao(String fundamentacao) {
        this.fundamentacao = fundamentacao;
    }

    void atribuirAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ObjetoSeguro other = (ObjetoSeguro) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        return Objects.equals(this.longitude, other.longitude);
    }


    @Override
    public String toString() {
        StringBuilder stringObj = new StringBuilder("Objeto Seguro: ");

        stringObj.append(descricao).
            append(", latitude ").append(latitude).
            append(", longitude ").append(longitude);

        if (avaliacao != null) {
            stringObj.append(avaliacao.toString());
        }

        for (Cobertura c : this.coberturas) {
            stringObj.append(c).append("\n");
        }

        return stringObj.toString();
    }

    public ObjetoSeguroDTO toDTO() {
        List<CoberturaDTO> coberturasDTO = new LinkedList<>();

        for (Cobertura cob : coberturas) {
            coberturasDTO.add(cob.toDTO());
        }

        AvaliacaoDTO avalDTO = this.avaliacao == null ? null : this.avaliacao.toDTO();

        return new ObjetoSeguroDTO(this.descricao, this.latitude, this.longitude, coberturasDTO, avalDTO);
    }
}
