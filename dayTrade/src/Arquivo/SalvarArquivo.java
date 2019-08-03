/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arquivo;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Felipe Fraga
 */
public class SalvarArquivo extends Arquivo{
    public void Write(String caminho, String [] listaAnalise, Double [] resultados) throws IOException {
        criarArquivo(caminho);
        openTextFile(caminho);
        
        int cont = 0;
        for (int c = 0; c < 75000; c++) {
            String salvar = "";
            String auxResultado = String.valueOf(resultados[c]);
            salvar += listaAnalise[c];
            getsFilePath().write(salvar);
            getsFilePath().flush();  // Valida o fluxo
        }  
        getsFilePath().close();
        closeTextFile();

    }
    public void Write(String caminho, String [] listaAnalise, int [] resultados) throws IOException {
        criarArquivo(caminho);
        openTextFile(caminho);
        
        int cont = 0;
        for (int c = 0; c < 75000; c++) {
            String salvar = "";
            String auxResultado = String.valueOf(resultados[c]);
            salvar += listaAnalise[c] + ";" + auxResultado+"\n";
            getsFilePath().write(salvar);
            getsFilePath().flush();  // Valida o fluxo
        }  
        getsFilePath().close();
        closeTextFile();

    }

}
