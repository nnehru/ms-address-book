package au.com.reece.msaddressbook.repository;

import au.com.reece.msaddressbook.entity.ReeceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReeceUserRepository extends JpaRepository<ReeceUser, Long> {

  ReeceUser findByUserId(long userId);
}
