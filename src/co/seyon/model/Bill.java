package co.seyon.model;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;
import org.eclipse.persistence.annotations.Customizer;

import co.seyon.customizer.BillCustomizer;
import co.seyon.enums.BillPaymentStatus;
import co.seyon.enums.BillStatus;
import co.seyon.enums.BillType;
import co.seyon.enums.PaymentInstallementType;
import co.seyon.enums.PaymentStatus;

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
@AdditionalCriteria(":disableRetiredFeature = 1 or this.retired = false")
@Customizer(value=BillCustomizer.class)
@NamedQueries({
	@NamedQuery(name = "Bill.findAll", query = "SELECT b FROM Bill b"),
	@NamedQuery(name = "Bill.findAllByReverseNumber", query = "SELECT b FROM Bill b order by b.billNumber desc") })
public class Bill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int idbill;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="bill_date", nullable=false)
	private Date billDate;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="due_date", nullable=false)
	private Date dueDate;
    
	@Column(name="bill_number", nullable=false, length=100)
	private String billNumber;

	@Column(name="bill_status", nullable=false, length=45)
	private String billStatus;

	@Column(name="bill_type", nullable=false, length=45)
	private String billType;

	@Column(name="payment_installement", length=45)
	private String paymentInstallement;
	
	@Column(name="create_time", nullable=false)
	private Timestamp createTime;

	@Column(name="total_bill_amount", precision=10, scale=2)
	private BigDecimal totalBillAmount;

	@Basic
	private boolean retired;
	
	//bi-directional many-to-one association to Project
    @ManyToOne
	@JoinColumn(name="project_id", nullable=false)
	private Project project;

	//bi-directional many-to-one association to Payment
	@OneToMany(mappedBy="bill", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Payment> payments;

	//bi-directional one-to-one association to Document
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
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

	public BillStatus getBillStatus() {
		if(this.billStatus != null){
			return BillStatus.valueOf(this.billStatus);	
		}
		return null;
	}

	public void setBillStatus(BillStatus billStatus) {
		if(billStatus != null){
			this.billStatus = billStatus.toString();	
		}
		this.billStatus = null;
	}

	public BillType getBillType() {
		if(this.billType != null){
			return BillType.valueOf(this.billType);	
		}
		return null;
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
	
	public boolean isRetired() {
		return retired;
	}

	public void setRetired(boolean retired) {
		this.retired = retired;
	}
	
	public PaymentInstallementType getPaymentInstallement() {
		if(this.billType != null){
			return PaymentInstallementType.valueOf(this.paymentInstallement);	
		}
		return null;
	}

	public void setPaymentInstallement(PaymentInstallementType paymentInstallementType) {
		this.paymentInstallement = paymentInstallementType.toString();
	}
	
	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getPaidAmount(){
		double paidAmount = 0.00;
		if(this.getPayments() != null){
			for(Payment p : this.getPayments()){
				if(p.getStatus() == PaymentStatus.PAID){
					paidAmount += p.getAmountPayable().doubleValue();	
				}
			}
		}
		return BigDecimal.valueOf(paidAmount);
	}
	
	public BigDecimal getAmountPending(){
		return BigDecimal.valueOf(this.getTotalBillAmount().doubleValue() - this.getPaidAmount().doubleValue());
	}
	
	public BillPaymentStatus getBillPaymentStatus(){
		if(this.getAmountPending().doubleValue() > 0){
			return BillPaymentStatus.PENDING;
		}else if(this.getAmountPending().doubleValue() < 0){
			return BillPaymentStatus.PAID_IN_EXCESS;
		} else{
			return BillPaymentStatus.PAID;
		}
	}
}