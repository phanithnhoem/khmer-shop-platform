package biz.phanithnhoem.api.product;

import biz.phanithnhoem.api.file.web.FileDto;
import biz.phanithnhoem.api.product.web.CreateProductDto;
import biz.phanithnhoem.api.product.web.ProductDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ProductService {
    List<ProductDto> findALlWithPagination(Pageable pageable);
    Long getTotalProduct();

    void createNew(CreateProductDto createProductDto);
    FileDto uploadImage(String uuid, MultipartFile file);

    ProductDto findByUuid(String uuid);
}
