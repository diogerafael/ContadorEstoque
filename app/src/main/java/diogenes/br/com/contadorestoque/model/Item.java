package diogenes.br.com.contadorestoque.model;

/**
 * Created by Diogenes on 11/12/2015.
 */
public class Item {
    private Integer i;
    private String descricao;
    private String codean;
    private Double quantidade;

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public String getCodean() {
        return codean;
    }

    public void setCodean(String codean) {
        this.codean = codean;
    }
}
