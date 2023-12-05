package biz.phanithnhoem.api.review;

import biz.phanithnhoem.api.review.web.CreateReviewDto;
import biz.phanithnhoem.api.review.web.ReviewDto;
import biz.phanithnhoem.api.user.User;
import biz.phanithnhoem.api.user.web.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(source = "user", target = "user")
    ReviewDto toReviewDto(Review review);
    Review fromCreateReviewDto(CreateReviewDto createReviewDto);
    List<ReviewDto> toReviewDtoList(List<Review> reviews);
    UserDto toUserDto(User user);

}
