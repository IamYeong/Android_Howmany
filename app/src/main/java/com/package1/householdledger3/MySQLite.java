package com.package1.householdledger3;

public class MySQLite {

    private MySQLite( ) {}

    //복수의 테이블과 컬럼을 선언할 것.
    public static final String TBL_CONTACT = "CONTACT_T";
    public static final String COL_NAME = "PLACE";
    public static final String COL_NAME2 = "MONEY";
    public static final String COL_NAME3 = "DATE";

    //두번째 테이블
    public static final String TBL_MAIN = "MAIN_T";
    public static final String TCOL_NAME = "GOAL";
    public static final String TCOL_NAME2 = "DATE2";

    //세번째 테이블
    public static final String TBL_AVGLOG = "AVG_T";
    public static final String ACOL_NAME = "AVG";
    public static final String ACOL_NAME2 = "DATE3";
    public static final String ACOL_NAME3 = "IMGID";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TBL_CONTACT + " " + "(" + COL_NAME + " TEXT" + ", " + COL_NAME2 + " INTEGER" + ", " + COL_NAME3 + " TEXT" + ")";
    public static final String SQL_DROP_TBL = "DROP TABLE IF EXISTS " + TBL_CONTACT;
    public static final String SQL_SELECT = "SELECT * FROM " + TBL_CONTACT;
    public static final String SQL_INSERT = "INSERT OR REPLACE INTO " + TBL_CONTACT + " " + "(" + COL_NAME + ", " + COL_NAME2 + ", " + COL_NAME3 + ") VALUES ";
    public static final String SQL_DELETE = "DELETE FROM " + TBL_CONTACT;

    public static final String SQL_CREATE_TABLE2 = "CREATE TABLE IF NOT EXISTS " + TBL_MAIN + " (" + TCOL_NAME + " INTEGER" + ", " + TCOL_NAME2 + " TEXT" + ")";
    public static final String SQL_DROP_TBL2 = "DROP TABLE IF EXISTS " + TBL_MAIN;
    public static final String SQL_SELECT2 = "SELECT * FROM " + TBL_MAIN;
    public static final String SQL_INSERT2 = "INSERT OR REPLACE INTO " + TBL_MAIN + " (" + TCOL_NAME + ", " + TCOL_NAME2 + ") VALUES ";
    public static final String SQL_DELETE2 = "DELETE FROM " + TBL_MAIN;

    public static final String SQL_CREATE_TABLE3 = "CREATE TABLE IF NOT EXISTS " + TBL_AVGLOG + " (" + ACOL_NAME + " INTEGER" + ", " + ACOL_NAME2 + " TEXT" + ", " + ACOL_NAME3 + " INTEGER" + ")";
    public static final String SQL_DROP_TBL3 = "DROP TABLE IF EXISTS " + TBL_AVGLOG;
    public static final String SQL_SELECT3 = "SELECT * FROM " + TBL_AVGLOG;
    public static final String SQL_INSERT3 = "INSERT OR REPLACE INTO " + TBL_AVGLOG + " (" + ACOL_NAME + ", " + ACOL_NAME2 + ", " + ACOL_NAME3 + ") VALUES ";
    public static final String SQL_DELETE3 = "DELETE FROM " + TBL_AVGLOG;



}
