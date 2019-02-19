package models.matriz;

import models.Cobertura;
import models.Envolvente;
import models.FatorRisco;
import models.caracteristica.Contribuicao;
import models.caracteristica.Necessidade;
import models.caracteristica.Peso;
import models.celula.Celula;
import models.celula.CelulaBase;
import models.celula.CelulaCaracterizada;
import models.celula.CelulaDetalhada;
import models.detalhe.Escala;
import modelsDTO.CelulaBaseDTO;
import modelsDTO.CelulaCaracterizadaDTO;
import modelsDTO.CelulaDetalhadaDTO;
import modelsDTO.MatrizDTO;
import settings.Settings;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table
public class Matriz implements IMatriz, Serializable {

    // Variáveis de Instância
    //================================================================

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Long versao;
    @Embedded
    private EstadoMatriz estadoMatriz;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<CelulaBase> celulas;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data;


    // Construtores
    //================================================================

    protected Matriz() {
    }

    public Matriz(List<CelulaBase> celulas) {
        this.celulas = celulas;
        this.estadoMatriz = EstadoMatriz.estadoBase();
    }

    public Long getMatrizID() {
        return this.id;
    }

    // Métodos
    //================================================================

    public Matriz caracterizarMatriz(LinkedList<CelulaBase> ccList) {
        boolean celulaFound;
        if (!estadoMatriz.equals(EstadoMatriz.estadoBase()))
            return null;

        for (Celula c : celulas) {
            celulaFound = false;
            for (Celula cn : ccList) {
                if (c.match(cn)) {
                    celulaFound = true;
                    break;
                }
            }
            if (!celulaFound) {
                return null;
            }
        }

        celulas = ccList;

        estadoMatriz = EstadoMatriz.estadoCaracterizada();

        return this;
    }

    public Matriz detalharMatriz(LinkedList<CelulaDetalhada> cdList) {
        boolean celulaFound;
        if (!estadoMatriz.equals(EstadoMatriz.estadoCaracterizada()))
            return null;

        List<CelulaBase> cdListReal = new LinkedList<>();

        for (Celula c : celulas) {
            celulaFound = false;
            for (CelulaDetalhada cd : cdList) {
                if (c.match(cd)) {
                    celulaFound = true;
                    CelulaCaracterizada cc = (CelulaCaracterizada) c;

                    CelulaCaracterizadaDTO ccDTO = cc.toCaracterizadaDTO();
                    CelulaDetalhadaDTO cdDTO = cd.toDetalhadaDTO();

                    cdListReal.add(
                        new CelulaDetalhada(
                            Escala.criarEscala(
                                cdDTO.getEscala().getEscalaBaixa(),
                                cdDTO.getEscala().getEscalaMedia(),
                                cdDTO.getEscala().getEscalaElevada()
                            ),
                            Peso.criarPeso(ccDTO.getPeso().getPeso()),
                            ccDTO.getContribuicao().getContribuicao().equalsIgnoreCase(Settings.POSITIVA) ?
                                Contribuicao.contribuicaoPositiva() :
                                Contribuicao.contribuicaoNegativa(),
                            ccDTO.getNecessidade().getNecessidade().equalsIgnoreCase(Settings.OBRIGATORIO) ?
                                Necessidade.necessidadeObrigatoria() :
                                Necessidade.necessidadeObrigatoria(),
                            new Cobertura(ccDTO.getCoberturaDTO().getTitulo(), ccDTO.getCoberturaDTO().getDescricao()),
                            new Envolvente(ccDTO.getEnvolventeDTO().getTitulo(), ccDTO.getEnvolventeDTO().getDescricao(), ccDTO.getEnvolventeDTO().getLatitude(), ccDTO.getEnvolventeDTO().getLongitude()),
                            new FatorRisco(ccDTO.getFatorRiscoDTO().getTitulo(), ccDTO.getFatorRiscoDTO().getDescricao())
                        )
                    );
                    break;
                }
            }
            if (!celulaFound) {
                return null;
            }
        }

        celulas = cdListReal;

        estadoMatriz = EstadoMatriz.estadoDetalhada();

        return this;
    }

    public Boolean publicarMatriz() {
        if (!estadoMatriz.equals(EstadoMatriz.estadoDetalhada()))
            return false;

        estadoMatriz = EstadoMatriz.estadoPublicada();
        data = new Date();

        return true;
    }

    public Boolean publicarMatrizDataFutura(Date dataFutura) {
        if (!estadoMatriz.equals(EstadoMatriz.estadoDetalhada()))
            return false;

        data = dataFutura;

        return true;
    }

    public Boolean cancelarMatriz() {
        if (!estadoMatriz.equals(EstadoMatriz.estadoPublicada()))
            return false;

        estadoMatriz = EstadoMatriz.estadoCancelada();

        return true;
    }

    public Matriz rebasearMatriz() {
        if (!isBase()) {
            List<CelulaBase> cbList = new LinkedList<>();

            for (CelulaBase c : celulas) {
                CelulaBaseDTO cDTO = c.toBaseDTO();
                cbList.add(
                    new CelulaBase(
                        new Cobertura(cDTO.getCoberturaDTO().getTitulo(), cDTO.getCoberturaDTO().getDescricao()),
                        new Envolvente(cDTO.getEnvolventeDTO().getTitulo(), cDTO.getEnvolventeDTO().getDescricao(), cDTO.getEnvolventeDTO().getLatitude(), cDTO.getEnvolventeDTO().getLongitude()),
                        new FatorRisco(cDTO.getFatorRiscoDTO().getTitulo(), cDTO.getFatorRiscoDTO().getDescricao())
                    )
                );
            }

            return new Matriz(cbList);
        }
        return null;
    }

    public boolean isBase() {
        return EstadoMatriz.estadoBase().equals(this.estadoMatriz);
    }

    public boolean isCaracterizada() {
        return EstadoMatriz.estadoCaracterizada().equals(this.estadoMatriz);
    }

    public boolean isDetalhada() {
        return EstadoMatriz.estadoDetalhada().equals(this.estadoMatriz);
    }

    public boolean isPublicada() {
        return EstadoMatriz.estadoPublicada().equals(this.estadoMatriz);
    }

    @Override
    public String toString() {
        return "MatrizBase{" +
            "celulas=" + celulas +
            '}';
    }

    @Override
    public List<String[]> toExporterDTO() {
        ArrayList<String[]> result = new ArrayList<>();

        int cellLength =
            isBase() ? Settings.CELULA_BASE_LENGTH :
                isCaracterizada() ? Settings.CELULA_CARATERIZADA_LENGTH :
                    (isDetalhada() || isPublicada()) ? Settings.CELULA_DETALHADA_LENGTH : 0;

        if (cellLength > 0) {
            for (Celula c : celulas) {
                result.add(c.toExporterDTO().get(0));
            }
        }

        return result;
    }

    public MatrizDTO toDTO() {
        List<CelulaBaseDTO> celulasDTO = new LinkedList<>();

        if (estadoMatriz.toString().equalsIgnoreCase(EstadoMatriz.estadoBase().toString())) {
            for (CelulaBase cb : this.celulas) {
                celulasDTO.add(cb.toBaseDTO());
            }
        } else if (estadoMatriz.toString().equalsIgnoreCase(EstadoMatriz.estadoCaracterizada().toString())) {
            for (CelulaBase cb : this.celulas) {
                CelulaCaracterizada cc = (CelulaCaracterizada) cb;
                celulasDTO.add(cc.toCaracterizadaDTO());
            }
        } else {
            for (CelulaBase cb : this.celulas) {
                CelulaDetalhada cd = (CelulaDetalhada) cb;
                celulasDTO.add(cd.toDetalhadaDTO());
            }
        }

        String dataString = data == null ? null : this.data.toString();

        return new MatrizDTO(this.estadoMatriz.toString(), celulasDTO, dataString);
    }
}
