package au.com.reece.msaddressbook.repository;

import au.com.reece.msaddressbook.entity.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {

  @Query(nativeQuery = true, value = "Select * from address_book where user_id = :userId")
  List<AddressBook> findByReeceUser(long userId);
}
