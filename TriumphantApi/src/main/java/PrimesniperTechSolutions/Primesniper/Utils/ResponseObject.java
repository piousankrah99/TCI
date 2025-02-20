package PrimesniperTechSolutions.Primesniper.Utils;

import lombok.*;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ResponseObject {
    private int statusCode;
    private String message;
    private Object data;
    private ZonedDateTime timeStamp;
    private Integer totalPages; // Total number of pages
    private Integer currentPage; // Current page number
    private Integer pageSize; // Page size
    private Integer totalRecords; // Page size
}
