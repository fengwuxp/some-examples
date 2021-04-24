package com.oak.api.model;


import com.wuxp.api.signature.ApiSignatureRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;


/**
 * @author wxup
 */
@Data
@Schema(name = "ApiBaseReq", description = "api基础请求对象")
@Validated
public abstract class ApiBaseReq implements ApiSignatureRequest {


}
