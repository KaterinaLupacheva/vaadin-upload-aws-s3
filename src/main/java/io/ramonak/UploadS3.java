package io.ramonak;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class UploadS3 extends Div {

    private final MemoryBuffer buffer;
    private final Upload upload;

    private AmazonS3 s3client;

    private final String accessKey;
    private final String secretKey;
    private final String bucketName;

    public UploadS3(String accessKey, String secretKey, String bucketName) {
        this.buffer = new MemoryBuffer();
        this.upload = new Upload(buffer);
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
        initAWSClient();
        uploadFile();
        add(upload);
    }

    private void initAWSClient() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
    }

    private void uploadFile() {
        upload.addSucceededListener(event-> {
            try {
                InputStream is = buffer.getInputStream();
                File tempFile = new File(event.getFileName());
                FileUtils.copyInputStreamToFile(is, tempFile);

                String objectKey = tempFile.getName();
                s3client.putObject(new PutObjectRequest(bucketName, objectKey, tempFile));
                String url = s3client.getUrl(bucketName, objectKey).toString();
                if(tempFile.exists()) {
                    tempFile.delete();
                }
            } catch (AmazonServiceException | IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
