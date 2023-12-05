package biz.phanithnhoem.api.shop;

import biz.phanithnhoem.api.shop.web.CreateShopDto;
import biz.phanithnhoem.api.shop.web.ShopDto;
import biz.phanithnhoem.api.shop.web.UpdateShopDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShopMapper {

    @Mapping(source = "userId", target = "user.id")
    Shop fromCreateShopDto(CreateShopDto createShopDto);

    ShopDto toShopDto(Shop shop);

    List<ShopDto> toShopDtoList(List<Shop> shops);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdateShopDto(@MappingTarget Shop shop, UpdateShopDto updateShopDto);

}
