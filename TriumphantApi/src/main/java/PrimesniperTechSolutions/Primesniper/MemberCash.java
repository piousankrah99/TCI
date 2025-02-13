package PrimesniperTechSolutions.Primesniper;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Month;
import java.util.UUID;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class MemberCash {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member memberId;

    private Month month;

    private float cash;


}
