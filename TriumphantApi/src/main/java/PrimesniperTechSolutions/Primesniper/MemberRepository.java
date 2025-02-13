package PrimesniperTechSolutions.Primesniper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


public interface MemberRepository extends JpaRepository<Member, UUID> {

}
