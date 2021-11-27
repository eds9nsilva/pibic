/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntos;

/**
 *
 * @author ricardo
 */
public abstract class Conjunto {
    private double pTreino;
    private double pTeste;
    private int n;
    private double[][]input;
    private double[]target;
    private double[][]inputTreinamento;
    private double[]targetTreinamento;    
    private double[][]inputTeste;
    private double[]targetTeste;

    public Conjunto(double pTreino, double pTeste) {
        this.pTreino = pTreino;
        this.pTeste = pTeste;
    }

    public double getpTreino() {
        return pTreino;
    }

    public void setpTreino(double pTreino) {
        this.pTreino = pTreino;
    }

    public double getpTeste() {
        return pTeste;
    }

    public void setpTeste(double pTeste) {
        this.pTeste = pTeste;
    }

    public double[][] getInput() {
        return input;
    }

    public void setInput(double[][] input) {
        this.input = input;
    }

    public double[] getTarget() {
        return target;
    }

    public void setTarget(double[] target) {
        this.target = target;
    }

    public double[][] getInputTreinamento() {
        return inputTreinamento;
    }

    public void setInputTreinamento(double[][] inputTreinamento) {
        this.inputTreinamento = inputTreinamento;
    }

    public double[] getTargetTreinamento() {
        return targetTreinamento;
    }

    public void setTargetTreinamento(double[] targetTreinamento) {
        this.targetTreinamento = targetTreinamento;
    }

    public double[][] getInputTeste() {
        return inputTeste;
    }

    public void setInputTeste(double[][] inputTeste) {
        this.inputTeste = inputTeste;
    }

    public double[] getTargetTeste() {
        return targetTeste;
    }

    public void setTargetTeste(double[] targetTeste) {
        this.targetTeste = targetTeste;
    }

}
