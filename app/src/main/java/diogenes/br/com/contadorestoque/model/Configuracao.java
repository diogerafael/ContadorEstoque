package diogenes.br.com.contadorestoque.model;

/**
 * Created by Diogenes on 11/12/2015.
 */
public class Configuracao {
    private Integer id;
    private String caracterSeparacao;
    private Integer perguntarQuantidade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaracterSeparacao() {
        return caracterSeparacao;
    }

    public void setCaracterSeparacao(String caracterSeparacao) {
        this.caracterSeparacao = caracterSeparacao;
    }

    public Integer getPerguntarQuantidade() {
        return perguntarQuantidade;
    }

    public void setPerguntarQuantidade(Integer perguntarQuantidade) {
        this.perguntarQuantidade = perguntarQuantidade;
    }
}
