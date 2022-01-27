package project.SangHyun.common.helper;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class FileStoreHelper {
    private final AmazonS3Client awsS3Client;
    private final String bucket;
    private final String filePath;

    public FileStoreHelper(@Value("${spring.file.dir}/") String filePath,
                           @Value("${cloud.aws.s3.bucket}") String bucket,
                           AmazonS3Client awsS3Client) {
        this.filePath = filePath;
        this.bucket = bucket;
        this.awsS3Client = awsS3Client;
    }

    public String storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) {
            return "디폴트이미지 경로";
        }
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

        return upload(uploadFile);
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        log.info("convertFile : {}", file);
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    private String upload(File uploadFile) {
        String fileName = createFileName(uploadFile.getName());
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String createFileName(String originalFileName) {
        String randomName = UUID.randomUUID().toString();
        return filePath + randomName + originalFileName;
    }

    private String putS3(File uploadFile, String fileName) {
        awsS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return awsS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }
}
