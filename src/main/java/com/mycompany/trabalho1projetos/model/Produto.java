package com.mycompany.trabalho1projetos.model;

import java.text.DecimalFormat;

public final class Produto {

    private String nome;
    private String tipo;
    private double valorUnitario;
    private double valorUltimaCompra;
    private EstoqueProduto estoqueProduto;

    public Produto(String nome, String tipo, double valorUnitario) {
        this.nome = nome;
        this.tipo = tipo;
        setValorUnitario(valorUnitario);
        this.estoqueProduto = new EstoqueProduto();
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public double getValorUltimaCompra() {
        return valorUltimaCompra;
    }
    
    public EstoqueProduto getEstoqueProduto() {
        return estoqueProduto;
    }

    public void setNome(String nome) {
        if (nome == null) {
            throw new RuntimeException("Nome inválido: " + nome);
        }
        this.nome = nome;
    }

    public void setValorUnitario(double valorUnitario) {
        if (valorUnitario <= 0) {
            throw new RuntimeException("Valor inválido: " + valorUnitario);
        }
        this.valorUltimaCompra = this.valorUnitario;
        this.valorUnitario = valorUnitario;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return "Produto: " + nome
                + ", valor unitario: R$" + df.format(valorUnitario)
                + ", valor da ultima compra: R$" + df.format(valorUltimaCompra);
    }

}
