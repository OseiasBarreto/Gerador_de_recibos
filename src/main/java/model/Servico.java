package model;

import java.time.LocalDate;

public class Servico {
    private String nome;
    private double valor;
    private String formaPagamento;
    private LocalDate data;

    // Construtor
    public Servico(String nome, double valor) {
        this.nome = nome;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
        this.data = data;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public LocalDate getData() {
        return data;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    // toString personalizado
    @Override
    public String toString() {
        return "\n📄 Serviço: " + nome +
                "\n💰 Valor: R$ " + String.format("%.2f", valor) +
                "\n💳 Forma de Pagamento: " + formaPagamento +
                "\n📅 Data: " + data;
    }
}
