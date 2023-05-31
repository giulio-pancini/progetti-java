package it.jac.corsojava.entity;

import java.time.ZonedDateTime;
import java.util.Objects;

public class User {

	private long idUtente;
	private String username;
	private String nome;
	private String cognome;
	private String password;

	private String utenteIns;
	private String utenteMod;
	private ZonedDateTime dataIns;
	private ZonedDateTime dataMod;

	public long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUtenteIns() {
		return utenteIns;
	}

	public void setUtenteIns(String utenteIns) {
		this.utenteIns = utenteIns;
	}

	public String getUtenteMod() {
		return utenteMod;
	}

	public void setUtenteMod(String utenteMod) {
		this.utenteMod = utenteMod;
	}

	public ZonedDateTime getDataIns() {
		return dataIns;
	}

	public void setDataIns(ZonedDateTime dataIns) {
		this.dataIns = dataIns;
	}

	public ZonedDateTime getDataMod() {
		return dataMod;
	}

	public void setDataMod(ZonedDateTime dataMod) {
		this.dataMod = dataMod;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cognome, dataIns, dataMod, idUtente, nome, password, username, utenteIns, utenteMod);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		User other = (User) obj;
		return Objects.equals(cognome, other.cognome) && Objects.equals(dataIns, other.dataIns)
				&& Objects.equals(dataMod, other.dataMod) && idUtente == other.idUtente
				&& Objects.equals(nome, other.nome) && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username) && Objects.equals(utenteIns, other.utenteIns)
				&& Objects.equals(utenteMod, other.utenteMod);
	}

	@Override
	public String toString() {
		return "User [idUtente=" + idUtente + ", username=" + username + ", nome=" + nome + ", cognome=" + cognome
				+ ", password=" + password + ", utenteIns=" + utenteIns + ", utenteMod=" + utenteMod + ", dataIns="
				+ dataIns + ", dataMod=" + dataMod + "]";
	}

}
