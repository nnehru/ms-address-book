package au.com.reece.msaddressbook.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "reece_user")
public class ReeceUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_reece_user")
  private long userId;

  private String firstName;

  private String lastName;

  private String designation;

  @Override
  public String toString() {
    return new StringBuilder()
        .append("First name : ")
        .append(this.firstName)
        .append("\n")
        .append("Last name : ")
        .append(this.lastName)
        .append("\n")
        .append("Designation : ")
        .append(this.designation)
        .append("\n")
        .toString();
  }
}
