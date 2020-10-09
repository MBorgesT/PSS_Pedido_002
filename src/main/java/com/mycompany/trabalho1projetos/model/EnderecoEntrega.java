
package com.mycompany.trabalho1projetos.model;

import org.json.JSONObject;

public class EnderecoEntrega {
    
    private String estado, cidade, logradouro, numero;

    public EnderecoEntrega(String estado, String cidade, String logradouro, String numero) {
        this.estado = estado;
        this.cidade = cidade;
        this.logradouro = logradouro;
        this.numero = numero;
    }

    public String getEstado() {
        return estado;
    }

    public String getCidade() {
        return cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }
    
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        
        json.put("estado", estado);
        json.put("cidade", cidade);
        json.put("logradouro", logradouro);
        json.put("numero", numero);
        
        return json;
    }
    
}
