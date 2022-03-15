package rest_demo.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import rest_demo.entity.UserEntity;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

    /** Query method (which internally uses JPQL), used with predicates like:
     * predicates IsStartingWith, StartingWith, StartsWith, IsEndingWith, EndingWith,
     * EndsWith, IsNotContaining, NotContaining, NotContains, IsContaining, Containing, Contains
     * **/
    /** findBy **/
    UserEntity findByAgeBetween(int from, int to);
    UserEntity findByAgeAndName(int age, String name);
    List<UserEntity> findByNameStartingWith(String prefix);
    List<UserEntity> findByNameEndingWith(String suffix);
    List<UserEntity> findByNameContaining(String infix);
    List<UserEntity> findByNameLike(String likePattern); // fetch the users whose names start with an a, contain b and end with c: "a%b%c"
    List<UserEntity> findByAgeGreaterThanEqual(Integer age);
    List<UserEntity> findByAgeLessThanEqual(Integer age);
    List<UserEntity> findByAgeIn(Collection<Integer> ages);

    /** existBy **/
    boolean existsById(long id);

    /** countBy **/
    int countByAgeGreaterThanEqual(int age);

    /** deleteBy **/
    @Modifying
    void deleteById(Long id);
//    int deleteByName(String name);
    @Modifying
    @Query("delete from UserEntity u where u.name = ?1")
    List<UserEntity> deleteByName(String name);

    // select
    // insert
    // update
    // delete
}
