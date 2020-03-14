package io.ramonak;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;

@Route
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {

    private final Image image;

    private final UploadS3 upload;

    public MainView(@Value("${aws.accessKey}") String accessKey,
                    @Value("${aws.secretKey}") String secretKey,
                    @Value("${aws.s3bucket.name}") String bucketName) {
        addClassName("centered-content");

        TextField link = new TextField("Link");
        link.setWidthFull();

        upload = new UploadS3(accessKey, secretKey, bucketName);
        upload.uploadFile(link);
        image = new Image("", "image");
        link.addValueChangeListener(e -> {
            byte[] imageBytes = upload.downloadImage();
            StreamResource resource = new StreamResource("image",
                    () -> new ByteArrayInputStream(imageBytes));
            image.setSrc(resource);
            add(image);
        });
        add(upload, link);
    }
}
