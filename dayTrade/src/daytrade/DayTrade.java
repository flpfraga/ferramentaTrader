/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daytrade;

import Classes.Movimento;
import Frames.Grafico;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Felipe Fraga
 */
public class DayTrade {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Movimento movimento = new Movimento();
        try {
            movimento.lerArquivo();
        } catch (IOException ex) {
            Logger.getLogger(Grafico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Não foi possível carregar o arquivo", "Erro", 0);
        }
        movimento.verificaExtremos();
        movimento.calculaDiferencaAberturaFechamento();
        movimento.calculaDiferencaMaiorMenorValor();
        movimento.mediaAberturaFechamento();
        movimento.mediaMaiorMenor();
        //movimento.verificaTendencia();
        //movimento.printExtremos();
        //movimento.printTendencia();
        //movimento.printListaTendencia();
        //movimento.printListaTopos();
        //movimento.printListaFundos();
        //movimento.printCandles();
        movimento.ocorrencia();
        movimento.padroesAnalise();
        /*try {
            movimento.salvar();
        } catch (IOException ex) {
            Logger.getLogger(Grafico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Não foi possível salvar o arquivo", "Erro", 0);
        }*/
    }    
}
