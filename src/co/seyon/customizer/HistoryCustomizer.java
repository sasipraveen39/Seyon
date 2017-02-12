package co.seyon.customizer;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;

public class HistoryCustomizer implements DescriptorCustomizer {

    @Override
    public void customize(ClassDescriptor classDescriptor) throws Exception {
       classDescriptor.getQueryManager().setDeleteSQLString("UPDATE history SET retired = true WHERE idhistory = #IDHISTORY");
    }
}