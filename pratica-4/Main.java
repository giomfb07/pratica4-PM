import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final ArrayList<Pedido> pedidos = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);
    private static int proximoId = 1;

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n===== GERENCIADOR DE PEDIDOS =====");
            System.out.println("1 - Cadastrar pedido");
            System.out.println("2 - Alterar status de um pedido");
            System.out.println("3 - Processar pedidos");
            System.out.println("4 - Remover cancelados");
            System.out.println("5 - Exibir lista de pedidos");
            System.out.println("6 - Exibir pedidos entregaveis");
            System.out.println("0 - Encerrar");
            System.out.print("Opcao: ");
            opcao = lerInt();

            switch (opcao) {
                case 1: cadastrar(); break;
                case 2: alterarStatus(); break;
                case 3: processarTodos(); break;
                case 4: removerCancelados(); break;
                case 5: exibirLista(); break;
                case 6: exibirEntregaveis(); break;
                case 0: System.out.println("Encerrando..."); break;
                default: System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private static void cadastrar() {
        System.out.print("Categoria (1-Eletronico, 2-Roupa, 3-Alimento): ");
        int cat = lerInt();
        if (cat < 1 || cat > 3) {
            System.out.println("Categoria invalida.");
            return;
        }
        System.out.print("Nome do cliente: ");
        String nome = sc.nextLine();
        System.out.print("Valor: ");
        double valor = lerDouble();

        try {
            Pedido p;
            if (cat == 1) p = new PedidoEletronico(proximoId, nome, valor);
            else if (cat == 2) p = new PedidoRoupa(proximoId, nome, valor);
            else p = new PedidoAlimento(proximoId, nome, valor);

            pedidos.add(p);
            proximoId++;
            System.out.println("Pedido cadastrado com id " + p.getId());
        } catch (ValorInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void alterarStatus() {
        System.out.print("Id do pedido: ");
        Pedido p = buscar(lerInt());
        if (p == null) {
            System.out.println("Pedido nao encontrado.");
            return;
        }
        StatusPedido[] status = StatusPedido.values();
        for (int i = 0; i < status.length; i++) {
            System.out.println((i + 1) + " - " + status[i]);
        }
        System.out.print("Novo status: ");
        int op = lerInt();
        if (op < 1 || op > status.length) {
            System.out.println("Status invalido.");
            return;
        }
        try {
            p.alterarStatus(status[op - 1]);
            System.out.println("Status alterado para " + p.getStatus());
        } catch (PedidoInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void processarTodos() {
        for (Pedido p : pedidos) {
            try {
                p.processarPedido();
                System.out.println("Processado: " + p);
            } catch (PedidoInvalidoException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static void removerCancelados() {
        int antes = pedidos.size();
        pedidos.removeIf(p -> p.getStatus() == StatusPedido.CANCELADO);
        System.out.println((antes - pedidos.size()) + " pedido(s) cancelado(s) removido(s).");
    }

    private static void exibirLista() {
        boolean achou = false;
        for (Pedido p : pedidos) {
            if (p.getStatus() != StatusPedido.CANCELADO) {
                System.out.println(p);
                achou = true;
            }
        }
        if (!achou) System.out.println("Nenhum pedido para exibir.");
    }

    private static void exibirEntregaveis() {
        boolean achou = false;
        for (Pedido p : pedidos) {
            if (p instanceof Entregavel && p.getStatus() != StatusPedido.CANCELADO) {
                System.out.printf("%s | Frete: R$ %.2f%n", p, ((Entregavel) p).calcularFrete());
                achou = true;
            }
        }
        if (!achou) System.out.println("Nenhum pedido entregavel.");
    }

    private static Pedido buscar(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    private static int lerInt() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double lerDouble() {
        try {
            return Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            return 0; // sera rejeitado pela ValorInvalidoException
        }
    }
}
