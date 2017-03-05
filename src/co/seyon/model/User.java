package co.seyon.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.AdditionalCriteria;
import org.eclipse.persistence.annotations.Customizer;

import co.seyon.customizer.UserCustomizer;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "user")
@AdditionalCriteria(":disableRetiredFeature = 1 or this.retired = false")
@Customizer(value=UserCustomizer.class)
@NamedQueries({
		@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
		@NamedQuery(name = "User.findAllByReverseNumber", query = "SELECT u FROM User u order by u.accountNumber desc") })
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int iduser;

	@Column(name = "create_time", nullable = false)
	private Timestamp createTime;

	@Column(nullable = false, length = 100)
	private String email;

	@Column(name = "landline_number", length = 100)
	private String landlineNumber;

	@Column(name = "mobile_number", nullable = false, length = 100)
	private String mobileNumber;

	@Column(name = "account_number", nullable = false, unique = true, length = 10)
	private String accountNumber;

	@Column(nullable = false, length = 100)
	private String name;

	@Basic
	private boolean retired;
	
	// bi-directional one-to-one association to Login
	@OneToOne(mappedBy = "user")
	private Login login;

	// bi-directional one-to-one association to Address
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "address_id", nullable = false)
	private Address address;

	// bi-directional many-to-one association to Project
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Project> projects;

	public User() {
	}

	public int getIduser() {
		return this.iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLandlineNumber() {
		return this.landlineNumber;
	}

	public void setLandlineNumber(String landlineNumber) {
		this.landlineNumber = landlineNumber;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Login getLogin() {
		return this.login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public boolean isRetired() {
		return retired;
	}

	public void setRetired(boolean retired) {
		this.retired = retired;
	}
}