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

// package com.adriandavid.java11.ws;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class WebSocketDemo {
	
	public static void main (String[] args) {
        try {
	        var server = new WebSocketServer().getWebSocket();
	        server.sendPing(ByteBuffer.wrap("Ping: Client <--- Server".getBytes(Charset.forName("UTF-16"))));
	        server.sendPing(ByteBuffer.wrap("Pong: Client <--- Server".getBytes(Charset.forName("UTF-16"))));
	        server.sendText("Hello!", false);
	        server.sendClose(1001, "Goodbye!");
	    } catch (Exception e) { 
	    	System.out.println("Failure:" + e.getClass().toString().replace("class", "") + " was thrown.\nMessage: " + e.getMessage()); 
	    	
	    	if (e.getMessage().contains("WebSocketHandshakeException")) {
	    		var ex = ((java.net.http.WebSocketHandshakeException) e.getCause()).getResponse();
	    		System.out.println("Body:\t" + ex.body());
	    		System.out.println("Headers:");
	    		ex.headers().map().forEach( (k,v)-> System.out.println("\t" + k + ":  " + v));
	    		System.out.println("HTTP request:  " + ex.request());
	    		System.out.println("HTTP version:  " + ex.version());
	    		System.out.println("Previous Reponse?:  " + ex.previousResponse());
	    	}
    	}
	};
};