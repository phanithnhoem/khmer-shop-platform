package biz.phanithnhoem.api.product;

import biz.phanithnhoem.api.product.web.CategoryDto;
import biz.phanithnhoem.api.product.web.CreateProductDto;
import biz.phanithnhoem.api.product.web.ProductDto;
import biz.phanithnhoem.api.shop.Shop;
import biz.phanithnhoem.api.shop.web.ShopDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "shopId", target = "shop.id")
    Product fromCreateProductDto(CreateProductDto createProductDto);

    ShopDto toShopDto(Shop shop);

    List<ProductDto> toProductDtoList(List<Product> products);

    Set<CategoryDto> toCategoryDtoSet(Set<Category> categories);

    CategoryDto toCategoryDto(Category category);

//    @Mapping(source = "categories", target = "categories", qualifiedByName = "mapCategoriesToStrings")
    @Mapping(source = "categories", target = "categories")
    ProductDto toProductDto(Product product);
//    @Named("mapCategoriesToStrings")
//    default Set<String> mapCategoriesToStrings(Set<Category> categories) {
//        return categories.stream()
//                .map(Category::getName)
//                .collect(Collectors.toSet());
//    }
}
