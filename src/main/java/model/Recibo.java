package model;

import java.time.LocalDate;

public class Recibo {
    private Cliente cliente;
    private Servico servico;
    private LocalDate data;

    public Recibo(Cliente cliente, Servico servico, LocalDate data) {
        this.cliente = cliente;
        this.servico = servico;
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Servico getServico() {
        return servico;
    }

    public LocalDate getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Recibo emitido em " + data +
                "\nCliente: " + cliente.getNome() +
                "\nCPF/CNPJ: " + cliente.getCpfCnpj() +
                "\nServiço: " + servico.getNome() +
                "\nValor: R$ " + servico.getValor() +
                "\nForma de pagamento: " + servico.getFormaPagamento();
    }
}
