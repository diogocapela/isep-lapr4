package modelsDTO;

import models.Cobertura;
import models.matriz.EstadoMatriz;

import java.util.LinkedList;
import java.util.List;

public class MatrizDTO {
    private final String estadoMatriz;
    private final List<CelulaBaseDTO> celulas;
    private final String data;

    public MatrizDTO(String estadoMatriz, List<CelulaBaseDTO> celulas, String data) {
        this.estadoMatriz = estadoMatriz;
        this.celulas = celulas;
        this.data = data;
    }

    public List<CelulaBaseDTO> getCelulas() {
        return celulas;
    }

    public String getData() {
        return data;
    }

    public String getEstadoMatriz() {
        return estadoMatriz;
    }

    public List<CelulaBaseDTO> getCelulasByCoberturaToDTO(Cobertura cobertura) {
        List<CelulaBaseDTO> result = new LinkedList<>();
        CoberturaDTO cobDTO = cobertura.toDTO();

        if (estadoMatriz.equalsIgnoreCase(EstadoMatriz.estadoBase().toString())) {
            for (CelulaBaseDTO celulaDTO : this.celulas) {
                if (celulaDTO.getCoberturaDTO().equals(cobDTO)) {
                    result.add(celulaDTO);
                }
            }
        } else if (estadoMatriz.equalsIgnoreCase(EstadoMatriz.estadoCaracterizada().toString())) {
            for (CelulaBaseDTO celulaDTO : this.celulas) {
                CelulaCaracterizadaDTO celulaCaracterizadaDTO = (CelulaCaracterizadaDTO) celulaDTO;
                if (celulaCaracterizadaDTO.getCoberturaDTO().equals(cobDTO)) {
                    result.add(celulaCaracterizadaDTO);
                }
            }
        } else {
            for (CelulaBaseDTO celulaDTO : this.celulas) {
                CelulaDetalhadaDTO celulaDetalhadaDTO = (CelulaDetalhadaDTO) celulaDTO;
                if (celulaDetalhadaDTO.getCoberturaDTO().equals(cobDTO)) {
                    result.add(celulaDetalhadaDTO);
                }
            }
        }


        return result;
    }
}
