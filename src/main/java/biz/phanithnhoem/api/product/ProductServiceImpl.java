package biz.phanithnhoem.api.product;

import biz.phanithnhoem.api.file.FileService;
import biz.phanithnhoem.api.file.web.FileDto;
import biz.phanithnhoem.api.product.web.CreateProductDto;
import biz.phanithnhoem.api.product.web.ProductDto;
import biz.phanithnhoem.util.GeneratorUtil;
import biz.phanithnhoem.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final FileService fileService;

    @Value("${FILE_BASE_URI}")
    private String fileBaseUri;

    @Override
    public FileDto uploadImage(String uuid, MultipartFile file) {
        // Retrieve a Product from the repository based on UUID
        Product product = productRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested product was not found in the database."));
        FileDto fileDto = fileService.uploadSingle(file);
        product.setImage(fileBaseUri + fileDto.name());
        // Save updated image product into database
        productRepository.save(product);
        return fileDto;
    }

    @Override
    public ProductDto findByUuid(String uuid) {
        Product product = productRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Product uuid: %s has not been found.", uuid)));
        return productMapper.toProductDto(product);
    }

    @Override
    public List<ProductDto> findALlWithPagination(Pageable pageable) {
        List<Product> products = productRepository.findAll(pageable).get().toList();
        return productMapper.toProductDtoList(products);
    }

    @Override
    public Long getTotalProduct() {
        return productRepository.count();
    }


    @Transactional
    @Override
    public void createNew(CreateProductDto createProductDto) {
        // Check the category id must not be a parent category
        boolean parentId = createProductDto.categoryIds().stream()
                .anyMatch(categoryRepository::existsByIdAndParentIdIsNull);
        if (parentId){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You cannot set product to parent category.");
        }

        System.out.println("Check product exists or not");
        if(productRepository.existsByNameAndShopId(createProductDto.name(), createProductDto.shopId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("%s already exists in your shop.", createProductDto.name()));
        }

        // Create a set of Category entities based on the categories IDs provided in createUserDto
        Set<Category> categories = createProductDto.categoryIds().stream()
                .map(cateId -> Category.builder().Id(cateId).build())
                .collect(Collectors.toSet());

        Product product = productMapper.fromCreateProductDto(createProductDto);
        product.setUuid(UUID.randomUUID().toString());
        product.setCode("PRO-" + GeneratorUtil.generateCode());
        product.setSlug(SlugUtil.toSlug(createProductDto.name()));
        product.setIsDeleted(false);
        product.setInStock(true);
        product.setCreatedAt(Instant.now());
        product.setCategories(categories);
        productRepository.save(product);
    }
}
