package co.seyon.customizer;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;

public class ProjectCustomizer implements DescriptorCustomizer {

    @Override
    public void customize(ClassDescriptor classDescriptor) throws Exception {
       classDescriptor.getQueryManager().setDeleteSQLString("UPDATE project SET retired = true WHERE idproject = #IDPROJECT");
    }
}