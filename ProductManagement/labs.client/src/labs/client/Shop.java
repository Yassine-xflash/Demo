/*
 * Copyright (C) 2021 oracle
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package labs.client;



import io.helidon.common.http.Http;
import io.helidon.webserver.Routing;

import io.helidon.webserver.WebServer;
import labs.pm.service.ProductManager;
import labs.pm.service.ProductManagerException;

import java.io.IOException;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @version 15.2
 * @author oracle
 */
public class Shop {

    private static final Logger logger = Logger.getLogger(Shop.class.getName());

    public static void main(String[] args) {
        try {
            ResourceFormatter formatter = ResourceFormatter.getResourceFormatter("en-GB");
            ServiceLoader<ProductManager> serviceLoader = ServiceLoader.load(ProductManager.class);
            serviceLoader.stream().forEach(x -> System.out.println(x.type()));
            ProductManager pm = serviceLoader.stream()
                    .filter(provider -> provider.type().toString().contains("db"))
                    .findFirst().get().get();
//            ProductManager pm = serviceLoader.stream()
//                    .filter(provider -> provider.type().toString().contains("file"))
//                    .findFirst().get().get();
//            ProductManager pm = serviceLoader.findFirst().get();
//            pm.findProducts(p->true)
//            .forEach(p->System.out.println(formatter.formatProduct(p)));
            Routing routing = Routing.builder()
                    .any( "/", (request, response) -> {
                                response.status(Http.Status.OK_200)
                                        .send("Specify id to retrieve product information");
                            }
                    )
                    .get("/{id}", (request, response) -> {
                                String result = null;
                                String id = request.path().param("id");
                                try {
                                    result = formatter.formatProduct(pm.findProduct(Integer.parseInt(id)));
                                } catch (NumberFormatException e) {
                                    logger.log(Level.WARNING, e.toString());
                                    response.status(Http.Status.I_AM_A_TEAPOT);
                                    result = "Product id " + id + " is invalid";
                                } catch (ProductManagerException e) {
                                    logger.log(Level.WARNING, e.toString());
                                    response.status(Http.Status.I_AM_A_TEAPOT);
                                    result = "Product with id " + id + " not found";
                                }
                                response.status(Http.Status.OK_200);
                                response.headers()
                                        .put("Content-Type", "text/html; charset=UTF-8");
                                response.send(result);
                            }
                    ).get("/{id}/reviews", (request, response) -> {
                        String result = null;
                        String id = request.path().param("id");
                        try {
                            result = formatter.formatProduct(pm.findProduct(Integer.parseInt(id)))+"<br>";
                            result += pm.findReviews(Integer.parseInt(id)).stream()
                                    .map(r->formatter.formatReview(r))
                                    .reduce((s1,s2)->s1+"<br>"+s2).orElse(formatter.formatData("no.reviews"));
                        } catch (NumberFormatException e) {
                            logger.log(Level.WARNING, e.toString());
                            response.status(Http.Status.I_AM_A_TEAPOT);
                            result = "Product id " + id + " is invalid";
                        } catch (ProductManagerException e) {
                            logger.log(Level.WARNING, e.toString());
                            response.status(Http.Status.I_AM_A_TEAPOT);
                            result = "Product with id " + id + " not found";
                        }
                        response.status(Http.Status.OK_200);
                        response.headers()
                                .put("Content-Type", "text/html; charset=UTF-8");
                        response.send(result);
                    }
                ).build();
            WebServer server = WebServer.builder()
                    .bindAddress(InetAddress.getLocalHost())
                    .port(8888)
                    .routing(routing)
                    .build();
            server.start();
        } catch (UnknownHostException e) {
            logger.log(Level.SEVERE, "Error binding internet address", e);
        }
//        catch (ProductManagerException e) {
//            logger.log(Level.SEVERE, "Error accessing product", e);
//        }

    }

    private static void printFile(String content, Path file) {
        try {
            Files.writeString(file, content, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error printing file", e);
        }
    }
    
}
