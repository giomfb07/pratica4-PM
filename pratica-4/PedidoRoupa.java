public class PedidoRoupa extends Pedido implements Entregavel {

    public PedidoRoupa(int id, String nomeCliente, double valor)
            throws ValorInvalidoException {
        super(id, nomeCliente, valor, Categoria.ROUPA);
    }

    @Override
    public double calcularValorFinal() {
        return getValor() > 200 ? getValor() * 0.80 : getValor();
    }

    @Override
    public double calcularFrete() {
        return 10 + getValor() * 0.05;
    }
}
