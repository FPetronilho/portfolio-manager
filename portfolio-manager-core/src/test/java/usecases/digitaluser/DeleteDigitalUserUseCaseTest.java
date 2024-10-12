package usecases.digitaluser;

import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfoliomanager.usecases.digitaluser.DeleteDigitalUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class DeleteDigitalUserUseCaseTest {

    @Mock
    private PortfolioManagerDataProvider dataProvider;

    @InjectMocks
    private DeleteDigitalUserUseCase deleteDigitalUserUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_shouldDeleteDigitalUser() {
        // Given
        String id = "test-id";

        DeleteDigitalUserUseCase.Input input = DeleteDigitalUserUseCase.Input.builder()
                .id(id)
                .build();

        // When
        deleteDigitalUserUseCase.execute(input);

        // Then
        verify(dataProvider).deleteDigitalUser(id);
    }
}
