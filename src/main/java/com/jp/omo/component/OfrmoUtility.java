package com.jp.omo.component;

import org.springframework.stereotype.Component;

import com.jp.omo.dto.JpResponseModel;


@Component
public class OfrmoUtility {
	
	public JpResponseModel prepareResponse (Object requestResult,String customMessage, boolean isPositve){
		JpResponseModel response = new JpResponseModel ();
		if(isPositve) {
			response.setResponseMessage("the request was successfully served");
			response.setResposeStatus(ApplicationConstant.OFRMO_SUCCESSFUL_STATUS.getStatus());
		}else {
			response.setResponseMessage("some internal error or requesting resource is not found");
			response.setResposeStatus(ApplicationConstant.OFRMO_FIALED_STATUS.getStatus());
		}
		response.setResponse(requestResult);
		response.setCustomMessage(customMessage);
		return response;
	}

}
