package com.urbanladder.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class JsonUtil {
	

	public static Object[][] getdata(String JSON_path, String typeData,
			int totalDataRow, int totalColumnEntry) {
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObj;
		JsonArray array = null;
		try {
			jsonObj = jsonParser.parse(new FileReader(JSON_path))
					.getAsJsonObject();
			 array = (JsonArray) jsonObj.get(typeData);
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searchJsonElemnet(array, totalDataRow, totalColumnEntry);
	}

	public static Object[][] toArray(List<List<Object>> list) {
		Object[][] matrix = new Object[list.size() + 1][];
		int i = 0;
		for (List<Object> next : list) {
			matrix[i++] = next.toArray(new Object[next.size() + 1]);
		}
		return matrix;
	}

	public static Object[][] searchJsonElemnet(JsonArray jsonArray,
			int totalDataRow, int totalColumnEntry){
		
		Object[][] matrix = new Object[totalDataRow][totalColumnEntry];
		int i = 0;
		int j = 0;
		for (JsonElement jsonElement : jsonArray) {
			for (Map.Entry<String, JsonElement> entry : jsonElement
					.getAsJsonObject().entrySet()) {
				matrix[i][j] = entry.getValue().toString().replace("\"", "");
				j++;
			}
			i++;
			j = 0;
		}
		return matrix;
	}
}

