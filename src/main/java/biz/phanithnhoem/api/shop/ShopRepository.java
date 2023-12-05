package biz.phanithnhoem.api.shop;

import biz.phanithnhoem.api.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Integer> {

    boolean existsByNameAndIsDeletedFalse(String name);

    boolean existsByUuidAndIsDeletedFalse(String uuid);

    Optional<Shop> findByUuidAndIsDeletedFalse(String uuid);

    /**
     * Performs a dynamic search for users based on provided criteria.
     * This method uses JPQL (Java Persistence Query Language) to construct a query
     * with optional parameters for name, email, phone and location.
     * The search is further refined to include only verified and non-deleted users.
     *
     * @param name  The name to search for. If null, it is ignored in the search.
     * @param email     The email to search for. If null, it is ignored in the search.
     * @param phone     The phone to search for. If null, it is ignored in the search.
     * @param location     The location to search for. If null, it is ignored in the search.
     * @return          A list of non-deleted users matching the specified criteria.
     */
    @Query("""
        SELECT s FROM Shop s
        WHERE (?1 IS NULL OR s.name LIKE %?1%)
        AND(?2 IS NULL OR s.email LIKE  %?2%)
        AND(?3 IS NULL OR s.phone LIKE  %?3%)
        AND(?4 IS NULL OR s.location LIKE  %?4%)
        AND (s.isDeleted = false)
    """)
    List<Shop> searchDynamic(String name, String email, String phone, String location);


}
