package ${basepackage}.pageModel;

import java.util.List;

/**
 * easyui的datagrid模型
 */
public class DataGrid<T> implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Long total;// 总记录数
	private List<T> rows;// 每行记录
	private List<T> footer;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public List<T> getFooter() {
		return footer;
	}

	public void setFooter(List<T> footer) {
		this.footer = footer;
	}


}
