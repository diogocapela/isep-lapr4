package repository;

import georef.utils.LocationInfos;
import models.avaliacao.Caso;
import models.avaliacao.ObjetoSeguro;
import modelsDTO.CasoDTO;
import settings.Settings;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class CasoRepository extends JPARepository<Caso, Long> implements Serializable {

    @Override
    protected String persistenceUnitName() {
        return Settings.DB_PU;
    }

    public List<Caso> fetchCasesNotProcessing() {
        List<Caso> result = new LinkedList<>();

        for (Caso cTmp : findAll()) {
            if (!cTmp.isEmProcessamento()) {
                result.add(cTmp);
            }
        }
        return result;
    }

    public List<Caso> fetchCasesEmValidacaoNaoAtribuidos() {
        List<Caso> result = new LinkedList<>();

        for (Caso caso : findAll()) {
            if (caso.isEmValidacao() && !caso.isValidacaoAtribuida()) {
                result.add(caso);
            }
        }

        return result;
    }

    public List<Caso> fetchCasosValidadosPorAnalista(String nomeAnalista) {
        List<Caso> result = new LinkedList<>();
        //Para cada caso que esteja processado e validado
        for (Caso caso : findAll()) {
            detach(caso);
            if (caso.isProcessado() && caso.isValidacaoConcluida()) {
                if (nomeAnalista.equals(caso.toDTO().getValidacaoDTO().getAnalistaNome()))
                    result.add(caso);
            }
        }
        return result;
    }

    public List<Caso> fetchCasosValidadosAtribuidosPorAnalista(String nomeAnalista) {
        List<Caso> result = new LinkedList<>();
        for (Caso caso : findAll()) {
            detach(caso);
            if (caso.isValidacaoAtribuida()) {
                if (nomeAnalista.equals(caso.toDTO().getValidacaoDTO().getAnalistaNome()))
                    result.add(caso);
            }
        }
        return result;
    }

    public List<CasoDTO> fetchCasesProceeded() {
        List<CasoDTO> result = new LinkedList<>();
        for (Caso caso : findAll()) {
            detach(caso);
            if (caso.isProcessado()) {
                if (!result.contains(new CasoDTO(caso.toDTO()) {
                    @Override
                    public boolean equals(Object obj) {
                        if (this == obj)
                            return true;

                        if (obj == null)
                            return false;

                        if (obj instanceof CasoDTO) {
                            CasoDTO other = (CasoDTO) obj;
                            return other.getID() == this.getID();
                        }
                        return false;
                    }
                }))
                    result.add(caso.toDTO());
            }
        }
        return result;
    }

    public List<CasoDTO> fetchCasesProceeded(String from, String to) {
        List<CasoDTO> result = new LinkedList<>();
        for (Caso caso : findAll()) {
            detach(caso);
            if (caso.isProcessado()) {
                Date dfrom, dto;
                try {
                    dfrom = (new SimpleDateFormat("yyyy-MM-dd").parse(from));
                    dto = (new SimpleDateFormat("yyyy-MM-dd").parse(to));

                    if (caso.getDataConclusao().after(dfrom) && caso.getDataConclusao().before(dto)) {
                        if (!result.contains(new CasoDTO(caso.toDTO()) {
                            @Override
                            public boolean equals(Object obj) {
                                if (this == obj)
                                    return true;

                                if (obj == null)
                                    return false;

                                if (obj instanceof CasoDTO) {
                                    CasoDTO other = (CasoDTO) obj;
                                    return other.getID() == this.getID();
                                }
                                return false;
                            }
                        }))
                            result.add(caso.toDTO());
                    }
                } catch (Exception ex) {

                }
            }
        }
        return result;
    }

    public List<CasoDTO> fetchCasesProceeded(String cidade) {
        List<CasoDTO> result = new LinkedList<>();
        for (Caso caso : findAll()) {
            detach(caso);
            if (caso.isProcessado()) {
                for (ObjetoSeguro obj : caso.objetosSegurados()) {
                    String cd = LocationInfos.getDistrictFromCoordinates(obj.getLatitude(), obj.getLongitude());
                    if (cidade.equalsIgnoreCase(cd)) {
                        if (!result.contains(new CasoDTO(caso.toDTO()) {
                            @Override
                            public boolean equals(Object obj) {
                                if (this == obj)
                                    return true;

                                if (obj == null)
                                    return false;

                                if (obj instanceof CasoDTO) {
                                    CasoDTO other = (CasoDTO) obj;
                                    return other.getID() == this.getID();
                                }
                                return false;
                            }
                        }))
                            result.add(caso.toDTO());
                    }
                }
            }
        }
        return result;
    }

    public List<CasoDTO> fetchCasesProceeded(String from, String to, String cidade) {
        List<CasoDTO> result = new LinkedList<>();
        for (Caso caso : findAll()) {
            detach(caso);
            if (caso.isProcessado()) {
                Date dfrom, dto;
                for (ObjetoSeguro obj : caso.objetosSegurados()) {
                    String cd = LocationInfos.getDistrictFromCoordinates(obj.getLatitude(), obj.getLongitude());
                    try {
                        dfrom = (new SimpleDateFormat("yyyy-MM-dd").parse(from));
                        dto = (new SimpleDateFormat("yyyy-MM-dd").parse(to));
                        if (caso.getDataConclusao() != null)
                            if (cidade.equalsIgnoreCase(cd) && caso.getDataConclusao().after(dfrom) && caso.getDataConclusao().before(dto)) {
                                if (!result.contains(new CasoDTO(caso.toDTO()) {
                                    @Override
                                    public boolean equals(Object obj) {
                                        if (this == obj)
                                            return true;

                                        if (obj == null)
                                            return false;

                                        if (obj instanceof CasoDTO) {
                                            CasoDTO other = (CasoDTO) obj;
                                            return other.getID() == this.getID();
                                        }
                                        return false;
                                    }
                                }))
                                    result.add(caso.toDTO());
                            }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }


}
