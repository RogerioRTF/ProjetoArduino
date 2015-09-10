import java.util.Date;


public class TicketEstacionamento {
	private Carro carro;
	private Date horaDeEntrada;
	
	public Carro getCarro() {
		return carro;
	}
	public void setCarro(Carro carro) {
		this.carro = carro;
	}
	public Date getHoraDeEntrada() {
		return horaDeEntrada;
	}
	public void setHoraDeEntrada(Date horaDeEntrada) {
		this.horaDeEntrada = horaDeEntrada;
	}
	
	public TicketEstacionamento(Carro carro, Date horarioEntrada){
		this.setCarro(carro);
		this.setHoraDeEntrada(horarioEntrada);
	}
}
