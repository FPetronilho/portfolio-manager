package usecases.asset;

import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FindAssetByExternalIdUseCaseTest {

    @Mock
    private PortfolioManagerDataProvider portfolioManagerDataProvider;

    @InjectMocks
    private FindAssetByExternalIdUseCaseTest findAssetByExternalIdUseCaseTest;

    @BeforeEach
    void setup() {

    }
}
