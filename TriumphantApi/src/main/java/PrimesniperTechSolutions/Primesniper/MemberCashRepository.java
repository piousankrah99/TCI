package PrimesniperTechSolutions.Primesniper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemberCashRepository extends JpaRepository<MemberCash, UUID> {
}
