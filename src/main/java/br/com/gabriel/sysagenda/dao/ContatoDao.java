package br.com.gabriel.sysagenda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.gabriel.sysagenda.domain.Contato;
import br.com.gabriel.sysagenda.factory.ConnectionFactory;

public class ContatoDao {

	private Connection connection;

	public ContatoDao() {
		connection = ConnectionFactory.getConnection();
	}

	public void adiciona(Contato contato) {

		try {
			PreparedStatement stmt = connection
					.prepareStatement("insert into CONTATO (COD_CONTATO, NOME, TELEFONE) values (?, ?, ?)");

			stmt.setString(1, "" + contato.getCodContato());
			stmt.setString(2, contato.getNome());
			stmt.setString(3, "" + contato.getTelefone());

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Contato> lista() {

		PreparedStatement stmt;
		List<Contato> contatos = null;
		try {
			stmt = connection.prepareStatement("SELECT * FROM CONTATO ORDER BY COD_CONTATO");
			contatos = new ArrayList<>();
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Contato contato = new Contato();

				contato.setCodContato(rs.getInt("COD_CONTATO"));
				contato.setNome(rs.getString("NOME"));
				contato.setTelefone(rs.getLong("TELEFONE"));

				contatos.add(contato);

			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return contatos;
	}

	public Contato getContato(int Cod_cliente) {
		Contato contato = new Contato();

		try {
			PreparedStatement stmt = connection
					.prepareStatement("select * from CONTATO where COD_CONTATO = " + Cod_cliente);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				contato.setCodContato(Cod_cliente);
				contato.setNome(rs.getString("NOME"));
				contato.setTelefone(rs.getLong("TELEFONE"));
			} else 
				contato = null;

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return contato;
	}

	public void altera(Contato contato) {
		try {
			PreparedStatement stmt = connection
					.prepareStatement("update contato SET nome=?, telefone=? where COD_CONTATO=?");

			stmt.setString(1, contato.getNome());
			stmt.setLong(2, contato.getTelefone());
			stmt.setInt(3, contato.getCodContato());

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
	}

	public void deleta(Integer codContato) {
		try {
			PreparedStatement stmt = connection.prepareStatement("delete from CONTATO where COD_CONTATO = ? ");

			stmt.setInt(1, codContato);

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getUtlCodContato() {
		int codContato = 0;
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT max(cod_contato) COD_CONTATO FROM contato");
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				codContato = rs.getInt("COD_CONTATO");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return codContato;

	}
}
