package br.com.gabriel.sysagenda.business;

import java.util.List;

import br.com.gabriel.sysagenda.dao.LigacaoDao;
import br.com.gabriel.sysagenda.domain.Ligacao;

public class LigacaoBss {

	LigacaoDao dao = new LigacaoDao();

	public void adicionaLigacao(Ligacao ligacao) {

		dao.adiciona(ligacao);
	}

	public List<Ligacao> getlist() {

		return dao.lista();
	}

	public Ligacao getLigacao(int codContato, int codLigacao) {

		return dao.getLigacao(codContato, codLigacao);
	}

	public void alteraLigacao(Ligacao ligacao) {

		dao.alterar(ligacao);
	}

	public void deletaLigacoes(Integer codContato) {

		dao.deletaTodas(codContato);
	}

	public void deletaLigacao(Ligacao ligacao) {

		dao.deletaUma(ligacao);
	}
	
	public Integer getCodLigacao(Integer cod) {
		
		return dao.getUtlCodLigacao(cod);
	}

}
