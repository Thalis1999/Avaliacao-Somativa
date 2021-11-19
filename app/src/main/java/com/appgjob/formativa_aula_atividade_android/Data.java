package com.appgjob.formativa_aula_atividade_android;

public class Data {

    public String chave;
    public String autenticacao;
    public String status;

    @Override
    public String toString(){
        return " Chave: " + chave +
                "\n\rAutenticação: " + autenticacao +
                "\n\rStatus " + status ;
    }

    public Data(String chave, String autenticacao, String status) {
        this.chave = chave;
        this.autenticacao = autenticacao;
        this.status = status;
    }

    public String getChave() {
        return chave;
    }

    public String getAutenticacao() {
        return autenticacao;
    }

    public String getStatus() {
        return status;
    }

    public void setAutenticacao(String autenticacao) {
        this.autenticacao = autenticacao;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
