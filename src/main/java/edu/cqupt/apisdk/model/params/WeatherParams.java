package edu.cqupt.apisdk.model.params;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class WeatherParams implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ip;

	private String city;

	private String type;

}
