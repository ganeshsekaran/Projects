package com.theatre.proxy.transaction;

import java.lang.reflect.Method;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.theatre.application.Application;
import com.theatre.proxy.ProxyInvocationHandler;

public class TransactionInvocationHandler extends ProxyInvocationHandler {

	private final PlatformTransactionManager transactionManger;

	public TransactionInvocationHandler(Object target) {
		super(target, Application.getApplicationContext());
		transactionManger = (PlatformTransactionManager) applicationContext
				.get("transactionManger");
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		Object value;
		Method targetMatchingMethod = getTargetMatchingMethod(method);
		if (targetMatchingMethod.isAnnotationPresent(Transactional.class)) {
			Transactional transactional = targetMatchingMethod
					.getAnnotation(Transactional.class);
			DefaultTransactionDefinition definition = new DefaultTransactionDefinition();

			definition.setPropagationBehavior(transactional.propagation()
					.value());
			definition.setPropagationBehaviorName(transactional.propagation()
					.name());

			TransactionStatus status = transactionManger
					.getTransaction(definition);
			try {
				value = method.invoke(target, args);
				transactionManger.commit(status);
			} catch (Exception e) {
				transactionManger.rollback(status);
				throw e;
			}
		} else {
			value = method.invoke(target, args);
		}
		return value;
	}
}
