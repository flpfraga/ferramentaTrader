/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arquivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Felipe Fraga
 */
public abstract class Arquivo {

        private String sBody;

        private FileInputStream arquivo;
        private BufferedReader LinhaArquivo;
        private BufferedWriter sFilePath;

    public void openTextFile(String caminho) throws IOException {
        setArquivo(new FileInputStream(caminho));
        setLinhaArquivo(new BufferedReader(new InputStreamReader(getArquivo())));
    }

    public void openBuffered(String caminho) throws IOException {
        setsFilePath(new BufferedWriter(new FileWriter(caminho)));
    }

    public void closeTextFile() throws IOException {
        getLinhaArquivo().close();
    }

    public void closeBuffered() throws IOException {
        getsFilePath().flush();  // Valida o fluxo
        getsFilePath().close();
    }

    /**
     * @return the sBody
     */
    public String getsBody() {
        return sBody;
    }

    /**
     * @param sBody the sBody to set
     */
    public void setsBody(String sBody) {
        this.sBody = sBody;
    }

    /**
     * @return the Arquivo
     */
    public FileInputStream getArquivo() {
        return arquivo;
    }

    /**
     * @param Arquivo the Arquivo to set
     */
    public void setArquivo(FileInputStream Arquivo) {
        this.arquivo = Arquivo;
    }

    /**
     * @return the LinhaArquivo
     */
    public BufferedReader getLinhaArquivo() {
        return LinhaArquivo;
    }

    /**
     * @param LinhaArquivo the LinhaArquivo to set
     */
    public void setLinhaArquivo(BufferedReader LinhaArquivo) {
        this.LinhaArquivo = LinhaArquivo;
    }

    /**
     * @return the sFilePath
     */
    public BufferedWriter getsFilePath() {
        return sFilePath;
    }

    /**
     * @param sFilePath the sFilePath to set
     */
    public void setsFilePath(BufferedWriter sFilePath) {
        this.sFilePath = sFilePath;
    }

    public void criarArquivo(String caminho) throws IOException {
        sFilePath = new BufferedWriter(new FileWriter(caminho));
    }

}
