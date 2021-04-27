package au.com.reece.msaddressbook.api;

import au.com.reece.msaddressbook.entity.ReeceApiStatus;
import au.com.reece.msaddressbook.exception.ForbiddenException;
import au.com.reece.msaddressbook.model.AddressBookApiRequest;
import au.com.reece.msaddressbook.model.AddressBookApiResponse;
import au.com.reece.msaddressbook.service.AddressBookService;
import au.com.reece.msaddressbook.service.ContactService;
import au.com.reece.msaddressbook.vo.ContactVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/v1")
public class AddressBookController {

  private final AddressBookService addressBookService;
  private final ContactService contactService;
  private String apiCode;

  public AddressBookController(
      AddressBookService addressBookService,
      ContactService contactService,
      @Value("${reece.apiCode}") String apiCode) {
    this.addressBookService = addressBookService;
    this.contactService = contactService;
    this.apiCode = apiCode;
  }

  @ApiOperation(
      value = "createAddressBook",
      notes = "Creates address book with name and contact entries",
      nickname = "getGreeting")
  @ApiResponses(
      value = {
        @ApiResponse(code = 500, message = "Server error"),
        @ApiResponse(code = 404, message = "Service not found"),
        @ApiResponse(
            code = 201,
            message = "Successful save",
            response = AddressBookApiResponse.class)
      })
  @PostMapping(
      path = "/users/{userId}/address-book",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<AddressBookApiResponse> addAddressBook(
      @RequestHeader(name = "api-code", required = false) String apiCode,
      @RequestHeader(name = "correlationId", required = false) String correlationId,
      @PathVariable(name = "userId") Long userId,
      @RequestBody AddressBookApiRequest addressBookApiRequest) {
    log.info("Save Request::: {}", addressBookApiRequest);
    validateForbidden(apiCode);
    return new ResponseEntity<>(
        ApiResponseHandler.transformResponse(
            userId,
            Collections.singletonList(addressBookService.save(userId, addressBookApiRequest)),
            ReeceApiStatus.API_202),
        HttpStatus.CREATED);
  }

  @ApiOperation(
      value = "getAllContacts",
      notes = "Gets all unique contact (Firstname & phone number) for a user",
      nickname = "getGreeting")
  @GetMapping(
      value = {
        "/users/{userId}/contacts",
        "/users/{userId}/address-books/{addressBookId}/contacts"
      },
      produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<AddressBookApiResponse> getContactEntries(
      @RequestHeader(name = "api-code") String apiCode,
      @RequestHeader(name = "correlationId", required = false) String correlationId,
      @PathVariable(name = "userId") Long userId,
      @PathVariable(name = "addressBookId", required = false) Long addressBookId) {

    log.info("Received request for printing contacts ");
    validateForbidden(apiCode);
    List<ContactVo> contacts =
        Objects.nonNull(addressBookId)
            ? contactService.getContactsByAddressBook(userId, addressBookId)
            : contactService.getContactsByUser(userId);

    return new ResponseEntity<>(
        AddressBookApiResponse.builder()
            .contacts(Set.copyOf(contacts))
            .apiStatus(ReeceApiStatus.API_200)
            .userId(userId)
            .build(),
        HttpStatus.OK);
  }

  @ApiResponses(
      value = {
        @ApiResponse(code = 500, message = "Server error"),
        @ApiResponse(code = 404, message = "Service not found"),
        @ApiResponse(code = 204, message = "Successful Delete")
      })
  @DeleteMapping(value = "/users/{userId}/address-books/{addressbookId}/contacts/{contactId}")
  public ResponseEntity deleteContact(
      @RequestHeader(name = "api-code") String apiCode,
      @RequestHeader(name = "correlationId") String correlationId,
      @PathVariable(name = "userId") Long userId,
      @PathVariable(name = "addressbookId") Long addressbookId,
      @PathVariable(name = "contactId") Long contactId) {

    log.info("Received request for deleting a contact");
    validateForbidden(apiCode);
    contactService.removeContact(userId, addressbookId, contactId);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  private void validateForbidden(String requestApiCode) {
    if (!apiCode.equals(requestApiCode)) {
      log.error("");
      throw ForbiddenException.builder().message("Invalid API code").build();
    }
  }
}
