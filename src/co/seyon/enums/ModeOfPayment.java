package co.seyon.enums;

public enum ModeOfPayment {
	CASH("Cash"), CHEQUE("Cheque"), DEMAND_DRAFT("DD"), CREDIT_CARD("Credit Card"), DEBIT_CARD("Debit Card"), NET_BANKING("Net Banking");

	private String value;

	private ModeOfPayment(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
