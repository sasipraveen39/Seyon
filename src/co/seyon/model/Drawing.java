package co.seyon.model;

import java.io.Serializable;
import javax.persistence.*;

import co.seyon.enums.DrawingStatus;
import co.seyon.enums.DrawingType;

import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the drawing database table.
 * 
 */
@Entity
@Table(name="drawing")
public class Drawing implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int iddrawing;

	@Column(name="create_time", nullable=false)
	private Timestamp createTime;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="date_of_issue")
	private Date dateOfIssue;

	@Column(name="drawing_number", nullable=false, length=100)
	private String drawingNumber;

	@Column(nullable=false, length=45)
	private String status;

	@Column(name="type_of_drawing", nullable=false, length=45)
	private String typeOfDrawing;

	//bi-directional many-to-one association to Project
    @ManyToOne
	@JoinColumn(name="project_id", nullable=false)
	private Project project;

	//bi-directional one-to-one association to Document
    @OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="drawing_document_id", nullable=false)
	private Document document;

    public Drawing() {
    }

	public int getIddrawing() {
		return this.iddrawing;
	}

	public void setIddrawing(int iddrawing) {
		this.iddrawing = iddrawing;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Date getDateOfIssue() {
		return this.dateOfIssue;
	}

	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getDrawingNumber() {
		return this.drawingNumber;
	}

	public void setDrawingNumber(String drawingNumber) {
		this.drawingNumber = drawingNumber;
	}

	public DrawingStatus getStatus() {
		return DrawingStatus.valueOf(this.status);
	}

	public void setStatus(DrawingStatus status) {
		this.status = status.toString();
	}

	public DrawingType getTypeOfDrawing() {
		return DrawingType.valueOf(this.typeOfDrawing);
	}

	public void setTypeOfDrawing(DrawingType typeOfDrawing) {
		this.typeOfDrawing = typeOfDrawing.toString();
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public Document getDocument() {
		return this.document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	
}