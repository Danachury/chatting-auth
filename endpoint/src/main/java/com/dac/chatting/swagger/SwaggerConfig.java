// package com.dac.chatting.swagger;
//
// import com.dac.chatting.App;
// import com.google.inject.Guice;
// import com.google.inject.Injector;
// import com.google.inject.Singleton;
// import com.google.inject.servlet.GuiceServletContextListener;
// import com.google.inject.servlet.ServletModule;
// import io.swagger.sample.util.ApiOriginFilter;
// import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
// import io.swagger.v3.oas.integration.OpenApiConfigurationException;
// import io.swagger.v3.oas.integration.SwaggerConfiguration;
// import io.swagger.v3.oas.models.OpenAPI;
// import io.swagger.v3.oas.models.info.Contact;
// import io.swagger.v3.oas.models.info.Info;
// import io.swagger.v3.oas.models.info.License;
// import io.swagger.v3.oas.models.servers.Server;
// import org.glassfish.jersey.servlet.ServletContainer;
//
// import javax.ws.rs.ApplicationPath;
// import java.util.*;
//
// @ApplicationPath("/path")
// public class SwaggerConfig extends GuiceServletContextListener {
//
//     @Override
//     protected Injector getInjector() {
//         return Guice.createInjector(new ServletModule() {
//             @Override
//             protected void configureServlets() {
//                 bind(ServletContainer.class).in(Singleton.class);
//                 bind(ApiOriginFilter.class).in(Singleton.class);
//
//                 final Map<String, String> props = new HashMap<>();
//                 props.put("javax.ws.rs.Application", App.class.getName());
//                 props.put("jersey.config.server.wadl.disableWadl", "true");
//                 serve("/api/*").with(ServletContainer.class, props);
//
//                 OpenAPI oas = new OpenAPI();
//                 Info info = new Info()
//                     .title("Swagger Sample App bootstrap code")
//                     .description("This is a sample server Petstore server.  You can find out more about Swagger " +
//                         "at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/).  For this sample, " +
//                         "you can use the api key `special-key` to test the authorization filters.")
//                     .termsOfService("http://swagger.io/terms/")
//                     .contact(new Contact()
//                         .email("apiteam@swagger.io"))
//                     .license(new License()
//                         .name("Apache 2.0")
//                         .url("http://www.apache.org/licenses/LICENSE-2.0.html"));
//
//                 oas.info(info);
//
//                 final Server server = new Server().url("/api");
//                 oas.servers(Collections.singletonList(server));
//
//                 final SwaggerConfiguration oasConfig = new SwaggerConfiguration()
//                     .openAPI(oas)
//                     .prettyPrint(true)
//                     .resourcePackages(Collections.singleton("io.swagger.sample.resource"));
//
//                 try {
//                     new JaxrsOpenApiContextBuilder()
//                         .openApiConfiguration(oasConfig)
//                         .buildContext(true);
//                 } catch (OpenApiConfigurationException e) {
//                     throw new RuntimeException(e.getMessage(), e);
//                 }
//             }
//         });
//     }
// }
