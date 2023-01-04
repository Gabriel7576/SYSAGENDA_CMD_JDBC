package br.com.gabriel.sysagenda.domain;

public class LigacaoId {

	private int codContato;
	private int codLigacao;

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

	@Override
	public String toString() {
		return "LigacaoId [codContato=" + codContato + ", codLigacao=" + codLigacao + "]";
	}

}
