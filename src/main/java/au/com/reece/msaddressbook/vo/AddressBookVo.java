package au.com.reece.msaddressbook.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class AddressBookVo {
    private long addressBookId;
    private String savedName;
    private Set<ContactVo> contacts;
}
