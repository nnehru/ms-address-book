package au.com.reece.msaddressbook.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table
public class AddressBook {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_address_book")
  private long addressBookId;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "userId")
  private ReeceUser reeceUser;

  @Column(name = "name")
  private String savedName;

  @OneToMany(mappedBy = "addressBook", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Contact> contacts;

  @Override
  public String toString() {
    return new StringBuilder()
        .append("Reece user : ")
        .append(this.reeceUser)
        .append("\n")
        .append("Address book name : ")
        .append(this.savedName)
        .append("\n")
        .append("Contacts : ")
        .append(this.contacts)
        .append("\n")
        .toString();
  }
}
