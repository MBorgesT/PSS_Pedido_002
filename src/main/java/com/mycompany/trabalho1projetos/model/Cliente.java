package com.mycompany.trabalho1projetos.model;

import java.time.LocalDate;
import org.json.JSONObject;

public final class Cliente {

    private final String nome;
    private final String CNPJOuCPF;
    private final LocalDate dataAniversario;
    private double pontuacao;

    public Cliente(String nome, String codigo, LocalDate dataAniversario) {
        this.nome = nome;
        this.CNPJOuCPF = codigo;
        this.dataAniversario = dataAniversario;
        this.pontuacao = 0;
    }
    
    public void adicionarPontuacao(double valor) {
        this.pontuacao += valor;
    }

    public String getNome() {
        return nome;
    }

    public String getCNPJOuCPF() {
        return CNPJOuCPF;
    }

    public LocalDate getDataAniversario() {
        return dataAniversario;
    }
    
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        
        json.put("nome", nome);
        json.put("CNPJOuCPF", CNPJOuCPF);
        json.put("dataAniversario", dataAniversario);
        json.put("pontuacao", pontuacao);
        
        return json;
    }
    
    @Override
    public String toString() {
        return "Cliente: " + nome + ", CNPJ/CPF = " + CNPJOuCPF;
    }

}
