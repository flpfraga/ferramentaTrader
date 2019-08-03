/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Arquivo.LerArquivo;
import Arquivo.SalvarArquivo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Felipe Fraga
 */
public class Movimento {

    private Candle[] listaCandle;
    private int[] topos;
    private int[] fundos;
    private int[] tendencia;
    private Double[] resultados;
    private int[][] subidaM;
    private int[][] subidaG;
    private int[][] descidaP;
    private int[][] descidaM;
    private int[][] descidaG;
    private String analise[];
    private Double maiorDiferencaAberuraFechamentoPositivo;
    private Double menorDiferencaAberuraFechamentoPositivo;
    private Double maiorDiferencaAberuraFechamentoNegativo;
    private Double menorDiferencaAberuraFechamentoNegativo;
    private Double maiorDiferencaMaiorMenorValor;
    private Double menorDiferencaMaiorMenorValor;
    private Double maiorDiferencaToposEFundoPositivo;
    private Double maiorDiferencaToposEFundoNegativo;
    private int nCandle;
    private Double mediaMaiorMenor;
    private Double mediaMaiorMenorNaoNula;
    private Double mediaAberturaFechamento;
    private Double mediaAberturaFechamentoNaoNula;

    public Movimento() {
        listaCandle = new Candle[150000];
        topos = new int[150000];
        fundos = new int[150000];
        tendencia = new int[75000];
        resultados = new Double[75000];
        subidaM = new int[75000][];
        subidaG = new int[75000][];
        descidaP = new int[75000][];
        descidaM = new int[75000][];
        descidaG = new int[75000][];
        analise = new String[150000];
        maiorDiferencaAberuraFechamentoPositivo = 0.0;
        menorDiferencaAberuraFechamentoPositivo = 0.0;
        maiorDiferencaAberuraFechamentoNegativo = 0.0;
        menorDiferencaAberuraFechamentoNegativo = 0.0;
        maiorDiferencaMaiorMenorValor = 0.0;
        menorDiferencaMaiorMenorValor = 0.0;
        nCandle = 0;
        mediaMaiorMenor = 0.0;
        mediaMaiorMenorNaoNula = 0.0;
        mediaAberturaFechamento = 0.0;
        mediaAberturaFechamentoNaoNula = 0.0;
        for (int c = 0; c < 75000; c++) {
            tendencia[c] = 0;
            fundos[c] = topos[c] = -1;
            listaCandle[c] = new Candle();
            analise[c] = "";
            resultados[c] = 0.0;
        }
        for (int c = 75001; c < 150000; c++) {
            listaCandle[c] = new Candle();
            analise[c] = "";
        }

        for (int c = 0; c < 75000; c++) {

            subidaM[c] = new int[2];
            subidaG[c] = new int[2];
            descidaP[c] = new int[2];
            descidaM[c] = new int[2];
            descidaG[c] = new int[2];

        }
    }

    public void verificaExtremos() {
        int contFundos = 0;
        int contTopos = 0;
        boolean subindo = true;
        topos[0] = 0;
        fundos[0] = 0;
        for (int c = 1; c < 150000; c++) {
            if (listaCandle[c].getInstante() == "") {
                break;
            }
            Double valorFechamentoX = getListaCandle()[c - 1].getValorFechamento();
            Double valorFechamentoY = getListaCandle()[c].getValorFechamento();
            //System.out.println("fechamentoX: " + valorFechamentoX + " fechamentoY: "+ valorFechamentoY);
            while (subindo) {
                if (subiu(valorFechamentoX, valorFechamentoY) < 0) {
                    topos[contTopos++] = c - 1;
                    //System.out.println("Topo: " + (c-1) + " instante: " + listaCandle[c-1].getInstante());
                    subindo = false;
                    break;
                }
                c++;
                if (!(c < 15000)) {
                    break;
                }
                valorFechamentoX = getListaCandle()[c - 1].getValorFechamento();
                valorFechamentoY = getListaCandle()[c].getValorFechamento();
                //System.out.println("subiu fechamentoX: " + valorFechamentoX + " fechamentoY: "+ valorFechamentoY);
            }
            while (!subindo && c < 150000) {
                if (subiu(valorFechamentoX, valorFechamentoY) > 0) {
                    getFundos()[contFundos++] = c - 1;
                    subindo = true;
                    break;
                }
                c++;
                if (!(c < 15000)) {
                    break;
                }
                valorFechamentoX = getListaCandle()[c - 1].getValorFechamento();
                valorFechamentoY = getListaCandle()[c].getValorFechamento();
                //System.out.println("!subiu fechamentoX: " + valorFechamentoX + " fechamentoY: "+ valorFechamentoY);
            }
        }

    }

    public int subiu(double valorFechamentoX, double valorFechamentoY) {
        if (valorFechamentoX < valorFechamentoY) {
            return 1;
        } else if (valorFechamentoY < valorFechamentoX) {
            return -1;
        }
        return 0;
    }

    public void verificaTendencia() {
        boolean subindo = true;
        int inicial = 0;
        for (int c = 0; c < 75000; c++) {
            if (listaCandle[c].getInstante() == "" && listaCandle[c].getInstante() == "") {
                break;
            }
            Double topoX = listaCandle[topos[c]].getValorFechamento();
            Double topoY = listaCandle[topos[c + 1]].getValorFechamento();
            Double fundoX = listaCandle[fundos[c]].getValorFechamento();
            Double fundoY = listaCandle[fundos[c + 1]].getValorFechamento();
            System.out.println("topoX: " + topoX + " topoY: " + topoY + " fundoX: " + fundoX + " fundoY: " + fundoY + " c: " + c);
            if (subiu(topoX, topoY) < 0 && subiu(fundoX, fundoY) < 0) {
                //subindo = false;
                System.out.println("decendo: " + listaCandle[topos[c]].getInstante());
                while (inicial < topos[c]) {
                    tendencia[inicial++] = 1;
                }
                subindo = false;
            } else if (subiu(topoX, topoY) > 0 && subiu(fundoX, fundoY) > 0) {
                //subindo = true;
                System.out.println("subindo: " + listaCandle[fundos[c]].getInstante() + " fundos posicao: " + fundos[c]);
                //listaCandle[fundos[c]].print();
                while (inicial < fundos[c]) {
                    tendencia[inicial++] = -1;
                }

            }
        }
    }

    public void resultadoTempoATempo(int tempo) {

        for (int c = 1; c < 150000; c++) {
            analisaPeriodoAberturaFechamento(c, tempo++);
        }
    }

    public String analisaPeriodoAberturaFechamento(int inicio, int fim) {
        String retorno = "";
        while (inicio < fim) {
            Double valorAberturaAux = listaCandle[inicio].getValorAbetura();
            Double valorFechamentoAux = listaCandle[inicio++].getValorFechamento();
            Double diferencaAberturaFechamento = valorFechamentoAux - valorAberturaAux;
            if (diferencaAberturaFechamento > 0) {
                if (diferencaAberturaFechamento <= this.mediaAberturaFechamento * 0.2) {
                    retorno += "SubidaP";
                } else if (diferencaAberturaFechamento > this.mediaAberturaFechamento * 0.2 && diferencaAberturaFechamento <= this.mediaAberturaFechamento * 0.35) {
                    retorno += "SubidaM";
                } else {
                    retorno += "SubidaG";
                }
            } else if (diferencaAberturaFechamento < 0) {
                diferencaAberturaFechamento = Math.abs(diferencaAberturaFechamento);
                if (diferencaAberturaFechamento <= this.mediaAberturaFechamento * 0.2) {
                    retorno += "DecidaP";
                } else if (diferencaAberturaFechamento > this.mediaAberturaFechamento * 0.2 && diferencaAberturaFechamento <= this.mediaAberturaFechamento * 0.35) {
                    retorno += "DecidaM";
                } else {
                    retorno += "DecidaG";
                }
            } else {
                retorno += "Manteve";
            }
            retorno += ";";
        }
        return retorno;
    }

    public Double[] shiftRigth(int indice, Double[] analise, int tamanho, int qnt) {

        for (int c = 0; c < tamanho - qnt; c++) {
            analise[c] = analise[c + qnt];
        }
        System.out.println("analise pre");
        printAnalise(analise);
        for (int c = tamanho - qnt; c < tamanho; c++) {
            analise[c] = listaCandle[indice++].getValorFechamento();
        }

        return analise;
    }

    public void diferencaTopoFundo() {
        this.maiorDiferencaToposEFundoPositivo = 0.0;
        this.maiorDiferencaToposEFundoNegativo = 0.0;
        for (int c = 0; c < 75000; c++) {
            if (fundos[c] == -1 || topos[c] == -1) {
                break;
            }
            int indiceTopo1 = topos[c];
            int indiceTopo2 = topos[c + 1];
            int indiceFundo = fundos[c];
            Double valorFundo = listaCandle[indiceFundo].getValorFechamento();
            Double valorTopos1 = listaCandle[indiceTopo1].getValorFechamento();
            Double valorTopos2 = listaCandle[indiceTopo2].getValorFechamento();

            if (this.maiorDiferencaToposEFundoNegativo < Math.abs(valorFundo - valorTopos1)) {
                maiorDiferencaToposEFundoNegativo = Math.abs(valorFundo - valorTopos1);
            }
            if (this.maiorDiferencaToposEFundoPositivo < Math.abs(valorFundo - valorTopos2)) {
                maiorDiferencaToposEFundoPositivo = Math.abs(valorFundo - valorTopos2);
            }
        }
        System.out.println("Maior diferenca positiva: " + this.maiorDiferencaToposEFundoPositivo);
        System.out.println("Maior diferenca negativo: " + this.maiorDiferencaToposEFundoNegativo);
    }

    public void ocorrencia() {
        String[] resultado = new String[14];
        resultado[0] = "SubidaGG";
        resultado[1] = "SubidaG";
        resultado[2] = "SubidaMG";
        resultado[3] = "SubidaM";
        resultado[4] = "SubidaMP";
        resultado[5] = "SubidaP";
        resultado[6] = "SubidaPP";
        resultado[7] = "DecidaPP";
        resultado[8] = "DecidaP";
        resultado[9] = "DecidaMP";
        resultado[10] = "DecidaM";
        resultado[11] = "DecidaMG";
        resultado[12] = "DecidaG";
        resultado[13] = "DecidaGG";
        int contAnalise = 0;
        diferencaTopoFundo();
        Double auxDiferenca = 0.0;
        if (maiorDiferencaAberuraFechamentoNegativo > maiorDiferencaAberuraFechamentoPositivo) {
            auxDiferenca = maiorDiferencaAberuraFechamentoPositivo;
        } else {
            auxDiferenca = maiorDiferencaAberuraFechamentoNegativo;
        }

        for (int c = 0; c < 75000; c++) {
            if (fundos[c] == -1 || topos[c] == -1) {
                break;
            }
            int indiceTopo1 = topos[c];
            int indiceTopo2 = topos[c + 1];
            int indiceFundo = fundos[c];
            Double valorFundo = listaCandle[indiceFundo].getValorFechamento();
            Double valorTopos1 = listaCandle[indiceTopo1].getValorFechamento();
            Double valorTopos2 = listaCandle[indiceTopo2].getValorFechamento();

            Double primeiraDiferenca = Math.abs(valorTopos1 - valorFundo);
            Double segundaDiferenca = Math.abs(valorTopos2 - valorFundo);

            if (primeiraDiferenca <= (this.maiorDiferencaToposEFundoPositivo * 0.025)) {//PP
                resultados[contAnalise] = primeiraDiferenca;
                this.analise[contAnalise++] = resultado[7];
                //System.out.println("Primeira diferença: "+ primeiraDiferenca);
            } else if (primeiraDiferenca > (this.maiorDiferencaToposEFundoPositivo * 0.025) && primeiraDiferenca <= (this.maiorDiferencaToposEFundoPositivo * 0.04)) {//p
                resultados[contAnalise] = primeiraDiferenca;
                this.analise[contAnalise++] = resultado[8];
                //System.out.println("Primeira diferença: "+ primeiraDiferenca);  
            } else if (primeiraDiferenca > (this.maiorDiferencaToposEFundoPositivo * 0.04) && primeiraDiferenca <= (this.maiorDiferencaToposEFundoPositivo * 0.1)) {//mp
                resultados[contAnalise] = primeiraDiferenca;
                this.analise[contAnalise++] = resultado[9];
                //System.out.println("Primeira diferença: "+ primeiraDiferenca);
            } else if (primeiraDiferenca > (this.maiorDiferencaToposEFundoPositivo * 0.1) && primeiraDiferenca <= (this.maiorDiferencaToposEFundoPositivo * 0.175)) {//M
                resultados[contAnalise] = primeiraDiferenca;
                this.analise[contAnalise++] = resultado[10];
                //System.out.println("Primeira diferença: "+ primeiraDiferenca);
            } else if (primeiraDiferenca > (this.maiorDiferencaToposEFundoPositivo * 0.175) && primeiraDiferenca <= (this.maiorDiferencaToposEFundoPositivo * 0.275)) {//MG
                resultados[contAnalise] = primeiraDiferenca;
                this.analise[contAnalise++] = resultado[11];
                //System.out.println("Primeira diferença: "+ primeiraDiferenca);
            } else if (primeiraDiferenca > (this.maiorDiferencaToposEFundoPositivo * 0.275) && primeiraDiferenca <= (this.maiorDiferencaToposEFundoPositivo * 0.4)) {//G
                resultados[contAnalise] = primeiraDiferenca;
                this.analise[contAnalise++] = resultado[12];
                //System.out.println("Primeira diferença: "+ primeiraDiferenca);
            } else {
                resultados[contAnalise] = primeiraDiferenca;
                this.analise[contAnalise++] = resultado[13];
                //System.out.println("Primeira diferença: "+ primeiraDiferenca);
            }
            if (segundaDiferenca < (this.maiorDiferencaToposEFundoPositivo * 0.025)) {//pp
                resultados[contAnalise] = segundaDiferenca;
                this.analise[contAnalise++] = resultado[6];
                //System.out.println("Segunda diferença: "+ segundaDiferenca);
            } else if (segundaDiferenca > (this.maiorDiferencaToposEFundoPositivo * 0.025) && segundaDiferenca <= (this.maiorDiferencaToposEFundoPositivo * 0.04)) {//p
                resultados[contAnalise] = segundaDiferenca;
                this.analise[contAnalise++] = resultado[5];
                //System.out.println("Segunda diferença: "+ segundaDiferenca);
            } else if (segundaDiferenca > (this.maiorDiferencaToposEFundoPositivo * 0.04) && segundaDiferenca <= (this.maiorDiferencaToposEFundoPositivo * 0.1)) {//mp
                resultados[contAnalise] = segundaDiferenca;
                this.analise[contAnalise++] = resultado[4];
                //System.out.println("Segunda diferença: "+ segundaDiferenca);
            } else if (segundaDiferenca > (this.maiorDiferencaToposEFundoPositivo * 0.1) && segundaDiferenca <= (this.maiorDiferencaToposEFundoPositivo * 0.175)) {//m
                resultados[contAnalise] = segundaDiferenca;
                this.analise[contAnalise++] = resultado[3];
                //System.out.println("Segunda diferença: "+ segundaDiferenca);
            } else if (segundaDiferenca > (this.maiorDiferencaToposEFundoPositivo * 0.175) && segundaDiferenca <= (this.maiorDiferencaToposEFundoPositivo * 0.275)) {//mg
                resultados[contAnalise] = segundaDiferenca;
                this.analise[contAnalise++] = resultado[2];
                //System.out.println("Segunda diferença: "+ segundaDiferenca);
            } else if (segundaDiferenca > (this.maiorDiferencaToposEFundoPositivo * 0.275) && segundaDiferenca <= (this.maiorDiferencaToposEFundoPositivo * 0.40)) {//g
                resultados[contAnalise] = segundaDiferenca;
                this.analise[contAnalise++] = resultado[1];
                //System.out.println("Segunda diferença: "+ segundaDiferenca);
            } else {//GG
                resultados[contAnalise] = segundaDiferenca;
                this.analise[contAnalise++] = resultado[0];
                //System.out.println("Segunda diferença: "+ segundaDiferenca);
            }
        }

    }

    public void combinarOcorrenciaToposFundos() {
        String[] combinacao = new String[75000];
        combinacao[0] = "@relation comprar\n";
        combinacao[1] = "@attribute estado {SubidaGG;SubidaG;SubidaMG;SubidaM;SubidaMP;SubidaP;SubidaPP;DecidaPP;DecidaP;DecidaMP;DecidaM;DecidaMG;DecidaG;DecidaGG}\n";
        combinacao[2] = "@attribute decisao {Comprar;Nao_Comprar}\n";
        combinacao[3] = "@data\n";
        int contCombinacao = 4;
        for (int c = 0; c < 75000; c++) {
            if (analise[c] == "") {
                break;
            }
            String resultado = "";
            resultado += this.analise[c++] + ";";
            resultado += this.analise[c++] + ";";
            resultado += this.analise[c++] + ";";
            resultado += this.analise[c++] + ";";
            resultado += this.analise[c++] + ";";
            if (this.analise[c] == "SubidaGG" || this.analise[c] == "SubidaG" || this.analise[c] == "SubidaMG") {
                resultado += "Comprar\n";
            } else {
                resultado += "Nao_Comprar\n";
            }
            combinacao[contCombinacao++] = resultado;

        }
        try {
            salvar(combinacao);
        } catch (IOException ex) {
            Logger.getLogger(Movimento.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Não foi possível salvar o arquivo", "Erro", 0);
        }
    }

    public void padroesAnalise() {
        String[] padrao = new String[75000];
        int cont = 0;
        int[] padraoCont = new int[75000];
        for (int c = 0; c < 15000; c++) {
            padrao[c] = "";
            padraoCont[c] = 0;
        }
        for (int c = 0; c < 75000; c++) {
            if (analise[c] == "") {
                break;
            }
            String auxPadrao = "";
            int auxC = c;
            auxPadrao += this.analise[auxC++] + ";";
            auxPadrao += this.analise[auxC++] + ";";
            auxPadrao += this.analise[auxC++] + ";";
            auxPadrao += this.analise[auxC++] + ";";
            auxPadrao += this.analise[auxC];
            //System.out.println("aux padrao: " + auxPadrao);
            int indiceExitente = indicePadrao(auxPadrao, padrao);
            if (indiceExitente == -1) {
                padrao[cont] = auxPadrao;
                padraoCont[cont++]++;
            } else {
                padraoCont[indiceExitente]++;
            }
        }
        try {
            salvar(padrao, padraoCont);
        } catch (IOException ex) {
            Logger.getLogger(Movimento.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Não foi possível salvar o arquivo", "Erro", 0);
        }
        //printListaPadrao (padrao, padraoCont); 
    }
    
    public void printListaPadrao (String [] padrao, int [] qnts){
        for (int c =0 ; c < 75000;  c++){
            if (padrao[0] == ""){
                break;
            }
            System.out.println("Padrão: " + padrao[c] + " Qnt: " + qnts[c]);
        }
    }

    public int indicePadrao(String auxPadrao, String[] padrao) {
        int cont = 0;
        for (String s : padrao) {
            //System.out.println("valor lista padrao: " + s + " aux padrao: "+auxPadrao);
            if (s == "") {
                //System.out.println("retornando -1 fim");
                return -1;
            }
            if (s.equals(auxPadrao)) {
                //System.out.println("retornando cont: " + cont);
                return cont;
            }else{
                //System.out.println("se nao");
            }
            cont++;
        }
        //System.out.println("retornando -1 final");
        return -1;
    }

    public int estaAumentando(Double[] analise) {
        int tamanho = analise.length - 2;
        int contSubindo = 0;
        int contEstavel = 0;
        int contDescendo = 0;
        for (int c = 1; c < tamanho; c++) {
            Double valorA = analise[c];
            Double valorB = analise[c + 1];
            Double resultado = valorB - valorA;
            if (resultado > 0) {
                contSubindo++;
            } else if (resultado < 0) {
                contDescendo++;
            } else {
                contEstavel++;
            }
        }

        if (contSubindo == 4) {

        }
        return 0;
    }

    public void printAnalise(Double[] analise) {
        for (Double b : analise) {
            System.out.println("analise: " + b);
        }
    }

    public void printAnalise() {
        for (String s : this.analise) {
            System.out.println("analise: " + s);
        }
    }

    public void calculaDiferencaMaiorMenorValor() {

        for (int c = 0; c < 150000; c++) {
            Candle candle = listaCandle[c];
            if (candle.getInstante() == "") {
                break;
            }
            Double maior = candle.getMaiorValor();
            Double menor = candle.getMenorValor();
            Double diferenca = maior - menor;
            if (diferenca > this.maiorDiferencaMaiorMenorValor) {
                this.maiorDiferencaMaiorMenorValor = diferenca;
            }
            if (diferenca < this.menorDiferencaMaiorMenorValor) {
                this.menorDiferencaMaiorMenorValor = diferenca;
            }
        }
        System.out.println("Maior diferença maior menor valor: " + maiorDiferencaMaiorMenorValor);
        System.out.println("Menor diferença maior menor valor: " + menorDiferencaMaiorMenorValor);
    }

    public void calculaDiferencaAberturaFechamento() {

        for (int c = 0; c < 150000; c++) {
            Candle candle = listaCandle[c];
            if (candle.getInstante() == "") {
                this.nCandle = c;
                break;
            }
            Double abertura = candle.getValorAbetura();
            Double fechamento = candle.getValorFechamento();
            Double diferenca = fechamento - abertura;
            if (diferenca > 0) {
                if (diferenca > this.maiorDiferencaAberuraFechamentoPositivo) {
                    this.maiorDiferencaAberuraFechamentoPositivo = diferenca;
                }
                if (diferenca < this.menorDiferencaAberuraFechamentoPositivo) {
                    this.menorDiferencaAberuraFechamentoPositivo = diferenca;
                }
            } else {
                if (diferenca > this.maiorDiferencaAberuraFechamentoNegativo) {
                    this.maiorDiferencaAberuraFechamentoNegativo = diferenca;
                }
                if (diferenca < this.menorDiferencaAberuraFechamentoNegativo) {
                    this.menorDiferencaAberuraFechamentoNegativo = diferenca;
                }
            }
        }
        System.out.println("Maior diferença abertura e fechamento positivo: " + maiorDiferencaAberuraFechamentoPositivo);
        System.out.println("Menor diferença abertura e fechamento positivo: " + menorDiferencaAberuraFechamentoPositivo);
        System.out.println("Maior diferença abertura e fechamento negativa: " + maiorDiferencaAberuraFechamentoNegativo);
        System.out.println("Maior diferença abertura e fechamento negativa: " + menorDiferencaAberuraFechamentoNegativo);
    }

    public void mediaAberturaFechamento() {
        Double total = 0.0;
        Double diferenca = 0.0;
        int naoNulo = 0;
        for (int c = 0; c < 150000; c++) {
            Candle candle = listaCandle[c];
            if (candle.getInstante() == "") {
                this.nCandle = c;
                break;
            }
            Double abertura = candle.getValorAbetura();
            Double fechamento = candle.getValorFechamento();
            diferenca = Math.abs(fechamento - abertura);
            if (diferenca > 0) {
                naoNulo++;
            }
            total += diferenca;
        }
        mediaAberturaFechamento = total / this.nCandle;
        mediaAberturaFechamentoNaoNula = total / naoNulo;
        System.out.println("Média abertura fechamento: " + mediaAberturaFechamento + "; media nao nulo: " + mediaAberturaFechamentoNaoNula);
    }

    public void mediaMaiorMenor() {
        Double total = 0.0;
        Double diferenca = 0.0;
        int naoNulo = 0;
        for (int c = 0; c < 150000; c++) {
            Candle candle = listaCandle[c];
            if (candle.getInstante() == "") {
                this.nCandle = c;
                break;
            }
            Double maior = candle.getMaiorValor();
            Double menor = candle.getMenorValor();

            diferenca = Math.abs(maior - menor);
            if (diferenca > 0) {
                naoNulo++;
            }
            total += diferenca;
        }
        mediaMaiorMenor = total / this.nCandle;
        mediaMaiorMenorNaoNula = total / naoNulo;
        System.out.println("Média maior menor: " + mediaMaiorMenor + "; media nao nulo: " + mediaMaiorMenorNaoNula);
    }

    /**
     * @return the listaCandle
     */
    public Candle getListaCandle(int indice) {
        return getListaCandle()[indice];
    }

    /**
     * @param listaCandle the listaCandle to set
     */
    public void setListaCandle(Candle[] listaCandle) {
        this.setListaCandle(listaCandle);
    }

    /**
     * @return the listaCandle
     */
    public Candle[] getListaCandle() {
        return listaCandle;
    }

    /**
     * @return the topos
     */
    public int[] getTopos() {
        return topos;
    }

    /**
     * @param topos the topos to set
     */
    public void setTopos(int[] topos) {
        this.topos = topos;
    }

    /**
     * @return the fundos
     */
    public int[] getFundos() {
        return fundos;
    }

    /**
     * @param fundos the fundos to set
     */
    public void setFundos(int[] fundos) {
        this.fundos = fundos;
    }

    /**
     * @return the tendencia
     */
    public int[] getTendencia() {
        return tendencia;
    }

    /**
     * @param tendencia the tendencia to set
     */
    public void setTendencia(int[] tendencia) {
        this.tendencia = tendencia;
    }

    public void printCandles() {
        for (Candle c : listaCandle) {
            c.print();
        }
    }

    public void lerArquivo() throws IOException {
        LerArquivo l = new LerArquivo();
        listaCandle = l.ler();
    }

    public void printTendencia() {
        for (int c = 0; c < 75000; c++) {
            if (listaCandle[c].getInstante() == "") {
                break;
            }
            int subindo = tendencia[c];
            if (subindo > 0) {
                System.out.println("subindo: " + listaCandle[c].getInstante());
            } else {
                System.out.println("decendo: " + listaCandle[c].getInstante());
            }

        }
    }

    public void printExtremos() {
        int cont = 0;
        for (int c = 0; c < 150000; c++) {
            if ((topos[c] != 0 || cont == 0)) {
                System.out.println("Topo: " + topos[c] + " instante: " + listaCandle[topos[c]].getInstante() + " fechamento: " + listaCandle[topos[c]].getValorFechamento());
            }
            if (fundos[c] != 0 || cont == 0) {
                System.out.println("Fundo: " + fundos[c] + " instante: " + listaCandle[fundos[c]].getInstante() + " fechamento: " + listaCandle[fundos[c]].getValorFechamento());
            }
            cont++;
        }
    }

    public void printListaTopos() {
        for (int i : topos) {
            System.out.println("Topo: " + i + " instante: " + listaCandle[i].getInstante());
        }
    }

    public void printListaFundos() {
        for (int i : fundos) {
            System.out.println("Fundo: " + i);
            listaCandle[i].print();
        }
    }

    public void printListaTendencia() {
        for (int c = 0; c < 75000; c++) {
            if (listaCandle[c].getInstante() == "") {
                break;
            }
            System.out.println("Tendencia: " + tendencia[c]);
        }
    }

    public void salvar() throws IOException {
        SalvarArquivo sA = new SalvarArquivo();
        sA.Write("resultadoSubidasDecidas.csv", analise, this.resultados);

    }

    public void salvar(String[] lista) throws IOException {
        SalvarArquivo sA = new SalvarArquivo();
        sA.Write("analiseCincoOcorrencias.csv", lista, this.resultados);

    }
    public void salvar(String[] padrao, int [] qnts) throws IOException {
        SalvarArquivo sA = new SalvarArquivo();
        sA.Write("padraoOcorrencia.csv", padrao, qnts);

    }

}
