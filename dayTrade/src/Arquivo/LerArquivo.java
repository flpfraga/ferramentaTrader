/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arquivo;

import Classes.Candle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Felipe Fraga
 */
public class LerArquivo {

    private String caminho = "GerdauTratado1.csv";
    private FileInputStream arquivo;
    private BufferedReader LinhaArquivo;
    private BufferedWriter sFilePath;
    private Candle candle;
    
    public void openTextFile() throws IOException{
        arquivo = new FileInputStream(caminho);
        LinhaArquivo = new BufferedReader(new InputStreamReader(arquivo));
    }
     public BufferedReader getLinhaArquivo() {
        return LinhaArquivo;
    }
    public Candle [] ler () throws IOException{
        Candle [] listaCandle = new Candle[150000];
        for (int c = 0; c < 150000; c++){
            listaCandle[c] = new Candle();
        }
        int contCandle = 0;
        openTextFile();
        getLinhaArquivo().readLine();
        while (getLinhaArquivo().ready()) {
            String aux = getLinhaArquivo().readLine();
            String linha [] = aux.split(";");
            String instante = linha [0];
            Double abertura = Double.parseDouble(linha[1]);
            Double maior = Double.parseDouble(linha[2]);
            Double menor = Double.parseDouble(linha[3]);
            Double fechamento = Double.parseDouble(linha[4]);
            //System.out.println("fechamento leitura: "+ fechamento);
            Candle candle = new Candle (instante, abertura, maior, menor, fechamento);
            listaCandle[contCandle++] = candle;
        }
        closeTextFile();
        return listaCandle;
    }
    
    public Double converterDouble(String valor){
        String aux [] = valor.split(".");
        System.out.println(aux[0] + " " + aux[1]);
        int aux1 = Integer.parseInt(aux[0]);
        System.out.println(aux1);
        int aux2 = Integer.parseInt(aux[1]);
        System.out.println("aux 2 " + aux2 );
        Double aux3 = (double) aux2/100000 + aux1;
        System.out.println("aux 2 " + aux2 );
        return aux3;
    }
    public void closeTextFile() throws IOException {
        getLinhaArquivo().close();
    }

}
