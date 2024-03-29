package io.github.tuyendev.msv.common;

import io.github.tuyendev.msv.common.annotation.MessageResourceClaim;

import org.springframework.stereotype.Component;

@Component
public class CommonMessageSource implements MessageResourceClaim {


	@Override
	public String[] messagesSources() {
		return new String[] {"classpath:common-messages"};
	}
}
