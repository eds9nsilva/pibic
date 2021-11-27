/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author ricardo
 */
public class ArquivoClassificacao extends Arquivo{
    
    public ArquivoClassificacao(String endereco, String nome) {
        super(endereco, nome);
    }

    public void lerDados() throws IOException {
        ArrayList<String> dadosTemp = new ArrayList<String>();
        InputStream f;
        f = new FileInputStream(super.getEndereco()+super.getNome());
        InputStreamReader leitorf = new InputStreamReader(f);
        try (BufferedReader leitorbuffer = new BufferedReader(leitorf)) {
            String linha;
            while ((linha=leitorbuffer.readLine())!=null) {
                dadosTemp.add(linha);
            }
        }
        int quantidadeLinhas=dadosTemp.size();
        String[][] temp = new String[quantidadeLinhas][];
        int j=0;
        for (String l: dadosTemp) {
            temp[j++] = l.split("\t");
        }
        double[][] dados = new double[temp.length][temp[0].length];
        for (int i=0; i<temp.length; i++) {
            for (j=0; j<temp[0].length; j++) {
                dados[i][j] = Double.valueOf(temp[i][j]);
            }
        }
        super.setDados(dados);
    }
    
}
