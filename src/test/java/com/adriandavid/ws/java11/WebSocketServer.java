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

import java.net.URI;
import java.net.ProxySelector;
import java.net.http.WebSocket;
import java.net.http.HttpClient;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CompletableFuture;

/* WebSockets in Java 11 */
public class WebSocketServer {
	private final CompletableFuture<WebSocket> server_cf;
	private final String ENDPOINT = "ws://localhost:80"; //

	WebSocketServer () throws InterruptedException, ExecutionException {
		this.server_cf = HttpClient
							.newBuilder()
							.proxy(ProxySelector.of(new java.net.InetSocketAddress("localhost", 80)))
							.build()
							.newWebSocketBuilder()
							.subprotocols("80")
							.buildAsync(URI.create(ENDPOINT), new WebSocketClient());
	}

	CompletableFuture<WebSocket> getServerCf() { return this.server_cf; };
}