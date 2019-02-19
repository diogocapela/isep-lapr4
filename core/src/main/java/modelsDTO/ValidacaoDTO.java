package modelsDTO;

public class ValidacaoDTO {

    private final String dataPedido;
    private final String dataAtribuicao;
    private final String analistaEmail;
    private final String analistaNome;
    private final String dataConclusao;


    public ValidacaoDTO(String dataPedido, String dataAtribuicao, String analistaEmail, String analistaNome, String dataConclusao) {
        this.dataPedido = dataPedido;
        this.dataAtribuicao = dataAtribuicao;
        this.analistaEmail = analistaEmail;
        this.analistaNome = analistaNome;
        this.dataConclusao = dataConclusao;
    }

    public String getDataPedido() {
        return this.dataPedido;
    }

    public String getDataAtribuicao() {
        return dataAtribuicao;
    }

    public String getAnalistaEmail() {
        return this.analistaEmail;
    }

    public String getAnalistaNome() {
        return this.analistaNome;
    }

    public String getDataConclusao() {
        return this.dataConclusao;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (obj instanceof ValidacaoDTO) {
            ValidacaoDTO other = (ValidacaoDTO) obj;
            return this.dataPedido.equals(other.dataPedido) && this.dataAtribuicao.equals(other.dataAtribuicao) && this.analistaEmail.equals(other.analistaEmail) && this.analistaNome.equals(other.analistaNome) && this.dataConclusao.equals(other.dataConclusao);
        }

        return false;
    }
}
