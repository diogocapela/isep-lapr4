package models.avaliacao;

import models.User;
import modelsDTO.CasoDTO;
import modelsDTO.ObjetoSeguroDTO;
import modelsDTO.ValidacaoDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/*
 * Refere-se a um pedido de avaliação de risco concreto;
 * (i.e. para um ou mais objetos seguros específicos e respetivos conjunto de coberturas) bem como o respetivo resultado
 * produzido desse caso.
 */
@Entity
@Table
public class Caso implements Comparable<Caso> {
    /* Processar pedido único de análise de risco: cada pedido é efetuado por um sistema externo
       indicando o tipo e código/identificador do processo que enquadra o pedido (e.g. simulação
       2019.00027), o local ou locais de risco a avaliar e o conjunto de coberturas pretendidas. Cada
       pedido de análise é tratado como um Caso.
    */
    // Variáveis de Instância
    //================================================================
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Long version;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ObjetoSeguro> objetosSegurados;
    private EstadoCaso estado;

    @JoinColumn(name = "VALIDACAO", nullable = true)
    private Validacao validacao;

    private String comentario;

    // Construtores
    //================================================================
    public Caso() {
        this.estado = EstadoCaso.ESPERA;
        this.objetosSegurados = new LinkedList<>();
        this.validacao = null;
        this.comentario = null;
    }

    public Caso(List<ObjetoSeguro> objetosSegurados, EstadoCaso estado) {
        this.objetosSegurados = objetosSegurados;
        if (estado != null) {
            this.estado = estado;
        } else {
            this.estado = EstadoCaso.ESPERA;
        }
        this.validacao = null;
        this.comentario = null;
    }

    public Caso(List<ObjetoSeguro> objetosSegurados, EstadoCaso estado, Validacao validacao) {
        this.objetosSegurados = objetosSegurados;
        if (estado != null) {
            this.estado = estado;
        } else {
            this.estado = EstadoCaso.ESPERA;
        }
        this.validacao = validacao;
    }

    public Caso(String id, Long version, List<ObjetoSeguro> objetosSegurados, EstadoCaso estado) {
        this.objetosSegurados = objetosSegurados;
        if (estado == null) {
            this.estado = estado;
        } else {
            this.estado = EstadoCaso.ESPERA;
        }
        this.validacao = null;
    }

    // Atribuição de Estados
    //================================================================
    public void emProcessamento() {
        if (isEmEspera()) {
            this.estado = EstadoCaso.PROCESSAMENTO;
        }
    }

    public void emValidacao() {
        if (isEmProcessamento()) {
            this.estado = EstadoCaso.VALIDACAO;
            if (this.validacao == null) {
                this.validacao = new Validacao();
            }
        }
    }

    public void processado() {
        if (isEmProcessamento() || isEmValidacao()) {
            this.estado = EstadoCaso.PROCESSADO;
            if (this.validacao != null) {
                this.validacao.concluirValidacao();
            }
        }
    }

    // Verificação de Estados
    //================================================================
    public boolean isEmEspera() {
        return this.estado == EstadoCaso.ESPERA;
    }

    public boolean isEmProcessamento() {
        return this.estado == EstadoCaso.PROCESSAMENTO;
    }

    public boolean isEmValidacao() {
        return this.estado == EstadoCaso.VALIDACAO;
    }

    public boolean isProcessado() {
        return this.estado == EstadoCaso.PROCESSADO;
    }

    public Long getID() {
        return id;
    }

    // Métodos
    //================================================================
    public void copyAttributes(Caso c2) {
        this.estado = c2.estado;
        this.objetosSegurados = c2.objetosSegurados;
        this.validacao = c2.validacao;
    }

    public Date getDataPedido() {
        return this.validacao.getDataPedido();
    }

    public Date getDataAtribuicao() {
        return this.validacao.getDataAtribuicao();
    }

    public Date getDataConclusao() {
        if (validacao != null)
            return this.validacao.getDataConclusao();
        else
            return null;
    }

    public List<ObjetoSeguro> objetosSegurados() {
        return new ArrayList<>(this.objetosSegurados);
    }

    public void atribuirComentario(String comentario) {
        this.comentario = comentario;
        processado();
    }

    public void atribuirFundamentacao(Long objetoSeguroID, String fundamentacao) {
        // TODO : josé carneiro
    }

    public void atribuirAvaliacao(ObjetoSeguro objSeguro, Avaliacao avaliacao) {
        for (ObjetoSeguro obj : this.objetosSegurados) {
            if (obj.equals(objSeguro)) {
                obj.atribuirAvaliacao(avaliacao);
            }
        }
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void atribuirValidacao(User analistaRisco) {
        this.validacao.atribuirValidacao(analistaRisco);
    }

    public boolean isValidacaoAtribuida() {
        if (this.validacao == null) {
            return false;
        }
        return this.validacao.isAtribuida();
    }

    public boolean isValidacaoConcluida() {
        if (this.validacao == null) {
            return false;
        }
        return this.validacao.isValidada();
    }

    public Integer getTotalAvaliacao() {
        Integer total = 0;
        Integer totalObjectos = 0;
        if (this.isProcessado() || this.isEmValidacao()) {
            for (ObjetoSeguro obj : this.objetosSegurados) {
                totalObjectos++;
                Avaliacao aval = obj.getAvaliacao();
                if (aval != null) {
                    total += aval.toDTO().getIndiceRisco();
                }

            }
            return total / totalObjectos;
        }
        return null;
    }

    public Long tempoOcorridoEntreDataConclusaoEDataAtribuido() {
        return this.validacao.tempoOcorridoEntreDataConclusaoEDataAtribuido();
    }

    public Long tempoOcorridoEntreDataAtribuidoeDataActual() {
        return this.validacao.tempoOcorridoEntreDataAtribuidoeDataActual();
    }


    @Override
    public String toString() {
        StringBuilder stringCaso = new StringBuilder("CASO:" + this.id + "\n");

        for (ObjetoSeguro obj : objetosSegurados) {
            stringCaso.append(obj).append("\n");
        }

        return stringCaso.toString();
    }

    public CasoDTO toDTO() {
        // non nullable
        List<ObjetoSeguroDTO> objetosSegurosDTO = new LinkedList<>();
        for (ObjetoSeguro os : objetosSegurados) {
            objetosSegurosDTO.add(os.toDTO());
        }

        // nullable
        ValidacaoDTO validacaoDTO = null;
        if (this.validacao != null) {
            validacaoDTO = validacao.toDTO();
        }
        return new CasoDTO(objetosSegurosDTO, this.estado.toString(), this.id, validacaoDTO);
    }

    public boolean hasValidacao() {
        return this.validacao != null;
    }


    @Override
    public int compareTo(Caso o) {
        return this.validacao.compareTo(o.validacao);
    }
}
