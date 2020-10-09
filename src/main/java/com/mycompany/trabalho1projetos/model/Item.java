package com.mycompany.trabalho1projetos.model;

import com.mycompany.trabalho1projetos.CalculadoraDeDescontos;
import java.text.DecimalFormat;
import org.json.JSONObject;

public final class Item {

    protected double valorUnitario;
    protected int quantidade;
    protected Produto produto;
    protected double desconto;
    protected double valorDesconto;
    protected double valorAPagar;

    public Item(Produto produto, int quantidadeAdquirida) {
        if (!produto.getEstoqueProduto().estoqueDisponivel(quantidadeAdquirida)) {
            throw new RuntimeException("Estoque indisponível para atender a quantidade solicitada (" + quantidadeAdquirida
                    + ") para o produto " + produto.getNome()
                    + ", restam " + produto.getEstoqueProduto().getQuantidade() + " em estoque.");
        }
        this.produto = produto;
        this.quantidade = quantidadeAdquirida;
        this.valorUnitario = produto.getValorUnitario();
        this.desconto = CalculadoraDeDescontos.getDescontoTipoProduto(produto.getTipo());
        
        calcularValores();
    }
    
    public void alterarQuantidade(int valor) {
        quantidade = valor;
        calcularValores();
    }

    private void calcularValores() {
        valorDesconto = desconto * valorUnitario * quantidade;
        this.valorAPagar = valorUnitario * quantidade - valorDesconto;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public double getDesconto() {
        return desconto;
    }

    public double getValorDesconto() {
        return valorDesconto;
    }

    public double getValorAPagar() {
        return valorAPagar;
    }
    
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        
        json.put("produto", produto.getNome());
        json.put("valorUnitario", valorUnitario);
        json.put("quantidade", quantidade);
        json.put("desconto", desconto);
        json.put("valorDesconto", valorDesconto);
        json.put("valorAPagar", valorAPagar);
                
        return json;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return produto.getNome()
                + ", valor Unitario: R$ " + df.format(valorUnitario)
                + (desconto > 0 ? (", desconto: R$ " + df.format(valorDesconto) + " (" + df.format(desconto * 100) + "%)") : "")
                + ", quantidade no pedido: " + quantidade
                + ", valor a pagar: R$ " + df.format(valorAPagar);
    }

}
