package biz.phanithnhoem.api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Performs a dynamic search for users based on provided criteria.
     * This method uses JPQL (Java Persistence Query Language) to construct a query
     * with optional parameters for full name, username, and email.
     *
     * @param fullName  The full name to search for. If null, it is ignored in the search.
     * @param username  The username to search for. If null, it is ignored in the search.
     * @param email     The email to search for. If null, it is ignored in the search.
     * @return          A list of users matching the specified criteria.
     */
    @Query("""
        SELECT u FROM User u
        WHERE (:fullName IS NULL OR u.fullName LIKE %:fullName%)
        AND (:username IS NULL OR u.username LIKE %:username%)
        AND (:email IS NULL OR u.email LIKE %:email%)
        """)
    List<User> searchUsers(String fullName, String username, String email);

    /**
     * Checks if a user with the specified username exists and is not marked as deleted.
     *
     * @param username the username to check for existence.
     * @return true if a non-deleted user with the given username exists, false otherwise.
     */
    boolean existsByUsernameAndIsDeletedFalse(String username);

    /**
     * Checks if a user with the specified username exists and is not marked as deleted.
     *
     * @param email the username to check for existence.
     * @return true if a non-deleted user with the given username exists, false otherwise.
     */
    boolean existsByEmailAndIsDeletedFalse(String email);
}
