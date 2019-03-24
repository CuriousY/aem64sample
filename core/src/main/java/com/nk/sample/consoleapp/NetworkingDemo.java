package com.nk.sample.consoleapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NetworkingDemo {

	public static void main(String[] args) {
		try {
			URL restServiceURL = new URL("https://jsonplaceholder.typicode.com/users");
			URLConnection con = restServiceURL.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String response;
			StringBuffer responseString = new StringBuffer();			
			while((response =  in.readLine()) != null) {
				responseString.append(response);
			}
			System.out.println(responseString.toString());
			in.close();			
		} catch (IOException e) {
			System.out.println("Please check the URL provided " + e.getMessage());
		}

	}

}
