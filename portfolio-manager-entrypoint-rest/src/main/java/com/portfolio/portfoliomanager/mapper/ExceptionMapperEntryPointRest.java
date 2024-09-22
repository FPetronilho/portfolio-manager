package com.portfolio.portfoliomanager.mapper;

import com.portfolio.portfoliomanager.exception.BusinessException;
import com.portfolio.portfoliomanager.exception.ExceptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ExceptionMapperEntryPointRest {

    ExceptionDto toExceptionDto(BusinessException e);
}
