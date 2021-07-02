package com.server.fmb.engine.task;

import org.springframework.scheduling.annotation.Async;

import com.server.fmb.engine.Context;
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
		
		
//		  const value = access(accessor, data)
//				  if (typeof value === 'undefined') {
//				    throw new Error('accessor value not a found')
//				  }
//
//				  const isLE = endian === 'little'
//				  const nBytes = floatType === 'float' ? 4 : 8
//				  const mantissa = floatType === 'float' ? 23 : 52
//
//				  var result
//
//				  if (operation == 'write') {
//				    result = Buffer.alloc(nBytes)
//				    ieee754.write(result, value, 0, isLE, mantissa, nBytes)
//				  } else {
//				    result = ieee754.read(value, 0, isLE, mantissa, nBytes)
//				  }

		HandlerResult hResult = new HandlerResult();
//		hResult.data = result;
		return hResult;
	}

}
