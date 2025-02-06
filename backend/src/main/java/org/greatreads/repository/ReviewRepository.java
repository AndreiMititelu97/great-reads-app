package org.greatreads.repository;

import org.greatreads.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByBook_Id(int bookId);

    List<Review> findAllByUser_id(Integer userId);
}
