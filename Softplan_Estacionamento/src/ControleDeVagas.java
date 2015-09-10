import java.util.Date;

import exception.CarroNaoEncontradoException;
import exception.EstacionamentoLotadoException;

public class ControleDeVagas {

	private TicketEstacionamento[] tickets;
	
	public ControleDeVagas(int total) {
		this.tickets = new TicketEstacionamento[total];
	}

	public int getVagasDisponiveis() {
		int quantidadeCarros = 0;
		for (TicketEstacionamento carro : tickets) {
			if (carro != null)
				quantidadeCarros++;
		}
		return tickets.length - quantidadeCarros;
	}

	public int getVagasOcupadas() {
		int quantidadeCarros = 0;
		for (TicketEstacionamento carro : tickets) {
			if (carro != null)
				quantidadeCarros++;
		}
		return quantidadeCarros;
	}
	
	public void gerarTicket(Carro carro, Date horario) throws EstacionamentoLotadoException {
		tickets[buscarVagaDisponivel()] = new TicketEstacionamento(carro, horario);		
	}
	
	public Integer buscarVagaDisponivel() throws EstacionamentoLotadoException{
		for (int i = 0; i < tickets.length; i++) {
			if (tickets[i] == null) return i;
		}
		throw new EstacionamentoLotadoException();
	}

	private Integer buscarVagaCarroEstacionado(Carro carro) throws CarroNaoEncontradoException {
		for (int i = 0; i < tickets.length; i++) {
			if (tickets[i].getCarro().equals(carro))
				return i;
		}
		throw new CarroNaoEncontradoException();
	}
	public boolean isVagaDisponivel() {
		return getVagasDisponiveis() > 0;
	}

	public void removerTicket(Carro carro) throws CarroNaoEncontradoException {
		tickets[buscarVagaCarroEstacionado(carro)] = null;
	}

	public TicketEstacionamento getTicket(Carro carro) throws CarroNaoEncontradoException {
		return tickets[buscarVagaCarroEstacionado(carro)];
	}
	
	public Boolean existeCarro(Carro carro) {
		for(int i = 0; i < getVagasOcupadas() ; i++){
			if(tickets[i] != null && tickets[i].getCarro().equals(carro)) return true;
		}
		return false;
	}
}