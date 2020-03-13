package io.ramonak;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;

public class UploadS3 extends Div {

    private final MemoryBuffer buffer;
    private final Upload upload;

    public UploadS3() {
        buffer = new MemoryBuffer();
        upload = new Upload(buffer);
        add(upload);
    }
}
