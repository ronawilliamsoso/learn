package com.example.learn.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

public class HttpPostAsync {

	public static void main(String[] args) throws Exception {
		// 设置HTTP请求的超时配置
		RequestConfig config = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
		// 根据配置创建异步的HTTP客户端
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom().setDefaultRequestConfig(config).build();

		// 运行客户端
		httpclient.start();

		// 构建5个HTTPPOST请求
		String url = "https://httpbin.org/post";
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"name\":\"Wei\",");
		json.append("\"notes\":\"hello\"");
		json.append("}");
		HttpPost[] requests = new HttpPost[] { new HttpPost(url), new HttpPost(url), new HttpPost(url),
				new HttpPost(url), new HttpPost(url) };

		// 创建一个计数器
		final CountDownLatch latch = new CountDownLatch(requests.length);
		// 循环请求
		for (final HttpPost post : requests) {

			post.setEntity(new StringEntity(json.toString()));// 可以在循环里加入不同的json

			httpclient.execute(post, new FutureCallback<HttpResponse>() {

				@Override
				public void completed(HttpResponse response) {
					latch.countDown();

					BufferedReader br;
					try {
						br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
						String output;
						System.out.println("==================================>Output from Server .... \n");
						while ((output = br.readLine()) != null) {
							System.out.println(output);
						}
					} catch (UnsupportedOperationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				@Override
				public void failed(Exception excptn) {
					latch.countDown();
					System.out.println("HttpPost failed");
				}

				@Override
				public void cancelled() {
					latch.countDown();
					System.out.println("HttpPost cancelled");
				}
			});
		}
		// 等待5个都请求完成
		latch.await();
		System.out.println("Shutting down");
		// 关闭
		httpclient.close();
	}
}
