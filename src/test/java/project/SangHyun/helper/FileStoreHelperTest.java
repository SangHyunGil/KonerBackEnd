//package project.SangHyun.helper;
//
//import org.apache.tomcat.util.http.fileupload.FileUtils;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//class FileStoreHelperTest {
//    String filePathDir = new File("C:/Users/Family/Desktop/SH/spring/Study").getAbsolutePath() + "/";
//    FileStoreHelper fileStoreHelper = new FileStoreHelper(filePathDir);
//
//    @BeforeEach
//    void init() throws IOException {
//        FileUtils.cleanDirectory(new File(filePathDir));
//    }
//
//    @Test
//    @DisplayName("어떠한 파일 한 개를 저장한다.")
//    public void storeFile() throws Exception {
//        //given
//        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Family\\Pictures\\Screenshots\\1.png");
//        MultipartFile multipartFile = new MockMultipartFile("Img", "myImg.png", MediaType.IMAGE_PNG_VALUE, fileInputStream);
//
//        //when
//        String profileImgUrl = fileStoreHelper.storeFile(multipartFile);
//
//        //then
//        Assertions.assertNotNull(profileImgUrl);
//    }
//
//    @Test
//    @DisplayName("어떠한 파일 한 개를 삭제한다.")
//    public void deleteFile() throws Exception {
//        //given
//        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Family\\Pictures\\Screenshots\\1.png");
//        MultipartFile multipartFile = new MockMultipartFile("Img", "myImg.png", MediaType.IMAGE_PNG_VALUE, fileInputStream);
//        String profileImgUrl = fileStoreHelper.storeFile(multipartFile);
//
//        //when, then
//        Assertions.assertDoesNotThrow(() -> fileStoreHelper.deleteFile(profileImgUrl));
//    }
//}