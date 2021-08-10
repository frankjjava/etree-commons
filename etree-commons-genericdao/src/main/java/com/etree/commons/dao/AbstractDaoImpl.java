/**
* Copyright (c) eTree Technologies
*
* @author  Franklin Abel
* @version 1.0
* @since   2020-06-08 
*
* This file is part of the etree-commons.
* 
*  The etree-commons is free library: you can redistribute it and/or modify
*  it under the terms of the GNU General Public License as published by
*  the Free Software Foundation, version 3 of the License.
*
*  The etree-commons is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*  GNU General Public License for more details.
*
*  A copy of the GNU General Public License is made available as 'License.md' file, 
*  along with etree-commons project.  If not, see <https://www.gnu.org/licenses/>.
*
*/
package com.etree.commons.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import com.etree.commons.dao.dto.TableMetaDataDto;
import com.etree.commons.dao.dto.TableMetaDataDto.ColumnMetaDataDto;
import com.etree.commons.dao.dto.TableMetaDataDto.ColumnMetaDataDto.CONSTRAINTS;
import com.etree.commons.dao.exception.DaoException;

public abstract class AbstractDaoImpl implements BaseDao {
	private Logger LOGGER = LoggerFactory.getLogger(AbstractDaoImpl.class);
	@Autowired
	private DataSource dataSource;
	protected JdbcTemplate jdbcTemplate;
	protected NamedParameterJdbcTemplate namedJdbcTemplate;

	protected enum AND_OR {
		AND, OR
	};

	public static final String WHERE = " where ";
	public static final String IS_NULL = " IS NULL ";
	public static final String IS_NOT_NULL = " IS NOT NULL ";
	private List<Class<?>> simpleTypes = new ArrayList<>();

	protected AbstractDaoImpl() {
		simpleTypes.add(String.class);
		simpleTypes.add(short.class);
		simpleTypes.add(Short.class);
		simpleTypes.add(int.class);
		simpleTypes.add(Integer.class);
		simpleTypes.add(long.class);
		simpleTypes.add(Long.class);
		simpleTypes.add(double.class);
		simpleTypes.add(Double.class);
		simpleTypes.add(java.sql.Date.class);
		simpleTypes.add(java.sql.Timestamp.class);
	}

	@PostConstruct
	public void init() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		setJdbcTemplate(jdbcTemplate);
	}

	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	@Override
	public boolean isTableExists(String tableName) {
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			ResultSet resultSet = databaseMetaData.getTables(null, null, tableName, null);
			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean createTable(TableMetaDataDto tableMetaDataDto) {
		StringBuilder sql = null;
		for (ColumnMetaDataDto columnMetaDataDto : tableMetaDataDto.getColumns()) {
			if (sql == null) {
				sql = new StringBuilder("CREATE TABLE").append(tableMetaDataDto.getTableName());
			} else {
				sql.append(",");
			}
			sql.append(columnMetaDataDto.getColumnName()).append(" ").append(columnMetaDataDto.getTypeName());
			Set<CONSTRAINTS> constraints = columnMetaDataDto.getConstraints();
			if (constraints != null && !constraints.isEmpty()) {
				for (CONSTRAINTS constraint : constraints) {
					if (constraint == CONSTRAINTS.PRIMARY_KEY) {
						sql.append(" PRIMARY KEY");
					}
				}
			}
		}
		sql.append(";");
		jdbcTemplate.execute(sql.toString());
		return true;
	}

	@Override
	public long fetchNextSeqValue(String seqName) {
		StringBuilder sql = new StringBuilder("SELECT ").append(seqName).append(".NEXTVAL FROM DUAL");
		return jdbcTemplate.queryForObject(sql.toString(), Long.class);
	}

	@Override
	public int executeInsert(String table, Map<String, Object> params) {
		SimpleJdbcInsert jdbcInsert = createSimpleJdbcInsert(null, table, params.keySet(), null);
		int count = jdbcInsert.execute(params);
		return count;
	}

	@Override
	public KeyHolder executeInsertAndReturnKeyHolder(String table, Map<String, Object> params, String generatedKey) {
		Set<String> generatedKeys = new HashSet<>();
		generatedKeys.add(generatedKey);
		SimpleJdbcInsert jdbcInsert = createSimpleJdbcInsert(null, table, params.keySet(), generatedKeys);
		KeyHolder keyHolder = jdbcInsert.executeAndReturnKeyHolder(params);
		return keyHolder;
	}

	@Override
	public KeyHolder executeInsertAndReturnKeyHolder(String table, Map<String, Object> params,
			Set<String> generatedKeys) {
		SimpleJdbcInsert jdbcInsert = createSimpleJdbcInsert(null, table, params.keySet(), generatedKeys);
		KeyHolder keyHolder = jdbcInsert.executeAndReturnKeyHolder(params);
		return keyHolder;
	}

	@Override
	public Object executeInsertAndReturnKey(String table, Map<String, Object> params, String generatedKey) {
		Set<String> generatedKeys = new HashSet<>();
		generatedKeys.add(generatedKey);
		SimpleJdbcInsert jdbcInsert = createSimpleJdbcInsert(null, table, params.keySet(), generatedKeys);
		Object object = jdbcInsert.executeAndReturnKey(params);
		return object;
	}

	@Override
	public Object executeInsertAndReturnKey(String table, Map<String, Object> params, Set<String> generatedKeys) {
		SimpleJdbcInsert jdbcInsert = createSimpleJdbcInsert(null, table, params.keySet(), generatedKeys);
		Object object = jdbcInsert.executeAndReturnKey(params);
		return object;
	}

	@Override
	public int[] executeBatchInsert(String table, Map<String, Object>[] params) {
		SimpleJdbcInsert jdbcInsert = createSimpleJdbcInsert(null, table, params, null);
		int[] count = jdbcInsert.executeBatch(params);
		return count;
	}

	@Override
	public int[] executeBatchInsert(String schema, String table, Map<String, Object>[] params) {
		SimpleJdbcInsert jdbcInsert = createSimpleJdbcInsert(null, table, params, null);
		int[] count = jdbcInsert.executeBatch(params);
		return count;
	}

	@Override
	public int[] executeBatchUpsert(List<String> lstBatchSql) {
		LOGGER.debug(lstBatchSql.toString());
		long startTime = System.nanoTime();
		String[] arrSql = lstBatchSql.toArray(new String[lstBatchSql.size()]);
		int[] arrCount = jdbcTemplate.batchUpdate(arrSql);
		LOGGER.debug("Executed 'executeBatchInsertOrUpdate(..)' in (millis) = "
				+ (System.nanoTime() - startTime) / NANOS_IN_ONE_MILLI);
		return arrCount;
	}

	@Override
	public <T> T executeQuery(String query, Class<T> cls) {
		return jdbcTemplate.queryForObject(query, cls);
	}

	@Override
	public <T> List<T> executeQuery(String query, RowMapper<T> rowMapper) {
		return jdbcTemplate.query(query, rowMapper);
	}

	@Override
	public <T> List<T> executeQuery(String query, Object[] params, RowMapper<T> rowMapper) {
		return jdbcTemplate.query(query, params, rowMapper);
	}

	@Override
	public <T> T executeForObject(String query, Object[] params, RowMapper<T> rowMapper) {
		return jdbcTemplate.queryForObject(query, params, rowMapper);
	}

	@Override
	public <T> T executeQuery(String query, ResultSetExtractor<T> rsExtractor) {
		return jdbcTemplate.query(query, rsExtractor);
	}

	@Override
	public int executeUpdate(String sql) {
		return jdbcTemplate.update(sql);
	}

	@Override
	public int executeUpdate(String query, Object[] params) {
		return jdbcTemplate.update(query, params);
	}

	@Override
	public int executeUpdate(String query, PreparedStatementSetter pss) {
		return jdbcTemplate.update(query, pss);
	}

	/*
	 * @Override public int executeUpdate(String query, Object[] params, int[]
	 * types) { return jdbcTemplate.update(query, params, types); }
	 */
	@Override
	public int executeUpdateForNamedSql(String sql, SqlParameterSource params) {
		return jdbcTemplate.update(sql, params);
	}

	@Override
	public int[] executeBatchUpdate(String query, List<Object[]> objArr) {
		return jdbcTemplate.batchUpdate(query, objArr);
	}

	@Override
	public int[] executeNamedBatchUpdate(String sql, SqlParameterSource[] params) {
		return namedJdbcTemplate.batchUpdate(sql, params);
	}

	@Override
	public int executeNamedUpdate(String sql, Map<String, ?> paramMap) {
		return namedJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public <T> T executeNamedQuery(String sql, Map<String, Object> paramMap, ResultSetExtractor<T> rse) {
		sql = sanitizeWhereClauseForNullCriteria(sql, paramMap);
		return namedJdbcTemplate.query(sql, paramMap, rse);
	}

	@Override
	public <T> T executeNamedQuery(String sql, Map<String, Object> paramMap, RowMapper<T> rowMapper) {
		return namedJdbcTemplate.queryForObject(sql, paramMap, rowMapper);
	}

	@Override
	public <T> Object executeForObject(String query, Object[] params, int[] types, Class<T> type) {
		return jdbcTemplate.queryForObject(query, params, types, type);
	}

	@Override
	public <T> Object executeNamedQueryForObj(String sql, Map<String, ?> paramMap, Class<T> requiredType) {
		return namedJdbcTemplate.queryForObject(sql, paramMap, requiredType);
	}

	protected StringBuilder buildWhereClause(StringBuilder whereClause, String colName, String value,
			AND_OR logicalOperator, boolean isUseNamedCriteria) {
		return buildWhereClause(whereClause, colName, value, logicalOperator, "=", isUseNamedCriteria);
	}

	protected StringBuilder buildWhereClause(StringBuilder whereClause, String colName, String value,
			AND_OR logicalOperator, String relationalOperator, boolean isUseNamedCriteria) {
		return buildWhereClause(whereClause, colName, value, logicalOperator, relationalOperator, isUseNamedCriteria,
				null);
	}

	protected StringBuilder buildWhereClause(StringBuilder whereClause, String colName, String value,
			AND_OR logicalOperator, String relationalOperator, boolean isUseNamedCriteria,
			List<String> ignoreCriteriaFields) {
		boolean isIgnoreFieldsContainsIgnoreCase = containsIgnoreCase(ignoreCriteriaFields, colName);
		if (whereClause == null) {
			whereClause = new StringBuilder(WHERE);
		} else if (!whereClause.toString().equals(WHERE)) {
			if (logicalOperator != null && (ignoreCriteriaFields == null || !isIgnoreFieldsContainsIgnoreCase)) {
				whereClause.append(" ").append(logicalOperator.name()).append(" ");
			}
		}
		if (value != null && !isIgnoreFieldsContainsIgnoreCase) {
			value = value.trim();
			if (value.equalsIgnoreCase(IS_NULL.trim())) {
				whereClause.append(colName).append(IS_NULL);
			} else if (value.equalsIgnoreCase("*") || IS_NOT_NULL.trim().equals(value)) {
				whereClause.append(colName).append(IS_NOT_NULL);
			} else {
				whereClause.append(colName).append(relationalOperator).append("'").append(value).append("'");
			}
		} else if (isUseNamedCriteria) {
			whereClause.append(colName).append(relationalOperator).append(":").append(colName);
		} else if (ignoreCriteriaFields == null || !isIgnoreFieldsContainsIgnoreCase) {
			whereClause.append(colName).append(IS_NULL);
		}
		return whereClause;
	}

	protected StringBuilder buildWhereBetweenClause(StringBuilder whereClause, AND_OR logicalOperator,
			String columnName, String beginValue, String endValue) {
		if (beginValue == null || endValue == null) {
			throw new DaoException("", "Invalid Begin-value and / or End-value!");
		}
		if (whereClause == null) {
			whereClause = new StringBuilder(WHERE);
		} else if (!whereClause.toString().equals(WHERE)) {
			if (logicalOperator != null) {
				whereClause.append(" ").append(logicalOperator.name()).append(" ");
			}
		}
		whereClause.append(columnName).append(" between '").append(beginValue).append("' ").append(AND_OR.AND)
				.append(" '").append(endValue).append("'");
		return whereClause;
	}

	protected StringBuilder buildSetClause(StringBuilder setClause, String colName) {
		return buildSetClause(setClause, colName, null);
	}

	protected StringBuilder buildSetClause(StringBuilder setClause, String colName, String value) {
		if (colName != null) {
			if (setClause == null) {
				setClause = new StringBuilder(" set ");
			} else {
				setClause.append(", ");
			}
			if (value != null) {
				setClause.append(colName).append("='").append(value).append("'");
			} else {
				setClause.append(colName).append("=:").append(colName);
			}
		}
		return setClause;
	}

	protected String buildInClause(List<String> lstValues) {
		String csvSqlString = buildCsvSqlString(lstValues);
		if (csvSqlString == null || csvSqlString.isEmpty()) {
			return null;
		}
		csvSqlString = new StringBuilder(" in (").append(csvSqlString).append(")").toString();
		return csvSqlString;
	}

	protected String buildCsvSqlString(List<String> lstValues) {
		StringBuilder values = null;
		for (Object value : lstValues) {
			if (values == null) {
				values = new StringBuilder();
			} else {
				values.append(",");
			}
			values.append("'").append(value).append("'");
		}
		return values.toString();
	}

	protected Map<String, Object> createSimpleTypesParamsMap(Object obj) {
		Map<String, Object> paramsMap = null;
		Class<? extends Object> cls = obj.getClass();
		for (Field fld : cls.getDeclaredFields()) {
			Object val = retrieveValue(fld, obj);
			if (paramsMap == null) {
				paramsMap = new HashMap<>();
			}
			paramsMap.put(fld.getName(), val);
		}
		return paramsMap;
	}

	protected static java.sql.Date getCurrentDate() {
		return new java.sql.Date(System.currentTimeMillis());
	}

	protected static java.sql.Timestamp getCurrentTimestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}

	protected ResultSetExtractor<Boolean> createIsExistsResultSetExtractor() {
		return new ResultSetExtractor<Boolean>() {
			@Override
			public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
				try {
					return rs.next();
				} catch (SQLException e) {
					LOGGER.warn(e.toString());
					return false;
				}
			}
		};
	}

	protected ResultSetExtractor<Object> createResultSetExtractorForObject() {
		return new ResultSetExtractor<Object>() {
			@Override
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				Object object = null;
				try {
					while (rs.next()) {
						object = rs.getObject(1);
					}
					return object;
				} catch (DataAccessException e) {
					LOGGER.warn(e.toString());
					return null;
				}
			}
		};
	}

	private Object retrieveValue(Field fld, Object obj) {
		Class<?> type = fld.getType();
		if (!simpleTypes.contains(type)) {
			return null;
		}
		Object value;
		try {
			value = fld.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new DaoException(e);
		}
		return value;
	}

	private String sanitizeWhereClauseForNullCriteria(String sql, Map<String, Object> paramasMap) {
		String tempSql = sql.toLowerCase();
		if (!tempSql.contains(" where ")) {
			return sql;
		}
		int idx = tempSql.indexOf(" where ") + 7;
		String selectClause = sql.substring(0, idx);
		String whereClause = sql.substring(idx).trim();
		if (!whereClause.contains(":")) {
			return sql;
		}
		idx = whereClause.indexOf(":");
		while (idx >= 0) {
			String namedParam = null;
			if (whereClause.indexOf(" ", idx + 2) > 0) {
				namedParam = whereClause.substring(idx + 1, whereClause.indexOf(" ", idx + 2));
			} else {
				namedParam = whereClause.substring(idx + 1);
			}
			String leadSql = whereClause.substring(0, idx).trim();
			if (paramasMap.get(namedParam) == null) {
				if (leadSql.endsWith("=")) {
					leadSql = leadSql.substring(0, leadSql.length() - 2).concat(" is ");
				}
			}
			whereClause = leadSql.concat(whereClause.substring(idx));
			idx = whereClause.indexOf(":", idx + namedParam.length());
		}
		return selectClause.concat(whereClause);
	}

	private SimpleJdbcInsert createSimpleJdbcInsert(String schema, String table, Set<String> params,
			Set<String> generatedKeys) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(table);
		if (schema != null) {
			jdbcInsert = jdbcInsert.withSchemaName(schema);
		}
		if (generatedKeys != null && !generatedKeys.isEmpty()) {
			jdbcInsert = jdbcInsert.usingGeneratedKeyColumns(generatedKeys.toArray(new String[generatedKeys.size()]));
		}
		String[] columnNames = params.toArray(new String[params.size()]);
		jdbcInsert = jdbcInsert.usingColumns(columnNames);
		return jdbcInsert;
	}

	private SimpleJdbcInsert createSimpleJdbcInsert(String schema, String table, Map<String, Object>[] arrParams,
			Set<String> generatedKeys) {
		Set<String> columnNames = new HashSet<>();
		for (Map<String, Object> params : arrParams) {
			for (String columnName : params.keySet()) {
				if (!columnNames.contains(columnName)) {
					columnNames.add(columnName);
				}
			}
		}
		SimpleJdbcInsert jdbcInsert = createSimpleJdbcInsert(schema, table, columnNames, generatedKeys);
		return jdbcInsert;
	}

	private boolean containsIgnoreCase(List<String> values, String searchStr) {
		if (values == null && searchStr == null) {
			return true;
		}
		if (values == null || values.isEmpty() || searchStr == null) {
			return false;
		}
		for (String value : values) {
			if (value.equalsIgnoreCase(searchStr)) {
				return true;
			}
		}
		return false;
	}
}