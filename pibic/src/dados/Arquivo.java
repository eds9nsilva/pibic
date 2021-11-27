/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados;

/**
 *
 * @author ricardo
 */
public abstract class Arquivo {
    private String endereco;
    private String nome;
    private double[][] dados;

    public Arquivo(String endereco, String nome) {
        this.endereco = endereco;
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double[][] getDados() {
        return dados;
    }

    public void setDados(double[][] dados) {
        this.dados = dados;
    }
    
    
    
}
