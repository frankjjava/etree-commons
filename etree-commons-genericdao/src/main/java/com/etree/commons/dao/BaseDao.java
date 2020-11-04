/**
* Copyright © 2020 eTree Technologies Pvt. Ltd.
*
* @author  Franklin Joshua
* @version 1.0
* @since   2016-01-15 
*/
package com.etree.commons.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;

import com.etree.commons.dao.dto.TableMetaDataDto;


public interface BaseDao extends BaseCommonDao {
	
	public boolean isTableExists(String tableName);
	
	public boolean createTable(TableMetaDataDto tableMetaDataDto);
	
	/**
	 * Fetch next seq value.
	 *
	 * @param seqName the seq name
	 * @return the long
	 */
	public abstract long fetchNextSeqValue(String seqName);

	/**
	 * Insert a row into the table using spring SimpleJdbcInsert object
	 * 
	 * @param table target table name
	 * @param params parameters that matches the table fields
	 * @return number of rows updated
	 */
	public abstract int executeInsert(String table, Map<String, Object> params);

	public KeyHolder executeInsertAndReturnKeyHolder(String table, Map<String, Object> params, String generatedKey);
	
	public KeyHolder executeInsertAndReturnKeyHolder(String table, Map<String, Object> params, Set<String> generatedKeys);

	public Object executeInsertAndReturnKey(String table, Map<String, Object> params, String generatedKey);
	
	public Object executeInsertAndReturnKey(String table, Map<String, Object> params, Set<String> generatedKeys);

	/**
	 * Execute batch insert.
	 *
	 * @param table the table
	 * @param params the params
	 * @return the int[]
	 */
	public abstract int[] executeBatchInsert(String table, Map<String, Object>[] params);

	/**
	 * Execute batch insert.
	 *
	 * @param schema the schema
	 * @param table the table
	 * @param params the params
	 * @return the int[]
	 */
	public abstract int[] executeBatchInsert(String schema, String table, Map<String, Object>[] params);

	/**
	 * Execute batch insert or update.
	 *
	 * @param query the query
	 * @return the int[]
	 */
	public abstract int[] executeBatchUpsert(List<String> lstBatchSql);

	/**
	 * Execute query.
	 *
	 * @param <T> the generic type
	 * @param query the query
	 * @param cls the cls
	 * @return the t
	 */
	public abstract <T> T executeQuery(String query, Class<T> cls);

	/**
	 * Execute for list.
	 *
	 * @param <T> the generic type
	 * @param query the query
	 * @param params the params
	 * @param rowMapper the row mapper
	 * @return the list
	 */
	public abstract <T> List<T> executeQuery(String query, RowMapper<T> rowMapper);

	/**
	 * Execute query.
	 *
	 * @param <T> the generic type
	 * @param query the query
	 * @param params the params
	 * @param rowMapper the row mapper
	 * @return the list
	 */
	public abstract <T> List<T> executeQuery(String query, Object[] params, RowMapper<T> rowMapper);

	/**
	 * Fetch a row from the table using spring jdbcTemplate and returns the target object
	 * 
	 * @param <T>
	 * @param query
	 * @param params parameters that matches the table fields
	 * @param rowMapper
	 * @return number of rows updated
	 */
	public abstract <T> T executeForObject(String query, Object[] params, RowMapper<T> rowMapper);

	/**
	 * Execute query.
	 *
	 * @param <T> the generic type
	 * @param query the query
	 * @param rsExtractor the rs extractor
	 * @return the list
	 */
	public abstract <T> T executeQuery(String query, ResultSetExtractor<T> rsExtractor);

	/**
	 * Execute update.
	 *
	 * @param query the query
	 * @return the int
	 */
	public abstract int executeUpdate(String sql);

	/**
	 * Update a row from the table using spring jdbcTemplate and returns the number of rows updated
	 * 
	 * @param query
	 * @param params parameters that matches the table fields
	 * @return number of rows updated
	 */
	public abstract int executeUpdate(String query, Object[] params);

	/**
	 * Update a row from the table using spring jdbcTemplate and returns the number of rows updated
	 * 
	 * @param query
	 * @param pss
	 * @return number of rows updated
	 */
	public abstract int executeUpdate(String query, PreparedStatementSetter pss);

	/**
	 * Execute update.
	 *
	 * @param sql the sql
	 * @param params the params
	 * @return the int
	 */
	public abstract int executeUpdateForNamedSql(String sql, SqlParameterSource params);

	/**
	 * Execute batch update.
	 *
	 * @param query the query
	 * @param objArr the obj arr
	 * @return the int[]
	 */
	public abstract int[] executeBatchUpdate(String query, List<Object[]> objArr);

	/**
	 * Execute batch update.
	 *
	 * @param sql the sql
	 * @param params the params
	 * @return the int[]
	 */
	public abstract int[] executeNamedBatchUpdate(String sql, SqlParameterSource[] params);

	/**
	 * Execute update.
	 *
	 * @param sql the sql
	 * @param params the params
	 * @return the int
	 */
	public abstract int executeNamedUpdate(String sql, Map<String, ?> paramMap);

	/**
	 * Execute named query.
	 *
	 * @param <T> the generic type
	 * @param sql the sql
	 * @param paramMap the param map
	 * @param rse the rse
	 * @return the t
	 */
	public abstract <T> T executeNamedQuery(String sql, Map<String, Object> paramMap, ResultSetExtractor<T> rse);

	public abstract <T> T executeNamedQuery(String sql, Map<String, Object> paramMap, RowMapper<T> rowMapper);

	public abstract <T> Object executeForObject(String query, Object[] params, int[] types, Class<T> type);

	public abstract <T> Object executeNamedQueryForObj(String sql, Map<String, ?> paramMap, Class<T> requiredType);

	public abstract int executeUpdate(String query, Object[] params, int[] types);

	void setJdbcTemplate(JdbcTemplate jdbcTemplate);
}
