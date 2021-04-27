package au.com.reece.msaddressbook.api;

import au.com.reece.msaddressbook.entity.AddressBook;
import au.com.reece.msaddressbook.entity.ReeceApiStatus;
import au.com.reece.msaddressbook.model.AddressBookApiResponse;
import au.com.reece.msaddressbook.vo.AddressBookVo;
import au.com.reece.msaddressbook.vo.ContactVo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponseHandler {

  public static AddressBookApiResponse transformResponse(long userId,
      List<AddressBook> addressBookList, ReeceApiStatus apiStatus) {
    var addressBooks =
        addressBookList.stream()
            .map(
                addressBook ->
                    AddressBookVo.builder()
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
                        .build())
            .collect(Collectors.toList());
    return AddressBookApiResponse.builder()
        .apiStatus(apiStatus)
        .userId(userId)
        .addressBooks(addressBooks)
        .build();
  }
}
