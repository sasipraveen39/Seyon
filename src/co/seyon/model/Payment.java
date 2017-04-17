package co.seyon.model;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;
import org.eclipse.persistence.annotations.Customizer;

import co.seyon.customizer.PaymentCustomizer;
import co.seyon.enums.ModeOfPayment;
import co.seyon.enums.PaymentStatus;
import co.seyon.util.NumberToWords;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the payment database table.
 * 
 */
@Entity
@Table(name="payment")
@AdditionalCriteria(":disableRetiredFeature = 1 or this.retired = false")
@Customizer(value = PaymentCustomizer.class)
@NamedQueries({
	@NamedQuery(name = "Payment.findAll", query = "SELECT b FROM Payment b"),
	@NamedQuery(name = "Payment.findAllByReverseNumber", query = "SELECT b FROM Payment b order by b.paymentNumber desc"),
	@NamedQuery(name = "Payment.findAllByReverseReceiptNumber", query = "SELECT b FROM Payment b where b.receiptNumber IS NOT NULL order by b.receiptNumber desc")})
public class Payment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int idpayment;

	@Column(name="payment_number", nullable=false, length=100)
	private String paymentNumber;
	
	@Column(name="amount_payable", nullable=false, precision=10, scale=2)
	private BigDecimal amountPayable;

	@Column(name="create_time", nullable=false)
	private Timestamp createTime;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="due_date", nullable=false)
	private Date dueDate;

	@Column(name="mode_of_payment", length=45)
	private String modeOfPayment;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="payment_date")
	private Date paymentDate;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="receipt_date")
	private Date receiptDate;

	@Column(name="receipt_number", length=100)
	private String receiptNumber;

	@Column(name="reference_number", length=100)
	private String referenceNumber;

	@Column(nullable=false, length=45)
	private String status;
	
	@Basic
	private boolean retired;

	//bi-directional many-to-one association to Bill
    @ManyToOne
	@JoinColumn(name="bill_id", nullable=false)
	private Bill bill;

	//bi-directional one-to-one association to Document
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
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

	public String getPaymentNumber() {
		return paymentNumber;
	}

	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
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

	public ModeOfPayment getModeOfPayment() {
		if(this.modeOfPayment != null){
			return ModeOfPayment.valueOf(this.modeOfPayment);	
		}
		return null;
	}

	public void setModeOfPayment(ModeOfPayment modeOfPayment) {
		if(modeOfPayment != null){
			this.modeOfPayment = modeOfPayment.toString();	
		}else{
			this.modeOfPayment = null;
		}
	}

	public Date getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
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

	public PaymentStatus getStatus() {
		if(this.status != null){
			return PaymentStatus.valueOf(this.status);
		}
		return null;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status.toString();
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
	
	public boolean isRetired() {
		return retired;
	}

	public void setRetired(boolean retired) {
		this.retired = retired;
	}
	
	public String getAmountInWords(){
		return NumberToWords.convertIntegerToWords(this.getAmountPayable().intValue()) + " Only";
	}
}