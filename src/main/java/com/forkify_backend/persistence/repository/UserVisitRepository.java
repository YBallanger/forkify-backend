package com.forkify_backend.persistence.repository;

import com.forkify_backend.persistence.entity.UserVisit;
import com.forkify_backend.persistence.projection.RestaurantRatingProjection;
import com.forkify_backend.persistence.projection.RestaurantSpendingProjection;
import com.forkify_backend.persistence.projection.RestaurantVisitProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserVisitRepository extends JpaRepository<UserVisit, Long> {

    Integer countByUser_UserId(String userId);

    @Query(value = """
            SELECT COUNT(DISTINCT v.restaurant.restaurantId)
            FROM UserVisit v
            WHERE v.user.userId = :userId
            """)
    Integer countDistinctRestaurantsByUserId(@Param("userId") String userId);

    @Query(value = """
            SELECT COALESCE(SUM(v.amountSpent), 0)
            FROM UserVisit v
            WHERE v.user.userId = :userId
            """)
    BigDecimal sumAmountSpentByUserId(@Param("userId") String userId);

    @Query("""
            SELECT r.name as restaurantName, 
                   COUNT(uv.userVisitId) as totalVisits
            FROM UserVisit uv
            JOIN uv.restaurant r
            WHERE uv.user.userId = :userId
            GROUP BY uv.restaurant.restaurantId, r.name
            ORDER BY COUNT(uv.userVisitId) DESC
            LIMIT 3
            """)
    List<RestaurantVisitProjection> findTop3VisitedRestaurantsByUserId(
            @Param("userId") String userId
    );

    @Query("""
            SELECT r.name as restaurantName, SUM(uv.amountSpent) as amountSpent
            FROM UserVisit uv
            JOIN uv.restaurant r
            WHERE uv.user.userId = :userId
            GROUP BY uv.restaurant.restaurantId, r.name
            ORDER BY SUM(uv.amountSpent) DESC
            LIMIT 3
            """)
    List<RestaurantSpendingProjection> findTop3SpendingRestaurantsByUserId(
            @Param("userId") String userId
    );

    @Query("""
            SELECT r.name as restaurantName, AVG(uv.rating) as rating
            FROM UserVisit uv
            JOIN uv.restaurant r
            WHERE uv.user.userId = :userId
            GROUP BY uv.restaurant.restaurantId, r.name
            ORDER BY AVG(uv.rating) DESC
            LIMIT 3
            """)
    List<RestaurantRatingProjection> findTop3RatedRestaurantsByUserId(@Param("userId") String userId);
}
