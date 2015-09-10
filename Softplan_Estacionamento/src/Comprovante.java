
public class Comprovante {
	private int valorTotal;
	private int tempoEstacionado;
	
	public Comprovante(int tempo, CalculadoraEnum calculadora) {
		this.tempoEstacionado = tempo;
		this.valorTotal = calculadora.calcularValor(tempo);
	}
	public int getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(int valorTotal) {
		this.valorTotal = valorTotal;
	}
	public int getTempoEstacionado() {
		return tempoEstacionado;
	}
	public void setTempoEstacionado(int tempoEstacionado) {
		this.tempoEstacionado = tempoEstacionado;
	}
	
}
