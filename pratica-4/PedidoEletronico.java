public class PedidoEletronico extends Pedido implements Entregavel {

    public PedidoEletronico(int id, String nomeCliente, double valor)
            throws ValorInvalidoException {
        super(id, nomeCliente, valor, Categoria.ELETRONICO);
    }

    @Override
    public double calcularValorFinal() {
        return getValor() > 100 ? getValor() * 0.90 : getValor();
    }

    @Override
    public double calcularFrete() {
        return 20 + getValor() * 0.10;
    }
}
