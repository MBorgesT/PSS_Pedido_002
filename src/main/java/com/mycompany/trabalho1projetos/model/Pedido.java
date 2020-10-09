package com.mycompany.trabalho1projetos.model;

import com.mycompany.trabalho1projetos.ControleIds;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.json.JSONObject;

public class Pedido {

    private final int idPedido;
    private final CarrinhoDeCompra carrinho;
    private final LocalDate data;
    private final LocalDate dataVencimento;
    private final String formaPagamento;
    private String estado;
    
    private static int diasVencimento = 5;

    public Pedido(CarrinhoDeCompra carrinho, LocalDate data, String formaPagamento) {
        this.idPedido = ControleIds.getNovoIdPedido();
        this.carrinho = carrinho;
        this.data = data;
        this.dataVencimento = data.plusDays(diasVencimento);
        this.formaPagamento = formaPagamento;
        this.estado = "PENDENTE";
    }

    public NotaFiscal pagar(EnderecoEntrega endereco) {
        if (LocalDate.now().isAfter(dataVencimento)) {
            this.estado = "VENCIDO";
            throw new RuntimeException("O pedido está vencido.");
        }
        
        this.estado = "PAGO";
        for (Item item : carrinho.getItens()) {
            item.getProduto().getEstoqueProduto().removerEstoque(item.getQuantidade());
        }
        
        return new NotaFiscal(
                this,
                endereco
        );
    }

    public CarrinhoDeCompra getCarrinho() {
        return carrinho;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }
    
    public void gerarArquivoJson() throws IOException {
        FileWriter pedido = new FileWriter("pedido.json");
        pedido.write(this.toJson().toString());
        pedido.close();
    }
    
    public JSONObject toJson(){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        JSONObject json = new JSONObject();
        
        json.put("idPedito", idPedido);
        json.put("data", df.format(data));
        json.put("dataVencimento", df.format(dataVencimento));
        json.put("formaPagamento", formaPagamento);
        json.put("estado", estado);
        json.put("carrinho", carrinho.toJson());
        
        return json;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "ID: " + String.valueOf(idPedido) + "\n"
                + "Estado: " + estado + "\n"
                + "Forma de pagamento: " + formaPagamento + "\n"
                + "Data: " + df.format(data) + "\n"
                + "Data de vencimento: " + df.format(dataVencimento) + "\n"
                + super.toString();
    }

}
