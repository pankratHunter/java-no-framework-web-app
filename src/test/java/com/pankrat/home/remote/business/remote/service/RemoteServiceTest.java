package  com.pankrat.home.remote.business.remote.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class RemoteServiceTest {
    @InjectMocks
    RemoteService remoteService;


    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks( this );
    }

    @Test
    public void callRemoteTest()
    {
        String returnString = remoteService.callRemote();
        assertEquals( returnString, "ok" );
    }
}

