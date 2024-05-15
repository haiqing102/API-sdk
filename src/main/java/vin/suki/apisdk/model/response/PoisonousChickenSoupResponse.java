package vin.suki.apisdk.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PoisonousChickenSoupResponse extends ResultResponse {

	private String text;

}
