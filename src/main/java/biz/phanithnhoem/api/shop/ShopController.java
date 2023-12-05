package biz.phanithnhoem.api.shop;

import biz.phanithnhoem.api.shop.web.CreateShopDto;
import biz.phanithnhoem.api.shop.web.ShopDto;
import biz.phanithnhoem.api.shop.web.UpdateShopDto;
import biz.phanithnhoem.base.ApiPagedResponse;
import biz.phanithnhoem.base.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/search")
    public ApiResponse<Object> searchDynamic(@RequestParam(required = false) String name,
                                             @RequestParam(required = false) String email,
                                             @RequestParam(required = false) String phone,
                                             @RequestParam(required = false) String location) {
        List<ShopDto> shopDtoList = shopService.searchDynamic(name, email, phone, location);
        return ApiResponse.builder()
                .status("Success")
                .code(HttpStatus.OK.value())
                .message(String.format("Successfully retrieved %d shop results.", shopDtoList.size()))
                .data(shopDtoList)
                .timestamp(Instant.now())
                .build();
    }

    @GetMapping("/{uuid}")
    public ApiResponse<Object> findByUuid(@PathVariable String uuid){

        ShopDto shopDto = shopService.findByUuid(uuid);
        return ApiResponse.builder()
                .status("Success")
                .code(HttpStatus.OK.value())
                .message("Shop has been found.")
                .data(shopDto)
                .timestamp(Instant.now())
                .build();
    }

    @PatchMapping("/{uuid}")
    public ApiResponse<Object> updateByUuid(@PathVariable String uuid,
                                            @RequestBody UpdateShopDto updateShopDto) {
        System.out.println(updateShopDto);
        shopService.updateByUuid(uuid, updateShopDto);

        return ApiResponse.builder()
                .status("success")
                .code(HttpStatus.OK.value())
                .message("Shop has been updated successfully.")
                .data(updateShopDto)
                .timestamp(Instant.now())
                .build();
    }

    @GetMapping
    public ApiPagedResponse<Object> findAllWithPagination(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "8") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        List<ShopDto> shopDtoList = shopService.findAllWithPagination(pageable);
        Long totalShop = shopService.getTotalShopCount();

        return ApiPagedResponse.builder()
                .status("Success")
                .code(HttpStatus.OK.value())
                .message("Retrieve shops has been successfully.")
                .data(shopDtoList)
                .pageNo(pageable.getPageNumber())
                .recordCount(pageable.getPageSize())
                .totalRecords(totalShop)
                .totalPages((long) Math.ceil(shopService.getTotalShopCount()/pageable.getPageSize() + 1))
                .timestamp(Instant.now())
                .build();
    }

    @PostMapping
    public ApiResponse<Object> createNewShop(@Valid @RequestBody CreateShopDto createShopDto) {

        shopService.createNewShop(createShopDto);
        return ApiResponse.builder()
                .status("Success")
                .code(HttpStatus.CREATED.value())
                .message("Shop has been created successfully.")
                .data(createShopDto)
                .timestamp(Instant.now())
                .build();
    }
}
