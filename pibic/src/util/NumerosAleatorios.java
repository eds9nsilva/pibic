/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Random;

/**
 *
 * @author ricardo
 */
public class NumerosAleatorios {
    private long semente;
    private Random gerador;

    public NumerosAleatorios(long semente) {
        this.semente = semente;
        this.gerador = new Random(this.semente);
    }
    
    private double gerarDouble() {
        return this.gerador.nextDouble();
    }

    public double gerarDouble(double valorMinimo, double valorMaximo) {
        return (valorMinimo + this.gerarDouble()*(valorMaximo-valorMinimo));
    }
    
    public double[] gerarDouble(double valorMinimo, double valorMaximo, int quantidadeElementos) {
        double[] numeros = new double[quantidadeElementos];
        for (int i=0; i<quantidadeElementos; i++) {
            numeros[i] = this.gerarDouble(valorMinimo, valorMaximo);
        }
        return numeros;
    }

    public double[][] gerarDouble(double valorMinimo, double valorMaximo, int quantidadeLinhas, int quantidadeColunas) {
        double[][] numeros = new double[quantidadeLinhas][quantidadeColunas];
        for (int i=0; i<quantidadeLinhas; i++) {
            for (int j=0; j<quantidadeColunas; j++) {
                numeros[i][j] = this.gerarDouble(valorMinimo, valorMaximo);
            }
        }
        return numeros;
    }
}
