package biz.phanithnhoem.api.file;

import biz.phanithnhoem.api.file.web.FileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileService {


    @Value("${file.base-uri}")
    private String fileBaseUri;
    @Value("${file.server-path}")
    private String serverPath;
    @Value("${file.download-uri}")
    private String downloadUri;

    @Override
    public FileDto findByName(String name) throws IOException {
        Path path = Paths.get(serverPath + name);
        if (Files.exists(path)){
            Resource resource = UrlResource.from(path.toUri());
            return FileDto.builder()
                    .name(resource.getFilename())
                    .uri(fileBaseUri + resource.getFilename())
                    .downloadUri(downloadUri + resource.getFilename())
                    .extension(this.getFileExtension(this.getFileExtension(Objects.requireNonNull(resource.getFilename()))))
                    .size(resource.contentLength())
                    .build();
        }
        return null;
    }

    @Override
    public List<FileDto> findAll() {
        Path path = Paths.get(serverPath);
        List<FileDto> fileDtoList = new ArrayList<>();
        try {
            Stream<Path> paths = Files.list(path);
            List<Path> pathList = paths.toList();
            for ( Path p: pathList) {
                Resource resource = UrlResource.from(p.toUri());
                fileDtoList.add(FileDto.builder()
                        .name(resource.getFilename())
                        .uri(fileBaseUri + resource.getFilename())
                        .downloadUri(downloadUri + resource.getFilename())
                        .extension(this.getFileExtension(Objects.requireNonNull(resource.getFilename())))
                        .size(resource.contentLength())
                        .build());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileDtoList;
    }

    @Override
    public Resource downloadByName(String name) {
        // Get base uri from server directories
        Path path = Paths.get(serverPath + name);
        if (Files.exists(path)){
            // Load file from server
            return UrlResource.from(path.toUri());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource file doesn't exists.");
    }

    @Override
    public FileDto uploadSingle(MultipartFile file){
        return this.save(file);
    }
    @Override
    public List<FileDto> uploadMultiple(List<MultipartFile> files) {
        return files.stream().map(this::save).collect(Collectors.toList());
    }

    private FileDto save(MultipartFile file) {
        // Generate File Name
        String name = UUID.randomUUID().toString() + "." + this.getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        String uri = fileBaseUri + name;
        Long size = file.getSize();

        //Create an absolute file path
        Path path = Paths.get(serverPath + name);
        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return FileDto.builder()
                .name(name)
                .size(size)
                .uri(uri)
                .downloadUri(downloadUri + name)
                .extension(this.getFileExtension(file.getOriginalFilename()))
                .build();
    }

    private String getFileExtension(String name){
        int lastDotIndex = name.lastIndexOf(".");
        return name.substring(lastDotIndex + 1);
    }

}
