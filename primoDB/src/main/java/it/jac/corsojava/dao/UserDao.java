package it.jac.corsojava.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.jac.corsojava.entity.User;
import it.jac.corsojava.exception.DaoException;

public class UserDao {

	private static Logger log = LogManager.getLogger(UserDao.class);

	private static UserDao instance = new UserDao();

	private UserDao() {

//		caricamento dei driver jdbc
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// do nothing
		}
	}

	public static UserDao getInstance() {
		return instance;
	}

	private Connection getConnection() throws SQLException {

		log.info("open connection");
		String jdbcUrl = "jdbc:mysql://localhost:3306/primodb?serverTimezone=Europe/Rome";
		String username = "root";
		String password = "0mJ22&Wr0TAN";

		return DriverManager.getConnection(jdbcUrl, username, password);
	}

	public void create(User entity) {

		StringBuilder sb = new StringBuilder();
		sb.append(" INSERT INTO utenti");
		sb.append(" (username,nome,cognome,password,utente_ins,data_ins)");
		sb.append(" VALUES");
		sb.append(" (?, ?, ?, ?, ?, ?)");

		log.debug("SQL [{}]", sb);
		log.debug("Entity [{}]", entity);

		try(Connection conn = getConnection()) {

			PreparedStatement pstm = conn.prepareStatement(sb.toString());

			int i = 1;
			pstm.setString(i++, entity.getUsername());
			pstm.setString(i++, entity.getNome());
			pstm.setString(i++, entity.getCognome());
			pstm.setString(i++, entity.getPassword());
			pstm.setString(i++, entity.getUtenteIns());
			pstm.setTimestamp(i++, Timestamp.valueOf(entity.getDataIns().toLocalDateTime()));

			log.debug("Tento di eseguire inserimento dati");

			int result = pstm.executeUpdate();

			log.debug("Modificate {} righe", result);

		} catch(SQLException e) {

			throw new DaoException("Error creating User", e);
		}
	}

	public void update(long id, User entity) {

		StringBuilder sb = new StringBuilder();
		sb.append(" update utenti");
		sb.append(" SET ");
		sb.append(" username = ?,");
		sb.append(" password = ?,");
		sb.append(" nome = ?,");
		sb.append(" cognome = ?,");
		sb.append(" utente_mod = ?,");
		sb.append(" data_mod = ?");
		sb.append(" where id = ?");

		log.debug("SQL [{}]", sb);
		log.debug("Entity [{}]", entity);

		try(Connection conn = getConnection()) {

			PreparedStatement pstm = conn.prepareStatement(sb.toString());

			int i = 1;
			pstm.setString(i++, entity.getUsername());
			pstm.setString(i++, entity.getPassword());
			pstm.setString(i++, entity.getNome());
			pstm.setString(i++, entity.getCognome());
			pstm.setString(i++, entity.getUtenteMod());

//			si può anche usare la funzione setObject(...)
			pstm.setObject(i++, entity.getDataMod());

			pstm.setLong(i, id);
			log.debug("Tento di eseguire modifica dati");

			int result = pstm.executeUpdate();

			log.debug("Modificate {} righe", result);

		} catch(SQLException e) {

			throw new DaoException("Error updating User", e);
		}
	}

	public void delete(User entity) {

		delete(entity.getIdUtente());
	}

	public void delete(long id) {

		StringBuilder sb = new StringBuilder();
		sb.append(" delete from utenti");
		sb.append(" where id = ?");

		log.debug("SQL [{}]", sb);
		log.debug("Id [{}]", id);

		try(Connection conn = getConnection()) {

			PreparedStatement pstm = conn.prepareStatement(sb.toString());

			int i = 1;
			pstm.setLong(i, id);
			log.debug("Tento di eseguire modifica dati");

			int result = pstm.executeUpdate();

			log.debug("Modificate {} righe", result);

		} catch(SQLException e) {

			throw new DaoException("Error deleting User", e);
		}
	}

	public User findById(long idUtente) {

		User result = null;

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT id, username, nome, cognome, password, ");
		sb.append(" utente_ins, data_ins, utente_mod, data_mod");
		sb.append(" FROM utenti");
		sb.append(" WHERE id = ?");

		try(Connection conn = getConnection()) {

			PreparedStatement pstm = conn.prepareStatement(sb.toString());

			int i = 1;
			pstm.setLong(i++, idUtente);

			ResultSet rs = pstm.executeQuery();

			while(rs.next()) {

				result = new User();

				result.setIdUtente(rs.getLong("id"));
				result.setUsername(rs.getString("username"));
				result.setNome(rs.getString("nome"));
				result.setCognome(rs.getString("cognome"));
				result.setPassword(rs.getString("password"));
				result.setUtenteIns(rs.getString("utente_ins"));
				result.setUtenteMod(rs.getString("utente_mod"));
				result.setDataIns(ZonedDateTime.of(rs.getTimestamp("data_ins").toLocalDateTime(), ZoneId.systemDefault()));
				Timestamp dataMod = rs.getTimestamp("data_mod");
				if (dataMod != null) {
					result.setDataMod(ZonedDateTime.of(dataMod.toLocalDateTime(), ZoneId.systemDefault()));
				}
			}

		} catch(SQLException e) {
			throw new DaoException("Error loading User", e);
		}

		return result;
	}

	public List<User> findAll() {

		List<User> resultList = new ArrayList<>();

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT id, username, nome, cognome, password, ");
		sb.append(" utente_ins, data_ins, utente_mod, data_mod");
		sb.append(" FROM utenti");

		try(Connection conn = getConnection()) {

			PreparedStatement pstm = conn.prepareStatement(sb.toString());

			ResultSet rs = pstm.executeQuery();

			while(rs.next()) {

				User user = new User();

				user.setIdUtente(rs.getLong("id"));
				user.setUsername(rs.getString("username"));
				user.setNome(rs.getString("nome"));
				user.setCognome(rs.getString("cognome"));
				user.setPassword(rs.getString("password"));
				user.setUtenteIns(rs.getString("utente_ins"));
				user.setUtenteMod(rs.getString("utente_mod"));
				user.setDataIns(ZonedDateTime.of(rs.getTimestamp("data_ins").toLocalDateTime(), ZoneId.systemDefault()));
				Timestamp dataMod = rs.getTimestamp("data_mod");
				if (dataMod != null) {
					user.setDataMod(ZonedDateTime.of(dataMod.toLocalDateTime(), ZoneId.systemDefault()));
				}

				resultList.add(user);
			}

		} catch(SQLException e) {
			throw new DaoException("Error loading User", e);
		}

		return resultList;
	}
}
