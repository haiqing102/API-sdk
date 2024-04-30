package edu.cqupt.apisdk.model.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ResultResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> data = new HashMap<>();

	@JsonAnyGetter
	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}