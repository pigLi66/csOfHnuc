package com.slamacraft.hnuc.common.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
public class BaseModel implements Serializable {

    protected int id;
    protected LocalDateTime createTime;
    protected LocalDateTime updateTime;
}
