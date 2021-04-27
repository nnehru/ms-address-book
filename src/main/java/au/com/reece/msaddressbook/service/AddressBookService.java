package au.com.reece.msaddressbook.service;

import au.com.reece.msaddressbook.entity.AddressBook;
import au.com.reece.msaddressbook.entity.Contact;
import au.com.reece.msaddressbook.model.AddressBookApiRequest;
import au.com.reece.msaddressbook.repository.AddressBookRepository;
import au.com.reece.msaddressbook.repository.ReeceUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressBookService {

  @Autowired private AddressBookRepository addressBookRepository;

  @Autowired private ReeceUserRepository reeceUserRepository;

  @Transactional(
      rollbackFor = IllegalArgumentException.class,
      noRollbackFor = EntityExistsException.class,
      rollbackForClassName = "IllegalArgumentException",
      noRollbackForClassName = "EntityExistsException")
  public AddressBook save(long userId, AddressBookApiRequest addressBookApiRequest) {
    var addressBook = new AddressBook();
    var reeceUser = reeceUserRepository.findByUserId(userId);
    addressBook.setReeceUser(reeceUser);
    addressBook.setSavedName(addressBookApiRequest.getAddressBookName());
    List<Contact> contacts =
        addressBookApiRequest.getContactEntries().stream()
            .map(
                contactEntry -> {
                  var contact = new Contact();
                  contact.setAddressBook(addressBook);
                  contact.setFirstName(contactEntry.getFirstName());
                  contact.setLastName(contactEntry.getLastName());
                  contact.setEmail(contactEntry.getEmail());
                  contact.setPhoneNumber(contactEntry.getPhoneNumber());
                  return contact;
                })
            .collect(Collectors.toList());
    addressBook.setContacts(contacts);
    return addressBookRepository.save(addressBook);
  }
}
