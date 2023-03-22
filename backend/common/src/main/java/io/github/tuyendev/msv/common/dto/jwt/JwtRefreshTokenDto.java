package io.github.tuyendev.msv.common.dto.jwt;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true, fluent = true)
@Builder
public class JwtRefreshTokenDto implements Serializable {

    private String id;

    private String accessTokenId;

    private Date issuedAt;

    private Date expiredAt;

    private String token;

}
