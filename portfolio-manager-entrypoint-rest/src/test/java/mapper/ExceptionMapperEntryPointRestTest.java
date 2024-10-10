package mapper;

import com.portfolio.portfoliomanager.exception.BusinessException;
import com.portfolio.portfoliomanager.exception.ExceptionCode;
import com.portfolio.portfoliomanager.exception.ExceptionDto;
import com.portfolio.portfoliomanager.mapper.ExceptionMapperEntryPointRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class ExceptionMapperEntryPointRestTest {

    private ExceptionMapperEntryPointRest mapper;

    @BeforeEach
    void setup() {
        mapper = Mappers.getMapper(ExceptionMapperEntryPointRest.class);
    }

    @Test
    void testToExceptionDto() {
        BusinessException exception;
        String message = "Test message.";
        exception = new BusinessException(ExceptionCode.RESOURCE_NOT_FOUND, message);

        ExceptionDto exceptionDto = mapper.toExceptionDto(exception);

        assertEquals(ExceptionCode.RESOURCE_NOT_FOUND.getCode(), exceptionDto.getCode());
        assertEquals(ExceptionCode.RESOURCE_NOT_FOUND.getReason(), exceptionDto.getReason());
        assertEquals(ExceptionCode.RESOURCE_NOT_FOUND.getHttpStatusCode(), exceptionDto.getHttpStatusCode());
        assertEquals(message, exceptionDto.getMessage());
    }
}
