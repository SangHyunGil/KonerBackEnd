package project.SangHyun.helper;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStoreHelper {
    private final String filePath;

    public FileStoreHelper(@Value("${spring.file.dir}/") String filePath) {
        this.filePath = filePath;
    }

    public void deleteFile(String deleteFilePath) throws IOException {
        FileUtils.forceDelete(new File(deleteFilePath));
    }

    public String storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) {
            return "/defaultImg2.png";
        }
        String storeFileName = createStoreFileName(multipartFile);
        String path = createPath(filePath, storeFileName);
        multipartFile.transferTo(new File(path));
        return path;
    }

    private String createPath(String filePath, String storeFileName) {
        return filePath + storeFileName;
    }

    private String createStoreFileName(MultipartFile multipartFile) {
        String randomName = UUID.randomUUID().toString();
        String originalFilename = multipartFile.getOriginalFilename();
        return randomName + originalFilename;
    }
}
