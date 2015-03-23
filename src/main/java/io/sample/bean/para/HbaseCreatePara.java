package io.sample.bean.para;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class HbaseCreatePara {

    @NotNull(message = "tableName")
    @Size(min = 1, max = 100 ,message = "tableName")
	private String tableName;
    @NotNull(message = "columnFamily")
    @Size(min = 1, max = 100 ,message = "columnFamily")
	private String columnFamily;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnFamily() {
		return columnFamily;
	}

	public void setColumnFamily(String columnFamily) {
		this.columnFamily = columnFamily;
	}


}