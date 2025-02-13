package PrimesniperTechSolutions.Primesniper;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8082")

@Controller
@AllArgsConstructor
@RequestMapping(path = "api/v1/Cash")
public class MemberCashController {

    MemberCashService memberCashService;

    @PostMapping(path = "/sendCash")
    ResponseEntity<Object> sendCash(@RequestBody MemberDto memberDto){

         memberCashService.sendCash(memberDto);

         return ResponseEntity.ok("Cash Sent!!!");
    }

    @GetMapping(path = "getAllCash")
    @ResponseBody
    List<MemberCash> getAllMemberCash(){
        return memberCashService.getAllCash();

    }

}
