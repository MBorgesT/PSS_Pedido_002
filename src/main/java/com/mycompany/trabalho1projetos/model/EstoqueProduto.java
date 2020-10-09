package com.mycompany.trabalho1projetos.model;

public class EstoqueProduto {

    private int quantidade;

    public EstoqueProduto() {
        this.quantidade = 0;
    }
    
    public void adicionarEstoque(int valor) {
        if (valor <= 0) {
            throw new RuntimeException("Informe um valor maior que zero.");
        }
        quantidade += valor;
    }
    
    public void removerEstoque(int valor) {
        if (valor <= 0) {
            throw new RuntimeException("Informe um valor maior que zero.");
        }
        quantidade -= valor;
    }

    public int getQuantidade() {
        return quantidade;
    }
    
    public boolean estoqueDisponivel(double quantidadeNecessaria) {
        return this.quantidade >= quantidadeNecessaria;
    }

}
