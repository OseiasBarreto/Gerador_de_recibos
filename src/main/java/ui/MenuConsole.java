package ui;

import java.time.format.DateTimeFormatter;

import model.Cliente;
import model.Servico;
import model.Recibo;
import repository.ClienteRepository;
import repository.ServicoRepository;
import repository.ReciboRepository;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuConsole {

    private static final ClienteRepository clienteRepository = new ClienteRepository();
    private static final ServicoRepository servicoRepository = new ServicoRepository();
    private static final ReciboRepository reciboRepository = new ReciboRepository();

    public static void executar() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║         MENU PRINCIPAL       ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║ 1 - Emitir Recibo            ║");
            System.out.println("║ 2 - Cadastrar Cliente        ║");
            System.out.println("║ 3 - Cadastrar Serviço        ║");
            System.out.println("║ 4 - Consultar Recibos por CPF║");
            System.out.println("║ 5 - Sair                     ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // consumir quebra de linha

            switch (opcao) {
                case 1:
                    emitirRecibo(scanner);
                    break;
                case 2:
                    cadastrarCliente(scanner);
                    break;
                case 3:
                    cadastrarServico(scanner);
                    break;
                case 4:
                    consultarRecibosPorCpf(scanner);
                    break;
                case 5:
                    System.out.println("👋 Encerrando o programa...");
                    break;
                default:
                    System.out.println("❌ Opção inválida. Tente novamente.");
            }

        } while (opcao != 5);

        scanner.close();
    }

    public static void emitirRecibo(Scanner scanner) {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║       EMISSÃO DE RECIBO     ║");
        System.out.println("╚══════════════════════════════╝");

        List<Cliente> clientes = clienteRepository.carregarTodos();
        List<Servico> servicos = servicoRepository.carregarTodos();

        if (clientes.isEmpty()) {
            System.out.println("⚠ Nenhum cliente cadastrado.");
            return;
        }

        if (servicos.isEmpty()) {
            System.out.println("⚠ Nenhum serviço cadastrado.");
            return;
        }

        System.out.println("\nClientes disponíveis:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + " - " + clientes.get(i).getNome() + " (" + clientes.get(i).getCpfCnpj() + ")");
        }

        System.out.print("Escolha o número do cliente: ");
        int clienteIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (clienteIndex < 0 || clienteIndex >= clientes.size()) {
            System.out.println("❌ Cliente inválido.");
            return;
        }

        System.out.println("\nServiços disponíveis:");
        for (int i = 0; i < servicos.size(); i++) {
            System.out.println((i + 1) + " - " + servicos.get(i).getNome() + " (R$ " + servicos.get(i).getValor() + ")");
        }

        System.out.print("Escolha o número do serviço: ");
        int servicoIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (servicoIndex < 0 || servicoIndex >= servicos.size()) {
            System.out.println("❌ Serviço inválido.");
            return;
        }

        Cliente cliente = clientes.get(clienteIndex);
        Servico servico = servicos.get(servicoIndex);
        LocalDate data = LocalDate.now();

        Recibo recibo = new Recibo(cliente, servico, data);
        reciboRepository.salvar(recibo);

        System.out.println("\n✅ Recibo emitido com sucesso!");
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║         RECIBO               ║");
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║ Cliente: " + cliente.getNome());
        System.out.println("║ CPF/CNPJ: " + cliente.getCpfCnpj());
        System.out.println("║ Serviço: " + servico.getNome());
        System.out.printf("║ Valor: R$ %.2f\n", servico.getValor());
        System.out.println("║ Data: " + data);
        System.out.println("╚══════════════════════════════╝");

        gerarArquivoRecibo(recibo);
    }

    public static void cadastrarCliente(Scanner scanner) {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║     CADASTRO DE CLIENTE      ║");
        System.out.println("╚══════════════════════════════╝");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF ou CNPJ: ");
        String cpfCnpj = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        Cliente cliente = new Cliente(nome, cpfCnpj, telefone, email);
        clienteRepository.salvar(cliente);

        System.out.println("\n✅ Cliente cadastrado com sucesso!");
    }

    private static void cadastrarServico(Scanner scanner) {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║       CADASTRO DE SERVIÇO    ║");
        System.out.println("╚══════════════════════════════╝");

        System.out.print("Nome do serviço: ");
        String nome = scanner.nextLine();

        System.out.print("Valor (R$): ");
        double valor = Double.parseDouble(scanner.nextLine());

        Servico servico = new Servico(nome, valor);
        servicoRepository.salvar(servico);

        System.out.println("✅ Serviço cadastrado com sucesso!");
    }


    private static void consultarRecibosPorCpf(Scanner scanner) {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║    CONSULTAR RECIBOS POR CPF ║");
        System.out.println("╚══════════════════════════════╝");

        System.out.print("Digite o CPF ou CNPJ do cliente: ");
        String cpfBusca = scanner.nextLine();

        List<Recibo> recibos = reciboRepository.filtrarPorCpf(cpfBusca);

        if (recibos.isEmpty()) {
            System.out.println("⚠ Nenhum recibo encontrado para esse CPF.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("\n🧾 Recibos encontrados:");
        for (Recibo r : recibos) {
            System.out.println("╔══════════════════════════════╗");
            System.out.println("║         RECIBO               ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║ Cliente: " + r.getCliente().getNome());
            System.out.println("║ CPF/CNPJ: " + r.getCliente().getCpfCnpj());
            System.out.println("║ Serviço: " + r.getServico().getNome());
            System.out.printf("║ Valor: R$ %.2f\n", r.getServico().getValor());
            System.out.println("║ Data: " + r.getData().format(formatter));
            System.out.println("╚══════════════════════════════╝\n");
        }
    }

    private static void gerarArquivoRecibo(Recibo recibo) {
        // Formata CPF/CNPJ e data
        String cpf = recibo.getCliente().getCpfCnpj().replaceAll("[^\\d]", "");
        String dataArquivo = recibo.getData().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String dataFormatada = recibo.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String nomeArquivo = "recibos/recibo_" + cpf + "_" + dataArquivo + ".txt";

        // Cria a pasta "recibos" se não existir
        File pasta = new File("recibos");
        if (!pasta.exists()) {
            pasta.mkdir();
        }

        // Gera o arquivo do recibo
        try (PrintWriter writer = new PrintWriter(nomeArquivo)) {
            writer.println("╔══════════════════════════════╗");
            writer.println("║           RECIBO             ║");
            writer.println("╠══════════════════════════════╣");
            writer.println("║ Cliente: " + recibo.getCliente().getNome());
            writer.println("║ CPF/CNPJ: " + recibo.getCliente().getCpfCnpj());
            writer.println("║ Serviço: " + recibo.getServico().getNome());
            writer.printf("║ Valor: R$ %.2f\n", recibo.getServico().getValor());
            writer.println("║ Data: " + dataFormatada);
            writer.println("╚══════════════════════════════╝");
            System.out.println("📄 Arquivo gerado com sucesso: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("❌ Erro ao gerar arquivo do recibo.");
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        executar();
    }

}
