package biz.phanithnhoem.api.product;

import biz.phanithnhoem.api.file.web.FileDto;
import biz.phanithnhoem.api.product.web.CreateProductDto;
import biz.phanithnhoem.api.product.web.ProductDto;
import biz.phanithnhoem.base.ApiPagedResponse;
import biz.phanithnhoem.base.ApiResponse;
import com.fasterxml.jackson.core.ObjectCodec;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.Instant;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PutMapping(path ="/image",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Object> uploadImage(@RequestParam String uuid,
                                           @RequestPart MultipartFile file) {
        System.out.println(uuid);
        System.out.println(file);
        FileDto fileDto = productService.uploadImage(uuid, file);
        return ApiResponse.builder()
                .status("SUCCESS")
                .code(HttpStatus.OK.value())
                .message("Image uploaded successfully.")
                .data(fileDto)
                .timestamp(Instant.now())
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{uuid}")
    public ApiResponse<Object> getProductByUuid(@PathVariable String uuid){
        ProductDto productDto = productService.findByUuid(uuid);
        return ApiResponse.builder()
                .status("Success")
                .code(HttpStatus.OK.value())
                .message("Product has been found.")
                .data(productDto)
                .timestamp(Instant.now())
                .build();
    }

    @GetMapping
    public ApiPagedResponse<Object> getALlWithPagination(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "4") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        List<ProductDto> productDtoSet = productService.findALlWithPagination(pageable);
        return ApiPagedResponse.builder()
                .status("Success")
                .code(HttpStatus.OK.value())
                .message("Get product with pagination successfully.")
                .data(productDtoSet)
                .recordCount(productDtoSet.size())
                .pageNo(pageable.getPageNumber())
                .totalPages((long) Math.ceil(productService.getTotalProduct() / pageable.getPageSize()))
                .totalRecords(productService.getTotalProduct())
                .timestamp(Instant.now())
                .build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNew(@RequestBody @Valid CreateProductDto createProductDto){
        productService.createNew(createProductDto);
    }
}
