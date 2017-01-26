package co.seyon.model;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the document database table.
 * 
 */
@Entity
@Table(name="document")
public class Document implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int iddocument;

	@Column(name="create_time", nullable=false)
	private Timestamp createTime;

	@Column(length=500)
	private String description;

	@Column(name="document_type", nullable=false, length=45)
	private String documentType;

	@Column(name="file_location", nullable=false, length=10000)
	private String fileLocation;

	@Column(nullable=false, length=100)
	private String name;

	//bi-directional many-to-one association to Project
    @ManyToOne
	@JoinColumn(name="project_id", nullable=false)
	private Project project;

	//bi-directional one-to-one association to Drawing
    @OneToOne(mappedBy="document", cascade={CascadeType.ALL})
	private Drawing drawing;

	//bi-directional one-to-one association to Bill
	@OneToOne(mappedBy="document", cascade={CascadeType.ALL})
	private Bill bill;

	//bi-directional one-to-one association to Payment
	@OneToOne(mappedBy="document", cascade={CascadeType.ALL})
	private Payment payment;

    public Document() {
    }

	public int getIddocument() {
		return this.iddocument;
	}

	public void setIddocument(int iddocument) {
		this.iddocument = iddocument;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getFileLocation() {
		return this.fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public Drawing getDrawing() {
		return this.drawing;
	}

	public void setDrawing(Drawing drawing) {
		this.drawing = drawing;
	}
	
	public Bill getBill() {
		return this.bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	public Payment getPayment() {
		return this.payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
}