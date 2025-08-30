package ${package}.controller;

import ${package}.config.AppProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public abstract class BaseController {

    protected final AppProperties appProperties;

    protected BaseController(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    protected Pageable createPageable(Integer page, Integer size, String sort, String direction) {
        int pageNumber = page != null ? page : 0;
        int pageSize = size != null ? 
            Math.min(size, appProperties.getPagination().getMaxPageSize()) : 
            appProperties.getPagination().getDefaultPageSize();

        if (sort != null && !sort.isEmpty()) {
            Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
            return PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sort));
        }

        return PageRequest.of(pageNumber, pageSize);
    }
}