package au.com.reece.msaddressbook.service;

import au.com.reece.msaddressbook.exception.ResourceNotFoundException;
import au.com.reece.msaddressbook.repository.ContactRepository;
import au.com.reece.msaddressbook.vo.ContactVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContactService {

  private final ContactRepository contactRepository;

  public List<ContactVo> getContactsByUser(final long userId) {
    return contactRepository.findContactsByUserId(userId).stream()
        .map(
            contact ->
                ContactVo.builder()
                    .contactId(contact.getContactId())
                    .firstName(contact.getFirstName())
                    .lastName(contact.getLastName())
                    .phoneNumber(contact.getPhoneNumber())
                    .email(contact.getEmail())
                    .build())
        .collect(Collectors.toList());
  }

  public List<ContactVo> getContactsByAddressBook(final long userId, final long addressBookId) {
    return contactRepository.findContactsByAddressBook_AddressBookId(userId, addressBookId).stream()
        .map(
            contact ->
                ContactVo.builder()
                    .contactId(contact.getContactId())
                    .firstName(contact.getFirstName())
                    .lastName(contact.getLastName())
                    .phoneNumber(contact.getPhoneNumber())
                    .email(contact.getEmail())
                    .build())
        .collect(Collectors.toList());
  }

  @Transactional
  public void removeContact(final long userId, final long addressBookId, final long contactId) {
    var contact =
        contactRepository.findContactByUserIdAddIdContactId(userId, addressBookId, contactId);
    if (contact == null) {
      throw ResourceNotFoundException.builder().build();
    }
    contactRepository.deleteContactByContactId(contactId);
  }
}
