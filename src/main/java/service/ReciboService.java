package service;

import model.Recibo;

import java.util.Comparator;
import java.util.List;

public class ReciboService {
    // Ordena a lista por data de emissão
    public void listarRecibosEmOrdem(List<Recibo> recibos) {
        recibos.sort(Comparator.comparing(Recibo::getData));
        for (Recibo r : recibos) {
            System.out.println("Cliente: " + r.getCliente().getNome());
            System.out.println("Serviço: " + r.getServico().getNome());
            System.out.printf("Valor: R$ %.2f\n", r.getServico().getValor());
            System.out.println("Data: " + r.getData());
            System.out.println("---------");
        }
    }
}
