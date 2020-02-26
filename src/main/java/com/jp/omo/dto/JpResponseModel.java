package com.jp.omo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JpResponseModel {
	private String resposeStatus;
	private String responseMessage;
	private Object response;
	private String customMessage;
	
}
