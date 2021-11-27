package modelo;

import util.NumerosAleatorios;
import conjuntos.*;

/**
 *
 * @author Edson Da Silva
 */
public class NeuronioLinear {

    private double[] w;
    private double bias;
    private int d;
    private NumerosAleatorios na;
    private double minValue = -0.5;
    private double maxValue = 0.5;
    
    public NeuronioLinear(int d, NumerosAleatorios na) {
        this.d = d;
        this.na = na;
    }

    public void InicializarParametros() {
        this.w = this.na.gerarDouble(this.minValue, this.maxValue, this.d);
        this.bias = this.na.gerarDouble(this.minValue, this.maxValue);
    }

    public double funcaoativacaolinear(double u) {
        return u;
    }
    
    public double calculary(double[] x) {
        double u = 0;
        double y=0;
        for (int i = 0; i < this.d; i++) {
            u = u + x[i] * this.w[i] ;
        }
        u = u + (-1)*this.bias;
        y = funcaoativacaolinear(u);
        return y;
    }

    public double[] simular(double x[][]){
        double y[]=new double[x.length];
        for (int i = 0; i < x.length; i++) {
            y[i]=this.calculary(x[i]);
        }
        return y;
    }
    
    public void atualizarW(double erro, double taxaAprendizagem, double x[]) {
        for (int i = 0; i < this.d; i++) {
            this.w[i] = this.w[i] + taxaAprendizagem*erro*x[i];
        }
    }
    
    public void atualizarBias(double erro, double taxaAprendizagem) {
        this.bias = this.bias + taxaAprendizagem*erro*(-1);
    }
    
    public void aprenderGradiente(int epocas, double taxaAprendizagem, Conjunto c) {
        double y,erro,somaerro;
        double yTreinamento[];
        for (int i = 0; i < epocas; i++) {
            for (int j = 0; j < c.getTargetTreinamento().length; j++) {
                y=this.calculary(c.getInputTreinamento()[j]);
                erro=c.getTargetTreinamento()[j]-y;
                this.atualizarW(erro, taxaAprendizagem, c.getInputTreinamento()[j]);
                this.atualizarBias(erro, taxaAprendizagem);
            }
            yTreinamento=this.simular(c.getInputTreinamento());
            somaerro=this.mse(c.getTargetTreinamento(), yTreinamento);
            System.out.println("Epoca " + i + " erro " + somaerro);
        }
    }

    public double[][] inicializarPopulacao(int tamanhoPopulacao){
        return this.na.gerarDouble(this.minValue, this.maxValue, tamanhoPopulacao, this.d+1);
    }
    
    public void atualizarParametros(double[] individuo) {
        for (int j = 0; j < this.d; j++) {
            this.w[j] = individuo[j];
        }
        this.bias=individuo[this.d];
    }
    
    public double[] calcularFitness(Conjunto c, double[][] populacao) {
        double yTreinamento[];
        double fitness[] = new double[populacao.length];
        for (int i = 0; i < populacao.length; i++) {
            this.atualizarParametros(populacao[i]);
            yTreinamento = this.simular(c.getInputTreinamento());
            fitness[i] = this.taxaacerto(c.getTargetTreinamento(), yTreinamento);
        }
        return fitness;
    }
    
    public int selectionarIndividuo(double populacao[][]) {
        return (int)Math.round(this.na.gerarDouble(0, 1)*(populacao.length-1));
    }

    public int selectionarIndice(int tamanhoVetor) {
        return (int)Math.round(this.na.gerarDouble(0, 1)*(tamanhoVetor-1));
    }
    
    public int melhorIndividuo(double fitness[]) {
        int melhor=0;
        for (int i = 1; i < fitness.length; i++) {
            if (fitness[melhor]<fitness[i]){
                melhor=i;
            }
        }
        return melhor;
    }

    public int piorIndividuo(double fitness[]) {
        int pior=0;
        for (int i = 1; i < fitness.length; i++) {
            if (fitness[pior]>fitness[i]){
                pior=i;
            }
        }
        return pior;
    }
    
    public void aprenderAlgoritmoGenetico(int geracoes, int tamanhoPopulacao, double pMutacao, Conjunto c) {
        double y,erro,somaerro;
        int indicePai,indiceMae,indiceMutacao;
        int melhorCruzamento, melhorPopulacao;
        int piorPopulacao;
        double cruzamento[][];
        double mutacao[][];
        double delta[][];
        double yTreinamento[];
        double populacao[][] = this.inicializarPopulacao(tamanhoPopulacao);
        double fitnessPopulacao[] = this.calcularFitness(c, populacao);
        double fitnessCruzamento[], fitnessMutacao[];
        for (int i = 0; i < geracoes; i++) {
            cruzamento = new double[tamanhoPopulacao][this.d+1];
            for (int j = 0; j < populacao.length; j++) {
                indicePai = this.selectionarIndividuo(populacao);
                indiceMae = this.selectionarIndividuo(populacao);
                for (int k = 0; k < this.d+1; k++) {
                    cruzamento[j][k] = (populacao[indicePai][k]+populacao[indiceMae][k])/2;
                }
            }
            fitnessCruzamento = this.calcularFitness(c, cruzamento);
            melhorCruzamento = this.melhorIndividuo(fitnessCruzamento);
            fitnessPopulacao = this.calcularFitness(c, populacao);
            piorPopulacao = this.piorIndividuo(fitnessPopulacao);
            if (fitnessCruzamento[melhorCruzamento]>fitnessPopulacao[piorPopulacao]) {
                populacao[piorPopulacao]=cruzamento[melhorCruzamento];
                fitnessPopulacao[piorPopulacao] = fitnessCruzamento[melhorCruzamento];
            }
            mutacao = new double[3][this.d+1];
            delta = this.na.gerarDouble(this.minValue, this.maxValue, 3, this.d+1);
            for (int j=0; j<3; j++) {
                mutacao[j] = cruzamento[melhorCruzamento];
                switch (j) {
                    case 0:
                        indiceMutacao = this.selectionarIndice(this.d+1);
                        mutacao[j][indiceMutacao] += delta[j][indiceMutacao];
                        break;
                    case 1:
                        for (int k = 0; k < this.d+1; k++) {
                            if (this.na.gerarDouble(0,1)<pMutacao) {
                                mutacao[j][k] += delta[j][k];
                            }
                        }
                        break;
                    case 2:
                        for (int k = 0; k < this.d+1; k++) {
                            mutacao[j][k] += delta[j][k];
                        }
                }
            }
            fitnessMutacao = this.calcularFitness(c, mutacao);
            for (int j = 0; j < 3; j++) {
                fitnessPopulacao = this.calcularFitness(c, populacao);
                piorPopulacao = this.piorIndividuo(fitnessPopulacao);
                if (fitnessMutacao[j]>fitnessPopulacao[piorPopulacao]) {
                    populacao[piorPopulacao]=mutacao[j];
                    fitnessPopulacao[piorPopulacao] = fitnessMutacao[j];
                }                
            }
            fitnessPopulacao = this.calcularFitness(c, populacao);
            melhorPopulacao = this.melhorIndividuo(fitnessPopulacao);
            this.atualizarParametros(populacao[melhorPopulacao]);
            yTreinamento=this.simular(c.getInputTreinamento());
            somaerro=this.taxaacerto(c.getTargetTreinamento(), yTreinamento);
            System.out.println("Geracao " + i + " acerto " + somaerro);
        }
    }
    
    public void aprenderAlgoritmoGeneticoMelhorado(int geracoes, int tamanhoPopulacao, double pMutacao, double pMax, double pMin, double w, Conjunto c) {
        double y,erro,somaerro;
        int indicePai,indiceMae,indiceMutacao;
        int melhorCruzamento, melhorPopulacao;
        int piorPopulacao;
        double cruzamento[][];
        double mutacao[][];
        double delta[][];
        double yTreinamento[];
        double populacao[][] = this.inicializarPopulacao(tamanhoPopulacao);
        double fitnessPopulacao[] = this.calcularFitness(c, populacao);
        double fitnessCruzamento[], fitnessMutacao[];
        for (int i = 0; i < geracoes; i++) {
            cruzamento = new double[4][this.d+1];
            indicePai = this.selectionarIndividuo(populacao);
            indiceMae = this.selectionarIndividuo(populacao);
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < this.d+1; k++) {
                    switch (k) {
                        case 0:
                            cruzamento[j][k] = (populacao[indicePai][k]+populacao[indiceMae][k])/2;
                            break;
                        case 1:
                            cruzamento[j][k] = pMax*(1-w) + Math.max(populacao[indicePai][k],populacao[indiceMae][k])*w;
                            break;
                        case 2:
                            cruzamento[j][k] = pMin*(1-w) + Math.min(populacao[indicePai][k],populacao[indiceMae][k])*w;
                            break;
                        case 3:
                            cruzamento[j][k] = ((pMax+pMin)*(1-w) + (populacao[indicePai][k]+populacao[indiceMae][k])*w)/2;
                            break;
                    }
                }
            }
            fitnessCruzamento = this.calcularFitness(c, cruzamento);
            melhorCruzamento = this.melhorIndividuo(fitnessCruzamento);
            fitnessPopulacao = this.calcularFitness(c, populacao);
            piorPopulacao = this.piorIndividuo(fitnessPopulacao);
            if (fitnessCruzamento[melhorCruzamento]>fitnessPopulacao[piorPopulacao]) {
                populacao[piorPopulacao]=cruzamento[melhorCruzamento];
                fitnessPopulacao[piorPopulacao] = fitnessCruzamento[melhorCruzamento];
            }
            mutacao = new double[3][this.d+1];
            delta = this.na.gerarDouble(this.minValue, this.maxValue, 3, this.d+1);
            for (int j=0; j<3; j++) {
                mutacao[j] = cruzamento[melhorCruzamento];
                switch (j) {
                    case 0:
                        indiceMutacao = this.selectionarIndice(this.d+1);
                        mutacao[j][indiceMutacao] += delta[j][indiceMutacao];
                        break;
                    case 1:
                        for (int k = 0; k < this.d+1; k++) {
                            if (this.na.gerarDouble(0,1)<pMutacao) {
                                mutacao[j][k] += delta[j][k];
                            }
                        }
                        break;
                    case 2:
                        for (int k = 0; k < this.d+1; k++) {
                            mutacao[j][k] += delta[j][k];
                        }
                }
            }
            fitnessMutacao = this.calcularFitness(c, mutacao);
            for (int j = 0; j < 3; j++) {
                fitnessPopulacao = this.calcularFitness(c, populacao);
                piorPopulacao = this.piorIndividuo(fitnessPopulacao);
                if (fitnessMutacao[j]>fitnessPopulacao[piorPopulacao]) {
                    populacao[piorPopulacao]=mutacao[j];
                    fitnessPopulacao[piorPopulacao] = fitnessMutacao[j];
                }                
            }
            fitnessPopulacao = this.calcularFitness(c, populacao);
            melhorPopulacao = this.melhorIndividuo(fitnessPopulacao);
            this.atualizarParametros(populacao[melhorPopulacao]);
            yTreinamento=this.simular(c.getInputTreinamento());
            somaerro=this.taxaacerto(c.getTargetTreinamento(), yTreinamento);
            System.out.println("Geracao " + i + " acerto " + somaerro);
        }
    }
    
    public double mse (double target[], double y[]) {
        double valor=0;
        for (int i = 0; i < target.length; i++) {
            valor += Math.pow(target[i]-y[i],2)/2;
        }
        return valor/target.length;
    }
    
    public double taxaacerto (double target[], double y[]) {
        double cont=0;
        double yTemp;
        for (int i = 0; i < target.length; i++) {
            if (y[i]>0.5) {
                yTemp = 1;
            } else {
                yTemp = 0;
            }
            if (target[i] == yTemp) {
                cont = cont + 1;
            }
        }
        return cont*100/target.length;
    }
    
}
