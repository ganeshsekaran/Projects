package com.theatre.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.theatre.annotation.Cacheable;
import com.theatre.proxy.cache.CacheInvocationHandler;
import com.theatre.proxy.transaction.TransactionInvocationHandler;

public class ProxyCreator {

	public static final Object createProxy(final Object target) {
		System.out.println("Came to creater");
		Class targetClass = target.getClass();
		Method[] methods = targetClass.getDeclaredMethods();
		List<Class> annotationClasses = getAnnotationsFromMethods(methods);
		return createProxy(target, annotationClasses);
	}

	private static List<Class> getAnnotationsFromMethods(Method[] methods) {
		List<Class> annotationList = new ArrayList<Class>();
		int size = methods == null ? 0 : methods.length;
		for (int i = 0; i < size; i++) {
			Method method = methods[i];
			Annotation[] annotations = method.getAnnotations();
			int annoSize = annotations == null ? 0 : annotations.length;
			for (int j = 0; j < annoSize; j++) {
				Annotation annotation = annotations[j];
				Class annotationClass = annotation.annotationType();
				System.out.println(annotationClass.getName());
				if (!containsClass(annotationList, annotationClass)) {
					annotationList.add(annotationClass);
				}
			}
		}

		return annotationList;
	}

	private static boolean containsClass(List<Class> annotationList,
			Class annotationClass) {
		boolean contains = false;
		for (Class classe : annotationList) {
			contains = classe.getName().equalsIgnoreCase(
					annotationClass.getName());
			if (contains) {
				break;
			}
		}
		return contains;
	}

	private static Object createProxy(final Object target,
			List<Class> annotationClasses) {
		System.out.println("Came to another method");
		Object proxy = target;
		System.out.println(annotationClasses.size());
		for (Class classe : annotationClasses) {
			proxy = createActualProxy(proxy, classe);
		}
		return proxy;
	}

	private static Object createActualProxy(final Object target, Class classe) {
		String name = classe.getName();
		InvocationHandler handler = null;
		if (name.equalsIgnoreCase(Cacheable.class.getName())) {
			handler = new CacheInvocationHandler(target);
		} else if (name.equalsIgnoreCase(Transactional.class.getName())) {
			handler = new TransactionInvocationHandler(target);
		}

		Object proxy;
		if (handler == null) {
			proxy = target;
		} else {
			proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(),
					target.getClass().getInterfaces(), handler);
		}
		return proxy;
	}
}
