package models.avaliacao;

import models.User;
import modelsDTO.ValidacaoDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
@Table
public class Validacao implements Serializable, Comparable<Validacao> {

    // Variáveis de Instância
    //================================================================
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Version
    private Long version;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataPedido;
    @Column(nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataAtribuicao;
    @OneToOne
    @JoinColumn(nullable = true)
    private User analistaRisco;
    @Column(nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataConclusao;
    @OneToOne(mappedBy = "validacao")
    private Caso caso;

    // Construtores
    //================================================================
    public Validacao() {
        this.dataPedido = new Date();
        this.dataAtribuicao = null;
        this.analistaRisco = null;
        this.dataConclusao = null;
    }

    // Métodos
    //================================================================

    public boolean isPedida() {
        return dataAtribuicao == null && analistaRisco == null && dataConclusao == null;
    }

    public void atribuirValidacao(User analistaRisco) {
        if (isPedida()) {
            this.dataAtribuicao = new Date();
            this.analistaRisco = analistaRisco;
        }
    }


    public Date getDataPedido() {
        return dataPedido;
    }

    public Date getDataAtribuicao() {
        return dataAtribuicao;
    }

    public Date getDataConclusao() {
        return dataConclusao;
    }

    public Boolean isAtribuida() {
        return this.analistaRisco != null && this.dataAtribuicao != null && dataConclusao == null;
    }

    public void concluirValidacao() {
        if (isAtribuida()) {
            this.dataConclusao = new Date();
        }
    }

    public Boolean isValidada() {
        return this.analistaRisco != null && this.dataAtribuicao != null && dataConclusao != null;
    }

    public Long tempoOcorridoEntreDataConclusaoEDataAtribuido() {
        Long resultadoDiferenca = Math.abs(this.dataConclusao.getTime() - this.dataAtribuicao.getTime());
        Long conversaoDiferenca = TimeUnit.SECONDS.convert(resultadoDiferenca, TimeUnit.MILLISECONDS);
        return conversaoDiferenca;
    }

    public Long tempoOcorridoEntreDataAtribuidoeDataActual() {
        Date tmp = new Date();
        Long resultadoDiferenca = Math.abs(this.dataAtribuicao.getTime() - tmp.getTime());
        Long conversaoDiferenca = TimeUnit.SECONDS.convert(resultadoDiferenca, TimeUnit.MILLISECONDS);
        return conversaoDiferenca;
    }

    @Override
    public String toString() {
        String a = "Validacao{" + "dataPedido=" + dataPedido;
        String b = ", dataAtribuicao=" + dataAtribuicao + ", analista de risco =" + analistaRisco;
        String c = ", dataConclusao=" + dataConclusao + '}';

        return a + (dataAtribuicao != null ? a : (dataConclusao != null) ? b : "");
    }

    public ValidacaoDTO toDTO() {
        String strDataPedido = null;
        String strDataAtribuicao = null;
        String strAnalistaEmail = null;
        String strAnalistaNome = null;
        String strDataConclusao = null;
        if (this.dataPedido != null) {
            strDataPedido = this.dataPedido.toString();
        }
        if (this.dataAtribuicao != null) {
            strDataAtribuicao = this.dataAtribuicao.toString();
        }
        if (this.analistaRisco != null) {
            strAnalistaEmail = analistaRisco.toDTO().getEmail();
            strAnalistaNome = analistaRisco.toDTO().getName();
        }
        if (this.dataConclusao != null) {
            strDataConclusao = dataConclusao.toString();
        }
        return new ValidacaoDTO(strDataPedido, strDataAtribuicao, strAnalistaEmail, strAnalistaNome, strDataConclusao);
    }

    @Override
    public int compareTo(Validacao o) {
        return this.dataPedido.compareTo(o.dataPedido);
    }
}
