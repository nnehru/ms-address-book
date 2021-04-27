package au.com.reece.msaddressbook.vo;

import au.com.reece.msaddressbook.entity.ReeceApiStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressBookApiResponse {
  private long userId;
  private ReeceApiStatus apiStatus;
  private List<AddressBookVo> addressBooks;
  private Set<ContactVo> contacts;
}
