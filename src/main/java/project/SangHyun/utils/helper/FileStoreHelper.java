package project.SangHyun.utils.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class FileStoreHelper {
    private final String filePath;

    public FileStoreHelper(@Value("${spring.file.dir}/") String filePath) {
        this.filePath = filePath;
    }

    public String storeFile(MultipartFile multipartFile) throws IOException {
        String path = createPath(filePath, multipartFile);
        log.info("path = {}", path);
        multipartFile.transferTo(new File(path));
        return path;
    }

    private String createPath(String filePath, MultipartFile multipartFile) {
        return filePath + createStoreFileName(multipartFile);
    }

    private String createStoreFileName(MultipartFile multipartFile) {
        String randomName = UUID.randomUUID().toString();
        String originalFilename = multipartFile.getOriginalFilename();
        return randomName + originalFilename;
    }
}
