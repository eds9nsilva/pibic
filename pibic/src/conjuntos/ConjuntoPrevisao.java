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
public class ConjuntoPrevisao extends Conjunto{
    
    private int d;
    
    public ConjuntoPrevisao(double pTreino, double pTeste, int d) {
        super(pTreino, pTeste);
        this.d=d;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public void criarConjuntos(Arquivo a) {
        double[][] dados = a.getDados();
        double[][] input = new double[dados.length-(this.d+1)][this.d];
        double[] target = new double[dados.length-(this.d+1)];
        for (int i = 0; i < dados.length-(this.d+1); i++) {
            for (int j = 0; j<this.d; j++) {
                input[i][j]=dados[i+j][0];
            }
            target[i] = dados[i+this.d][0];
        }
        int pc = (int)Math.round(input.length*super.getpTreino()/100);
        double[][] inputTreinamento = new double[pc][input[0].length];
        double[] targetTreinamento = new double[pc];
        for (int i = 0; i < pc; i++) {
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
