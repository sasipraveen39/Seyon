package co.seyon.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the project database table.
 * 
 */
@Entity
@Table(name="project")
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int idproject;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="actual_end_date")
	private Date actualEndDate;

	@Column(name="client_name", nullable=false, length=100)
	private String clientName;

	@Column(nullable=false, length=100)
	private String code;

	@Column(name="create_time", nullable=false)
	private Timestamp createTime;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="estimated_end_date")
	private Date estimatedEndDate;

	@Column(name="estimated_total_amount", precision=10, scale=2)
	private BigDecimal estimatedTotalAmount;

	@Column(name="poject_type", nullable=false, length=45)
	private String projectType;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="requested_date", nullable=false)
	private Date requestedDate;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="start_date", nullable=false)
	private Date startDate;

	@Column(nullable=false, length=100)
	private String title;

	@Column(name="total_area_of_project")
	private int totalAreaOfProject;

	//bi-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;

	//bi-directional one-to-one association to Address
    @OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="address_id", nullable=false)
	private Address address;

	//bi-directional many-to-one association to Bill
	@OneToMany(mappedBy="project")
	private List<Bill> bills;

	//bi-directional many-to-one association to Drawing
	@OneToMany(mappedBy="project")
	private List<Drawing> drawings;

	//bi-directional many-to-one association to Document
	@OneToMany(mappedBy="project")
	private List<Document> documents;

	//bi-directional many-to-one association to History
	@OneToMany(mappedBy="project")
	private List<History> histories;

    public Project() {
    }

	public int getIdproject() {
		return this.idproject;
	}

	public void setIdproject(int idproject) {
		this.idproject = idproject;
	}

	public Date getActualEndDate() {
		return this.actualEndDate;
	}

	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Date getEstimatedEndDate() {
		return this.estimatedEndDate;
	}

	public void setEstimatedEndDate(Date estimatedEndDate) {
		this.estimatedEndDate = estimatedEndDate;
	}

	public BigDecimal getEstimatedTotalAmount() {
		return this.estimatedTotalAmount;
	}

	public void setEstimatedTotalAmount(BigDecimal estimatedTotalAmount) {
		this.estimatedTotalAmount = estimatedTotalAmount;
	}

	public String getProjectType() {
		return this.projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public Date getRequestedDate() {
		return this.requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTotalAreaOfProject() {
		return this.totalAreaOfProject;
	}

	public void setTotalAreaOfProject(int totalAreaOfProject) {
		this.totalAreaOfProject = totalAreaOfProject;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public List<Bill> getBills() {
		return this.bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}
	
	public List<Drawing> getDrawings() {
		return this.drawings;
	}

	public void setDrawings(List<Drawing> drawings) {
		this.drawings = drawings;
	}
	
	public List<Document> getDocuments() {
		return this.documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
	
	public List<History> getHistories() {
		return this.histories;
	}

	public void setHistories(List<History> histories) {
		this.histories = histories;
	}
	
}