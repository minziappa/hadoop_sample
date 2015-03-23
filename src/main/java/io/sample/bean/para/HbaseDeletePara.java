package io.sample.bean.para;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class HbaseDeletePara {

    @NotNull(message = "tableName")
    @Size(min = 1, max = 100 ,message = "tableName")
	private String tableName;

	private String rowKey;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

}