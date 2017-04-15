package co.seyon.enums;

public enum ModeOfPayment implements Type{
	CASH("Cash"), CHEQUE("Cheque"), CREDIT_CARD("Credit Card"), DEMAND_DRAFT("DD"), DEBIT_CARD("Debit Card"), NET_BANKING("Net Banking");

	private String value;

	private ModeOfPayment(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}
}
