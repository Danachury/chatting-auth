package com.chatting.api;



@Path("/")
public class AuthApi extends BaseRoute implements RestApi, CustomExceptionHandler, CustomRejectionHandler {

    @Override
    public Route mainRoute() {
        return null;
    }
}
