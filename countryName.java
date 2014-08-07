package TPR;

import java.util.HashMap;
import java.util.Map;

/** 
 * @author Hao
 * to store all the names in a HashMap, if alias exists, point them to the same key. 
 *
 */
public class countryName {

	public static Map<String,String> CountryMap()
	{
		Map<String,String> CountryMap = new HashMap<String,String>();
		
		CountryMap.put("Albania", "Albania");
		CountryMap.put("Angola", "Angola");
		CountryMap.put("Antigua and Barbuda", "Antigua and Barbuda");
		CountryMap.put("Argentina", "Argentina");
		CountryMap.put("Armenia", "Armenia");
		CountryMap.put("Australia", "Australia");
		CountryMap.put("Austria", "Austria");
		//both "Bahrain, Kingdom of" and "Kingdom of Bahrain" represents the Kingdom of Bahrain.
		CountryMap.put("Bahrain, Kingdom of", "Bahrain, Kingdom of");
		CountryMap.put("Bahrain, Kingdom of", "Kingdom of Bahrain");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		CountryMap.put("", "");
		
		return CountryMap;
	}
	
	
}

