package rest_demo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rest_demo.entity.Provider;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {
    // derived method query (JPQL)
    // @Query (JPQL / Native SQL)
    // - findBy
    // - existBy
    // - countBy
    // - deleteBy

    Page<Provider> findAll(Pageable pageable);
    Optional<Provider> findById(long id);
    List<Provider> findByName(String name, Sort sort); // Sort.by(Sort.Direction.ASC, "name")
    List<Provider> findByNameOrderByName(String name);
    List<Provider> findByNameOrderByNameAsc(String name);
    List<Provider> findByNameOrderByNameDesc(String name);
    List<Provider> findByNameStartingWith(String prefix);
    List<Provider> findByNameEndingWith(String suffix);
    List<Provider> findByNameContaining(String infix);
    List<Provider> findByNameLike(String likePattern, Sort sort);
    List<Provider> findByIdOrName(Long id, String likePattern);
    // fetch the users whose names start with an a, contain b and end with c: "a%b%c"
    List<Provider> findByContractYearGreaterThanEqual(Integer year, Sort sort);
    List<Provider> findByContractYearLessThanEqual(Integer year);
    List<Provider> findByContractYearIn(Collection<Integer> years);
    List<Provider> findProviderByContractYear(int contractYear);
    List<Provider> findByOrderByContractYearDesc();
    List<Provider> findByNameOrderByContractYearDesc(String name);
    List<Provider> findByClinicalFocus(String clinicalFocus, Sort sort);

    /** existBy **/
    boolean existsById(long id);
    boolean existsByName(String name);

    /** countBy **/
    int countByContractYearGreaterThanEqual(int year);
    int countByContractYearLessThanEqual(int year);

    /** deleteBy **/
    @Modifying
    void deleteById(Long id);
    //    int deleteByName(String name);
    @Modifying
    @Query("delete from UserEntity u where u.name = ?1") // use JPQL
    List<Provider> deleteByName(String name);

}
