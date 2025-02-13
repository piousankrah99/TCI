package PrimesniperTechSolutions.Primesniper;


import PrimesniperTechSolutions.Primesniper.Utils.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "tbl_tcimember")
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use GenerationType.IDENTITY or UUID.randomUUID()
    private UUID id;

    private String username;

    private String fullname;

    private String msisdn;

    private String gender;

    private LocalDate birthday;

    private String email;

    private String password;

//    @Lob
    @Column(name = "picture", columnDefinition = "BYTEA")
    private byte[] picture;

    public Boolean hasCash =false;

    public Boolean isEnabled;
}
