package Service;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponse {
    
    private String returnCode;
    private String responseCode;
    private String responseMessage;
    
    public ServiceResponse() {
    }

    public ServiceResponse(String returnCode, String responseCode, String responseMessage) {
        this.returnCode = returnCode;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }
}