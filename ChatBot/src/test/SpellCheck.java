package test;

import java.io.IOException;
import org.apache.httpcomponent.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClientBuilder;

class SpellCheck{
	public SpellCheck(String queryString) throws ClientProtocolException, IOException
	{
		//CloseableHttpClient httpClient = HttpClientBuilder.create().build();		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String url = "https://api.cognitive.microsoft.com/bing/v5.0/spellcheck/?" + queryString;

		HttpPost httpPost = new HttpPost(url);

		httpPost.addHeader("Ocp-Apim-Subscription-Key " , "a6a3f0cf5b384bb7a8535db8eb5df295");

		HttpResponse response = httpclient.execute(httpPost);
	}
}