package PrimesniperTechSolutions.Primesniper;

import PrimesniperTechSolutions.Primesniper.Utils.Gender;
import PrimesniperTechSolutions.Primesniper.Utils.PageableResponseUtils;
import PrimesniperTechSolutions.Primesniper.Utils.ResponseObject;
import PrimesniperTechSolutions.Primesniper.Utils.ResponseUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


    @Service
    public class MemberServiceImpl implements MemberService {

        private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


        public MemberServiceImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        }


        public ResponseObject getAllMembers(Pageable pageable) {
            String sql = """
        SELECT id, msisdn, fullname, username, birthday, gender, email, picture
        FROM tbl_tcimember
        LIMIT :limit OFFSET :offset;
    """;

            String countQuery = "SELECT COUNT(*) FROM tbl_tcimember";

            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("limit", pageable.getPageSize());
            parameters.addValue("offset", pageable.getOffset());

            // Fetch paginated results
            List<Map<String, Object>> members = namedParameterJdbcTemplate.query(sql, parameters, (rs, i) -> memberMapRow(rs));

            // Get total count of records for pagination metadata
            Integer totalRecords = namedParameterJdbcTemplate.queryForObject(countQuery, parameters, Integer.class);
            if (totalRecords == null) totalRecords = 0;

            // Calculate total pages
            int totalPages = (int) Math.ceil((double) totalRecords / pageable.getPageSize());

            return PageableResponseUtils.successResponse(members, "Successful",totalPages, pageable.getPageNumber(), pageable.getPageSize(), totalRecords);
        }


        @Override
        public ResponseObject addMember(Member member, MultipartFile picture) {

            String sql = """
                INSERT INTO tbl_tcimember(id, fullname, username, msisdn, birthday, picture, gender, email)
                VALUES (:id, :fullname, :username, :msisdn, :birthday, :picture, :gender, :email);
                 """;

            Map<String, Object> params = new HashMap<>();
            params.put("id", member.getId());
            params.put("fullname", member.getFullname());
            params.put("username", member.getUsername());
            params.put("msisdn", member.getMsisdn());
            params.put("birthday", member.getBirthday());
            params.put("gender", member.getGender());  // Convert enum to string
            params.put("email", member.getEmail());

            try { params.put("picture", picture.getBytes());
            } catch (IOException e) { e.getMessage();
            }
            int rowsAffected=  namedParameterJdbcTemplate.update(sql, params);

            if (rowsAffected > 0) {
                return ResponseUtils.successResponse(rowsAffected, "Member Saved Successfully");
            } else {
                return ResponseUtils.errorResponse("Failed to save member.");
            }        }

        @Override
        public ResponseObject deleteMember(UUID id) {
            var sql = """
                DELETE FROM tbl_tcimember  
                WHERE id = :id
                """;
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);

            int rowsAffected=  namedParameterJdbcTemplate.update(sql, params);

            return ResponseUtils.successResponse(rowsAffected, "Deletion of id "+ id + " Complete");
        }

        @Override
        public ResponseObject disableMember(Boolean isEnabled, UUID id) {
            var sql= """
                    UPDATE tbl_tcimember
                    SET is_enabled = :isEnabled
                    WHERE id = :id
                    """;
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            params.put("isEnabled", isEnabled);
try {
    int rowsAffected = namedParameterJdbcTemplate.update(sql, params);
    return ResponseUtils.successResponse(rowsAffected, "Member with ID"+ id +"Has been disabled");

}catch(Exception e){
    return ResponseUtils.successResponse(e.getMessage(), "Error disabling Member with ID"+ id);
}
        }

        @Override
        public ResponseObject searchMembers(String search, Pageable pageable) {
            String sql = """
        SELECT id, msisdn, fullname, username, birthday, gender, email, picture
        FROM tbl_tcimember
        WHERE fullname ILIKE :search
        OR username ILIKE :search
        LIMIT :limit OFFSET :offset
    """;

            String countQuery = """
        SELECT COUNT(*) FROM tbl_tcimember
        WHERE fullname ILIKE :search
        OR username ILIKE :search
        OR msisdn ILIKE :search
    """;

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("search", "%" + search + "%");
            params.addValue("limit", pageable.getPageSize());
            params.addValue("offset", pageable.getOffset());

            // Fetch paginated results
            List<Map<String, Object>> results = namedParameterJdbcTemplate.query(sql, params, (rs, i) -> memberMapRow(rs));

            // Get total count of records for pagination metadata
            Integer totalRecords = namedParameterJdbcTemplate.queryForObject(countQuery, params, Integer.class);
            if (totalRecords == null) totalRecords = 0;

            // Calculate total pages
            int totalPages = (int) Math.ceil((double) totalRecords / pageable.getPageSize());

            return PageableResponseUtils.successResponse(results, "Successful", totalPages, pageable.getPageNumber(), pageable.getPageSize(), totalRecords);
        }

        @Override
        public ResponseObject updateMember(Member existingMember, MultipartFile picture) {
            String sql = """
                    UPDATE tbl_tcimember
                     SET fullname = :fullname,
                         username = :username,
                         msisdn = :msisdn,
                         birthday = :birthday,
                         picture = :picture,
                         email = :email
                     WHERE id = :id;
                      """;

            Map<String, Object> params = new HashMap<>();
            params.put("id", existingMember.getId());
            params.put("fullname", existingMember.getFullname());
            params.put("username", existingMember.getUsername());
            params.put("msisdn", existingMember.getMsisdn());
            params.put("birthday", existingMember.getBirthday());
            params.put("email", existingMember.getEmail());

            try { params.put("picture", picture.getBytes());
            } catch (IOException e) { e.getMessage();
            }
            int rowsAffected=  namedParameterJdbcTemplate.update(sql, params);

            if (rowsAffected > 0) {
                return ResponseUtils.successResponse(rowsAffected, "Member Saved Successfully");
            } else {
                return ResponseUtils.errorResponse("Failed to save member.");
            }        }


        @Override
        public ResponseObject getById(UUID id) {
            var sql = """
                SELECT id, fullname, username, msisdn, birthday, picture, gender, email
                FROM tbl_tcimember
                WHERE id = :id
                 """;
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);

            Object results = namedParameterJdbcTemplate.query(sql, params, (rs , i) -> memberMapRow(rs));
            return ResponseUtils.successResponse(results, "successful");
        }


        private Map<String, Object> memberMapRow(ResultSet resultSet) throws SQLException {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", resultSet.getObject("id", UUID.class));
            map.put("fullname", resultSet.getString("fullname"));
            map.put("username", resultSet.getString("username"));
            map.put("msisdn", resultSet.getString("msisdn"));
            map.put("birthday", resultSet.getDate("birthday").toLocalDate());
            map.put("gender", resultSet.getString("gender"));
            map.put("email", resultSet.getString("email"));

            byte[] pictureBytes = resultSet.getBytes("picture");
            String pictureBase64 = pictureBytes != null ? Base64.getEncoder().encodeToString(pictureBytes) : null;
            map.put("picture", pictureBase64);

            return map;
        }


    }


