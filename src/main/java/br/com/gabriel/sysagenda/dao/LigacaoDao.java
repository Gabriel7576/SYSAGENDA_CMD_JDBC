package br.com.gabriel.sysagenda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.gabriel.sysagenda.domain.Ligacao;
import br.com.gabriel.sysagenda.domain.LigacaoId;
import br.com.gabriel.sysagenda.factory.ConnectionFactory;
import br.com.gabriel.sysagenda.util.Funcoes;

public class LigacaoDao {

	private Connection connection;

	public LigacaoDao() {
		connection = ConnectionFactory.getConnection();
	}

	public void adiciona(Ligacao ligacao) {
		try {
			PreparedStatement stmt = connection.prepareStatement(
					"insert into ligacao (cod_contato, cod_ligacao, data_hora, observacao) values (?, ?, TO_DATE(?, 'DD/MM/YYYY HH24:MI'), ?)");

			LigacaoId id = ligacao.getId();
			stmt.setInt(1, id.getCodContato());
			stmt.setInt(2, id.getCodLigacao());
			stmt.setString(3, new SimpleDateFormat("dd/MM/yyyy HH:mm").format(ligacao.getDataHora()));
			stmt.setString(4, ligacao.getObs());

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Ligacao getLigacao(int codContato, int codLigacao) {
		Ligacao ligacao = null;
		try {
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT * FROM ligacao where cod_contato = " + codContato + " and cod_ligacao = " + codLigacao);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ligacao = new Ligacao();

				LigacaoId id = new LigacaoId();

				id.setCodContato(codContato);
				id.setCodLigacao(codLigacao);
				ligacao.setId(id);
				ligacao.setDataHora(rs.getDate("DATA_HORA"));
				ligacao.setObs(rs.getString("OBSERVACAO"));
			}

			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ligacao;
	}

	public List<Ligacao> getlista() {

		List<Ligacao> list = null;

		try {
			PreparedStatement stmt = connection.prepareStatement("select * from ligacao ORDER BY cod_contato");
			list = new ArrayList<>();
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Ligacao ligacao = new Ligacao();
				LigacaoId id = new LigacaoId();

				id.setCodLigacao(rs.getInt("COD_LIGACAO"));
				id.setCodContato(rs.getInt("COD_CONTATO"));
				ligacao.setId(id);
				ligacao.setDataHora(new Date(rs.getTimestamp("DATA_HORA").getTime()));
				ligacao.setObs(rs.getString("OBSERVACAO"));

				list.add(ligacao);
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void alterar(Ligacao ligacao) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE ligacao SET data_hora = TO_DATE('" + Funcoes.dateToStr(ligacao.getDataHora())
					+ "','DD/MM/YYYY HH24:MI'), observacao = '" + ligacao.getObs() + "' WHERE cod_contato = "
					+ ligacao.getId().getCodContato() + " AND cod_ligacao = " + ligacao.getId().getCodLigacao());

			PreparedStatement stmt = connection.prepareStatement(sql.toString());

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deletaUma(Ligacao ligacao) {
		try {
			PreparedStatement stmt = connection
					.prepareStatement("delete from ligacao where cod_contato = ? and cod_ligacao = ?");

			stmt.setInt(1, ligacao.getId().getCodContato());
			stmt.setInt(2, ligacao.getId().getCodLigacao());

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deletaTodas(Integer codContato) {
		try {
			PreparedStatement stmt = connection.prepareStatement("delete from ligacao where cod_contato = ?");

			stmt.setInt(1, codContato);

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getUtlCodLigacao(Integer codContato) {
		int codLigacao = 0;
		try {
			PreparedStatement stmt = connection
					.prepareStatement("SELECT max(COD_LIGACAO)cod_ligacao FROM ligacao WHERE cod_contato = " + codContato);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				codLigacao = rs.getInt("COD_LIGACAO");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return codLigacao;

	}
}
