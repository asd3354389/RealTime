package com.foxlink.realtime.util;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class StringAdapter extends TypeAdapter<String> {
	/*Gson解決 null value*/
	@Override
	public String read(JsonReader arg0) throws IOException {
		if (arg0.peek() == JsonToken.NULL) {
			arg0.nextNull();
            return "";
        }
        return arg0.nextString();
	}

	@Override
	public void write(JsonWriter writer, String value) throws IOException {
	       if (value == null) {
	            writer.nullValue();
	            return;
	        }
	        writer.value(value);
	}

}
