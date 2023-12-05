package biz.phanithnhoem.api.shop;

import biz.phanithnhoem.api.shop.web.CreateShopDto;
import biz.phanithnhoem.api.shop.web.ShopDto;
import biz.phanithnhoem.api.shop.web.UpdateShopDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private  final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    @Override
    public List<ShopDto> searchDynamic(String name, String email, String phone, String location) {
        List<Shop> shops = shopRepository.searchDynamic(name, email, phone, location);
        if (shops.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No shops found matching the criteria.");
        return shopMapper.toShopDtoList(shops);
    }

    @Override
    public ShopDto findByUuid(String uuid) {
        Shop shop = shopRepository.findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Shop with uuid: %s doesn't exists in database.", uuid)));
        return shopMapper.toShopDto(shop);
    }

    @Transactional
    @Override
    public void updateByUuid(String uuid, UpdateShopDto updateShopDto) {
        // Shop already exists or not
        if (shopRepository.existsByUuidAndIsDeletedFalse(uuid)){
            Shop shop = shopRepository.findByUuidAndIsDeletedFalse(uuid)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("Shop with uuid: %s doesn't exists in database.", uuid)));

            // Map data from updateShopDto to Shop entity
            if(updateShopDto != null){
                shopMapper.fromUpdateShopDto(shop, updateShopDto);
                shop.setUpdatedAt(Instant.now());
            }

            // save the latest data
            shopRepository.save(shop);
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Shop with uuid: %s doesn't exits in database.", uuid));
    }

    @Override
    public List<ShopDto> findAllWithPagination(Pageable pageable) {
        List<Shop> shops = shopRepository.findAll(pageable).get().toList();
        return shopMapper.toShopDtoList(shops);
    }

    @Override
    public Long getTotalShopCount() {
        return shopRepository.count();
    }

    @Transactional
    @Override
    public void createNewShop(CreateShopDto createShopDto) {
        // Check shop exists or not
        if(shopRepository.existsByNameAndIsDeletedFalse(createShopDto.name())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Shop %s already exists in database.", createShopDto.name()));
        }
        Shop shop = shopMapper.fromCreateShopDto(createShopDto);
        shop.setUuid(UUID.randomUUID().toString());
        shop.setCreatedAt(Instant.now());
        shop.setIsDeleted(false);
        shopRepository.save(shop);
    }
}
