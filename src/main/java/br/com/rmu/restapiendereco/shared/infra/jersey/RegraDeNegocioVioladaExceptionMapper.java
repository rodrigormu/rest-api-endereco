package br.com.rmu.restapiendereco.shared.infra.jersey;

import br.com.rmu.restapiendereco.shared.infra.exception.RegraDeNegocioVioladaException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by rodrigomunhoz on 02/09/15.
 */
@Provider
public class RegraDeNegocioVioladaExceptionMapper implements ExceptionMapper<RegraDeNegocioVioladaException> {
    @Override
    public Response toResponse(RegraDeNegocioVioladaException ex) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(ex.getErros())
                .build();
    }
}
