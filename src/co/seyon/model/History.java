package co.seyon.model;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;
import org.eclipse.persistence.annotations.Customizer;

import co.seyon.customizer.HistoryCustomizer;
import co.seyon.enums.HistoryType;

import java.sql.Timestamp;


/**
 * The persistent class for the history database table.
 * 
 */
@Entity
@Table(name="history")
@AdditionalCriteria("this.retired = false")
@Customizer(value = HistoryCustomizer.class)
public class History implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int idhistory;

	@Column(name="change_reason", length=300)
	private String changeReason;

	@Column(name="create_time", nullable=false)
	private Timestamp createTime;

	@Column(name="current_value", nullable=false, length=1000)
	private String currentValue;

	@Column(name="field_name", nullable=false, length=45)
	private String fieldName;

	@Column(name="history_type", nullable=false, length=45)
	private String historyType;

	@Column(name="previous_value", nullable=false, length=1000)
	private String previousValue;

	@Basic
	private boolean retired;
	
	//bi-directional many-to-one association to Project
    @ManyToOne
	@JoinColumn(name="project_id", nullable=false)
	private Project project;

    public History() {
    }

	public int getIdhistory() {
		return this.idhistory;
	}

	public void setIdhistory(int idhistory) {
		this.idhistory = idhistory;
	}

	public String getChangeReason() {
		return this.changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCurrentValue() {
		return this.currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public HistoryType getHistoryType() {
		return HistoryType.valueOf(this.historyType);
	}

	public void setHistoryType(HistoryType historyType) {
		this.historyType = historyType.toString();
	}

	public String getPreviousValue() {
		return this.previousValue;
	}

	public void setPreviousValue(String previousValue) {
		this.previousValue = previousValue;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public boolean isRetired() {
		return retired;
	}

	public void setRetired(boolean retired) {
		this.retired = retired;
	}
}