package co.seyon.model;

import java.io.Serializable;
import javax.persistence.*;

import co.seyon.enums.AddressType;

import java.sql.Timestamp;


/**
 * The persistent class for the address database table.
 * 
 */
@Entity
@Table(name="address")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int idaddress;

	@Column(name="address_line1", nullable=false, length=300)
	private String addressLine1;

	@Column(name="address_line2", length=300)
	private String addressLine2;

	@Column(name="address_line3", length=300)
	private String addressLine3;

	@Column(name="address_type", nullable=false, length=45)
	private String addressType;

	@Column(nullable=false, length=100)
	private String city;

	@Column(nullable=false, length=100)
	private String country;

	@Column(name="create_time", nullable=false)
	private Timestamp createTime;

	@Column(nullable=false, length=45)
	private String pincode;

	@Column(nullable=false, length=100)
	private String state;

	//bi-directional one-to-one association to User
	@OneToOne(mappedBy="address", cascade={CascadeType.ALL})
	private User user;

	//bi-directional one-to-one association to Project
	@OneToOne(mappedBy="address", cascade={CascadeType.ALL})
	private Project project;

    public Address() {
    }

	public int getIdaddress() {
		return this.idaddress;
	}

	public void setIdaddress(int idaddress) {
		this.idaddress = idaddress;
	}

	public String getAddressLine1() {
		return this.addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return this.addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return this.addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public AddressType getAddressType() {
		return AddressType.valueOf(this.addressType);
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType.toString();
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getPincode() {
		return this.pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
}