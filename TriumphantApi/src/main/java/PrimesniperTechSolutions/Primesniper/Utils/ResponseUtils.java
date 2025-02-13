package PrimesniperTechSolutions.Primesniper.Utils;


import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;

@Slf4j
public class ResponseUtils {


    public static ResponseObject successResponse(Object o,String message){
        return ResponseObject.builder()
                .data(o)
                .statusCode(200)
                .message(message)
                .timeStamp(ZonedDateTime.now())
                .build();
    }

    public static ResponseObject errorResponse(String message){
        return  ResponseObject.builder()
                .message(message)
                .timeStamp(ZonedDateTime.now())
                .statusCode(204)
                .build();
    }




}
