package co.seyon.customizer;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;

public class BillCustomizer implements DescriptorCustomizer {

    @Override
    public void customize(ClassDescriptor classDescriptor) throws Exception {
       classDescriptor.getQueryManager().setDeleteSQLString("UPDATE bill SET retired = true WHERE idbill = #IDBILL");
    }
}