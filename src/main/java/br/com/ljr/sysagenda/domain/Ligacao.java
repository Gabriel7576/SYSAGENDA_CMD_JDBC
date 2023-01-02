package br.com.ljr.sysagenda.domain;

public class Ligacao {

	private int codLigacao;
	private int codContato;
	private String data;
	private String hora;
	private String nome;
	private String obs;

	public int getCodContato() {
		return codContato;
	}

	public void setCodContato(int codContato) {
		this.codContato = codContato;
	}

	public int getCodLigacao() {
		return codLigacao;
	}

	public void setCodLigacao(int codLigacao) {
		this.codLigacao = codLigacao;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	@Override
	public String toString() {
		return "| Cod_Contato = "+codContato+"\t Nome = " + nome + "\t Cod_Ligação = " + codLigacao + "\t Data_Hora = " + data + "\t Observação = "
			+ obs + " |";
	}
}
