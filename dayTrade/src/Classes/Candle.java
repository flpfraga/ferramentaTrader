/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author Felipe Fraga
 */
public class Candle {
    private String instante;
    private Double valorAbertura;
    private Double valorFechamento;
    private Double maiorValor;
    private Double menorValor;
    private int tempoFrame;
   
    
    
    public Candle (){
        instante  = "";
        valorAbertura = 0.0;
        valorFechamento = 0.0;
        maiorValor = 0.0;
        menorValor = 0.0;
        tempoFrame = 0;
        
    }
    public Candle (String instante, Double valorAbertura, Double maiorValor, Double menorValor,Double valorFechamento){
        
        this.instante  = instante;
        this.valorAbertura = valorAbertura;
        this.maiorValor = maiorValor;
        this.menorValor = menorValor;
        this.valorFechamento = valorFechamento;
        
    }

    /**
     * @return the data
     */
    public String getInstante() {
        return instante;
    }

    /**
     * @param data the data to set
     */
    public void setInstante(String instante) {
        this.instante = instante;
    }

    /**
     * @return the valorAbetura
     */
    public double getValorAbetura() {
        return valorAbertura;
    }

    /**
     * @param valorAbetura the valorAbetura to set
     */
    public void setValorAbetura(double valorAbetura) {
        this.valorAbertura = valorAbetura;
    }

    /**
     * @return the valorFechamento
     */
    public double getValorFechamento() {
        return valorFechamento;
    }

    /**
     * @param valorFechamento the valorFechamento to set
     */
    public void setValorFechamento(double valorFechamento) {
        this.valorFechamento = valorFechamento;
    }

    /**
     * @return the maiorValor
     */
    public double getMaiorValor() {
        return maiorValor;
    }

    /**
     * @param maiorValor the maiorValor to set
     */
    public void setMaiorValor(double maiorValor) {
        this.maiorValor = maiorValor;
    }

    /**
     * @return the menorValor
     */
    public double getMenorValor() {
        return menorValor;
    }

    /**
     * @param menorValor the menorValor to set
     */
    public void setMenorValor(double menorValor) {
        this.menorValor = menorValor;
    }

    /**
     * @return the tempo
     */
    public int getTempoFrame() {
        return tempoFrame;
    }

    /**
     * @param tempo the tempo to set
     */
    public void setTempoFrame(int tempo) {
        this.tempoFrame = tempo;
    } 
    
    public void print(){
        System.out.println("Instante: " + instante);
        System.out.println("Valor Abertura: " + valorAbertura);
        System.out.println("Maior Valor: "  +  maiorValor);
        System.out.println("Menor Valor: " + menorValor);
        System.out.println("Valor Fechamento: " + valorFechamento);
    }
    
   
}
