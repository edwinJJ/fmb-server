package com.server.fmb.constant;

public class Constant {
	public static final String FILTERS = "filters";
	public static final String NAME = "name";
	public static final String GROUP_ID= "group_id";
	public static final String VALUE = "value";
	public static final String PAGINATION = "pagination";
	public static final String PAGE = "page";
	public static final String LIMIT = "limit";
	public static final String ITEMS = "items";
	public static final String TOTAL = "total";
	public static final String CATEGORY = "category";
	public static final String SORTERS = "sorters";
	public static final String STATUS = "status";
	public static final String ENCODING = "encoding";
	public static final String ROOT_DIVISION = "root.divison";
	
	/**
	 * Entity 변수 ID 
	 */
	public static final String VARIABLE_ID = "id";

	/**
	 * Database table 컬럼 ID 
	 */
	public static final String ID = "id";

	/**
	 * Database table 컬럼 ID 
	 */
	public static final String COLUMN_NAME = "columnName";
	
	/**
	 * Class를 다루는 변수 명  
	 */
	public static final String CLASS = "clazz";

	/**
	 * Class를 다루는 변수 명  
	 */
	public static final String QUERY = "query";
	
	/**
	 * condition 을 다루는 변수 명  
	 */
	public static final String CONDITION = "condition";
	
	/**
	 * paramMap 을 다루는 변수 명  
	 */
	public static final String PARAM_MAP = "paramMap";
	
	/**
	 * data 을 다루는 변수 명  
	 */
	public static final String DATA = "data";
	
	/**
	 * columns 을 다루는 변수 명  
	 */
	public static final String COLUMNS = "columns";
	/**
	 * variableNames 을 다루는 변수 명  
	 */
	public static final String VARIABLE_NAMES = "variableNames";
	
	/**
	 * postgresql column type char
	 */
	public static final String PG_COL_CHAR = "char";

	/**
	 * postgresql column type character
	 */
	public static final String PG_COL_CHARACTER = "character";
	
	/**
	 * postgresql column type character varying
	 */
	public static final String PG_COL_CHARACTER_VARYING = "character varying";
	
	/**
	 * postgresql column type timestamp without time zone
	 */
	public static final String PG_COL_TIMESTAMP_WITHOUT_TIME_ZONE = "timestamp without time zone";
	
	/**
	 * postgresql column type boolean
	 */
	public static final String PG_COL_BOOLEAN = "boolean";
	
	/**
	 * postgresql column type integer
	 */
	public static final String PG_COL_INTEGER = "integer";

	/**
	 * postgresql column type text
	 */
	public static final String PG_COL_TEXT = "text";

	/**
	 * postgresql column type bigint
	 */
	public static final String PG_COL_BIGINT = "bigint";

	/**
	 * oracle column type varchar2
	 */
	public static final String PG_COL_VARCHAR2 = "varchar2";

	/**
	 * oracle column type float
	 */
	public static final String PG_COL_FLOAT = "float";

	/**
	 * oracle column type number
	 */
	public static final String PG_COL_NUMBER = "number";

	/**
	 * oracle column type clob
	 */
	public static final String PG_COL_CLOB = "clob";

	/**
	 * oracle column type date
	 */
	public static final String PG_COL_DATE = "date";

	
	/**
	 * JSON WEB TOCKEN
	 */
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

	/**
	 * content type json
	 */
	public static final String CONTENT_TYPE_JSON = "json";
	
	/**
	 * content type form
	 */
	public static final String CONTENT_TYPE_FORM = "form";
	
	/**
	 * file id prefix temp
	 */
    public static final String FILE_ID_PREFIX_TEMP = "temp"; 
    

	/**
	 * other server info
	 */
	public static final String IMAGE_SERVER_CONTEXT = "image.server.context";
	public static final String IMAGE_SERVER_REPOSITORY = "image.server.repository";
	public static final String FILE_REPOSITORY = "file.repository";
	public static final String GSC_ENABLED = "google-cloud-storage.enabled";
	public static final String GSC_BUCKET = "google-cloud-storage.bucket";
	
	/**
	 * file folder
	 */
	public static final String FILE_REPOSITORY_PROFILE_USER = "profiles/";
	
	/**
	 * Database Types 
	 */
	
	public static final String DB_TYPE_POSTGRESQL = "postgresql";
	public static final String DB_TYPE_MSSQL = "sqlserver";
	public static final String DB_TYPE_MYSQL = "mysql";
	public static final String DB_TYPE_ORACLE = "oracle";
		
	public static final String AUTH_MAIL_TYPE_REGISTER = "register";
	public static final String AUTH_MAIL_TYPE_PASSWORD = "password";
	public static final String AUTH_MAIL_TYPE_INVITE = "invite";
	
	public static final String ENV_PROPERTY_BASE_PACKAGE = "bean.base.package";
}
