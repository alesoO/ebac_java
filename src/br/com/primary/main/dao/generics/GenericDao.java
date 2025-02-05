package br.com.primary.main.dao.generics;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.com.primary.annotation.TypeKey;
import br.com.primary.main.dao.Persistent;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public abstract class GenericDao<T extends Persistent, E extends Serializable> implements IGenericDAO<T, E> {
	
	private SingletonMap singletonMap;
	
	public abstract Class<T> getTypeClass();
	
	public abstract void updateData(T entity, T entityAdded);
	
	public GenericDao() {
		this.singletonMap = SingletonMap.getInstance();
	}
	
	public E getKey(T entity) throws TypeKeyNotFoundException {
		Field[] fields = entity.getClass().getDeclaredFields();
		E returnValue = null;
		for (Field field : fields) {
			if (field.isAnnotationPresent(TypeKey.class)) {
				TypeKey typeKey = field.getAnnotation(TypeKey.class);
				String nameMethod = typeKey.value();
				try {
					Method method = entity.getClass().getMethod(nameMethod);
					returnValue = (E) method.invoke(entity);
					return returnValue;
				} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
					throw new TypeKeyNotFoundException("Chave principal do objeto " + entity.getClass() + "não encontrada", e);
				}
			}
		}
		if (returnValue == null) {
			String msg = "Chave principal do objeto " + entity.getClass() + "não encontrada";
			System.out.println("=== ERROR ===" + msg);
			throw new TypeKeyNotFoundException(msg);
		}
		return null;
	}
	
	@Override
	public Boolean add(T entity) throws TypeKeyNotFoundException {
		Map<E, T> innerMap = getMapa();
		E key = getKey(entity);
		if (innerMap.containsKey(key)) {
			return false;
		}
		innerMap.put(key, entity);
		return true;
	}
	
	private Map<E, T> getMapa() {
		Map<E, T> innerMap = (Map<E, T>) this.singletonMap.getMap().get(getTypeClass());
		if (innerMap == null) {
			innerMap = new HashMap<>();
			this.singletonMap.getMap().put(getTypeClass(), innerMap);
		}
		return innerMap;
	}
	
	@Override
	public void delete(E value) {
		Map<E, T> innerMap = getMapa();
		T addedObject = innerMap.get(value);
		if (addedObject != null) {
			innerMap.remove(value, addedObject);
		}
	}
	
	@Override
	public void edit(T entity) throws TypeKeyNotFoundException {
		Map<E, T> innerMap = getMapa();
		E key = getKey(entity);
		T addedObject = innerMap.get(key);
		if (addedObject != null) {
			updateData(entity, addedObject);
		}
	}
	
	@Override
	public T show(E value) {
		Map<E, T> innerMap = getMapa();
		return innerMap.get(value);
	}
	
	@Override
	public Collection<T> showAll() {
		Map<E, T> innerMap = getMapa();
		return innerMap.values();
	}
}
