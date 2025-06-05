package com.iotproject.iotproject.Controller;

import com.iotproject.iotproject.Dto.ApiResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BaseSensorController<E, D, F> {

    @Autowired
    private ModelMapper modelMapper;

    protected abstract Page<E> filterEntities(F filter, Pageable pageable);

    // Add a method to convert entity to DTO using ModelMapper
    public D toDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    // Add a method to convert a page of entities to DTOs
    public List<D> toDtoList(Page<E> entities, Class<D> dtoClass) {
        return entities.stream()
                .map(entity -> toDto(entity, dtoClass))
                .collect(Collectors.toList());
    }


    public ResponseEntity<ApiResponseDto<List<D>>> filter(
            F filter,
            Class<D> dtoClass,
            @PageableDefault(size = 10, sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable) {

        try {
            Page<E> page = filterEntities(filter, pageable);

            List<D> dtoList = toDtoList(page, dtoClass);


            Map<String, Object> meta = Map.of(
                    "pageNumber", page.getNumber(),
                    "pageSize", page.getSize(),
                    "totalElements", page.getTotalElements(),
                    "totalPages", page.getTotalPages(),
                    "isLast", page.isLast()
            );

            return ResponseEntity.ok(new ApiResponseDto<>("Success", dtoList, meta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDto<>("Failed to filter sensor data.", null, null));
        }
    }
}

