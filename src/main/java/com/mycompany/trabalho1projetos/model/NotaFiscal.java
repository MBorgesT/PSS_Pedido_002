
package com.mycompany.trabalho1projetos.model;

import com.mycompany.trabalho1projetos.ControleIds;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;

public class NotaFiscal {
    
    private final int idNotaFiscal;
    private final Pedido pedido;
    private final EnderecoEntrega enderecoEntrega;
    private final double valorComICMS;
    
    private static double impostoICMS = .12; // de acordo com a tabela, esse valor vale pra qualquer estado

    public NotaFiscal(Pedido pedido, EnderecoEntrega enderecoEntrega) {
        this.idNotaFiscal = ControleIds.getNovoIdNotaFiscal();
        this.pedido = pedido;
        this.enderecoEntrega = enderecoEntrega;
        
        if (enderecoEntrega.getEstado().equals("ES")) {
            valorComICMS = pedido.getCarrinho().getValorAPagar();
        } else {
            valorComICMS = pedido.getCarrinho().getValorAPagar() + (pedido.getCarrinho().getValorAPagar() * impostoICMS);
        }
    }
    
    public void gerarArquivoJson() throws IOException {
        FileWriter notaFiscal = new FileWriter("notaFiscal.json");
        notaFiscal.write(this.toJson().toString());
        notaFiscal.close();
    }
    
    public String toJson(){
        JSONObject json = new JSONObject();
        
        json.put("idNotaFiscal", idNotaFiscal);
        json.put("pedido", pedido.toJson());
        json.put("enderecoEntrega", enderecoEntrega.toJson());
        json.put("valorFinal", valorComICMS);
        
        return json.toString();
    }
    
}
