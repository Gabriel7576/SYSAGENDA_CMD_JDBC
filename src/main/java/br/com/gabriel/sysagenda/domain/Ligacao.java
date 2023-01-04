package br.com.gabriel.sysagenda.domain;

import java.util.Date;

public class Ligacao {

	private LigacaoId id;
	private Date dataHora;
	private String obs;

	public LigacaoId getId() {
		return id;
	}

	public void setId(LigacaoId id) {
		this.id = id;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Override
	public String toString() {
		return "Ligacao [id=" + id + ", dataHora=" + dataHora + ", obs=" + obs + "]";
	}

}
