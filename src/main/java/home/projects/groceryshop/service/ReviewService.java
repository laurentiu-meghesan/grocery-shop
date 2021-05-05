package home.projects.groceryshop.service;

import home.projects.groceryshop.domain.Review;
import home.projects.groceryshop.persistance.ReviewRepository;
import home.projects.groceryshop.transfer.review.ReviewResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewService.class);

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public Page<ReviewResponse> getReviews(long productId, Pageable pageable) {
        LOGGER.info("Retrieving reviews for product {}", productId);

        Page<Review> reviewsPage = reviewRepository.findByProductId(productId, pageable);

        List<ReviewResponse> reviewsDtos = new ArrayList<>();

        for (Review review : reviewsPage.getContent()) {
            ReviewResponse dto = new ReviewResponse();
            dto.setId(review.getId());
            dto.setContent(review.getContent());

            reviewsDtos.add(dto);
        }

        return new PageImpl<>(reviewsDtos, pageable, reviewsPage.getTotalElements());
    }


}
