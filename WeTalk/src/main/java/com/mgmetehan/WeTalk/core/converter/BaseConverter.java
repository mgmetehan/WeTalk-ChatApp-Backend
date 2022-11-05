package com.mgmetehan.WeTalk.core.converter;

import com.mgmetehan.WeTalk.core.entity.BaseEntity;
import com.mgmetehan.WeTalk.core.model.dto.BaseEntityDto;
import com.mgmetehan.WeTalk.core.model.resource.BaseEntityResource;

public interface BaseConverter <D extends BaseEntityDto, E extends BaseEntity, R extends BaseEntityResource> {
    R toResource(E entity);

    E toEntity(D dto);
}
