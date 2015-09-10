public enum CalculadoraEnum {
	PRAIA{
		public int calcularValor(int tempo){
			return 5;
		}
	},
	CENTRO{
		public int calcularValor(int tempo){
			return 10 * tempo;
		}
	},
	SHOPPING{
		public int calcularValor(int tempo){
			return tempo <= 3 ? 6 : (6 + (tempo-3)*2);
		}
	},
	AEROPORTO{
		public int calcularValor(int tempo){
			return tempo <= 5 ? 5 : (5 + (tempo-5)*3);
		}
	};
	
	public abstract int calcularValor(int tempo);
}