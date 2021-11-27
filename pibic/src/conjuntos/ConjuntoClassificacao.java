/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntos;

import dados.Arquivo;

/**
 *
 * @author edson
 */
public class ConjuntoClassificacao extends Conjunto{
    
    private int d;
    
    public ConjuntoClassificacao(double pTreino, double pTeste) {
        super(pTreino, pTeste);
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }
    
    public void criarConjuntos(Arquivo a) {
        double[][] dados = a.getDados();
        this.d = dados[0].length-1;
        double[][] input = new double[dados.length][dados[0].length-1];
        double[] target = new double[dados.length];
        for (int i = 0; i < dados.length; i++) {
            for (int j = 0; j < dados[0].length-1; j++) {
                input[i][j]=dados[i][j];
            }
            target[i] = dados[i][dados[0].length-1];
        }
        int pc = (int)Math.round(input.length*super.getpTreino()/100);
        double[][] inputTreinamento = new double[pc+1][input[0].length];
        double[] targetTreinamento = new double[pc+1];
        for (int i = 0; i <= pc; i++) {
            for (int j = 0; j < input[0].length; j++) {
                inputTreinamento[i][j]=input[i][j];
            }
            targetTreinamento[i] = target[i];
        }
        double[][] inputTeste = new double[input.length-(pc+1)][input[0].length];
        double[] targetTeste = new double[input.length-(pc+1)];
        for (int i = pc+1; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                inputTeste[i-(pc+1)][j]=input[i][j];
            }
            targetTeste[i-(pc+1)] = target[i];
        }
        super.setInput(input);
        super.setTarget(target);
        super.setInputTreinamento(inputTreinamento);
        super.setTargetTreinamento(targetTreinamento);
        super.setInputTeste(inputTeste);
        super.setTargetTeste(targetTeste);
    }

}
