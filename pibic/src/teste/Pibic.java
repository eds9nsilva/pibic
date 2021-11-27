/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import conjuntos.Conjunto;
import conjuntos.ConjuntoClassificacao;
import conjuntos.ConjuntoPrevisao;
import dados.Arquivo;
import dados.ArquivoClassificacao;
import dados.ArquivoPrevisao;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.time.Clock;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.NeuronioLinear;
import util.NumerosAleatorios;

/**
 *
 * @author ricardo
 */
public class Pibic {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Arquivo a = new ArquivoClassificacao("../dados","BASE_DIABETIS.txt");
        try {
            ((ArquivoClassificacao)a).lerDados();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Conjunto c = new ConjuntoClassificacao(60,40);
        ((ConjuntoClassificacao)c).criarConjuntos(a);
        NumerosAleatorios na = new NumerosAleatorios(java.time.Clock.tickSeconds(ZoneId.systemDefault()).millis());
        NeuronioLinear nl = new NeuronioLinear(3,na);
        nl.InicializarParametros();
        //nl.aprenderGradiente(100000, 0.01, c);
        int quantidadeIndividuos = 10;
        int geracoes = 10000;
        double probabilidadeMutacao = 0.1;
        //nl.aprenderAlgoritmoGenetico(geracoes, quantidadeIndividuos, probabilidadeMutacao, c);
        double pMax = 1;
        double pMin = -1;
        double w = 0.9;
        nl.aprenderAlgoritmoGeneticoMelhorado(geracoes, quantidadeIndividuos, probabilidadeMutacao, pMax, pMin, w, c);
        try {
            FileOutputStream arquivoResultado = new FileOutputStream("Resultado.txt");
            OutputStreamWriter streamArquivoResultado = new OutputStreamWriter(arquivoResultado,"UTF-8");
            streamArquivoResultado.write("> Parametros\r\n");
            streamArquivoResultado.write("Geracoes " + geracoes+ "\r\n");
            streamArquivoResultado.write("Individuos " + quantidadeIndividuos+ "\r\n");
            streamArquivoResultado.write("Probabilidade Mutacao " + probabilidadeMutacao+ "\r\n");
            streamArquivoResultado.write("PMax " + pMax+ "\r\n");
            streamArquivoResultado.write("PMin " + pMin+ "\r\n");
            streamArquivoResultado.write("Peso cruzamento (w) " + w+ "\r\n\r\n\r\n");
            double[] yTreino = nl.simular(c.getInputTreinamento());
            double medidaMSETreino = nl.mse(c.getTargetTreinamento(), yTreino);
            double medidaTaxaAcertoTreino = nl.taxaacerto(c.getTargetTreinamento(), yTreino);
            double[] yTeste = nl.simular(c.getInputTeste());
            double medidaMSETeste = nl.mse(c.getTargetTeste(), yTeste);
            double medidaTaxaAcertoTeste = nl.taxaacerto(c.getTargetTeste(), yTeste);
            streamArquivoResultado.write("> Treinamento: \r\n\r\n");
            streamArquivoResultado.write("MSE: " + medidaMSETreino + "\r\n");
            streamArquivoResultado.write("Taxa Acerto: " + medidaTaxaAcertoTreino + "\r\n");
            streamArquivoResultado.write("\r\n\r\n");
            streamArquivoResultado.write("target\t y\r\n");
            for (int i = 0; i < yTreino.length; i++) {
                streamArquivoResultado.write(c.getTargetTreinamento()[i] + "\t" + yTreino[i] + "\r\n");
            }
            streamArquivoResultado.write("\r\n\r\n");
            streamArquivoResultado.write("> Teste: \r\n\r\n");
            streamArquivoResultado.write("MSE " + medidaMSETeste + "\r\n");
            streamArquivoResultado.write("Taxa Acerto: " + medidaTaxaAcertoTeste + "\r\n");
            streamArquivoResultado.write("\r\n\r\n");
            streamArquivoResultado.write("target\t y\r\n");
            for (int i = 0; i < yTeste.length; i++) {
                streamArquivoResultado.write(c.getTargetTeste()[i] + "\t" + yTeste[i] + "\r\n");
            }
            streamArquivoResultado.close();
            arquivoResultado.close();
        } catch (UnsupportedEncodingException|FileNotFoundException ex) {
            System.out.println(ex);
        }       
    }
   
}
