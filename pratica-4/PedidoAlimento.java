public class PedidoAlimento extends Pedido {

    public PedidoAlimento(int id, String nomeCliente, double valor)
            throws ValorInvalidoException {
        super(id, nomeCliente, valor, Categoria.ALIMENTO);
    }

    @Override
    public double calcularValorFinal() {
        return getValor();
    }

    // Frete fixo de R$ 5 (mesmo nao sendo Entregavel, conforme enunciado)
    public double getFreteFixo() {
        return 5;
    }
}
