package org.greatreads.repository;

import org.greatreads.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByBook_Id(int bookId);

    List<Review> findAllByUser_id(Integer userId);

    int countByBook_Id(int bookId);

    @Query("SELECT COALESCE(AVG(r.rating), 0) FROM Review r WHERE r.book.id = :bookId")
    Double findAverageByBook_Id(int bookId);
}
