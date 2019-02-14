package org.zero.spring.mybatis;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.zero.spring.mybatis.annotation.Column;
import org.zero.spring.mybatis.annotation.Operation;
import org.zero.spring.mybatis.annotation.Table;

import zero.commons.basics.ObjectUtil;
import zero.commons.basics.StringUtils;

public class BaseProvider<T> {

	/**
	 * 
	 * 方法: insert <br>
	 * 描述: 添加sql <br>
	 * 作者: zhy<br>
	 * 时间: 2019年2月14日 下午4:33:43
	 * 
	 * @param entity
	 * @return
	 */
	public String insert(T entity) {
		if (entity != null && entity.getClass().isAnnotationPresent(Table.class)) {
			Table table = entity.getClass().getAnnotation(Table.class);
			List<Map<String, String>> params = params(entity, Operation.INSERT);
			if (params != null && params.size() > 0) {
				return new SQL() {
					{
						INSERT_INTO(table.name());
						for (Map<String, String> map : params) {
							VALUES(map.get("name"), map.get("value"));
						}
					}
				}.toString();
			}
		}
		return null;
	}

	/**
	 * 
	 * 方法: update <br>
	 * 描述: 编辑SQL <br>
	 * 作者: zhy<br>
	 * 时间: 2019年2月14日 下午4:33:53
	 * 
	 * @param entity
	 * @return
	 */
	public String update(T entity) {
		if (entity != null && entity.getClass().isAnnotationPresent(Table.class)) {
			Table table = entity.getClass().getAnnotation(Table.class);
			List<Map<String, String>> params = params(entity, Operation.UPDATE);
			if (params != null && params.size() > 0) {
				return new SQL() {
					{
						UPDATE(table.name());
						for (Map<String, String> map : params) {
							SET(map.get("name") + "=" + map.get("value"));
						}
						WHERE("code=#{code}");
					}
				}.toString();
			}
		}
		return null;
	}

	public String selectByCode(Class<T> clazz, String code) {

		if (StringUtils.isNotBlank(code)) {
			Table table = clazz.getClass().getAnnotation(Table.class);
			return new SQL() {
				{
					SELECT("*");
					FROM(table.name());
					WHERE("code = #{code}");
				}
			}.toString();
		}
		return null;

	}

	public String select(T entity) {
		if (entity != null && entity.getClass().isAnnotationPresent(Table.class)) {
			Table table = entity.getClass().getAnnotation(Table.class);
			List<Map<String, String>> params = params(entity, Operation.UPDATE);
			if (params != null && params.size() > 0) {
				return new SQL() {
					{
						SELECT("*");
						FROM(table.name());
						for (Map<String, String> map : params) {
							WHERE(map.get("name") + "=" + map.get("value"));
						}
					}
				}.toString();
			}
		}
		return null;
	}

	public String deleteByCode(Class<T> clazz, String code) {
		if (StringUtils.isNotBlank(code)) {
			Table table = clazz.getClass().getAnnotation(Table.class);
			return new SQL() {
				{
					DELETE_FROM(table.name());
					WHERE("code = #{code}");
				}
			}.toString();
		}
		return null;
	}

	public String delete(T entity) {
		if (entity != null && entity.getClass().isAnnotationPresent(Table.class)) {
			Table table = entity.getClass().getAnnotation(Table.class);
			List<Map<String, String>> params = params(entity, Operation.UPDATE);
			if (params != null && params.size() > 0) {
				return new SQL() {
					{
						DELETE_FROM(table.name());
						for (Map<String, String> map : params) {
							WHERE(map.get("name") + "=" + map.get("value"));
						}
					}
				}.toString();
			}
		}
		return null;
	}

	private List<Map<String, String>> params(T entity, Operation oper) {
		if (entity != null) {
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Field[] fields = entity.getClass().getFields();
			for (Field field : fields) {
				boolean flag = field.isAnnotationPresent(Column.class);
				if (flag) {
					Column col = field.getAnnotation(Column.class);
					Object value = ObjectUtil.getFieldValueByName(field.getName(), entity);
					if (value != null) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("name", col.name());
						map.put("value", "#{" + field.getName() + "}");
						if (oper == Operation.INSERT && col.insertable() == true) {
							list.add(map);
						} else if (oper == Operation.UPDATE && col.updateable() == true) {
							list.add(map);
						} else if (oper == Operation.SELECT) {
							list.add(map);
						} else if (oper == Operation.DELETE) {
							list.add(map);
						}
					}
				}
			}
			return list;
		}
		return null;
	}
}
