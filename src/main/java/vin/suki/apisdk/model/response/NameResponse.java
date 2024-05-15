package vin.suki.apisdk.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NameResponse extends ResultResponse {

	private String name;

}
