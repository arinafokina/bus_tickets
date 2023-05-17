package proofit.arina.baseprice.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> ok(String message, Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", HttpStatus.OK.value());
        map.put("data", responseObj);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public static ResponseEntity<Object> badRequest(String message, Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", HttpStatus.BAD_REQUEST.value());
        map.put("data", responseObj);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}