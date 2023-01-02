package br.com.gabriel.sysagenda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.gabriel.sysagenda.domain.Ligacao;
import br.com.gabriel.sysagenda.factory.ConnectionFactory;

public class LigacaoDao {

	private Connection connection;

	public LigacaoDao() {
		connection = ConnectionFactory.getConnection();
	}

	public void adiciona(Ligacao ligacao) {
		try {
			PreparedStatement stmt = connection.prepareStatement(
					"insert into ligacao (cod_contato, cod_ligacao, data_hora, observacao) values (?, ?, TO_DATE(?, 'DD/MM/YYYY HH24:MI'), ?)");

			stmt.setInt(1, ligacao.getCodContato());
			stmt.setInt(2, ligacao.getCodLigacao());
			stmt.setString(3, ligacao.getData());
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
				ligacao.setCodContato(codContato);
				ligacao.setCodLigacao(codLigacao);
				ligacao.setData(rs.getString("DATA_HORA"));
				ligacao.setObs(rs.getString("OBSERVACAO"));
			}

			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ligacao;
	}

	public List<Ligacao> lista() {
		List<Ligacao> list = null;
		try {
			PreparedStatement stmt = connection.prepareStatement(
					"select ct.cod_contato,ct.nome,lg.cod_ligacao,lg.data_hora,lg.observacao from ligacao lg join Contato ct on ct.cod_contato = lg.cod_contato ORDER BY lg.cod_ligacao");
			list = new ArrayList<>();
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Ligacao ligacao = new Ligacao();

				ligacao.setNome(rs.getString("NOME"));
				ligacao.setCodLigacao(rs.getInt("COD_LIGACAO"));
				ligacao.setCodContato(rs.getInt("COD_CONTATO"));
				ligacao.setData(rs.getString("DATA_HORA"));
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
			sql.append("UPDATE ligacao SET data_hora = TO_DATE(" + ligacao.getData()
					+ ",'DD/MM/YYYY HH24:MI'), observacao = '" + ligacao.getObs() + "' WHERE cod_contato = "
					+ ligacao.getCodContato() + " AND cod_ligacao = " + ligacao.getCodLigacao());

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

			stmt.setInt(1, ligacao.getCodContato());
			stmt.setInt(2, ligacao.getCodLigacao());

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

	public int getUtlCodLigacao(Integer cod) {
		int codLigacao = 0;
		try {
			PreparedStatement stmt = connection
					.prepareStatement("SELECT max(COD_LIGACAO)cod_ligacao FROM ligacao WHERE cod_contato = " + cod);
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
