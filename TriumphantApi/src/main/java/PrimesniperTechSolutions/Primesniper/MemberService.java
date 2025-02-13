package PrimesniperTechSolutions.Primesniper;

import PrimesniperTechSolutions.Primesniper.Utils.ResponseObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface MemberService {



        public ResponseObject getAllMembers(Pageable pageable);


        public ResponseObject getById(UUID id);


        public ResponseObject addMember(Member jersey, MultipartFile picture);

        public ResponseObject deleteMember(UUID id);


        public ResponseObject disableMember(Boolean isEnabled, UUID id);

        public ResponseObject searchMembers(String search, Pageable pageable);

        public ResponseObject updateMember(Member existingMember, MultipartFile picture);
}
