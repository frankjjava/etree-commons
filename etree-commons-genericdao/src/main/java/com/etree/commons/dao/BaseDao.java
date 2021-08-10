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

public interface BaseDao extends DbCommonDao {
	public boolean isTableExists(String tableName);

	public boolean createTable(TableMetaDataDto tableMetaDataDto);

	public abstract long fetchNextSeqValue(String seqName);

	public abstract int executeInsert(String table, Map<String, Object> params);

	public KeyHolder executeInsertAndReturnKeyHolder(String table, Map<String, Object> params, String generatedKey);

	public KeyHolder executeInsertAndReturnKeyHolder(String table, Map<String, Object> params,
			Set<String> generatedKeys);

	public Object executeInsertAndReturnKey(String table, Map<String, Object> params, String generatedKey);

	public Object executeInsertAndReturnKey(String table, Map<String, Object> params, Set<String> generatedKeys);

	public abstract int[] executeBatchInsert(String table, Map<String, Object>[] params);

	public abstract int[] executeBatchInsert(String schema, String table, Map<String, Object>[] params);

	public abstract int[] executeBatchUpsert(List<String> lstBatchSql);

	public abstract <T> T executeQuery(String query, Class<T> cls);

	public abstract <T> List<T> executeQuery(String query, RowMapper<T> rowMapper);

	public abstract <T> List<T> executeQuery(String query, Object[] params, RowMapper<T> rowMapper);

	public abstract <T> T executeForObject(String query, Object[] params, RowMapper<T> rowMapper);

	public abstract <T> T executeQuery(String query, ResultSetExtractor<T> rsExtractor);

	public abstract int executeUpdate(String sql);

	public abstract int executeUpdate(String query, Object[] params);

	public abstract int executeUpdate(String query, PreparedStatementSetter pss);

	public abstract int executeUpdateForNamedSql(String sql, SqlParameterSource params);

	public abstract int[] executeBatchUpdate(String query, List<Object[]> objArr);

	public abstract int[] executeNamedBatchUpdate(String sql, SqlParameterSource[] params);

	public abstract int executeNamedUpdate(String sql, Map<String, ?> paramMap);

	public abstract <T> T executeNamedQuery(String sql, Map<String, Object> paramMap, ResultSetExtractor<T> rse);

	public abstract <T> T executeNamedQuery(String sql, Map<String, Object> paramMap, RowMapper<T> rowMapper);

	public abstract <T> Object executeForObject(String query, Object[] params, int[] types, Class<T> type);

	public abstract <T> Object executeNamedQueryForObj(String sql, Map<String, ?> paramMap, Class<T> requiredType);

//	public abstract int executeUpdate(String query, Object[] params, int[] types);
	void setJdbcTemplate(JdbcTemplate jdbcTemplate);
}
