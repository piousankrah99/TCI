package PrimesniperTechSolutions.Primesniper;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private UUID memberId;
    private String month;
    private Float cash;

}
