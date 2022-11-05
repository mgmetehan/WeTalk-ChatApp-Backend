package com.mgmetehan.WeTalk.core.model.resource;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IdNameResource implements IBaseResource {
    private static final long serialVersionUID = 9008965274826180953L;
    private long id;
    private String name;
}
