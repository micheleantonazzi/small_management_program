package small_management_program.view;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationShowFXML {
    public String FXMLName();
    public String Tilte();
}
