package br.com.rmu.restapiendereco.shared.infra.jersey;

import br.com.rmu.restapiendereco.shared.infra.exception.ParsableException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by rodrigomunhoz on 02/09/15.
 */
@Provider
public class ParsableExceptionMapper implements ExceptionMapper<ParsableException> {
    @Override
    public Response toResponse(ParsableException ex) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(ex.getErro())
                .build();
    }
}
