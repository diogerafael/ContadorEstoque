package diogenes.br.com.contadorestoque.model;


public class Item {

    private String codean;
    private Double quantidade;

    public Item(String codean,Double quantidade){
        this.codean= codean;
        this.quantidade = quantidade;
    }


    public Item(){
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