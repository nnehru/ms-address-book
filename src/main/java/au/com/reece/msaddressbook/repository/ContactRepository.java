package au.com.reece.msaddressbook.repository;

import au.com.reece.msaddressbook.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

  String CONTACTS_BY_USER_QUERY =
      "Select * from contact c inner join address_book a on c.address_book_id = a.address_book_id where a.user_id = :userId";

  String CONTACT_BY_USERID_ADDID_CNTID = "Select * from contact c " +
          "INNER JOIN address_book a on c.address_book_id = a.address_book_id" +
          " INNER JOIN reece_user u on u.user_id = a.user_id" +
          " where u.user_id = :userId and a.address_book_id = :addressBookId and c.contact_id = :contactId";

  @Query(nativeQuery = true, value = CONTACTS_BY_USER_QUERY)
  List<Contact> findContactsByUserId(Long userId);

  @Query(
      "Select c from Contact c INNER JOIN AddressBook a on c.addressBook.addressBookId = a.addressBookId where a.addressBookId = :addressBookId and a.reeceUser.userId = :userId")
  List<Contact> findContactsByAddressBook_AddressBookId(long userId, long addressBookId);

  @Query(nativeQuery = true, value = CONTACT_BY_USERID_ADDID_CNTID)
  Contact findContactByUserIdAddIdContactId(long userId, long addressBookId, long contactId);

  @Query(nativeQuery = true, value = "DELETE FROM contact where contact_id = :contactId")
  @Modifying(clearAutomatically = true)
  void deleteContactByContactId(long contactId);
}
