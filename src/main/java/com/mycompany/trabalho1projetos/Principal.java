package com.mycompany.trabalho1projetos;

import com.mycompany.trabalho1projetos.model.CarrinhoDeCompra;
import com.mycompany.trabalho1projetos.model.Cliente;
import com.mycompany.trabalho1projetos.model.EnderecoEntrega;
import com.mycompany.trabalho1projetos.model.NotaFiscal;
import com.mycompany.trabalho1projetos.model.Pedido;
import com.mycompany.trabalho1projetos.model.Produto;
import java.io.IOException;
import java.time.LocalDate;

public class Principal {

    public static void main(String[] args) throws IOException {
        
        Cliente cliente1 = new Cliente(
                "SÉRGIO",
                "111.111.111-32",
                LocalDate.now()
        );
        
        
        Produto banana = new Produto(
                "PENCA DE BANANA",
                "ALIMENTO",
                4
        );
        banana.getEstoqueProduto().adicionarEstoque(20);
        
        Produto caneta = new Produto(
                "CANETA BIC",
                "PAPELARIA",
                2
        );
        caneta.getEstoqueProduto().adicionarEstoque(12);
        
        
        CarrinhoDeCompra carrinho = new CarrinhoDeCompra(
                cliente1, 
                "15PRC"
        );
        
        carrinho.addItem(caneta, 5);
        carrinho.addItem(banana, 10);
        

        Pedido pedido = carrinho.fechar("CARTÃO");
        
        NotaFiscal notaFiscal = pedido.pagar(new EnderecoEntrega(
                "RJ",
                "Itaperuna",
                "Rua avenida central",
                "42"
        ));
        
        notaFiscal.gerarArquivoJson();

    }

}
