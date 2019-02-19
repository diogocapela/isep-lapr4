package modelsDTO;

import java.util.List;

public class CasoDTO {
    private List<ObjetoSeguroDTO> objetosSegurados;
    private String estado;
    private Long id;
    private ValidacaoDTO validacaoDTO;

    public CasoDTO(List<ObjetoSeguroDTO> objetosSegurados, String estado, Long id, ValidacaoDTO validacaoDTO) {
        this.objetosSegurados = objetosSegurados;
        this.estado = estado;
        this.id = id;
        this.validacaoDTO = validacaoDTO;
    }

    public CasoDTO(CasoDTO cdto) {
        this.objetosSegurados = cdto.getObjetosSegurados();
        this.estado = cdto.getEstado();
        this.id = cdto.getID();
        this.validacaoDTO = cdto.getValidacaoDTO();
    }

    public List<ObjetoSeguroDTO> getObjetosSegurados() {
        return objetosSegurados;
    }

    public String getEstado() {
        return estado;
    }

    public Long getID() {
        return this.id;
    }

    public ValidacaoDTO getValidacaoDTO() {
        return validacaoDTO;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (obj instanceof CasoDTO) {
            CasoDTO other = (CasoDTO) obj;
            boolean estadoBoolean = this.estado.equals(other.estado);
            boolean validacaoBoolean = false;
            if (other.validacaoDTO != null)
                validacaoBoolean = this.validacaoDTO.equals(other.validacaoDTO);
            boolean osBoolean = this.objetosSegurados.size() == other.objetosSegurados.size();
            for (int i = 0; i < this.objetosSegurados.size(); i++) {
                osBoolean = osBoolean && this.objetosSegurados.get(i).equals(other.objetosSegurados.get(i));
            }

            return estadoBoolean && validacaoBoolean && osBoolean;
        }

        return false;
    }
}
