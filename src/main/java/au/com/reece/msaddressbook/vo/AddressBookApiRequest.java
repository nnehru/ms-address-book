package au.com.reece.msaddressbook.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class AddressBookApiRequest {
  private String addressBookName;
  private List<ContactVo> contactEntries;
}
