package com.SeventeenWeek.DB;


import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DataBaseOpenHelper extends SQLiteOpenHelper {

    private static final String dbName = "Gtdb";
    
    //收集表
    private static final String idColumn = "_id";
    private static final String contentColumn = "content";
    private static final String collectTableName = "collect";
    
    //工作清单表--行动
    //idColumn
    //content
    private static final String workListActionTableName = "work_list_action";
    private static final String workActionStateColumn = "work_action_state";
    private static final String projectIdColumn = "project_id";
    
    //工作清单表--项目
    private static final String workListProjectTableName = "work_list_priject";
    //idColumn
    //content
    
    //回顾
    private static final String reviewTableName = "review";
    private static final String dateColumn = "create_date";
    
    //未来事宜
    private static final String futrueTableName = "futrue";
    
    
    private static int version = 1; 
	private static DataBaseOpenHelper databaseOpenHelper;
	
    static public void initOpenHelper(Context context)
    {
    	databaseOpenHelper = new DataBaseOpenHelper(context);
    }
    
    public static synchronized DataBaseOpenHelper getInstance(){    
    	return databaseOpenHelper;           
    }       
    
	private DataBaseOpenHelper(Context context) {  
        // 第一个参数是应用的上下文  
        // 第二个参数是应用的数据库名字  
        // 第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类  
        // 第四个参数是数据库版本，必须是大于0的int（即非负数）  
        super(context, dbName, null, version);  
        // TODO Auto-generated constructor stub  
    }  
	
	@Override
	public void onOpen(SQLiteDatabase db)
	  {
	    super.onOpen(db);
	    if (!db.isReadOnly())
	    {
	      // Enable foreign key constraints
	      db.execSQL("PRAGMA foreign_keys=ON;");
	    }
	  }
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		// 创建收集篮的DB
		String collectSql="Create table "+collectTableName+"("+ idColumn +" integer primary key autoincrement,"
        + contentColumn +" text not null);";
		db.execSQL(collectSql.toString()); 
		
		//创建工作清单--项目
		String workListProjectSql="Create table "+workListProjectTableName+ "(" + idColumn +" integer primary key autoincrement,"
        + contentColumn +" text );";
		db.execSQL(workListProjectSql.toString());
		
		//创建工作清单--行动		
		String workListActionSql="Create table "+workListActionTableName+ "(" + 
		idColumn +" integer primary key autoincrement,"
        + contentColumn +" text not null, " 
        +  workActionStateColumn + " integer, " + projectIdColumn + " integer," +
        	" foreign key(" + projectIdColumn + ")REFERENCES " + workListProjectTableName
        	+ " (" + idColumn + "));";
		db.execSQL(workListActionSql.toString()); 
		
		//创建工作清单--回顾
		String reviewSql="Create table "+reviewTableName+ "(" + idColumn +" integer primary key autoincrement,"
        + contentColumn +" text, " 
        + dateColumn + " date default CURRENT_DATE);";
		db.execSQL(reviewSql.toString());
		
		// 创建未来事宜的DB
		String futrueSql="Create table "+futrueTableName+"("+ idColumn +" integer primary key autoincrement,"
        + contentColumn +" text not null);";
		db.execSQL(futrueSql.toString()); 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		  String sql=" DROP TABLE IF EXISTS "+collectTableName;
	      db.execSQL(sql);
	      onCreate(db);
	}
	
	//-----------------------收集相关数据库操作----------------------------
	public Cursor selectCollect(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(collectTableName, null, null, null, null, null,  " _id desc");
        return cursor;
    }
	
	public Cursor selectCollect(Long id){
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor c = db.rawQuery("select * from " + collectTableName + " WHERE " + idColumn + "=?", 
				new String[]{id.toString()});
		return c;
	}
	
	public long insertCollect(String Title){
	    SQLiteDatabase db=this.getWritableDatabase();
	    ContentValues cv=new ContentValues(); 
	    cv.put(contentColumn, Title);
	    long row=db.insert(collectTableName, null, cv);
	    return row;
	}
	    
	public void deleteCollect(long id){
	    SQLiteDatabase db=this.getWritableDatabase();
	    String where= idColumn+"=?";
	    String[] whereValue={Long.toString(id)};
	    db.delete(collectTableName, where, whereValue);
	}
	    
	public void updateCollect(long id,String Title){
	    SQLiteDatabase db=this.getWritableDatabase();
	    String where=idColumn+"=?";
	    String[] whereValue={Long.toString(id)};
	    ContentValues cv=new ContentValues(); 
	    cv.put(contentColumn, Title);
	    db.update(collectTableName, cv, where, whereValue);
	}
	 
	//-----------------------工作清单项目相关数据库操作----------------------------
	public Cursor selectWorkProject(){
	        SQLiteDatabase db=this.getReadableDatabase();
	        Cursor cursor=db.query(workListProjectTableName, null, null, null, null, null,  " _id desc");
	        return cursor;
	}
		
	public Cursor selectWorkProject(Long id){
			SQLiteDatabase db=this.getReadableDatabase();
			Cursor c = db.rawQuery("select * from " + workListProjectTableName + " WHERE " + idColumn + "=?", 
					new String[]{id.toString()});
			return c;
	}
		
	public Cursor selectWorkProject(String title){
			SQLiteDatabase db=this.getReadableDatabase();
			Cursor c = db.rawQuery("select * from " + workListProjectTableName + " WHERE " + contentColumn + "=?", 
					new String[]{title});
			return c;
	}
		
	public long insertWorkProject(String Title){
		    SQLiteDatabase db=this.getWritableDatabase();
		    ContentValues cv=new ContentValues(); 
		    cv.put(contentColumn, Title);
		    long row=db.insert(workListProjectTableName, null, cv);
		    return row;
	}
		    
	public void deleteWorkProject(long id){
		    SQLiteDatabase db=this.getWritableDatabase();
		    String where= idColumn+"=?";
		    String[] whereValue={Long.toString(id)};
		    db.delete(workListProjectTableName, where, whereValue);
	}
		    
	public void updateWorkProject(int id,String Title){
		    SQLiteDatabase db=this.getWritableDatabase();
		    String where=idColumn+"=?";
		    String[] whereValue={Integer.toString(id)};
		    ContentValues cv=new ContentValues(); 
		    cv.put(contentColumn, Title);
		    db.update(workListProjectTableName, cv, where, whereValue);
	}
		 
	//-----------------------工作清单行动相关数据库操作----------------------------
	public Cursor selectWorkAction(){
		        SQLiteDatabase db=this.getReadableDatabase();
		        Cursor cursor=db.query(workListActionTableName, null, null, null, null, null,  " _id desc");
		        return cursor;
	}
			
	public Cursor selectWorkAction(Long id){
				SQLiteDatabase db=this.getReadableDatabase();
				Cursor c = db.rawQuery("select * from " + workListActionTableName + " WHERE " + idColumn + "=?", 
						new String[]{id.toString()});
				return c;
	}
			
	public Cursor selectWorkAction(String content){
				SQLiteDatabase db=this.getReadableDatabase();
				Cursor c = db.rawQuery("select * from " + workListActionTableName + " WHERE " + contentColumn + "=?", 
						new String[]{content});
				return c;
	}
			
	public Cursor selectWorkProjectAction(Long id){
				SQLiteDatabase db=this.getReadableDatabase();
				Cursor c;
				if (id >= 0){
					c = db.rawQuery("select * from " + workListActionTableName + " WHERE " + projectIdColumn + "=?", 
							new String[]{id.toString()});
				}else{
					c = db.rawQuery("select * from " + workListActionTableName + " WHERE " + projectIdColumn + " is null", null);
				}
				
				return c;
	}
			
	public long insertWorkAction(String title, long projectId, int state){
			    SQLiteDatabase db=this.getWritableDatabase();
			    ContentValues cv=new ContentValues(); 
			    cv.put(contentColumn, title);
			    cv.put(projectIdColumn, projectId);
			    cv.put(workActionStateColumn, state);
			    long row=db.insert(workListActionTableName, null, cv);
			    return row;
	}
			 
	public long insertNullWorkAction(String title){
			    SQLiteDatabase db=this.getWritableDatabase();
			    ContentValues cv=new ContentValues(); 
			    cv.put(contentColumn, title);
			    cv.put(workActionStateColumn, 0);
			    long row=db.insert(workListActionTableName, null, cv);
			    return row;
	}
			 
			    
	public void deleteWorkAction(long id){
				 
			    SQLiteDatabase db=this.getWritableDatabase();
			    String where= idColumn+"=?";
			    String[] whereValue={Long.toString(id)};
			    db.delete(workListActionTableName, where, whereValue);
	}
	
	public boolean deleteWorkProjectAction(long projectId){
		SQLiteDatabase db = this.getWritableDatabase();
		String where = projectIdColumn + "=?";
		String[] whereValue = {Long.toString(projectId)};
		db.delete(workListActionTableName, where, whereValue);
		return true;
	}
			    
	public void updateWorkAction(long id,String title, int projectId, int state){
			    SQLiteDatabase db=this.getWritableDatabase();
			    String where=idColumn+"=?";
			    String[] whereValue={Long.toString(id)};
			    ContentValues cv=new ContentValues(); 
			    cv.put(contentColumn, title);
			    cv.put(projectIdColumn, projectId);
			    cv.put(workActionStateColumn, state);
			    db.update(workListActionTableName, cv, where, whereValue);
	}
			 
	public void updateWorkActionContent(Long id, String content){
				 SQLiteDatabase db=this.getWritableDatabase();
				    String where=idColumn+"=?";
				    String[] whereValue={id.toString()};
				    ContentValues cv=new ContentValues(); 

				    cv.put(contentColumn, content);
				    db.update(workListActionTableName, cv, where, whereValue);
	}
			 
	public void updateWorkActionState(long id, int state){
			    SQLiteDatabase db=this.getWritableDatabase();
			    String where=idColumn+"=?";
			    String[] whereValue={Long.toString(id)};
			    ContentValues cv=new ContentValues(); 

			    cv.put(workActionStateColumn, state);
			    db.update(workListActionTableName, cv, where, whereValue);
	}
			 
			 
	//-----------------------回顾相关数据库操作----------------------------
	public Cursor selectReview(){
			        SQLiteDatabase db=this.getReadableDatabase();
			        Cursor cursor=db.query(reviewTableName, null, null, null, null, null,  " _id desc");
			        return cursor;
	}
				
	public Cursor selectReview(Long id){
					SQLiteDatabase db=this.getReadableDatabase();
					Cursor c = db.rawQuery("select * from " + reviewTableName + " WHERE " + idColumn + "=?", 
							new String[]{id.toString()});
					return c;
	}
				
	public Cursor selectReview(String startDate, String endDate){
					SQLiteDatabase db=this.getReadableDatabase();
					Cursor c = db.rawQuery("select * from " + reviewTableName + " WHERE " + 
							dateColumn + ">=? " + "and " + dateColumn + "<=?", 
							new String[]{startDate, endDate});
					return c;
	}
				
	public long insertReview(String Title){
				    SQLiteDatabase db=this.getWritableDatabase();
				    ContentValues cv=new ContentValues(); 
				    //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
				    //Date date = new Date();
				    
				    //cv.put(dateColumn, dateFormat.format(date));
				    cv.put(contentColumn, Title);
				    long row=db.insert(reviewTableName, null, cv);
				    return row;
	}
				    
	public void deleteReview(long id){
				    SQLiteDatabase db=this.getWritableDatabase();
				    String where= idColumn+"=?";
				    String[] whereValue={Long.toString(id)};
				    db.delete(reviewTableName, where, whereValue);
	}
				    
	public void updateReview(long id,String Title){
				    SQLiteDatabase db=this.getWritableDatabase();
				    String where=idColumn+"=?";
				    String[] whereValue={Long.toString(id)};
				    ContentValues cv=new ContentValues(); 
				    cv.put(contentColumn, Title);
				    db.update(reviewTableName, cv, where, whereValue);
	}
	
	//-----------------------未来事宜数据库操作----------------------------
	public Cursor selectFutrue(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(futrueTableName, null, null, null, null, null,  " _id desc");
        return cursor;
    }
	
	public Cursor selectFutrue(Long id){
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor c = db.rawQuery("select * from " + futrueTableName + " WHERE " + idColumn + "=?", 
				new String[]{id.toString()});
		return c;
	}
	
	 public long insertFutrue(String Title){
	    SQLiteDatabase db=this.getWritableDatabase();
	    ContentValues cv=new ContentValues(); 
	    cv.put(contentColumn, Title);
	    long row=db.insert(futrueTableName, null, cv);
	    return row;
	 }
	    
	 public void deleteFutrue(long id){
	    SQLiteDatabase db=this.getWritableDatabase();
	    String where= idColumn+"=?";
	    String[] whereValue={Long.toString(id)};
	    db.delete(futrueTableName, where, whereValue);
	 }
	    
	 public void updateFutrue(long id,String Title){
	    SQLiteDatabase db=this.getWritableDatabase();
	    String where=idColumn+"=?";
	    String[] whereValue={Long.toString(id)};
	    ContentValues cv=new ContentValues(); 
	    cv.put(contentColumn, Title);
	    db.update(futrueTableName, cv, where, whereValue);
	 }
				 
				
}
