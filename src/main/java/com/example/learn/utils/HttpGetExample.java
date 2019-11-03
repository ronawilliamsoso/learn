package com.example.learn.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpGetExample {

	public static void main(String[] args) throws IOException {

		CloseableHttpClient httpClient = HttpClients.createDefault();

		try {

			HttpGet request = new HttpGet("https://httpbin.org/get");

			// add request headers
			request.addHeader("custom-key", "wei");
			request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
			request.addHeader("accept", "application/json");

			CloseableHttpResponse response = httpClient.execute(request);

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

		} finally {
			httpClient.close();
		}

	}

}