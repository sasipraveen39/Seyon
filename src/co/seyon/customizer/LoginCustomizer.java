package co.seyon.customizer;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;

public class LoginCustomizer implements DescriptorCustomizer {

    @Override
    public void customize(ClassDescriptor classDescriptor) throws Exception {
       classDescriptor.getQueryManager().setDeleteSQLString("UPDATE login SET retired = true WHERE idlogin = #IDLOGIN");
    }
}