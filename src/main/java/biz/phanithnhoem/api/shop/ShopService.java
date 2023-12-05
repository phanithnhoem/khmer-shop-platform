package biz.phanithnhoem.api.shop;

import biz.phanithnhoem.api.shop.web.CreateShopDto;
import biz.phanithnhoem.api.shop.web.ShopDto;
import biz.phanithnhoem.api.shop.web.UpdateShopDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShopService {
    void createNewShop(CreateShopDto createShopDto);
    List<ShopDto> findAllWithPagination(Pageable pageable);
    Long getTotalShopCount();

    void updateByUuid(String uuid, UpdateShopDto updateShopDto);
    ShopDto findByUuid(String uuid);
    List<ShopDto> searchDynamic(String name, String email, String phone, String location);
}
