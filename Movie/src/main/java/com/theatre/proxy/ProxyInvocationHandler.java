package com.theatre.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.theatre.application.ApplicationContext;

public abstract class ProxyInvocationHandler implements InvocationHandler {

	protected final Object target;

	protected final ApplicationContext applicationContext;

	protected ProxyInvocationHandler(Object target,
			ApplicationContext applicationContext) {
		this.target = target;
		this.applicationContext = applicationContext;
	}

	protected String getkey(Method method, Object[] args) {
		Class returnType = method.getReturnType();
		int modifier = method.getModifiers();

		StringBuffer key = new StringBuffer();
		key.append(modifier).append(returnType.getName())
				.append(method.getName()).append("(");
		int size = args == null ? 0 : args.length;
		for (int i = 0; i < size; i++) {
			if (i != 0) {
				key.append(",");
			}
			key.append(constructArgumentString(args[i]));
		}
		key.append(")");
		System.out.println("Key : " + key);
		return key.toString();
	}

	protected Method getTargetMatchingMethod(Method method) throws Exception {
		return target.getClass().getMethod(method.getName(),
				method.getParameterTypes());
	}

	private String constructArgumentString(Object arg) {
		String argString;
		if (arg instanceof String) {
			argString = (String) arg;
		} else if (arg instanceof Integer || arg instanceof Boolean
				|| arg instanceof Byte || arg instanceof Long) {
			argString = String.valueOf(arg);
		} else {
			argString = arg.getClass().getName();
		}
		return argString;

	}

}
