package com.mycompany.trabalho1projetos;

import com.mycompany.trabalho1projetos.model.Cliente;
import java.time.LocalDate;

public class CalculadoraDeDescontos {

    private static StringValor[] cupons = {
        new StringValor("10PRC", (float) 0.1),
        new StringValor("15PRC", (float) 0.15)
    };

    private static StringValor[] tiposProduto = {
        new StringValor("PAPELARIA", (float) 0.05),
        new StringValor("ROUPA E CAMA", (float) 0.2)
    };

    private static float descontoAniversariante;

    public static float getDescontoAniversariante(Cliente cliente) {
        LocalDate aniversarioCliente = cliente.getDataAniversario();
        LocalDate agora = LocalDate.now();
        if (aniversarioCliente.getDayOfYear() == agora.getDayOfYear()) {
            return CalculadoraDeDescontos.descontoAniversariante;
        } else {
            return 0;
        }
    }

    public static float getDescontoCupom(String cupom) {
        for (StringValor sv : CalculadoraDeDescontos.cupons) {
            if (sv.getTexto().equals(cupom)) {
                return sv.getValor();
            }
        }
        return 0;
    }
    
    public static float getDescontoTipoProduto(String tipoProduto) {
        for (StringValor sv : CalculadoraDeDescontos.tiposProduto) {
            if (sv.getTexto().equals(tipoProduto)) {
                return sv.getValor();
            }
        }
        return 0;
    }
}

class StringValor {

    private String texto;
    private float valor;

    public StringValor(String texto, float valor) {
        this.texto = texto;
        this.valor = valor;
    }

    public String getTexto() {
        return texto;
    }

    public float getValor() {
        return valor;
    }

}
