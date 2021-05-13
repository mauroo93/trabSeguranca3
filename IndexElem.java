public class IndexElem {
    String nomeArq;
    String nomeCodSecreto;
    String donoArq;
    String grupoArq;
    IndexElem(String nome,String secreto,String dono,String grupo){
        this.nomeArq=nome;this.nomeCodSecreto=secreto;this.donoArq=dono;
        this.grupoArq=grupo;
    }
    boolean checkDono(String nome){
        return nome==this.donoArq;
    }
    boolean checkGrupo(String nome){
        return nome==this.grupoArq;
    }
    public String getNomeArq() {
        return nomeArq;
    }
    public String getNomeCodSecreto() {
        return nomeCodSecreto;
    }

}
