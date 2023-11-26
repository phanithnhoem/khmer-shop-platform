package biz.phanithnhoem.api.file;

import biz.phanithnhoem.api.file.web.FileDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    FileDto uploadSingle(MultipartFile file);
    List<FileDto> uploadMultiple(List<MultipartFile> files);
    Resource downloadByName(String name);
    List<FileDto> findAll();
    FileDto findByName(String name) throws IOException;
}
