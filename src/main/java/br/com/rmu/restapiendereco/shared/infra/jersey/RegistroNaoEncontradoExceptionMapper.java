package br.com.rmu.restapiendereco.shared.infra.jersey;

import br.com.rmu.restapiendereco.shared.infra.exception.RegistroNaoEncontradoException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by rodrigomunhoz on 02/09/15.
 */
@Provider
public class RegistroNaoEncontradoExceptionMapper implements ExceptionMapper<RegistroNaoEncontradoException> {
    @Override
    public Response toResponse(RegistroNaoEncontradoException ex) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(ex.getErro())
                .build();
    }
}
