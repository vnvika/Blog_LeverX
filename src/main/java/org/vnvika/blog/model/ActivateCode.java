package org.vnvika.blog.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@RedisHash("Activate")
public class ActivateCode implements Serializable {
    @Id
    private UUID id;
    private String activateCode;
    private boolean activate;
    private String username;
}
