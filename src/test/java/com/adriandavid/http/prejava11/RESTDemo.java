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

package com.adriandavid.http.prejava11;


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

/* HTTP GET Request, Response (Pre-Java 11 API)   */
public class RESTDemo {

	private String[] args;
	private final String API_ENDPOINT = "https://onyxfx-api.herokuapp.com/nbaBasicStatBean?";
	private final String API_ENDPOINT2 = "http://api.giphy.com/v1/gifs/";
    private final String API_ENDPOINT2_ID = "yoJC2COHSxjIqadyZW";
    private final String API_ENDPOINT2_KEY = "ZtFzb5dH6w9aYjoffJQ0RqlAsS5s0xwR";
    

	public RESTDemo (String[] args) {
        this.args = args;
    };

    public void call() throws Exception {	
        //HTTP GET REQUEST
        var HTTP_CLIENT= (HttpURLConnection) 
                            URI.create(
                                new StringBuilder(API_ENDPOINT)
                                .append("firstName=").append(args[0])
                                .append("&surname=").append(args[1])
                                .append("&season=").append(args[2])
                                .toString())
                            .toURL()
                            .openConnection();
        HTTP_CLIENT.setRequestMethod("GET");
        
        var HTTP_CLIENT2 = (HttpURLConnection)
                            URI.create( //Set the appropriate endpoint
                                new StringBuilder(API_ENDPOINT2)
                                .append(API_ENDPOINT2_ID)
                                .append("?api_key=").append(API_ENDPOINT2_KEY)
                                .append("&data")
                                .append("&type")
                                .append("&images")
                                .toString() )
                            .toURL()
                            .openConnection();
        HTTP_CLIENT2.setRequestMethod("GET");

        
        //HTTP RESPONSE
        var HTTP_RESPONSE = HTTP_CLIENT.getInputStream();
        var scn = new Scanner(HTTP_RESPONSE);
        var json_sb = new StringBuilder();
        while (scn.hasNext()) {
            json_sb.append(scn.next());
        }
        var JSON = json_sb.toString();
        
        if (HTTP_CLIENT2.getContentType().contains("json")) {
            var stream_in = (InputStream​)(HTTP_CLIENT2.getContent());
            var stream_out = new FileOutputStream​ (new File("response1.json"));
            stream_in.transferTo(stream_out);
            stream_in.close();
            stream_out.close();
        }
        else
            return; // suffice for now.

        // HTTP STATUS
        var statusCode = HTTP_CLIENT.getResponseCode();
        var statusCode2 = HTTP_CLIENT2.getResponseCode();
        
        // HANDLE RESPONSE
        if (statusCode == 200 || statusCode == 201)
            System.out.println("Success! -- Pre-Java 11 REST API Call\n" + 
                args[1] + ", " + args[0] + " [" + args[2] +"]\n" + JSON);
        else
            System.out.println("Failure! -- Pre-Java 11 REST API Call");
        
        System.out.println("---------------------------------");

        if (statusCode2 == 200 || statusCode2 == 201) {
            System.out.println("Success! -- Pre-Java 11 REST API Call\n" + "Let's download the file!" );
        }
        else
            System.out.println("Failure! -- Pre-Java 11 REST API Call");
       
    	System.out.println("---------------------------------");
    };
};