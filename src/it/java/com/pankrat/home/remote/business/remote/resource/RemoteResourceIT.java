package  com.pankrat.home.remote.business.remote.resource;


import org.junit.jupiter.api.Test;

import com.pankrat.home.remote.HomeRemoteSpec;

public class RemoteResourceIT extends HomeRemoteSpec  {
    @Test
    public void testHomeRemote()
    {
        String response = target.path( "remote/getAll" ).request().get( String.class );
        assert response != null;
    }
}

