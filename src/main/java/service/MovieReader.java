package service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import back.model.Movie;

import com.hazelcast.core.IMap;

public class MovieReader {

	private String filepath;

	public MovieReader(String filepath) {
		this.filepath = filepath;
	}

	public void readMovies(IMap<String, Movie> theIMap) throws JsonParseException, JsonMappingException, IOException {
		Reader aReader = new InputStreamReader(MovieReader.class.getResourceAsStream(filepath));
		List<Movie> movies = new ObjectMapper().readValue(aReader, new TypeReference<List<Movie>>() {
		});
		Map<String, Movie> map = new HashMap<String, Movie>();
		for (Movie m : movies) {
			if (m.isMovie()) {
				map.put(m.getTitle(), m);
			}
		}
		theIMap.putAll(map);
	}
}
