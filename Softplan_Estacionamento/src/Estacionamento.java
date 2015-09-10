import java.util.Date;

import exception.CarroDuplicadoException;
import exception.CarroNaoEncontradoException;
import exception.EstacionamentoLotadoException;
import exception.ForaDoHorarioDeFuncionamentoException;
import exception.PlacaInvalidaException;
import exception.SaidaAntesDaEntradaException;

public class Estacionamento {

	private ControleDeVagas controleDeVagas;
	private HorarioDeFuncionamento horarioDeFuncionamento;

	public Estacionamento(int total, Date inicio,Date fim){
		controleDeVagas = new ControleDeVagas(total);
		horarioDeFuncionamento = new HorarioDeFuncionamento(inicio,fim);
	}
	
	public ControleDeVagas getControleDeVagas() {
		return controleDeVagas;
	}
	
	public void entraCarro(Carro carro, Date horario) throws EstacionamentoLotadoException, CarroDuplicadoException, PlacaInvalidaException, ForaDoHorarioDeFuncionamentoException {
		if(!carro.isPlacaValida(carro.getPlaca())) throw new PlacaInvalidaException();
		if(!controleDeVagas.isVagaDisponivel()) throw new EstacionamentoLotadoException();
		if(controleDeVagas.existeCarro(carro)) throw new CarroDuplicadoException();
		if(!horarioDeFuncionamento.isHorarioValido(horario)) throw new ForaDoHorarioDeFuncionamentoException();
		controleDeVagas.gerarTicket(carro,horario);
	}

	public Comprovante saiCarro(Carro carro, Date horarioSaida, String localEstacionado) throws CarroNaoEncontradoException, SaidaAntesDaEntradaException, ForaDoHorarioDeFuncionamentoException {
		if(!controleDeVagas.existeCarro(carro)) throw new CarroNaoEncontradoException(); 
		if(!horarioDeFuncionamento.isHorarioValido(horarioSaida)) throw new ForaDoHorarioDeFuncionamentoException();
		TicketEstacionamento ticket = controleDeVagas.getTicket(carro);
		if(ticket.getHoraDeEntrada().after(horarioSaida)) throw new SaidaAntesDaEntradaException();
		Comprovante comprovante = new Comprovante(tempoEstacionado(ticket.getHoraDeEntrada(),horarioSaida),CalculadoraEnum.valueOf(localEstacionado));
		controleDeVagas.removerTicket(carro);
		return comprovante ;
	}
	
	public int tempoEstacionado(Date horaEntrada, Date horarioSaida) throws CarroNaoEncontradoException{
		return new HorarioDeFuncionamento(horaEntrada, horarioSaida).calcularDiferencaEmHoras();
	}
}
