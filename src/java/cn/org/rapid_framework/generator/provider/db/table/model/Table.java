package cn.org.rapid_framework.generator.provider.db.table.model;


import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import cn.org.rapid_framework.generator.GeneratorProperties;
import cn.org.rapid_framework.generator.provider.db.table.TableFactory;
import cn.org.rapid_framework.generator.util.StringHelper;
/**
 * �������ɴ����Table����.��Ӧ���ݿ��table
 * @author badqiu
 * @email badqiu(a)gmail.com
 */
public class Table implements java.io.Serializable,Cloneable {

	String sqlName;
	String remarks;
	String className;
	String checkId;
	
	/** the name of the owner of the synonym if this table is a synonym */
	private String ownerSynonymName = null;
	/** real table name for oracle SYNONYM */
	private String tableSynonymName = null; 
	
	LinkedHashSet<Column> columns = new LinkedHashSet<Column>();
	List<Column> primaryKeyColumns = new ArrayList<Column>();
	
	
	public Table() {}
	
	public Table(Table t) {
		setSqlName(t.getSqlName());
		this.remarks = t.getRemarks();
		this.className = t.getClassName();
		this.ownerSynonymName = t.getOwnerSynonymName();
		this.columns = t.getColumns();
		this.primaryKeyColumns = t.getPrimaryKeyColumns();
		this.tableAlias = t.getTableAlias();
		this.exportedKeys = t.exportedKeys;
		this.importedKeys = t.importedKeys;
	}
	
	public LinkedHashSet<Column> getColumns() {
		return columns;
	}
	public void setColumns(LinkedHashSet<Column> columns) {
		this.columns = columns;
	}
	public String getOwnerSynonymName() {
		return ownerSynonymName;
	}
	public void setOwnerSynonymName(String ownerSynonymName) {
		this.ownerSynonymName = ownerSynonymName;
	}
	public String getTableSynonymName() {
		return tableSynonymName;
	}
	public void setTableSynonymName(String tableSynonymName) {
		this.tableSynonymName = tableSynonymName;
	}

	/** ʹ�� getPkColumns() �滻*/
	@Deprecated
	public List<Column> getPrimaryKeyColumns() {
		return primaryKeyColumns;
	}
	/** ʹ�� setPkColumns() �滻*/
	@Deprecated
	public void setPrimaryKeyColumns(List<Column> primaryKeyColumns) {
		this.primaryKeyColumns = primaryKeyColumns;
	}
	/** ���ݿ��б��ı�����,�������Ժܶ඼�Ǹ��ݴ��������� */
	public String getSqlName() {
		return sqlName;
	}
	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}

	public static String removeTableSqlNamePrefix(String sqlName) {
		String prefixs = GeneratorProperties.getProperty("tableRemovePrefixes", "");
		for(String prefix : prefixs.split(",")) {
			String removedPrefixSqlName = StringHelper.removePrefix(sqlName, prefix,true);
			if(!removedPrefixSqlName.equals(sqlName)) {
				return removedPrefixSqlName;
			}
		}
		return sqlName;
	}
	
	/** ���ݿ��б��ı���ע */
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public void addColumn(Column column) {
		columns.add(column);
	}
	
	public void setClassName(String customClassName) {
		this.className = customClassName;
	}
	/**
	 * ����sqlName�õ��������ƣ�ʾ��ֵ: UserInfo
	 * @return
	 */
	public String getClassName() {
	    if(StringHelper.isBlank(className)) {
	        String removedPrefixSqlName = removeTableSqlNamePrefix(sqlName);
	        return StringHelper.makeAllWordFirstLetterUpperCase(StringHelper.toUnderscoreName(removedPrefixSqlName));
	    }else {
	    	return className;
	    }
	}
	/** ���ݿ��б��ı������ȼ���:  getRemarks().isEmpty() ? getClassName() : getRemarks() */
	public String getTableAlias() {
		if(StringHelper.isNotBlank(tableAlias)) return tableAlias;
		return StringHelper.removeCrlf(StringHelper.defaultIfEmpty(getRemarks(), getClassName()));
	}
	public void setTableAlias(String v) {
		this.tableAlias = v;
	}
	
	/**
	 * �ȼ���getClassName().toLowerCase()
	 * @return
	 */
	public String getClassNameLowerCase() {
		return getClassName().toLowerCase();
	}
	/**
	 * �õ����»��߷ָ��������ƣ���className=UserInfo,��underscoreName=user_info
	 * @return
	 */
	public String getUnderscoreName() {
		return StringHelper.toUnderscoreName(getClassName()).toLowerCase();
	}
	/**
	 * ����ֵΪgetClassName()�ĵ�һ����ĸСд,��className=UserInfo,��ClassNameFirstLower=userInfo
	 * @return
	 */
	public String getClassNameFirstLower() {
		return StringHelper.uncapitalize(getClassName());
	}
	
	/**
	 * ����getClassName()�������,���ڵõ�������,��className=UserInfo,��constantName=USER_INFO
	 * @return
	 */
	public String getConstantName() {
		return StringHelper.toUnderscoreName(getClassName()).toUpperCase();
	}
	
	/** ʹ�� getPkCount() �滻*/
	@Deprecated
	public boolean isSingleId() {
		return getPkCount() == 1 ? true : false;
	}
	
	/** ʹ�� getPkCount() �滻*/
	@Deprecated
	public boolean isCompositeId() {
		return getPkCount() > 1 ? true : false;
	}

	/** ʹ�� getPkCount() �滻*/
	@Deprecated
	public boolean isNotCompositeId() {
		return !isCompositeId();
	}
	
	/**
	 * �õ���������
	 * @return
	 */
	public int getPkCount() {
		int pkCount = 0;
		for(Column c : columns){
			if(c.isPk()) {
				pkCount ++;
			}
		}
		return pkCount;
	}
	/**
	 * use getPkColumns()
	 * @deprecated 
	 */
	public List getCompositeIdColumns() {
		return getPkColumns();
	}
	
	/**
	 * �õ���������ȫ��column
	 * @return
	 */	
	public List<Column> getPkColumns() {
		List results = new ArrayList();
		for(Column c : getColumns()) {
			if(c.isPk())
				results.add(c);
		}
		return results;
	}
	
	/**
	 * �õ�����������ȫ��column
	 * @return
	 */
	public List<Column> getNotPkColumns() {
		List results = new ArrayList();
		for(Column c : getColumns()) {
			if(!c.isPk())
				results.add(c);
		}
		return results;
	}
	/** �õ����������ȼ���getPkColumns().get(0)  */
	public Column getPkColumn() {
		if(!getPkColumns().isEmpty()) {
			return getPkColumns().get(0);
		}
		return null;
	}
	
	/**ʹ�� getPkColumn()�滻 */
	@Deprecated
	public Column getIdColumn() {
		return getPkColumn();
	}
	
	public List<Column> getEnumColumns() {
        List results = new ArrayList();
        for(Column c : getColumns()) {
            if(!c.isEnumColumn())
                results.add(c);
        }
        return results;	    
	}
	
	public Column getColumnByName(String name) {
	    Column c = getColumnBySqlName(name);
	    if(c == null) {
	    	c = getColumnBySqlName(StringHelper.toUnderscoreName(name));
	    }
	    return c;
	}
	
	public Column getColumnBySqlName(String sqlName) {
	    for(Column c : getColumns()) {
	        if(c.getSqlName().equalsIgnoreCase(sqlName)) {
	            return c;
	        }
	    }
	    return null;
	}
	
   public Column getRequiredColumnBySqlName(String sqlName) {
       if(getColumnBySqlName(sqlName) == null) {
           throw new IllegalArgumentException("not found column with sqlName:"+sqlName+" on table:"+getSqlName());
       }
       return getColumnBySqlName(sqlName);
    }
	
	/**
	 * ���Թ��˵�ĳЩ�ؼ��ֵ���,�ؼ��ֲ����ִ�Сд,�Զ��ŷָ�
	 * @param ignoreKeywords
	 * @return
	 */
	public List<Column> getIgnoreKeywordsColumns(String ignoreKeywords) {
		List results = new ArrayList();
		for(Column c : getColumns()) {
			String sqlname = c.getSqlName().toLowerCase();
			if(StringHelper.contains(sqlname,ignoreKeywords.split(","))) {
				continue;
			}
			results.add(c);
		}
		return results;
	}
	
	/**
	 * This method was created in VisualAge.
	 */
	public void initImportedKeys(DatabaseMetaData dbmd) throws java.sql.SQLException {
		
			   // get imported keys a
	
			   ResultSet fkeys = dbmd.getImportedKeys(catalog,schema,this.sqlName);

			   while ( fkeys.next()) {
				 String pktable = fkeys.getString(PKTABLE_NAME);
				 String pkcol   = fkeys.getString(PKCOLUMN_NAME);
				 String fktable = fkeys.getString(FKTABLE_NAME);
				 String fkcol   = fkeys.getString(FKCOLUMN_NAME);
				 String seq     = fkeys.getString(KEY_SEQ);
				 Integer iseq   = new Integer(seq);
				 getImportedKeys().addForeignKey(pktable,pkcol,fkcol,iseq);
			   }
			   fkeys.close();
	}
	
	/**
	 * This method was created in VisualAge.
	 */
	public void initExportedKeys(DatabaseMetaData dbmd) throws java.sql.SQLException {
			   // get Exported keys
	
			   ResultSet fkeys = dbmd.getExportedKeys(catalog,schema,this.sqlName);
			  
			   while ( fkeys.next()) {
				 String pktable = fkeys.getString(PKTABLE_NAME);
				 String pkcol   = fkeys.getString(PKCOLUMN_NAME);
				 String fktable = fkeys.getString(FKTABLE_NAME);
				 String fkcol   = fkeys.getString(FKCOLUMN_NAME);
				 String seq     = fkeys.getString(KEY_SEQ);
				 Integer iseq   = new Integer(seq);
				 getExportedKeys().addForeignKey(fktable,fkcol,pkcol,iseq);
			   }
			   fkeys.close();
	}
	
	/**
	 * @return Returns the exportedKeys.
	 */
	public ForeignKeys getExportedKeys() {
		if (exportedKeys == null) {
			exportedKeys = new ForeignKeys(this);
		}
		return exportedKeys;
	}
	/**
	 * @return Returns the importedKeys.
	 */
	public ForeignKeys getImportedKeys() {
		if (importedKeys == null) {
			importedKeys = new ForeignKeys(this);
		}
		return importedKeys;
	}
	
	public String toString() {
		return "Database Table:"+getSqlName()+" to ClassName:"+getClassName();
	}
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			//ignore
			return null;
		}
	}
	
	String catalog = TableFactory.getInstance().getCatalog();
	String schema = TableFactory.getInstance().getSchema();
	
	private String tableAlias;
	private ForeignKeys exportedKeys;
	private ForeignKeys importedKeys;
	
	public    static final String PKTABLE_NAME  = "PKTABLE_NAME";
	public    static final String PKCOLUMN_NAME = "PKCOLUMN_NAME";
	public    static final String FKTABLE_NAME  = "FKTABLE_NAME";
	public    static final String FKCOLUMN_NAME = "FKCOLUMN_NAME";
	public    static final String KEY_SEQ       = "KEY_SEQ";

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
}