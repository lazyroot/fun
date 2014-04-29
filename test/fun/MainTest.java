package fun;

import java.io.IOException;
import java.util.UUID;

import core.util.security.DES3Util;
import core.util.security.DESUtil;

public class MainTest {
	
	public static void main(String[] args) throws IOException {
		System.out.println(UUID.randomUUID().toString());
		
		System.out.println(DES3Util.getTwiceEncStr("111111"));
		
	}

}
