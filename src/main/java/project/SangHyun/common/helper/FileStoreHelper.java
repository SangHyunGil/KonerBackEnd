package project.SangHyun.common.helper;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Component
public class FileStoreHelper {
    private final AmazonS3Client awsS3Client;
    private final String bucket;
    private final String filePath;
    private final String STUDY_DEFAULT_IMG = "https://koner-bucket.s3.ap-northeast-2.amazonaws.com/logo/KakaoTalk_20220128_143615435.png";

    public FileStoreHelper(AmazonS3Client awsS3Client,
                           @Value("${cloud.aws.s3.bucket}") String bucket,
                           @Value("${spring.file.dir}") String filePath) {
        this.filePath = filePath;
        this.bucket = bucket;
        this.awsS3Client = awsS3Client;
    }

    public String storeFile(MultipartFile file) throws IOException {
        if (file == null) {
            return STUDY_DEFAULT_IMG;
        }
        String fileName = createFileName(file.getOriginalFilename());
        uploadToS3(file, fileName);
        return awsS3Client.getUrl(bucket, fileName).toString();
    }

    private String createFileName(String originalFileName) {
        String randomName = UUID.randomUUID().toString();
        return filePath + randomName + originalFileName;
    }

    private void uploadToS3(MultipartFile file, String fileName) {
        InputStream inputStream = getFileInputStream(file);
        ObjectMetadata objectMetaData = getObjectMetaData(file);
        awsS3Client.putObject(bucket, fileName, inputStream, objectMetaData);
    }

    private ObjectMetadata getObjectMetaData(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        return objectMetadata;
    }

    private InputStream getFileInputStream(MultipartFile file) {
        try {
            return file.getInputStream();
        } catch (IOException e) {
            throw new IllegalArgumentException("IO Exception!");
        }
    }
}
