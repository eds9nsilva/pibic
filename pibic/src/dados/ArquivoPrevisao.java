/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author ricardo
 */
public class ArquivoPrevisao extends Arquivo{
    
    public ArquivoPrevisao(String endereco, String nome) {
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
        double[][] dados = new double[dadosTemp.size()][1];;
        for (int i=0; i<dadosTemp.size(); i++) {
            dados[i][0] = Double.valueOf(dadosTemp.get(i));
        }
        super.setDados(dados);
    }
    
}
