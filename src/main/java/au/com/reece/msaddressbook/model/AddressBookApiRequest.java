package au.com.reece.msaddressbook.model;

import au.com.reece.msaddressbook.vo.ContactVo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class AddressBookApiRequest {
    private String addressBookName;
    private List<ContactVo> contactEntries;
}
