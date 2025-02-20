package PrimesniperTechSolutions.Primesniper.Utils;


import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;

@Slf4j
public class PageableResponseUtils {

    public static ResponseObject successResponse(Object data, String message, int totalPages, int currentPage, int pageSize, int totalRecords) {
        return ResponseObject.builder()
                .data(data)
                .statusCode(200)
                .message(message)
                .timeStamp(ZonedDateTime.now())
                .totalPages(totalPages)
                .currentPage(currentPage)
                .pageSize(pageSize)
                .totalRecords(totalRecords)
                .build();
    }

    public static ResponseObject errorResponse(String message) {
        return ResponseObject.builder()
                .message(message)
                .timeStamp(ZonedDateTime.now())
                .statusCode(204)
                .build();
    }
}
