package br.com.gabriel.sysagenda.business;

import java.util.List;

import br.com.gabriel.sysagenda.dao.ContatoDao;
import br.com.gabriel.sysagenda.domain.Contato;

public class ContatoBss {

	ContatoDao dao = new ContatoDao();

	public void adiciona(Contato contato) {

		dao.adiciona(contato);
	}

	public List<Contato> getList() {

		return dao.lista();
	}

	public Contato getContato(Integer codContato) {

		return dao.getContato(codContato);
	}

	public void alteraContato(Contato contato) {

		dao.altera(contato);
	}

	public void deletaContato(Integer codContato, boolean simNao) {
		if (simNao) {
			LigacaoBss ligacaoBss = new LigacaoBss();
			ligacaoBss.deletaLigacoes(codContato);
			dao.deleta(codContato);
		}
	}

	public Integer getCodContato() {

		return dao.getUtlCodContato();
	}
}
