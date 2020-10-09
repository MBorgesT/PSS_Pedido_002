package com.mycompany.trabalho1projetos.model;

import com.mycompany.trabalho1projetos.CalculadoraDeDescontos;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.json.JSONObject;

public class CarrinhoDeCompra {

    protected Cliente cliente;
    protected String cupom;
    protected double desconto;
    protected double valorDesconto;
    protected double valorAPagar;
    protected ArrayList<Item> itens;

    private static double incrementoPontuacao = 0.02;

    public CarrinhoDeCompra(Cliente cliente, String cupom) {
        if (cliente == null) {
            throw new RuntimeException("Informe um cliente válido");
        }
        this.cliente = cliente;
        this.cupom = cupom;
        this.itens = new ArrayList<>();
        
        desconto = CalculadoraDeDescontos.getDescontoAniversariante(cliente) + CalculadoraDeDescontos.getDescontoCupom(cupom);
    }

    public CarrinhoDeCompra(Cliente cliente, String cupom, double desconto, double valorDesconto, double valorAPagar, ArrayList<Item> itens) {
        if (cliente == null) {
            throw new RuntimeException("Informe um cliente válido");
        }
        this.cliente = cliente;
        this.cupom = cupom;
        this.desconto = desconto;
        this.valorDesconto = valorDesconto;
        this.valorAPagar = valorAPagar;
        this.itens = itens;
    }

    public final Pedido fechar(String formaPagamento) {
        if (itens.isEmpty()) {
            throw new RuntimeException("A quantidade de itens no carrinho não pode ser zero.");
        }
        
        Pedido pedido = new Pedido(
                this,
                LocalDate.now(),
                formaPagamento
        );

        cliente.adicionarPontuacao(valorAPagar * incrementoPontuacao);
        
        for (Item item : itens) {
            item.getProduto().getEstoqueProduto().removerEstoque(item.getQuantidade());
        }

        return pedido;
    }

    public final void addItem(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new RuntimeException("Informe uma quantidade válida.");
        }
        if (this.getItemPorNome(produto.getNome()).isPresent()) {
            throw new RuntimeException("Produto já existe! Remova-o ou altere a quantidade.");
        }
        if (quantidade > produto.getEstoqueProduto().getQuantidade()) {
            throw new RuntimeException("Essa quantidade não está disponível no estoque.");
        }
        itens.add(new Item(produto, quantidade));
        calcularValores();
    }

    public final void removerQuantidadeItem(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new RuntimeException("Informe uma quantidade válida!");
        }
        if (!this.getItemPorNome(produto.getNome()).isPresent()) {
            throw new RuntimeException("Este produto não está no carrinho");
        }

        Item item = null;
        for (Item i : itens) {
            if (i.getProduto().getNome().equals(produto.getNome())) {
                item = i;
                break;
            }
        }

        int novaQuantidade = item.getQuantidade() - quantidade;
        if (novaQuantidade < 0) {
            throw new RuntimeException("A quantidade informada é maior do que a que está no carrinho.");
        } else if (novaQuantidade > 0) {
            item.alterarQuantidade(novaQuantidade);
        } else {
            itens.remove(item);
        }
        
        calcularValores();
    }

    public final void adicionarQuantidadeItem(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new RuntimeException("Informe uma quantidade válida!");
        }
        if (!this.getItemPorNome(produto.getNome()).isPresent()) {
            throw new RuntimeException("Este produto não está no carrinho");
        }

        Item item = null;
        for (Item i : itens) {
            if (i.getProduto().getNome().equals(produto.getNome())) {
                item = i;
                break;
            }
        }
        
        if (quantidade + item.getQuantidade() > item.getProduto().getEstoqueProduto().getQuantidade()) {
            throw new RuntimeException("Esta quantidade não está disponível no estoque");
        }
        
        int novaQuantidade = item.getQuantidade() + quantidade;
        item.alterarQuantidade(novaQuantidade);
        
        calcularValores();
    }

    protected Optional<Item> getItemPorNome(String nomeProduto) {
        Optional<Item> itemEncontrado = Optional.empty();
        for (Item item : itens) {
            if (item.getProduto().getNome().toLowerCase().equals(nomeProduto.toLowerCase())) {
                itemEncontrado = Optional.of(item);
            }
        }
        return itemEncontrado;
    }

    private void calcularValores() {
        valorDesconto = 0;
        valorAPagar = 0;
        
        for (Item item : itens) {
            valorDesconto += item.getValorDesconto();
            valorAPagar += item.getValorAPagar();
        }
        
        double novoDesconto = desconto * valorAPagar;
        valorDesconto += novoDesconto;
        valorAPagar -= novoDesconto;
    }

    public double getValorDesconto() {
        return valorDesconto;
    }
    
    public double getValorDescontoItens() {
        double valor = 0;
        for (Item item : itens) {
            valor += item.getValorDesconto();
        }
        return valor;
    }
    
    public double getValorDescontoTotal() {
        return valorDesconto + getValorDescontoItens();
    }

    public void removerItem(String nomeProduto) {

        Optional<Item> produtoEncontrado = getItemPorNome(nomeProduto);
        if (!produtoEncontrado.isPresent()) {
            throw new RuntimeException("Item " + nomeProduto + " não encontrado");
        }

        itens.remove(produtoEncontrado.get());
        calcularValores();
    }

    public String getCupom() {
        return cupom;
    }

    public double getValorAPagar() {
        return valorAPagar;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Item> getItens() {
        return Collections.unmodifiableList(itens);
    }
    
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        
        json.put("cliente", cliente.toJson());
        if (!cupom.isEmpty()) 
            json.put("cupom", cupom);
        json.put("desconto", desconto);
        json.put("valorDesconto", valorDesconto);
        json.put("valorAPagar", valorAPagar);
        
        JSONObject itensJson = new JSONObject();
        for (int i = 0; i < itens.size(); i++) {
            itensJson.put("item" +  String.valueOf(i), itens.get(i).toJson());
        }
        json.put("itens", itensJson);
        
        return json;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        String retorno = "";
        retorno += cliente + "\n";
        retorno += "Valor sem desconto: R$ " + df.format(valorAPagar + getValorDescontoTotal()) + "\n";
        retorno += "Desconto: R$: " + df.format(getValorDescontoTotal()) + " (" + df.format(desconto * 100) + "% + desconto de itens individuais)\n";
        retorno += "Valor a pagar: R$ " + df.format(valorAPagar) + "\n";
        retorno += "Itens do pedido:\n";
        for (Item item : itens) {
            retorno += "\t- " + item.toString() + "\n";
        }

        return retorno;
    }

}
