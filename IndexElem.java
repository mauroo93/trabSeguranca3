public class IndexElem {
    String nomeArq;
    String nomeCodSecreto;
    String donoArq;
    String grupoArq;
    public void setNomeArq(String nomeArq) {
        this.nomeArq = nomeArq;
    }
    public void setNomeCodSecreto(String nomeCodSecreto) {
        this.nomeCodSecreto = nomeCodSecreto;
    }
    public String getDonoArq() {
        return donoArq;
    }
    public void setDonoArq(String donoArq) {
        this.donoArq = donoArq;
    }
    public String getGrupoArq() {
        return grupoArq;
    }
    public void setGrupoArq(String grupoArq) {
        this.grupoArq = grupoArq;
    }
    
    IndexElem(String nome,String secreto,String dono,String grupo){
        this.nomeArq=nome;this.nomeCodSecreto=secreto;this.donoArq=dono;
        this.grupoArq=grupo;
    }
    boolean checkDono(String nome){
        return this.donoArq.equals(nome);
    }
    boolean checkGrupo(String nome){
        return this.grupoArq.equals(nome);
    }
    public String toString() {
        return nomeArq +" "+ nomeCodSecreto +" "+donoArq+" "+grupoArq;
    } 
    

}
