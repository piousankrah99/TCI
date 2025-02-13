package PrimesniperTechSolutions.Primesniper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import java.time.Month;


@Service
@AllArgsConstructor
public class MemberCashService {

    MemberCashRepository memberCashRepository;
    MemberRepository memberRepository;
    public void sendCash(MemberDto memberDto) {
        MemberCash memberCash = new MemberCash();
        memberCash.setCash(memberDto.getCash());
        memberCash.setMonth(Month.valueOf(memberDto.getMonth().toUpperCase()));
        memberCash.setMemberId(memberRepository.findById(memberDto.getMemberId()).orElse(null)); // Fetching Member by ID

        //Use Member class to set has cash to true when Member has cash
        Member member = memberCash.getMemberId();
        member.setHasCash(true);
        memberRepository.save(member);


        memberCashRepository.save(memberCash);
    }
    public List<MemberCash> getAllCash() {
        return memberCashRepository.findAll();
    }
}
