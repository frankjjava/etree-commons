package com.etree.commons.dao.dto;

import java.sql.Types;
import java.util.Set;

public class TableMetaDataDto {

	public enum DIALECT { ORACLE, MS_SQL, MYSQL, POSTGRES };
	
	private String databaseName;
	private String tableName;
	private Set<ColumnMetaDataDto> columns;
	private DIALECT dialect;
	
	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Set<ColumnMetaDataDto> getColumns() {
		return columns;
	}

	public void setColumns(Set<ColumnMetaDataDto> columns) {
		this.columns = columns;
	}
	
	public DIALECT getDialect() {
		return dialect;
	}

	public void setDialect(DIALECT dialect) {
		this.dialect = dialect;
	}

	public static final class ColumnMetaDataDto {

		public enum CONSTRAINTS {PRIMARY_KEY};
		public enum TYPE {
			VARCHAR(Types.VARCHAR),
			BYTE(Types.TINYINT),
			SHORT(Types.SMALLINT),
			INT(Types.INTEGER),
			LONG(Types.BIGINT),
			FLOAT(Types.REAL),
			DOUBLE(Types.DOUBLE),
			DATE(Types.DATE),
			TIMESTAMP(Types.TIMESTAMP);
			
			int value;
			private TYPE(int value) {
				this.value = value;
			}
		};

		private String columnName;
		private TYPE type;
		private int length;
		private Set<CONSTRAINTS> constraints;

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
		
		public void setType(TYPE type) {
			this.type = type;
		}

		public String getTypeName() {
			if (type == TYPE.VARCHAR) {
				return new StringBuilder("varchar(").append(length).append(")").toString();
			} else if (type.equals(TYPE.BYTE)) {
				return "TINYINT";
			} else if (type.equals(TYPE.SHORT)) {
				return "SMALLINT";
			} else if (type.equals(TYPE.INT)) {
				return "INTEGER";
			} else if (type.equals(TYPE.LONG)) {
				return "BIGINT";
			} else if (type.equals(TYPE.FLOAT)) {
				return "REAL";
			} else if (type.equals(TYPE.DOUBLE)) {
				return "DOUBLE";
			} else if (type.equals(TYPE.DATE)) {
				return "DATE";
			} else if (type.equals(TYPE.TIMESTAMP)) {
				return "TIMESTAMP";
			}
			return null;
		}
		
		public int getLength() {
			return length;
		}

		public void setLength(int length) {
			this.length = length;
		}

		public Set<CONSTRAINTS> getConstraints() {
			return constraints;
		}

		public void setConstraints(Set<CONSTRAINTS> constraints) {
			this.constraints = constraints;
		}
	}
}

