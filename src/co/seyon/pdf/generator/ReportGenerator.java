package co.seyon.pdf.generator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import co.seyon.model.Payment;

public class ReportGenerator {

	public boolean generateReceipt(Payment payment, String filePath) {
		boolean result = false;
		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream("Receipt.jasper");
		try {
			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(inputStream);
			Map<String, Object> params = new HashMap<>();

			List<Payment> payments = new ArrayList<Payment>();
			payments.add(payment);

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
					payments);

			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, params, dataSource);

			JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
			result = true;
		} catch (JRException e) {
			e.printStackTrace();
		}
		return result;
	}

}
