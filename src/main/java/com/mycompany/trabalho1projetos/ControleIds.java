
package com.mycompany.trabalho1projetos;

public class ControleIds {
    
    private static int idPedido = 0;
    private static int idNotaFiscal = 0;
    
    public static int getNovoIdPedido() {
        int novoIdPedido = ControleIds.idPedido;
        ControleIds.idPedido++;
        return novoIdPedido;
    }
    
    public static int getNovoIdNotaFiscal() {
        int novaNotaFiscal = ControleIds.idNotaFiscal;
        ControleIds.idNotaFiscal++;
        return novaNotaFiscal;
    }
    
}
