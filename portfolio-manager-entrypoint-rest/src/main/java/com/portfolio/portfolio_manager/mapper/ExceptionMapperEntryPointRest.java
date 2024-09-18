package com.portfolio.portfolio_manager.mapper;

import com.portfolio.portfolio_manager.exception.BusinessException;
import com.portfolio.portfolio_manager.exception.ExceptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ExceptionMapperEntryPointRest {

    ExceptionDto toExceptionDto(BusinessException e);
}
