package com.darshan.graphqlsample.repository.impl;

import com.darshan.graphqlsample.exceptions.NonPaymentCSVLoaderException;
import com.darshan.graphqlsample.model.csv.LogonEvent;
import com.darshan.graphqlsample.repository.CSVLoader;
import com.google.api.gax.paging.Page;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CSVLoaderImpl implements CSVLoader {
//    @Override
//    public List<LogonEvent> loadLogonMetrics(String path) {
//        try{
//            File resource = new ClassPathResource(path).getFile();
//            return new CsvToBeanBuilder(new FileReader(resource.toPath().toString())).withType(LogonEvent.class)
//                    .build().parse();
//        }catch(IOException ioException){
//            log.error("Error while reading path {}", path);
//            throw new NonPaymentCSVLoaderException("Error while reading path {}", ioException);
//        }
//    }

    @Override
    public List<LogonEvent> loadLogonMetrics(String path) {
        try{
            String blobContent = getBlobContent(path);
            return new CsvToBeanBuilder(new StringReader(blobContent)).withType(LogonEvent.class)
                    .build().parse();
        }catch(IOException ioException){
            log.error("Error while reading path {}", path);
            throw new NonPaymentCSVLoaderException("Error while reading path {}", ioException);
        }
    }

    private Storage initGCSBucket(String pathToAuthKey, String projectId) throws IOException {
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(pathToAuthKey));
        return StorageOptions.newBuilder().setCredentials(credentials).setProjectId(projectId).build().getService();
    }

    private Bucket getBucket(String bucketName) throws IOException{
        Storage storage = initGCSBucket("/Volumes/myPro/workspace/microservices/gcs/sample-gcs/src/main/resources/arctic-joy-320713.json", "arctic-joy-320713");
        Optional<Bucket> bucketOptional = Optional.ofNullable(storage.get(bucketName));
        if (bucketOptional.isPresent()) {
            return bucketOptional.get();
        } else {
            throw new RuntimeException("Bucket with name " + bucketName + " does not exist");
        }
    }

    private String getBlobContent(String blobName) throws IOException{
        Bucket bucket = getBucket("insight-graphql-bucket");
        Page<Blob> blobs = bucket.list();
        for (Blob blob: blobs.getValues()) {
            if (blobName.equals(blob.getName())) {
                return new String(blob.getContent());
            }
        }
        throw new RuntimeException("Blob with name " + blobName + " does not exist");
    }

}
