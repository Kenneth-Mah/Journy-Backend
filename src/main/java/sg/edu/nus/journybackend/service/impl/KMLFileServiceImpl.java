package sg.edu.nus.journybackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sg.edu.nus.journybackend.entity.KMLFile;
import sg.edu.nus.journybackend.entity.Post;
import sg.edu.nus.journybackend.exception.ResourceNotFoundException;
import sg.edu.nus.journybackend.repository.KMLFileRepository;
import sg.edu.nus.journybackend.repository.PostRepository;
import sg.edu.nus.journybackend.service.KMLFileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class KMLFileServiceImpl implements KMLFileService {

    private final PostRepository postRepository;
    private final KMLFileRepository kmlFileRepository;

    private static final String BASE_PATH = System.getProperty("user.dir");
    private static final String RELATIVE_PATH = "src/main/kml/";
    private static final String FILE_PATH = Paths.get(BASE_PATH, RELATIVE_PATH).toString();

    @Override
    public KMLFile storeIntoFileSystem(Long postId, MultipartFile file) throws IOException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        String filePath = FILE_PATH + "\\" + postId + file.getOriginalFilename();
        System.out.println(filePath);

        KMLFile kmlFile = KMLFile.builder()
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .filePath(filePath)
                .fileData(file.getBytes())
                .build();

        post.setKmlFile(kmlFile);
        kmlFile.setPost(post);

        kmlFile = kmlFileRepository.save(kmlFile);

        file.transferTo(new File(filePath));

        if (kmlFile.getKmlFileId() != null) {
            return kmlFile;
        } else {
            throw new FileSystemException("Unable to save file");
        }
    }

    @Override
    public KMLFile downloadKMLFileUsingFileId(Long kmlFileId) throws IOException {
        KMLFile kmlFile = kmlFileRepository.findById(kmlFileId).orElseThrow(
                () -> new ResourceNotFoundException("KML file does not exist with given id : " + kmlFileId)
        );

        String path = kmlFile.getFilePath();

        byte[] fileContent = java.nio.file.Files.readAllBytes(new File(path).toPath());

        kmlFile.setFileData(fileContent);
        return kmlFile;
    }

}
