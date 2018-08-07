/*
Copyright (C) 2018 Adrian D. Finlay. All rights reserved.

Licensed under the MIT License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://opensource.org/licenses/MIT

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER INCLUDING AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
==============================================================================
**/

package com.adriandavid.http.java11;


import java.io.File;
import java.net.URI;
import java.util.Scanner;
import java.time.Duration;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.InputStream;
import java.io.FileOutputStream​;
import java.nio.charset.Charset;
import java.net.Authenticator;
import java.net.ProxySelector;
import java.net.http.WebSocket;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.HttpURLConnection;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardCopyOption;

/* HTTP GET Request, Response (Java 11 APIs)  */
public class RESTDemo {
    private final String API_ENDPOINT = "https://onyxfx-api.herokuapp.com/nbaBasicStatBean?";
    private final String API_ENDPOINT2 = "http://api.giphy.com/v1/gifs/";
    private final String API_ENDPOINT2_ID = "yoJC2COHSxjIqadyZW";
    private final String API_ENDPOINT2_KEY = "ZtFzb5dH6w9aYjoffJQ0RqlAsS5s0xwR";
    private final HttpResponse.BodyHandler<String> asString = HttpResponse.BodyHandlers.ofString();
    private final HttpResponse.BodyHandler<Void> asDiscarded = HttpResponse.BodyHandlers.discarding();
    private final HttpResponse.BodyHandler<InputStream> asInputStream= HttpResponse.BodyHandlers.ofInputStream();
    private final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
                            .followRedirects(HttpClient.Redirect.NORMAL).proxy(ProxySelector.getDefault()).build();
    private String[] args;

    public RESTDemo (String[] args) {
        this.args = args;
    };

    public void call() throws Exception {

        if (args.length < 4) {
            System.out.println("An invalid amount of arguments was supplied.");
            return;
        }

        System.out.println("---------------------------------");
    
        // HTTP GET REQUEST 
        var HTTP_REQUEST = HttpRequest.newBuilder()
            .uri(URI.create( //Set the appropriate endpoint
                    new StringBuilder(API_ENDPOINT)
                    .append("firstName=").append(args[0])
                    .append("&surname=").append(args[1])
                    .append("&season=").append(args[2])
                    .toString() ) )
            .timeout(Duration.ofMinutes(1))
            .header("Content-Type", "application/json")
            .build();              
        var HTTP_REQUEST2 = HttpRequest.newBuilder()
            .uri(URI.create( //Set the appropriate endpoint
                    new StringBuilder(API_ENDPOINT2)
                    .append(API_ENDPOINT2_ID)
                    .append("?api_key=").append(API_ENDPOINT2_KEY)
                    .append("&data")
                    .append("&type")
                    .append("&images")
                    .toString() ) )
            .timeout(Duration.ofMinutes(1))
            .header("Content-Type", "application/json")
            .build();


        // SEND HTTP GET REQUEST, RECIEVE OBJECT FOR HTTP GET RESPONSE 
        var HTTP_RESPONSE = HTTP_CLIENT.send(HTTP_REQUEST, asString);
        var HTTP_RESPONSE2 = HTTP_CLIENT.send(HTTP_REQUEST, asDiscarded);
        var HTTP_RESPONSE3 = HTTP_CLIENT.send(HTTP_REQUEST2, asInputStream);
        
        // HTTP STATUS CODE 
        var statusCode = HTTP_RESPONSE.statusCode();
        var statusCode2 = HTTP_RESPONSE2.statusCode();
        var statusCode3 = HTTP_RESPONSE3.statusCode();
        
            
        // HANDLE RESPONSE
        if (statusCode == 200 || statusCode == 201)
            System.out.println("Success! -- Java 11 REST API Call\n" + 
                args[1] + ", " + args[0] + " [" + args[3] +"]\n" + HTTP_RESPONSE.body());
        else 
            System.out.println("Failure! -- Java 11 REST API Call");
        
        System.out.println("---------------------------------");                
        
        if (statusCode2 == 200 || statusCode2 == 201)
            if (HTTP_RESPONSE2.body() == null)
            System.out.println("Success! -- Java 11 REST API Call\n" + 
                args[1] + ", " + args[0] + " [" + args[3] +"]\n" + "Data was discarded.");
        else 
            System.out.println("Failure! -- Java 11 REST API Call");
        
        System.out.println("---------------------------------");

        if (statusCode3 == 200 || statusCode3 == 201) {
            System.out.println("Success! -- Java 11 REST API Call\n" + "Let's download the file! ");
            var HTTP_STREAM = HTTP_RESPONSE3.body();
            Files.copy(HTTP_STREAM, new File("response2.json").toPath(), StandardCopyOption.REPLACE_EXISTING);
            HTTP_STREAM.close();
        }
        else 
            System.out.println("Failure! -- Java 11 REST API Call");                                               
        
        System.out.println("---------------------------------");
    };
};