package co.seyon.customizer;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;

public class DocumentCustomizer implements DescriptorCustomizer {

    @Override
    public void customize(ClassDescriptor classDescriptor) throws Exception {
       classDescriptor.getQueryManager().setDeleteSQLString("UPDATE document SET retired = true WHERE iddocument = #IDDOCUMENT");
    }
}