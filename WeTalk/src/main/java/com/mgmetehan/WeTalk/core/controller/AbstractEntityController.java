package com.mgmetehan.WeTalk.core.controller;

import com.mgmetehan.WeTalk.core.annotation.LogConsole;
import com.mgmetehan.WeTalk.core.entity.BaseEntity;
import com.mgmetehan.WeTalk.core.model.dto.BaseEntityDto;
import com.mgmetehan.WeTalk.core.model.resource.BaseEntityResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@Component
public abstract class AbstractEntityController<D extends BaseEntityDto, E extends BaseEntity, R extends BaseEntityResource, P extends Serializable> extends AbstractReadEntityController<D, E, R, P> {
    protected AbstractEntityController() {
    }

    @LogConsole
    @DeleteMapping({"/{id}"})
    public void delete(@PathVariable("id") P id) {
        this.getService().delete(id);
    }

    @LogConsole
    @PostMapping()
    public R save(@RequestBody @Valid D dto) {
        dto.validate();
        var entity = this.getService().save(toEntity(dto));
        return toResource(entity);
    }

    @LogConsole
    @PostMapping("/save-all")
    public List<R> saveAll(@RequestBody @Valid List<D> dtos){
        dtos.forEach(D::validate);
        List<E> entities= this.getService().saveAll(toEntity(dtos));
        return toResource(entities);
    }

    @LogConsole
    @PutMapping({"/{id}"})
    public R put(@PathVariable("id") P id, @RequestBody @Valid D dto) {
        dto.validate();
        var forSave = toEntity(dto);
        var entity = this.getService().put(id, forSave);
        return toResource(entity);
    }
}
