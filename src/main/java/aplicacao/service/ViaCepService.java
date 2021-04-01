package aplicacao.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import aplicacao.model.ViaCepModel;

@Service
public class ViaCepService {
	 
	 public ViaCepModel getModelByCep(String cep) {
		    HttpURLConnection c = null;
		    try {
				URL url = new URL("https://viacep.com.br/ws/" + cep + "/json");
		        c = (HttpURLConnection) url.openConnection();
		        c.setRequestMethod("GET");
		        c.setRequestProperty("Content-length", "0");
		        c.setUseCaches(false);
		        c.setAllowUserInteraction(false);
		        c.setConnectTimeout(5000);
		        c.setReadTimeout(5000);
		        c.connect();
		        int status = c.getResponseCode();

		        switch (status) {
		            case 200:
		            case 201:
		                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
		                StringBuilder sb = new StringBuilder();
		                String line;
		                while ((line = br.readLine()) != null) {
		                    sb.append(line+"\n");
		                }
		                br.close();
		                if(sb.toString() != null) {
		                	return new Gson().fromJson(sb.toString(), ViaCepModel.class);
		                } else {
		                	return null;
		                }
		        }

		    } catch (Exception ex) {
		    	return null;
		    } finally {
		       if (c != null) {
		          try {
		              c.disconnect();
		          } catch (Exception ex) {
		             System.out.println(ex);
		          }
		       }
		    }
		    return null;
		}
	 
	 
	static void getEnderecoByCep(String cep){
		try {
		URL url = new URL("https://viacep.com.br/ws/50721610/json");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");
		String contentType = con.getHeaderField("Content-Type");
		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
		    content.append(inputLine);
		}
		in.close();
		
		con.disconnect();

		BufferedReader br = null;
		if (100 <= con.getResponseCode() && con.getResponseCode() <= 399) {
		    br = new BufferedReader( new InputStreamReader(con.getInputStream()));
		} else {
		    br = new BufferedReader (new InputStreamReader(con.getErrorStream()));
		}
		String strResponse = br.readLine();
		System.out.println(strResponse);
		String response = getFullResponse(con);
		System.out.println(response);
		
		} catch (Exception  e) {
			System.out.println(e);
		}
		
		
		
		/** ADICIONANDO PARÂMETROS NA REQUEST
		Map<String, String> parameters = new HashMap<>();
		parameters.put("param1", "val");
		
		con.setDoOutput(true);
		DataOutputStream out = new DataOutputStream(con.getOutputStream());
		out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
		out.flush();
		out.close();
		 * */
	}
	
	public static String getFullResponse(HttpURLConnection con) throws IOException {
        StringBuilder fullResponseBuilder = new StringBuilder();

        // read status and message

        // read headers

        // read response content
        
        fullResponseBuilder.append(con.getResponseCode())
		  .append(" ")
		  .append(con.getResponseMessage())
		  .append("\n");
		
		con.getHeaderFields().entrySet().stream()
		  .filter(entry -> entry.getKey() != null)
		  .forEach(entry -> {
		      fullResponseBuilder.append(entry.getKey()).append(": ");
		      List headerValues = entry.getValue();
		      Iterator it = headerValues.iterator();
		      if (it.hasNext()) {
		          fullResponseBuilder.append(it.next());
		          while (it.hasNext()) {
		              fullResponseBuilder.append(", ").append(it.next());
		          }
		      }
		      fullResponseBuilder.append("\n");
		});

        return fullResponseBuilder.toString();
    }
	
	public static String getParamsString(Map<String, String> params) 
		      throws UnsupportedEncodingException{
		        StringBuilder result = new StringBuilder();

		        for (Map.Entry<String, String> entry : params.entrySet()) {
		          result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
		          result.append("=");
		          result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
		          result.append("&");
		        }

		        String resultString = result.toString();
		        return resultString.length() > 0
		          ? resultString.substring(0, resultString.length() - 1)
		          : resultString;
		    }

}
