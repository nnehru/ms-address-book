package au.com.reece.msaddressbook.api;

import au.com.reece.msaddressbook.entity.AddressBook;
import au.com.reece.msaddressbook.service.AddressBookService;
import au.com.reece.msaddressbook.service.ContactService;
import au.com.reece.msaddressbook.vo.AddressBookApiRequest;
import au.com.reece.msaddressbook.vo.AddressBookVo;
import au.com.reece.msaddressbook.vo.ContactVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AddressBookController.class)
@RunWith(SpringRunner.class)
class AddressBookControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean AddressBookService addressBookService;

  @MockBean ContactService contactService;

  @Test
  void shouldAddAddressBook() throws Exception {
    AddressBookApiRequest requestObj = getRequestObject();
    Mockito.when(addressBookService.save(1, requestObj)).thenReturn(getAddressObj());
    final ResultActions result =
        mvc.perform(
                post("/v1/users/1/address-book")
                    .content(getJsonRequestBody(requestObj))
                    .header("api-code", "Reece123")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
  }

  @Test
  void shouldReturnStatusCodeForbiddenWhileAdd() throws Exception {
    AddressBookApiRequest requestObj = getRequestObject();
    final ResultActions result =
        mvc.perform(
                post("/v1/users/1/address-book")
                    .content(getJsonRequestBody(requestObj))
                    .header("api-code", "Reece153")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());
  }

  @Test
  void shouldReturnStatusCodeInternalServerErrorWhileAdd() throws Exception {
    AddressBookApiRequest requestObj = getRequestObject();
    Mockito.when(addressBookService.save(1, requestObj)).thenThrow(RuntimeException.class);
    final ResultActions result =
        mvc.perform(
                post("/v1/users/1/address-book")
                    .content(getJsonRequestBody(requestObj))
                    .header("api-code", "Reece123")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());
  }

  @Test
  void shouldGetContacts() throws Exception {
    AddressBookApiRequest requestObj = getRequestObject();
    Mockito.when(addressBookService.save(1, requestObj)).thenThrow(RuntimeException.class);
    final ResultActions result =
        mvc.perform(
                post("/v1/users/1/address-book")
                    .content(getJsonRequestBody(requestObj))
                    .header("api-code", "Reece123")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());
  }

  private String getJsonRequestBody(AddressBookApiRequest addressBookApiRequest)
      throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(addressBookApiRequest);
  }

  private AddressBookApiRequest getRequestObject() {
    List<ContactVo> contactEntryList = new ArrayList<>(3);
    contactEntryList.add(
        ContactVo.builder()
            .firstName("AGHJ")
            .lastName("GHJ")
            .email("sdsdsd")
            .phoneNumber("1223477")
            .build());
    contactEntryList.add(
        ContactVo.builder()
            .firstName("sdsds")
            .lastName("dsds")
            .email("wewew")
            .phoneNumber("1468776577")
            .build());
    contactEntryList.add(
        ContactVo.builder()
            .firstName("Zvccvc")
            .lastName("rttrr")
            .email("sdrghb")
            .phoneNumber("1485653276577")
            .build());
    return AddressBookApiRequest.builder()
        .addressBookName("Tester")
        .contactEntries(contactEntryList)
        .build();
  }

  private AddressBookVo getAddressObj() {
    AddressBook addressBook = new AddressBook();
    addressBook.setAddressBookId(1);
    Set<ContactVo> contacts = new HashSet();
    ContactVo contact =
        ContactVo.builder()
            .contactId(1)
            .firstName("John")
            .lastName("Payne")
            .phoneNumber("0426789098")
            .email("aa@a.com")
            .build();
    contacts.add(contact);
    return AddressBookVo.builder()
        .addressBookId(1)
        .contacts(contacts)
        .savedName("TestAddBook1")
        .build();
  }
}
