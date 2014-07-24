package cn.org.rapid_framework.generator.provider.db.sql.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import cn.org.rapid_framework.generator.provider.db.sql.SqlFactory;
import cn.org.rapid_framework.generator.provider.db.table.model.Column;
import cn.org.rapid_framework.generator.provider.db.table.model.Table;
import cn.org.rapid_framework.generator.util.StringHelper;
import cn.org.rapid_framework.generator.util.sqlparse.SqlParseHelper;
import cn.org.rapid_framework.generator.util.sqlparse.SqlTypeChecker;

/**
 * �������ɴ����Sql����.��Ӧ���ݿ��sql���
 * ʹ��SqlFactory.parseSql()���� <br />
 * 
 * SQL����ͬʱ֧�����¼����﷨
 * <pre>
 * hibernate: :username,
 * ibatis2: #username#,$usename$,
 * mybatis(or ibatis3): #{username},${username}
 * </pre>
 * SQL���󴴽�ʾ����
 * <pre>
 * Sql sql = new SqlFactory().parseSql("select * from user_info where username=#username# and password=#password#");
 * </pre>
 * 
 * @see SqlFactory
 * @author badqiu
 *
 */
public class Sql {
	public static String MULTIPLICITY_ONE = "one";
	public static String MULTIPLICITY_MANY = "many";
	public static String MULTIPLICITY_PAGING = "paging";
	
	String                      tableSqlName        = null;                             // �Ƿ���Ҫ
	String operation = null;
	String resultClass;
	String parameterClass;
	String remarks;
	
	String                      multiplicity        = MULTIPLICITY_ONE;                 /* many or one or paging */
    boolean                     paging              = false;                            // �Ƿ��ҳ��ѯ
    String                      sqlmap;                                                 /* for ibatis and ibatis3 */
	
	LinkedHashSet<Column> columns = new LinkedHashSet<Column>();
	LinkedHashSet<SqlParameter> params = new LinkedHashSet<SqlParameter>();
	
	String sourceSql; // source sql
	String executeSql;
	private String              paramType           = "primitive";                      /* primitive or object */
	
	public Sql() {
	}
	
	public Sql(Sql sql) {
        this.tableSqlName = sql.tableSqlName;

        this.operation = sql.operation;
        this.parameterClass = sql.parameterClass;
        this.resultClass = sql.resultClass;
        this.multiplicity = sql.multiplicity;

        this.columns = sql.columns;
        this.params = sql.params;
        this.sourceSql = sql.sourceSql;
        this.executeSql = sql.executeSql;
        this.remarks = sql.remarks;
    }
	
	public boolean isColumnsInSameTable() {
		// FIXME ��Ҫ���ӱ��������columns�Ƿ����,�ſ���Ϊselect ���� include���
		if(columns == null || columns.isEmpty()) return false;
		Column firstTable = columns.iterator().next();
		if(columns.size() == 1) return true;
		if(firstTable.getTable() == null) {
			return false;
		}
		
		String preTableName = firstTable.getTable().getSqlName();
		for(Column c :columns) {
			Table table = c.getTable();
			if(table == null) {
				return false;
			}
			if(preTableName.equalsIgnoreCase(table.getSqlName())) {
				continue;
			}else {
			    return false;
			}
		}
		return true;
	}

    /**
     * �õ�select��ѯ���ص�resultClass,����ͨ��setResultClass()�Զ��壬���û���Զ�����Ϊ���Զ�����<br />
     * resultClass����Ϊcom.company.User����ȫ·��
     * ʾ��:
     * <pre>
     * select count(*) from user, ����ֵΪ: Long
     * select * from user ����ֵΪ: User
     * select count(*) cnt, sum(age) sum_age ����ֵΪ: getOperation()+"Result";
     * </pre>
     * @return
     */
	public String getResultClass() {
		if(StringHelper.isNotBlank(resultClass)) return resultClass;
		if(columns.size() == 1) {
			return columns.iterator().next().getSimpleJavaType();
		}
		if(isColumnsInSameTable()) {
			return columns.iterator().next().getTable().getClassName();
		}else {
			if(operation == null) return null;
			return StringHelper.makeAllWordFirstLetterUpperCase(StringHelper.toUnderscoreName(operation))+System.getProperty("generator.sql.resultClass.suffix","Result");
		}
	}    
	
	public void setResultClass(String queryResultClass) {
		this.resultClass = queryResultClass;
	}

    /**
     * ����getResultClass()�������� <br />
     * ʾ��: <br />
     * ��getResultClass()=com.company.User,������User
     */	
	public String getResultClassName() {
		int lastIndexOf = getResultClass().lastIndexOf(".");
		return lastIndexOf >= 0 ? getResultClass().substring(lastIndexOf+1) : getResultClass();
	}

    /**
     * SQL��������ʱ���ڷ�װΪһ��ParameterObject��class<br />
     * <pre>
     * ����ͨ��setParameterClass()�Զ���
     * û���Զ�����:
     * �����select��ѯ,���� operation+"Query"
     * �����򷵻�operation+"Parameter"
     * <pre>
     */	
	public String getParameterClass() {
		if(StringHelper.isNotBlank(parameterClass)) return parameterClass;
		if(StringHelper.isBlank(operation)) return null;
		if(isSelectSql()) {
			return StringHelper.makeAllWordFirstLetterUpperCase(StringHelper.toUnderscoreName(operation))+"Query";
		}else {
			return StringHelper.makeAllWordFirstLetterUpperCase(StringHelper.toUnderscoreName(operation))+"Parameter";
		}
	}
	
	public void setParameterClass(String parameterClass) {
		this.parameterClass = parameterClass;
	}

    /**
     * ����getParameterClass()�������� <br />
     * ʾ��: <br />
     * ��getParameterClass()=com.company.UserQuery,������UserQuery
     */		
	public String getParameterClassName() {
		int lastIndexOf = getParameterClass().lastIndexOf(".");
		return lastIndexOf >= 0 ? getParameterClass().substring(lastIndexOf+1) : getParameterClass();
	}
	
	// TODO columnsSize���ڶ����Ҳ�����ͬһ�ű���,������һ��QueryResultClassName��,ͬһ�ű���ҲҪ���Ǵ�����
	public int getColumnsCount() {
		return columns.size();
	}
	public void addColumn(Column c) {
		columns.add(c);
	}

    /**
     * �õ���sql�������Ӧ�Ĳ�������,ģ���е�ʹ�÷�ʽΪ: public List ${operation}(),ʾ��ֵ: findByUsername
     * @return
     */
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getOperationFirstUpper() {
		return StringHelper.capitalize(getOperation());
	}

    /**
     * ���ڿ��Ʋ�ѯ���,�̶�ֵΪ:one,many
     * @return
     */
	public String getMultiplicity() {
		return multiplicity;
	}
	public void setMultiplicity(String multiplicity) {
		// TODO �Ƿ�Ҫ������֤����Ϊ one,many
		this.multiplicity = multiplicity;
	}

    /**
     * �õ�sqlect ��ѯ���ж���(column),�����insert,delete,update���,�򷵻�empty Set.<br />
     * ʾ��:
     * <pre>
     * SQL : select count(*) cnt, sum(age) sum_age from user_info
     * columns: cnt,sum_age
     * </pre>
     * @return
     */
	public LinkedHashSet<Column> getColumns() {
		return columns;
	}
	public void setColumns(LinkedHashSet<Column> columns) {
		this.columns = columns;
	}

    /**
     * �õ�SQL�Ĳ�������<br />
     * ʾ��:
     * <pre>
     * SQL : select * from user_info where username=:user and password=:pwd limit :offset,:limit
     * params: user,pwd,offset,limit
     * </pre>
     * @return
     */
	public LinkedHashSet<SqlParameter> getParams() {
		return params;
	}
	public void setParams(LinkedHashSet<SqlParameter> params) {
		this.params = params;
	}
	public SqlParameter getParam(String paramName) {
		for(SqlParameter p : getParams()) {
			if(p.getParamName().equals(paramName)) {
				return p;
			}
		}
		return null;
	}

    /**
     * �õ�SQLԭʼ���
     * @return
     */
	public String getSourceSql() {
		return sourceSql;
	}
	public void setSourceSql(String sourceSql) {
		this.sourceSql = sourceSql;
	}
	
	public String getSqlmap() {
		return sqlmap;
	}

	public void setSqlmap(String sqlmap) {
	    if(StringHelper.isNotBlank(sqlmap)) {
	        sqlmap = StringHelper.replace(sqlmap, "${cdata-start}", "<![CDATA[");
	        sqlmap = StringHelper.replace(sqlmap, "${cdata-end}", "]]>");
	    }
	    this.sqlmap = sqlmap;
	}
	
    public String getSqlmap(List<String> params) {
        if (params == null || params.size() == 0) {
            return sqlmap;
        }

        String result = sqlmap;

        if (params.size() == 1) {
            return StringHelper.replace(result, "${param1}", "value");
        } else {
            for (int i = 0; i < params.size(); i++) {
                result = StringHelper.replace(result, "${param" + (i + 1) + "}", params.get(i));
            }
        }

        return result;
    }
	
	public boolean isHasSqlMap() {
		return StringHelper.isNotBlank(sqlmap);
	}

	//	public String replaceParamsWith(String prefix,String suffix) {
//		String sql = sourceSql;
//		List<SqlParameter> sortedParams = new ArrayList(params);
//		Collections.sort(sortedParams,new Comparator<SqlParameter>() {
//			public int compare(SqlParameter o1, SqlParameter o2) {
//				return o2.paramName.length() - o1.paramName.length();
//			}
//		});
    // for(SqlParameter s : sortedParams){ //FIXME ����ֻʵ����:username�����滻
//			sql = StringHelper.replace(sql,":"+s.getParamName(),prefix+s.getParamName()+suffix);
//		}
//		return sql;
//	}
    /**
     * sourceSqlת��Ϊ�����ݿ�ʵ��ִ�е�SQL,
     * ʾ��:
     * <pre>
     * sourceSql: select * from user where username=:username and password=:password
     * executeSql: select * from user where username=? and password=?
     * </pre>
     * @return
     */
	public String getExecuteSql() {
		return executeSql;
	}
	
	public void setExecuteSql(String executeSql) {
		this.executeSql = executeSql;
	}
	
    public String getCountHql() {
        return toCountSqlForPaging(getHql());
    }
	   
	public String getCountSql() {
	    return toCountSqlForPaging(getSql());
	}

    public String getIbatisCountSql() {
        return toCountSqlForPaging(getIbatisSql());
    }
    
    public String getIbatis3CountSql() {
        return toCountSqlForPaging(getIbatis3Sql());
    }

    public String getSqlmapCountSql() {
        return toCountSqlForPaging(getSqlmap());
    }
    
	public String getSql() {
		return replaceWildcardWithColumnsSqlName(sourceSql);
	}
	
	public String toCountSqlForPaging(String sql) {
	    if(sql == null) return null;
	    if(isSelectSql()) {
            return SqlParseHelper.toCountSqlForPaging(sql, "select count(*) ");
	    }
	    return sql;
	}
	
	public String getSpringJdbcSql() {
		return SqlParseHelper.convert2NamedParametersSql(getSql(),":","");
	}
	
	public String getHql() {
		return SqlParseHelper.convert2NamedParametersSql(getSql(),":","");
	}
	
	public String getIbatisSql() {
	    return StringHelper.isBlank(ibatisSql) ? SqlParseHelper.convert2NamedParametersSql(getSql(),"#","#") : ibatisSql;
	}
	
	public String getIbatis3Sql() {
	    return StringHelper.isBlank(ibatis3Sql) ? SqlParseHelper.convert2NamedParametersSql(getSql(),"#{","}") : ibatis3Sql;
	}

	public void setIbatisSql(String ibatisSql) {
        this.ibatisSql = ibatisSql;
    }

    public void setIbatis3Sql(String ibatis3Sql) {
        this.ibatis3Sql = ibatis3Sql;
    }

    private String joinColumnsSqlName() {
        // TODO δ��� a.*,b.*����
		StringBuffer sb = new StringBuffer();
		for(Iterator<Column> it = columns.iterator();it.hasNext();) {
			Column c = it.next();
			sb.append(c.getSqlName());
			if(it.hasNext()) sb.append(",");
		}
		return sb.toString();
	}
	
	public String replaceWildcardWithColumnsSqlName(String sql) {
		if(SqlTypeChecker.isSelectSql(sql) && SqlParseHelper.getSelect(SqlParseHelper.removeSqlComments(sql)).indexOf("*") >= 0 && SqlParseHelper.getSelect(SqlParseHelper.removeSqlComments(sql)).indexOf("count(") < 0) {
			return SqlParseHelper.getPrettySql("select " + joinColumnsSqlName() + " " + SqlParseHelper.removeSelect(sql));
		}else {
			return sql;
		}
	}

    /**
     * ��ǰ��sourceSql�Ƿ���select���
     * @return
     */
	public boolean isSelectSql() {
		return SqlTypeChecker.isSelectSql(sourceSql);
	}

    /**
     * ��ǰ��sourceSql�Ƿ���update���
     * @return
     */
	public boolean isUpdateSql() {
		return SqlTypeChecker.isUpdateSql(sourceSql);
	}

    /**
     * ��ǰ��sourceSql�Ƿ���delete���
     * @return
     */
	public boolean isDeleteSql() {
		return SqlTypeChecker.isDeleteSql(sourceSql);
	}

    /**
     * ��ǰ��sourceSql�Ƿ���insert���
     * @return
     */
	public boolean isInsertSql() {
		return SqlTypeChecker.isInsertSql(sourceSql);
	}

    /**
     * �õ������Ӧ��sqlName,��Ҫ��;Ϊ�����ļ�ʱ�ķ���.
     * @return
     */
	public String getTableSqlName() {
		return tableSqlName;
	}

	public void setTableSqlName(String tableName) {
		this.tableSqlName = tableName;
	}

    /**
     * �õ���ע
     * @return
     */
	public String getRemarks() {
		return remarks;
	}
	
	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public void setRemarks(String comments) {
		this.remarks = comments;
	}
	
	public boolean isPaging() {
		if(MULTIPLICITY_PAGING.equalsIgnoreCase(multiplicity)) {
			return true;
		}
        return paging;
    }

    public void setPaging(boolean paging) {
        this.paging = paging;
    }

    /**
     * ����tableSqlName�ͳ����Ӧ��tableClassName,��Ҫ��;·����������.��${tableClassName}Dao.java
     * @return
     */
	public String getTableClassName() {
		System.out.println("getTableClassName()......");
		if(StringHelper.isBlank(tableSqlName)) return null;
		String removedPrefixSqlName = Table.removeTableSqlNamePrefix(tableSqlName);
		return StringHelper.makeAllWordFirstLetterUpperCase(StringHelper.toUnderscoreName(removedPrefixSqlName));
	}

	public Column getColumnBySqlName(String sqlName) {
		for(Column c : getColumns()) {
			if(c.getSqlName().equalsIgnoreCase(sqlName)) {
				return c;
			}
		}
		return null;
	}
	
	public Column getColumnByName(String name) {
	    Column c = getColumnBySqlName(name);
	    if(c == null) {
	    	c = getColumnBySqlName(StringHelper.toUnderscoreName(name));
	    }
	    return c;
	}
	
	public String toString() {
		return "sourceSql:\n"+sourceSql+"\nsql:"+getSql();
	}
	
	private String ibatisSql;
	private String ibatis3Sql;
}