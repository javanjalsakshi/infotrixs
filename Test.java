import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;


public class Test {

    private static final String API_URL = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_bN2pJMUxUgDqL9dTD5lBNEW84pt6ZsD8C8GPDqNk";

    public static void main(String[] args) throws IOException {
        Map<String, Double> rates = getExchangeRates();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        double amount ;String fromCurrency,toCurrency;
        
        	while(true) {
            System.out.println("Enter the amount to convert:");
            String amountStr = reader.readLine();
            if(dob_val(amountStr)) {
            	 amount = Double.parseDouble(amountStr);break;
            }else System.out.println("enter a double value");
            
        	}
        	while(true) {
            System.out.println("Enter the currency to convert from:");
            String fromCurrency1 = reader.readLine();
            if(cur_val(fromCurrency1)) {
            	fromCurrency=fromCurrency1;
            	if(rates.get(fromCurrency)!=null)break;
            	else System.out.println("enter a valid currency");
            }
        	}
        	while(true) {
            System.out.println("Enter the currency to convert to:");
            String toCurrency1 = reader.readLine();
            if(cur_val(toCurrency1)) {
            	toCurrency=toCurrency1;
            	if(rates.get(toCurrency)!=null)break;
            	else System.out.println("enter a valid currency");
            }
        	}
            double conversionRate = rates.get(toCurrency) / rates.get(fromCurrency);
            double convertedAmount = amount * conversionRate;

            System.out.println("Converted amount: " + convertedAmount);
        
    }

    private static Map<String, Double> getExchangeRates() throws IOException {
        URL url = new URL(API_URL);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("Accept", "application/json");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        	 String jsonString= response.toString();
        	  Map<String, Double> dataMap = new HashMap<>();
              String[] dataPairs = jsonString.substring(jsonString.indexOf("{") + 9, jsonString.lastIndexOf("}")-1).split(",");
              for (String dataPair : dataPairs) {
            	 // System.out.println(dataPair);
                  String[] keyValue = dataPair.split(":");
                  String currencyCode = keyValue[0].replace("\"", "");
                  String exchangeRateString = keyValue[1].replace("\"", "");
                //  System.out.println(exchangeRateString);
                  double exchangeRate = Double.parseDouble(exchangeRateString);
                  dataMap.put(currencyCode, exchangeRate);
              }
        

        return dataMap;
    }
    private static boolean dob_val(String s) {
    	int cnt=0,dot=0;
    	for(int i=0;i<s.length();i++) {
    		if((s.charAt(i)>=48)&&(s.charAt(i)<=57)) {
    			cnt++;
    		}
    		if(s.charAt(i)==46) {
    			dot++;;
    		}
    	}
    	return cnt+dot==s.length();
    }
    private static boolean cur_val(String s) {
    	int cnt=0;
    	if(s.length()!=3) {
    		System.out.println("currency must be 3 dig capital letter char");return false;
    	}
    	for(int i=0;i<s.length();i++) {
    		if(s.charAt(i)>=65&&s.charAt(i)<=90) {
    			cnt++;
    		}
    	}
    	if(cnt==3&&s.length()==3) {
    		return true;
    	}else
    		System.out.println("currency must be 3 dig capital letter char");
    	return false;
    }
}
