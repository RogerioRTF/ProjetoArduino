import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import exception.CarroDuplicadoException;
import exception.CarroNaoEncontradoException;
import exception.EstacionamentoLotadoException;
import exception.ForaDoHorarioDeFuncionamentoException;
import exception.PlacaInvalidaException;
import exception.SaidaAntesDaEntradaException;

public class EstacionamentoTest {
	
	private Estacionamento estacionamento;
	private Comprovante comprovante;
	@Before
	public void criarEstacionamento(){
		estacionamento = new Estacionamento(500,horarioFicticio(7),horarioFicticio(23));
	}
	
	@Test
	public void deveTer500VagasDisponiveis() {
		int vagas = estacionamento.getControleDeVagas().getVagasDisponiveis();
		assertEquals(500, vagas);
	}
	
	@Test
	public void deveEntrarUmCarro() throws EstacionamentoLotadoException, CarroDuplicadoException, PlacaInvalidaException, ForaDoHorarioDeFuncionamentoException {
		estacionamento.entraCarro(corsa(), horarioFicticio(8));
		int vagas = estacionamento.getControleDeVagas().getVagasDisponiveis();
		assertEquals(499, vagas);
	}

	@Test
	public void deveSairUmCarro() throws EstacionamentoLotadoException, CarroNaoEncontradoException, CarroDuplicadoException, PlacaInvalidaException, ForaDoHorarioDeFuncionamentoException, SaidaAntesDaEntradaException {
		estacionamento.entraCarro(corsa(), horarioFicticio(8));
		estacionamento.saiCarro(corsa(), horarioFicticio(22),  "PRAIA");
		int vagas = estacionamento.getControleDeVagas().getVagasDisponiveis();
		assertEquals(500, vagas);
	}
	
	@Test(expected= CarroDuplicadoException.class)
	public void naoPodeEntrarCarroDuplicado() throws EstacionamentoLotadoException, CarroDuplicadoException, PlacaInvalidaException, ForaDoHorarioDeFuncionamentoException{
		estacionamento.entraCarro(corsa(), horarioFicticio(8));
		estacionamento.entraCarro(corsa(), horarioFicticio(8));
	}
	
	@Test(expected = EstacionamentoLotadoException.class)
	public void estacionamentoLotado() throws EstacionamentoLotadoException, CarroDuplicadoException, PlacaInvalidaException, ForaDoHorarioDeFuncionamentoException{
		estacionamento = new Estacionamento(1,horarioFicticio(7), horarioFicticio(23));
		estacionamento.entraCarro(corsa(), horarioFicticio(8));
		estacionamento.entraCarro(new Carro("XXX-1010"), horarioFicticio(8));
	}
	
	@Test(expected = CarroNaoEncontradoException.class)
	public void naoPodeSairCarroQueNaoExiste() throws CarroNaoEncontradoException, SaidaAntesDaEntradaException, ForaDoHorarioDeFuncionamentoException{
		estacionamento.saiCarro(corsa(), horarioFicticio(22), "PRAIA");
	}
	
	@Test(expected = PlacaInvalidaException.class)
	public void naoPodeEntrarCarroComPlacaInvalida() throws EstacionamentoLotadoException, CarroDuplicadoException, PlacaInvalidaException, ForaDoHorarioDeFuncionamentoException{
		estacionamento.entraCarro(new Carro("AAAA-101010"), horarioFicticio(8));
	}
	
	@Test(expected = ForaDoHorarioDeFuncionamentoException.class)
	public void tentouEntrarUmCarroForaDoHorario() throws EstacionamentoLotadoException, CarroDuplicadoException, PlacaInvalidaException, ForaDoHorarioDeFuncionamentoException{
		estacionamento.entraCarro(celta(),horarioFicticio(7));
	}
	
	@Test(expected = ForaDoHorarioDeFuncionamentoException.class)
	public void tentouSairUmCarroForaDoHorario() throws EstacionamentoLotadoException, CarroDuplicadoException, PlacaInvalidaException, ForaDoHorarioDeFuncionamentoException, CarroNaoEncontradoException, SaidaAntesDaEntradaException{
		estacionamento.entraCarro(celta(), horarioFicticio(8));
		estacionamento.saiCarro(celta(), horarioFicticio(23), "PRAIA");
	}
	
	@Test(expected = SaidaAntesDaEntradaException.class)
	public void umCarroNaoPodeSairAntesDeTerEntrado() throws EstacionamentoLotadoException, CarroDuplicadoException, PlacaInvalidaException, ForaDoHorarioDeFuncionamentoException, CarroNaoEncontradoException, SaidaAntesDaEntradaException{
		estacionamento.entraCarro(celta(), horarioFicticio(10));
		estacionamento.saiCarro(celta(), horarioFicticio(9), "PRAIA");
	}
	
	@Test
	public void umCarroPermaneceuDurante3HorasNoEstacionamento() throws EstacionamentoLotadoException, CarroDuplicadoException, PlacaInvalidaException, ForaDoHorarioDeFuncionamentoException, CarroNaoEncontradoException, SaidaAntesDaEntradaException{
		estacionamento.entraCarro(celta(), horarioFicticio(8));
		this.comprovante  = estacionamento.saiCarro(celta(), horarioFicticio(11), "PRAIA");
		assertEquals(3, comprovante.getTempoEstacionado());
	}
	
	@Test
	public void umCarroParouNaPraiaEDevePagar5Reais() throws EstacionamentoLotadoException, CarroDuplicadoException, PlacaInvalidaException, ForaDoHorarioDeFuncionamentoException, CarroNaoEncontradoException, SaidaAntesDaEntradaException{
		estacionamento.entraCarro(celta(), horarioFicticio(8));
		this.comprovante = estacionamento.saiCarro(celta(), horarioFicticio(10), "PRAIA");
		assertEquals(5, this.comprovante.getValorTotal());
	}
	
	@Test
	public void umCarroParouNoCentroPor4HorasEDevePagar40Reais() throws EstacionamentoLotadoException, CarroDuplicadoException, PlacaInvalidaException, ForaDoHorarioDeFuncionamentoException, CarroNaoEncontradoException, SaidaAntesDaEntradaException{
		estacionamento.entraCarro(celta(), horarioFicticio(8));
		this.comprovante  = estacionamento.saiCarro(celta(), horarioFicticio(12), "CENTRO");
		assertEquals(40, this.comprovante.getValorTotal());
	}
	
	@Test
	public void umCarroParouNoShoppingPor2HorasEDevePagar6Reais() throws EstacionamentoLotadoException, CarroDuplicadoException, PlacaInvalidaException, ForaDoHorarioDeFuncionamentoException, CarroNaoEncontradoException, SaidaAntesDaEntradaException{
		estacionamento.entraCarro(celta(), horarioFicticio(8));
		this.comprovante = estacionamento.saiCarro(celta(), horarioFicticio(10),  "SHOPPING");
		assertEquals(6, this.comprovante.getValorTotal());
	}
	
	@Test
	public void umCarroParouNoAeroportoPor3HorasEDevePagar5Reais() throws EstacionamentoLotadoException, CarroDuplicadoException, PlacaInvalidaException, ForaDoHorarioDeFuncionamentoException, CarroNaoEncontradoException, SaidaAntesDaEntradaException{
		estacionamento.entraCarro(celta(), horarioFicticio(8));
		this.comprovante  = estacionamento.saiCarro(celta(), horarioFicticio(11), "AEROPORTO");
		assertEquals(5, this.comprovante.getValorTotal());
	}
	
	private Date horarioFicticio(int hora){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hora);
		return calendar.getTime();
	}
	
	private Carro corsa() {
		return new Carro("LXM-9984");
	}
	private Carro celta() {
		return new Carro("MKJ-3886");
	}
}