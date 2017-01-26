package co.seyon.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the payment database table.
 * 
 */
@Entity
@Table(name="payment")
public class Payment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int idpayment;

	@Column(name="amount_payable", nullable=false, precision=10, scale=2)
	private BigDecimal amountPayable;

	@Column(name="create_time", nullable=false)
	private Timestamp createTime;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="due_date", nullable=false)
	private Date dueDate;

	@Column(name="mode_of_payment", length=45)
	private String modeOfPayment;

	@Column(name="payment_date")
	private int paymentDate;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="receipt_date")
	private Date receiptDate;

	@Column(name="receipt_number", length=100)
	private String receiptNumber;

	@Column(name="reference_number", length=100)
	private String referenceNumber;

	@Column(nullable=false, length=45)
	private String status;

	//bi-directional many-to-one association to Bill
    @ManyToOne
	@JoinColumn(name="bill_id", nullable=false)
	private Bill bill;

	//bi-directional one-to-one association to Document
    @OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="receipt_document_id")
	private Document document;

    public Payment() {
    }

	public int getIdpayment() {
		return this.idpayment;
	}

	public void setIdpayment(int idpayment) {
		this.idpayment = idpayment;
	}

	public BigDecimal getAmountPayable() {
		return this.amountPayable;
	}

	public void setAmountPayable(BigDecimal amountPayable) {
		this.amountPayable = amountPayable;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getModeOfPayment() {
		return this.modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public int getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(int paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getReceiptDate() {
		return this.receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getReceiptNumber() {
		return this.receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public String getReferenceNumber() {
		return this.referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Bill getBill() {
		return this.bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	public Document getDocument() {
		return this.document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	
}