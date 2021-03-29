package aplicacao.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import aplicacao.model.Endereco;

public class ViaCepService {
	
	 public static void main(String[] args) {
		 getEnderecoByCep("");
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
		
		int status = con.getResponseCode();

		Reader streamReader = null;

		if (status > 299) {
		    streamReader = new InputStreamReader(con.getErrorStream());
		} else {
		    streamReader = new InputStreamReader(con.getInputStream());
		}
		
		String response = getFullResponse(con);
		System.out.println(response);
		
		} catch (Exception  e) {
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
