package co.seyon.model;

import java.io.Serializable;
import javax.persistence.*;

import co.seyon.enums.BillType;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the bill database table.
 * 
 */
@Entity
@Table(name="bill")
public class Bill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int idbill;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="bill_date", nullable=false)
	private Date billDate;

	@Column(name="bill_number", nullable=false, length=100)
	private String billNumber;

	@Column(name="bill_status", nullable=false, length=45)
	private String billStatus;

	@Column(name="bill_type", nullable=false, length=45)
	private String billType;

	@Column(name="create_time", nullable=false)
	private Timestamp createTime;

	@Column(name="total_bill_amount", precision=10, scale=2)
	private BigDecimal totalBillAmount;

	//bi-directional many-to-one association to Project
    @ManyToOne
	@JoinColumn(name="project_id", nullable=false)
	private Project project;

	//bi-directional many-to-one association to Payment
	@OneToMany(mappedBy="bill")
	private List<Payment> payments;

	//bi-directional one-to-one association to Document
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="bill_document_id", nullable=false)
	private Document document;

    public Bill() {
    }

	public int getIdbill() {
		return this.idbill;
	}

	public void setIdbill(int idbill) {
		this.idbill = idbill;
	}

	public Date getBillDate() {
		return this.billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getBillNumber() {
		return this.billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public String getBillStatus() {
		return this.billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	public BillType getBillType() {
		return BillType.valueOf(this.billType);
	}

	public void setBillType(BillType billType) {
		this.billType = billType.toString();
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getTotalBillAmount() {
		return this.totalBillAmount;
	}

	public void setTotalBillAmount(BigDecimal totalBillAmount) {
		this.totalBillAmount = totalBillAmount;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public List<Payment> getPayments() {
		return this.payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
	
	public Document getDocument() {
		return this.document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	
}