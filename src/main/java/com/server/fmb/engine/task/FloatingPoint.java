package com.server.fmb.engine.task;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.Context;
import com.server.fmb.engine.EngineUtil;
import com.server.fmb.engine.ITaskHandler;
import com.server.fmb.entity.Steps;

public class FloatingPoint implements ITaskHandler {

	@Async
	@Override
	public HandlerResult run(Steps step, Context context) throws Exception {
		// TODO Auto-generated method stub
		return runAwait(step, context);
	}

	@Override
	public HandlerResult runAwait(Steps step, Context context) throws Exception {
		
		HandlerResult hResult = new HandlerResult();
		
		JSONObject paramsJson = (JSONObject)(new JSONParser()).parse(step.getParams());
		String params_accessor = (String)paramsJson.get("accessor");
		String params_operation = (String)paramsJson.get("operation");
		String params_endian = (String)paramsJson.get("endian");
		String params_floatType = (String)paramsJson.get("floatType");

		
		String value = (String)EngineUtil.access(params_accessor, context.data);
		if (value != null) {
			throw new Exception("Accessor value not found");
		}
		boolean isLE = "little".equals(params_endian);
		int nBytes = "float".equals(params_floatType) ? 4: 8;
		int mantissa = "float".equals(params_floatType) ? 23: 52;
		
		Object result = null;
		
		
		// 어디에 필요한지 파악하고 필요시 구현
//		  var result
//
//		  if (operation == 'write') {
//		    result = Buffer.alloc(nBytes)
//		    ieee754.write(result, value, 0, isLE, mantissa, nBytes)
//		  } else {
//		    result = ieee754.read(value, 0, isLE, mantissa, nBytes)
//		  }

		
		// 아래는 IEEE-754 Floating-Point Conversion 예제
//		if ("write".equals(params_operation)) {
//			StringBuilder sb=new StringBuilder();
//			for(int i=start; i<(start+length); ++i){
//			    sb.append(byteToBinaryString(value[i]));
//			}
//			int bits = Integer.parseInt(sb.toString(), 2);
//			result = Float.intBitsToFloat(bits);			
//		} else {
//			
//		}
		
		hResult.data = result;
		return hResult;
	}

}
