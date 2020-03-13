package io.ramonak;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Value;

@Route
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {

    public MainView(@Value("${aws.accessKey}") String accessKey,
                    @Value("${aws.secretKey}") String secretKey,
                    @Value("${aws.s3bucket.name}") String bucketName) {
        addClassName("centered-content");

        UploadS3 upload = new UploadS3(accessKey, secretKey, bucketName);
        add(upload);
    }
}
