package au.com.reece.msaddressbook.service;

import au.com.reece.msaddressbook.entity.AddressBook;
import au.com.reece.msaddressbook.entity.Contact;
import au.com.reece.msaddressbook.repository.AddressBookRepository;
import au.com.reece.msaddressbook.repository.ReeceUserRepository;
import au.com.reece.msaddressbook.vo.AddressBookApiRequest;
import au.com.reece.msaddressbook.vo.AddressBookVo;
import au.com.reece.msaddressbook.vo.ContactVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Set;
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
  public AddressBookVo save(long userId, AddressBookApiRequest addressBookApiRequest) {
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
    return transform(addressBookRepository.save(addressBook));
  }

  private AddressBookVo transform(AddressBook addressBook) {

    return AddressBookVo.builder()
        .addressBookId(addressBook.getAddressBookId())
        .savedName(addressBook.getSavedName())
        .contacts(
            Set.copyOf(
                addressBook.getContacts().stream()
                    .map(
                        contact ->
                            ContactVo.builder()
                                .firstName(contact.getFirstName())
                                .lastName(contact.getLastName())
                                .phoneNumber(contact.getPhoneNumber())
                                .email(contact.getEmail())
                                .contactId(contact.getContactId())
                                .build())
                    .collect(Collectors.toList())))
        .build();
  }
}
