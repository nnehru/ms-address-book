package au.com.reece.msaddressbook.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_contact")
  private long contactId;

  @ManyToOne
  @JoinColumn(name = "addressBookId")
  private AddressBook addressBook;

  private String firstName;

  private String lastName;

  private String phoneNumber;

  private String email;
}
