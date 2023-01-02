package br.com.ljr.sysagenda.domain;

public class Contato {

	private int codContato;
	private String nome;
	private long telefone;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getTelefone() {
		return telefone;
	}

	public void setTelefone(long telefone) {
		this.telefone = telefone;
	}

	public int getCodContato() {
		return codContato;
	}

	public void setCodContato(int codContato) {
		this.codContato = codContato;
	}

	@Override
	public String toString() {
		return "| codContato = " + codContato + "\t nome = " + nome + "\t\t telefone = " + telefone + " |";
	}

}
