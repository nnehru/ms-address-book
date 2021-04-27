package au.com.reece.msaddressbook.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Getter
@Setter
@Builder
public class ContactVo {

  private long contactId;

  private String firstName;

  private String lastName;

  private String phoneNumber;

  private String email;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    var contactVo = (ContactVo) o;
    return firstName.equals(contactVo.firstName) && phoneNumber.equals(contactVo.phoneNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, phoneNumber);
  }

  @Override
  public String toString() {
    return new StringBuilder()
        .append("First name : ")
        .append(this.firstName)
        .append("\n")
        .append("Last name : ")
        .append(StringUtils.isNotBlank(this.lastName) ? this.lastName : StringUtils.EMPTY)
        .append("\n")
        .append("Phone Number : ")
        .append(this.phoneNumber)
        .append("\n")
        .append("Email : ")
        .append(StringUtils.isNotBlank(this.email) ? this.email : StringUtils.EMPTY)
        .toString();
  }
}
