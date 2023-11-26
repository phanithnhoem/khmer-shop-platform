package biz.phanithnhoem.api.file;

import biz.phanithnhoem.api.file.web.FileDto;
import biz.phanithnhoem.base.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/{name}")
    public ApiResponse<Object> findByName(@PathVariable String name) throws IOException {
        FileDto fileDto = fileService.findByName(name);
        return ApiResponse.builder()
                .status("Success")
                .message("File has been found.")
                .code(HttpStatus.OK.value())
                .data(fileDto)
                .timestamp(Instant.now())
                .build();
    }

    @GetMapping
    public ApiResponse<Object> findAll(){
        List<FileDto> fileDtos = fileService.findAll();
        return ApiResponse.builder()
                .status("Success")
                .message("Get files from server successfully.")
                .code(HttpStatus.OK.value())
                .data(fileDtos)
                .timestamp(Instant.now())
                .build();
    }

    @GetMapping("/download/{name}")
    public ResponseEntity<?> downloadByName(@PathVariable String name){
        Resource resource = fileService.downloadByName(name);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=" + resource.getFilename())
                .body(resource);
    }

    @PostMapping(value = "/single", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Object> uploadSingle(@RequestPart("file") @Valid MultipartFile file) {
        System.out.println(file);
        FileDto fileDto = fileService.uploadSingle(file);
        return ApiResponse.builder()
                .status("Success")
                .code(HttpStatus.OK.value())
                .message("File upload successfully")
                .timestamp(Instant.now())
                .data(fileDto)
                .build();
    }
    @PostMapping(value = "/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Object> uploadMultiple(@RequestPart("files") @Valid List<MultipartFile> files) {
        System.out.println(files);
        List<FileDto> fileDtos = fileService.uploadMultiple(files);
        return ApiResponse.builder()
                .status("Success")
                .code(HttpStatus.OK.value())
                .message("File upload successfully")
                .timestamp(Instant.now())
                .data(fileDtos)
                .build();
    }
}
