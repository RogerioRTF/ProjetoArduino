import java.util.Calendar;
import java.util.Date;

public class HorarioDeFuncionamento {
	private Date inicio;
	private Date fim;
	
	public HorarioDeFuncionamento(Date horarioEntrada, Date horarioSaida) {
		super();
		inicio = horarioEntrada;
		fim = horarioSaida;
	}
	
	public boolean isHorarioValido(Date horario) {
		return horario.after(inicio) && horario.before(fim);
	}

	public int calcularDiferencaEmHoras() {
		return (int) ((fim.getTime() - inicio.getTime()) /(1000*60*60));
	}
}
