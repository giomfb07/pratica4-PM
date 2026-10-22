public abstract class Pedido implements Processavel {
    private int id;
    private String nomeCliente;
    private double valor;
    private StatusPedido status = StatusPedido.PENDENTE;
    private Categoria categoria;

    public Pedido(int id, String nomeCliente, double valor, Categoria categoria)
            throws ValorInvalidoException {
        if (valor <= 0) {
            throw new ValorInvalidoException("O valor do pedido deve ser maior que zero.");
        }
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.valor = valor;
        this.categoria = categoria;
    }

    @Override
    public void processarPedido() throws PedidoInvalidoException {
        if (status == StatusPedido.CANCELADO) {
            throw new PedidoInvalidoException(
                "Pedido " + id + ": um pedido cancelado nao pode ser processado.");
        }
        verificarEntregue();
        if (status == StatusPedido.PENDENTE) {
            status = StatusPedido.PROCESSANDO;
        }
    }

    public void alterarStatus(StatusPedido novoStatus) throws PedidoInvalidoException {
        verificarEntregue();
        status = novoStatus;
    }

    private void verificarEntregue() throws PedidoInvalidoException {
        if (status == StatusPedido.ENTREGUE) {
            throw new PedidoInvalidoException(
                "Pedido " + id + ": um pedido entregue nao pode mudar de status.");
        }
    }

    public int getId() { return id; }
    public String getNomeCliente() { return nomeCliente; }
    public double getValor() { return valor; }
    public StatusPedido getStatus() { return status; }
    public Categoria getCategoria() { return categoria; }

    @Override
    public String toString() {
        return String.format("Pedido #%d | Cliente: %s | Categoria: %s | Valor: R$ %.2f | Final: R$ %.2f | Status: %s",
                id, nomeCliente, categoria, valor, calcularValorFinal(), status);
    }
}
