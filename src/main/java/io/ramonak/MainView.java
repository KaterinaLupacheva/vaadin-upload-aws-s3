package io.ramonak;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {

    public MainView() {
        addClassName("centered-content");

        UploadS3 upload = new UploadS3();
        add(upload);
    }
}
