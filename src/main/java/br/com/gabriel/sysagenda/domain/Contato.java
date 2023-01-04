package br.com.gabriel.sysagenda.domain;

public class Contato {

	private int codContato;
	private String nome;
	private Long telefone;

	public int getCodContato() {
		return codContato;
	}

	public void setCodContato(int codContato) {
		this.codContato = codContato;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}

	@Override
	public String toString() {
		return "| codContato = " + codContato + "\t nome = " + nome + "\t\t telefone = " + telefone + " |";
	}

}
