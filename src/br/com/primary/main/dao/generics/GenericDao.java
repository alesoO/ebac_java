package br.com.primary.main.dao.generics;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.primary.annotation.Table;
import br.com.primary.annotation.TableColumn;
import br.com.primary.annotation.TypeKey;
import br.com.primary.main.dao.Persistent;
import br.com.primary.main.dao.generics.jdbc.ConnectionFactory;
import br.com.primary.main.exceptions.DaoException;
import br.com.primary.main.exceptions.MoreThanOneRegisterException;
import br.com.primary.main.exceptions.TableException;
import br.com.primary.main.exceptions.TypeElementNotFoundException;
import br.com.primary.main.exceptions.TypeKeyNotFoundException;

public abstract class GenericDao<T extends Persistent, E extends Serializable> implements IGenericDAO<T, E> {
	public abstract Class<T> getTypeClass();
	
	public abstract void updateData(T entity, T entityAdded);
	
	protected abstract String getQueryInsert();
	
	protected abstract String getQueryDelete();
	
	protected abstract String getQueryUpdate();
	
	protected abstract void setParametersQueryInsert(PreparedStatement stmInsert, T entity) throws SQLException;
	
	protected abstract void setParametersQueryDelete(PreparedStatement stmDelete, E value) throws SQLException;

	protected abstract void setParametersQueryUpdate(PreparedStatement stmUpdate, T entity) throws SQLException;
	
	protected abstract void setParametersQuerySelect(PreparedStatement stmUpdate, E value) throws SQLException;
	
	public GenericDao(){}
	
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
	public Boolean add(T entity) throws TypeKeyNotFoundException, DaoException {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = getConnection();
			stm = connection.prepareStatement(getQueryInsert(), Statement.RETURN_GENERATED_KEYS);
			setParametersQueryInsert(stm, entity);
			int rowsAffected = stm.executeUpdate();
			if(rowsAffected > 0) {
				try(ResultSet rs = stm.getGeneratedKeys()){
					if(rs.next()) {
						Persistent per = (Persistent) entity;
						per.setId(rs.getLong(1));
					}
				}
				return true;
			}
		} catch(SQLException e) {
			throw new DaoException("ERRO CADASTRANDO OBJETO ", e);
		} finally {
			closeConnection(connection, stm, null);
		}
		return false;
	}
	
	@Override
	public void delete(E value) throws DaoException {
		Connection connection = getConnection();
		PreparedStatement stm = null;
		try {
			stm = connection.prepareStatement(getQueryDelete());
			setParametersQueryDelete(stm, value);
			int rowsAffected = stm.executeUpdate();
		} catch(SQLException e) {
			throw new DaoException("ERRO EXCLUINDO OBJETO ", e);
		} finally {
			closeConnection(connection, stm, null);
		}
	}
	
	@Override
	public void edit(T entity) throws TypeKeyNotFoundException, DaoException {
		Connection connection = getConnection();
		PreparedStatement stm = null;
		try {
			stm = connection.prepareStatement(getQueryUpdate());
			setParametersQueryUpdate(stm, entity);
			int rowsAffected = stm.executeUpdate();
		} catch(SQLException e) {
			throw new DaoException("ERRO ALTERANDO OBJETO ", e);
		} finally {
			closeConnection(connection, stm, null);
		}
	}
	
	@Override
	public T show(E value) throws MoreThanOneRegisterException, TableException, DaoException {
		try {
			validateMoreThanOneRegister(value);
			Connection connection = getConnection();
			PreparedStatement stm = connection.prepareStatement("SELECT * FORM " + getTableName() +  " WHERE " + getNameFieldKey(getTypeClass()) + " = ?");
			setParametersQuerySelect(stm, value);
			ResultSet rs = stm.executeQuery();
			if(rs.next()) {
				T entity = getTypeClass().getConstructor(null).newInstance(null);
				Field[] fields = entity.getClass().getDeclaredFields();
				for(Field field : fields) {
					if(field.isAnnotationPresent(TableColumn.class)) {
						TableColumn column = field.getAnnotation(TableColumn.class);
						String dbName = column.dbName();
						String javaSetName = column.setJavaName();
						Class<?> classField = field.getType();
						try {
							Method method = entity.getClass().getMethod(javaSetName, classField);
							setValueByType(entity, method, classField, rs, dbName);
						} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
							throw new DaoException("ERRO CONSULTANDO OBJETO", e);
						} catch (TypeElementNotFoundException e) {
							throw new DaoException("ERRO CONSULTANDO OBJETO", e);
						}
					}
				}
				return entity;
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | TypeKeyNotFoundException e) {
			throw new DaoException("ERRO CONSULTANDO OBJETO", e);
		}
		return null;
	}
	
	public String getNameFieldKey(Class clazz) throws TypeKeyNotFoundException {
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields) {
			if(field.isAnnotationPresent(TypeKey.class) && field.isAnnotationPresent(TableColumn.class)) {
				TableColumn column = field.getAnnotation(TableColumn.class);
				return column.dbName();
			}
		}
		return null;
	}
	
	private void setValueByType(T entity, Method method, Class<?> classField, ResultSet rs, String fieldName) throws  IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException, TypeElementNotFoundException {
		if(classField.equals(Integer.class)) {
			Integer val = rs.getInt(fieldName);
			method.invoke(entity, val);
		} else if(classField.equals(Long.class)) {
			Long val = rs.getLong(fieldName);
			method.invoke(entity, val);
		} else if(classField.equals(Double.class)) {
			Double val = rs.getDouble(fieldName);
			method.invoke(entity, val);
		} else if(classField.equals(Short.class)) {
			Short val = rs.getShort(fieldName);
			method.invoke(entity, val);
		} else if(classField.equals(BigDecimal.class)) {
			BigDecimal val = rs.getBigDecimal(fieldName);
			method.invoke(entity, val);
		} else if(classField.equals(String.class)) {
			String val = rs.getString(fieldName);
			method.invoke(entity, val);
		} else {
			throw new TypeElementNotFoundException("TIPO DE CLASSE NÃO CONHECIDO: " + classField);
		}
	}
	
	private Long validateMoreThanOneRegister(E value) throws  MoreThanOneRegisterException, TableException, TypeKeyNotFoundException, DaoException {
    	Connection connection = getConnection();
    	PreparedStatement stm = null;
    	ResultSet rs = null;
    	Long count = null;
    	try {
    		stm = connection.prepareStatement("SELECT count(*) FROM " + getTableName() + " WHERE " + getNameFieldKey(getTypeClass()) + " = ?");
    		setParametersQuerySelect(stm, value);
    		rs = stm.executeQuery();
    		if(rs.next()) {
    			count = rs.getLong(1);
    			if(count > 1) {
    				throw new MoreThanOneRegisterException("ENCONTRADO MAIS DE UM REGISTRO DE " + getTableName());
    			}
    		}
    		return count;
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} finally {
    		closeConnection(connection, stm, rs);
		}
    	return count;
	}
	
	protected void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) {
		try {
			if(rs != null && !rs.isClosed()) {
				rs.close();
			}
			if(stm != null && !stm.isClosed()) {
				stm.close();
			}
			if(connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String getTableName() throws TableException {
		if(getTypeClass().isAnnotationPresent(Table.class)) {
			Table table = getTypeClass().getAnnotation(Table.class);
			return table.value();
		} else {
			throw new TableException("TABELA NO TIPO " + getTypeClass().getName() + "NÃO FOI ENCONTRADO");
		}
	}
	
	@Override
	public Collection<T> showAll() throws DaoException {
		List<T> list = new ArrayList<>();
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			stm = connection.prepareStatement("SELECT * FROM " + getTableName());
			rs = stm.executeQuery();
			while (rs.next()) {
				T entity = getTypeClass().getConstructor(null).newInstance(null);
				Field[] fields = entity.getClass().getDeclaredFields();
				for(Field field : fields) {
					if(field.isAnnotationPresent(TableColumn.class)) {
						TableColumn column = field.getAnnotation(TableColumn.class);
						String dbName = column.dbName();
						String javaSetName = column.setJavaName();
						Class<?> classField = field.getType();
						try {
							Method method = entity.getClass().getMethod(javaSetName, classField);
							setValueByType(entity, method, classField, rs, dbName);
						} catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
							throw new DaoException("ERRO LISTANDO OBJETOS ", e);
						} catch(TypeElementNotFoundException e) {
							throw new DaoException("ERRO LISTANDO OBJETOS ", e);
						}
					}
				}
				list.add(entity);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | TableException e) {
			throw new DaoException("ERRO LISTANDO OBJETOS ", e);
		} finally {
			closeConnection(connection, stm, rs);
		}
		return list;
	}
	
	protected Connection getConnection() throws DaoException {
		try {
			return ConnectionFactory.getConnection();
		} catch(SQLException e) {
			throw new DaoException("ERRO ABRINDO CONEXAO COM O BANCO DE DADOS ", e);
		}
	}
}
