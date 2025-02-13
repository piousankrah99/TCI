package PrimesniperTechSolutions.Primesniper;

import PrimesniperTechSolutions.Primesniper.Utils.Gender;
import PrimesniperTechSolutions.Primesniper.Utils.ResponseObject;
import PrimesniperTechSolutions.Primesniper.Utils.ResponseUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import PrimesniperTechSolutions.Primesniper.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@Controller
@RequestMapping(path="api/v1/Member")
@AllArgsConstructor
public class MemberController {

    MemberService memberService;

    private final MemberRepository memberRepository;

    @GetMapping("/index")
    public String getIndexPage(){
        return "index";
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseObject> getMembers(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        ResponseObject responseObject;

        if (search != null && !search.isEmpty()) {
            responseObject = memberService.searchMembers(search, pageable);
        } else {
            responseObject = memberService.getAllMembers(pageable);
        }

        return ResponseEntity.ok(responseObject);
    }

    @GetMapping("/getById/{id}")
    @ResponseBody   // This annotation tells Spring to directly return the data as the response body
    public ResponseEntity<ResponseObject> getById(@PathVariable("id") UUID id){
        return  ResponseEntity.ok(memberService.getById(id));
    }

    @PostMapping("/addMember")
    public ResponseEntity<ResponseObject> addJersey(
            @RequestParam("fullname") String fullname,
            @RequestParam("username") String username,
            @RequestParam("msisdn") String msisdn,
            @RequestParam("birthday") LocalDate birthday,
            @RequestParam("gender") String gender,
            @RequestParam("email") String email,
            @RequestParam("picture") MultipartFile picture) {
        Member member = new Member();

        try {
            member.setId(UUID.randomUUID());
            member.setFullname(fullname);
            member.setUsername(username);
            member.setMsisdn(msisdn);
            member.setBirthday(birthday);
            member.setGender(gender);
            member.setEmail(email);

            ResponseObject response = memberService.addMember(member, picture);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ResponseUtils.errorResponse("Failed to add member: " + e.getMessage()));

        }
    }

    @PutMapping("/updateMember/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable("id") UUID id,
                                               @RequestParam("fullname") String fullname,
                                               @RequestParam("username") String username,
                                               @RequestParam("msisdn") String msisdn,
                                               @RequestParam("birthday") LocalDate birthday,
                                               @RequestParam("email") String email,
                                               @RequestParam("picture") MultipartFile picture
    ) throws MembersException {

        Member existingMember = memberRepository.findById(id).orElseThrow(
                () -> new MembersException("Member with ID: " + id + " does not exist", HttpStatus.BAD_REQUEST));

        existingMember.setFullname(fullname);
        existingMember.setUsername(username);
        existingMember.setEmail(email);
        existingMember.setMsisdn(msisdn);
        existingMember.setBirthday(birthday);

        memberService.updateMember(existingMember, picture);

        return ResponseEntity.ok(existingMember);
    }

    @PutMapping(path= "/isEnabled/{id}")
    public ResponseEntity<ResponseObject> disableMember(@PathVariable("id") UUID id, @RequestParam("isEnabled") Boolean isEnabled){
        return ResponseEntity.ok(memberService.disableMember(isEnabled ,id));
    }

    @DeleteMapping(path= "/delete/{id}")
    public ResponseEntity<ResponseObject> removeMember(@PathVariable("id") UUID id){
        return ResponseEntity.ok(memberService.deleteMember(id));
    }



}
