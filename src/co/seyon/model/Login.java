package co.seyon.model;

import java.io.Serializable;
import javax.persistence.*;

import co.seyon.enums.UserType;

import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the login database table.
 * 
 */
@Entity
@Table(name="login")
public class Login implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int idlogin;

	@Column(nullable=false)
	private boolean active;

	@Column(name="create_time", nullable=false)
	private Timestamp createTime;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="last_login")
	private Date lastLogin;

	@Column(name="logged_in", nullable=false)
	private boolean loggedIn;

	@Column(nullable=false, length=1000)
	private String password;

	@Column(name="user_type", nullable=false, length=45)
	private String userType;

	@Column(nullable=false, length=45)
	private String username;

	//bi-directional one-to-one association to User
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="user_id", nullable=false)
	private User user;

    public Login() {
    }

	public int getIdlogin() {
		return this.idlogin;
	}

	public void setIdlogin(int idlogin) {
		this.idlogin = idlogin;
	}

	public boolean getActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Date getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public boolean getLoggedIn() {
		return this.loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getUserType() {
		return UserType.valueOf(this.userType);
	}

	public void setUserType(UserType userType) {
		this.userType = userType.toString();
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}