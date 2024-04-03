package io.iqark.tcauth.utils;

import io.iqark.tcauth.pojo.ErrorMsg;
import jakarta.ws.rs.core.Response;

public class RUtil {
    public static Response success(Object obj) {
        return Response.status(Response.Status.OK)
                .entity(obj)
                .build();
    }

    public static Response authorizedFailed(Integer code, String msg) {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new ErrorMsg(code, msg))
                .build();
    }

    public static Response preconditionFailed(Integer code, String msg) {
        return Response.status(Response.Status.PRECONDITION_FAILED)
                .entity(new ErrorMsg(code, msg))
                .build();
    }

    public static Response expectationFailed(Integer code, String msg) {
        return Response.status(Response.Status.EXPECTATION_FAILED)
                .entity(new ErrorMsg(code, msg))
                .build();
    }
}
