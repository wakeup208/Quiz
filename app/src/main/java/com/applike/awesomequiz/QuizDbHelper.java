package com.applike.awesomequiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.applike.awesomequiz.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "AwesomeQuiz.db";
    public static final int DATABASE_VERSION = 1;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " +
                CategoriesTable.TABLE_NAME + " ( " +
                CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " + ")";

        final String SQL_CREATE_QUESTION_TABLE = "CREATE TABLE " +
                QuestionTable.TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionTable.COLUMN_ANSWER_NR + " INTEGER, " +
                //QuestionTable.COLUMN_DIFFICULTY + " TEXT," +
                QuestionTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" + ")";

        db.execSQL(SQL_CREATE_CATEGORY_TABLE);
        db.execSQL(SQL_CREATE_QUESTION_TABLE);
        fillCategoriesTable();
        //fillQuestionTable();
        fillQuestionTarotTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable() {
        Category c1 = new Category("Major Arcana");
        insertCategory(c1);

        Category c2 = new Category("Suit of Wands");
        insertCategory(c2);

        Category c3 = new Category("Suit of Cups");
        insertCategory(c3);

        Category c4 = new Category("Suit of Swords");
        insertCategory(c4);

        Category c5 = new Category("Suit of Pentacles");
        insertCategory(c5);
    }

    private void insertCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }

//    private void fillQuestionTable() {
//        // HTML
//        Question q1 = new Question("Which of the following is used to create Web Pages ?",
//                "C++", "Java", "HTML", 3, Question.DIFFICULTY_EASY , Category.HTML);
//        insertQuestion(q1);
//
//        Question q2 = new Question("HTML stands for ","Hyper Text Makeup Language",
//                "Hyper Tech Markup Language", "none of the above", 1, Question.DIFFICULTY_EASY , Category.HTML);
//        insertQuestion(q2);
//
//        Question q3 = new Question("PC running with Windows 3.x will have _____ extension for HTML page.",".html",
//                "hml", "htm", 1, Question.DIFFICULTY_MEDIUM , Category.HTML);
//        insertQuestion(q3);
//
//        Question q4 = new Question("HTML was firstly proposed in year","1990",
//                "1995", "1197", 2, Question.DIFFICULTY_MEDIUM , Category.HTML);
//        insertQuestion(q4);
//
//        Question q5 = new Question("HTML tags are surrounded by _____ brackets.","Squart",
//                "Angle", "Round", 1, Question.DIFFICULTY_HARD , Category.HTML);
//        insertQuestion(q5);
//
//        Question q6 = new Question("HTML program can be read and rendered by","Compiler",
//                "Browser", "Server", 2, Question.DIFFICULTY_HARD , Category.HTML);
//        insertQuestion(q6);
//
//        // JAVA
//        Question q10 = new Question("Which of these keywords is used to define packages in Java ?",
//                "Package", "package", "pkg", 1, Question.DIFFICULTY_EASY , Category.JAVA);
//        insertQuestion(q10);
//
//        Question q20 = new Question("Which of the following is a keyword in Java ?","this",
//                "that", "it", 1, Question.DIFFICULTY_EASY , Category.JAVA);
//        insertQuestion(q20);
//
//        Question q30 = new Question(" Which of the following is a valid declaration of an object of class Box ?","obj = new Box();",
//                "Box obj = new Box;", "Box obj = new Box();", 3, Question.DIFFICULTY_MEDIUM , Category.JAVA);
//        insertQuestion(q30);
//
//        Question q40 = new Question("Which of these operators is used to allocate memory for an object ?","give",
//                "new", "alloc", 2, Question.DIFFICULTY_MEDIUM , Category.JAVA);
//        insertQuestion(q40);
//
//        Question q50 = new Question("What will be the return type of a method that not returns any value ?","double",
//                "void", "int", 2, Question.DIFFICULTY_HARD , Category.JAVA);
//        insertQuestion(q50);
//
//        Question q60 = new Question("Which of the modifier can not be used for constructors ?","static",
//                "private", "public", 3, Question.DIFFICULTY_HARD , Category.JAVA);
//        insertQuestion(q60);
//
//    }

    private void fillQuestionTarotTable() {
        Question q1 = new Question("Ý nghĩa của lá bài The Fool ?",
                "Hành Động, Nhận Thức Có Ý Thức, Tập Trung, Quyền Lực",
                "Khởi Đầu, Tự Nguyện, Tự Phát, Niềm Tin, Điên Rồ",
                "Nuôi Nấng, Sung Túc, Trù Phú, Tri Giác, Thiên Nhiên",
                "Sức Mạnh, Nhẫn Nại, Lòng Trắc Ẩn, Kiểm Soát Mềm Mỏng",
                4, Category.MajorArcana);
        insertQuestion(q1);

        Question q2 = new Question("Ý nghĩa của lá bài The Magician ?",
                "Nội Quan (*tự xem xét nội tâm), Tìm Kiếm, Dẫn Dắt, Đơn Độc",
                "Khởi Đầu, Tự Nguyện, Tự Phát, Niềm Tin, Điên Rồ",
                "Hành Động, Nhận Thức Có Ý Thức, Tập Trung, Quyền Lực",
                "Sự Hợp Nhất, Sự Hoàn Thành Trọn Vẹn, Sự Kết Nối, Sự Thỏa Mãn",
                3, Category.MajorArcana);
        insertQuestion(q2);

        Question q3 = new Question("Ý nghĩa của lá bài The High Priestess ?",
                "Thụ Động, Vô Thức, Tiềm Năng, Bí Ẩn",
                "Làm Cha, Cấu Trúc, Nắm Quyền, Luật Lệ",
                "Chừng Mực, Cân Bằng, Sức Khỏe, Kết Hợp",
                "Bỏ Qua, Đảo Ngược, Tạm Dừng, Hy Sinh",
                1, Category.MajorArcana);
        insertQuestion(q3);

        Question q4 = new Question("Ý nghĩa của lá bài The Empress ?",
                "Định Mệnh, Điểm Bùng Phát, Sự Biến Chuyển, Tầm Nhìn Cá Nhân",
                "Nuôi Nấng, Sung Túc, Trù Phú, Tri Giác, Thiên Nhiên",
                "Thay Đổi Đột Ngột, Giải Thoát, Sa Sút, Mặc Khải",
                "Công Lý, Trách Nhiệm, Quyết Định, Nguyên Nhân – Kết Quả",
                2, Category.MajorArcana);
        insertQuestion(q4);

        Question q5 = new Question("Ý nghĩa của lá bài The Emperor ?",
                "Hy Vọng, Cảm Hứng, Hào Phóng, Yên Bình",
                "Khởi Đầu, Tự Nguyện, Tự Phát, Niềm Tin, Điên Rồ",
                "Giáo Dục, Hệ Thống Niềm Tin, Tiếp nhận, Nhận dạng nhóm",
                "Làm Cha, Cấu Trúc, Nắm Quyền, Luật Lệ",
                4, Category.MajorArcana);
        insertQuestion(q5);

        Question q6 = new Question("Ý nghĩa của lá bài The Hierophant ?",
                "Giáo Dục, Hệ Thống Niềm Tin, Tiếp nhận, Nhận dạng nhóm",
                "Nội Quan (*tự xem xét nội tâm), Tìm Kiếm, Dẫn Dắt, Đơn Độc",
                "Định Mệnh, Điểm Bùng Phát, Sự Biến Chuyển, Tầm Nhìn Cá Nhân",
                "Bỏ Qua, Đảo Ngược, Tạm Dừng, Hy Sinh",
                1, Category.MajorArcana);
        insertQuestion(q6);

        Question q7 = new Question("Ý nghĩa của lá bài The Lovers ?",
                "Bỏ Qua, Đảo Ngược, Tạm Dừng, Hy Sinh",
                "Mối Quan Hệ, Bản Năng Giới Tính, Niềm Tin Cá Nhân, Giá Trị",
                "Hy Vọng, Cảm Hứng, Hào Phóng, Yên Bình",
                "Công Lý, Trách Nhiệm, Quyết Định, Nguyên Nhân – Kết Quả",
                2, Category.MajorArcana);
        insertQuestion(q7);

        Question q8 = new Question("Ý nghĩa của lá bài The Chariot ?",
                "Chiến Thắng, Ước Muốn, Tự Khẳng Định, Khó Kiểm Soát",
                "Kết Thúc, Thay Đổi, Loại Bỏ, Tác Động Tuyệt Đối",
                "Cảnh Nô Lệ, Chủ Nghĩa Vật Chất, Sự Ngu Dốt, Sự Tuyệt Vọng",
                "Định Mệnh, Điểm Bùng Phát, Sự Biến Chuyển, Tầm Nhìn Cá Nhân",
                1, Category.MajorArcana);
        insertQuestion(q8);

        Question q9= new Question("Ý nghĩa của lá bài Strength ?",
                "Giáo Dục, Hệ Thống Niềm Tin, Tiếp nhận, Nhận dạng nhóm",
                "Sự Phán Xét, Sự Tái Sinh, Tiếng Gọi Nội Tâm, Sự Rửa Tội",
                "Sức Mạnh, Nhẫn Nại, Lòng Trắc Ẩn, Kiểm Soát Mềm Mỏng",
                "Thụ Động, Vô Thức, Tiềm Năng, Bí Ẩn",
                3, Category.MajorArcana);
        insertQuestion(q9);

        Question q10= new Question("Ý nghĩa của lá bài The Hermit ?",
                "Chiến Thắng, Ước Muốn, Tự Khẳng Định, Khó Kiểm Soát",
                "Bỏ Qua, Đảo Ngược, Tạm Dừng, Hy Sinh",
                "Thay Đổi Đột Ngột, Giải Thoát, Sa Sút, Mặc Khải",
                "Nội Quan (*tự xem xét nội tâm), Tìm Kiếm, Dẫn Dắt, Đơn Độc",
                4, Category.MajorArcana);
        insertQuestion(q10);

        Question q11= new Question("Ý nghĩa của lá bài Wheel of Fortune ?",
                "Mối Quan Hệ, Bản Năng Giới Tính, Niềm Tin Cá Nhân, Giá Trị",
                "Định Mệnh, Điểm Bùng Phát, Sự Biến Chuyển, Tầm Nhìn Cá Nhân",
                "Hành Động, Nhận Thức Có Ý Thức, Tập Trung, Quyền Lực",
                "Khởi Đầu, Tự Nguyện, Tự Phát, Niềm Tin, Điên Rồ",
                2, Category.MajorArcana);
        insertQuestion(q11);

        Question q12= new Question("Ý nghĩa của lá bài JUSTICE ?",
                "Công Lý, Trách Nhiệm, Quyết Định, Nguyên Nhân – Kết Quả",
                "Nỗi Sợ Hãi, Ảo Ảnh, Sự Tưởng Tượng, Sự Hoang Mang",
                "Sự Khai Sáng, Sự Vĩ Đại, Sức Sống, Sự Tự Tin",
                "Sự Phán Xét, Sự Tái Sinh, Tiếng Gọi Nội Tâm, Sự Rửa Tội",
                1, Category.MajorArcana);
        insertQuestion(q12);

        Question q13= new Question("Ý nghĩa của lá bài The Hanged Man ?",
                "Bỏ Qua, Đảo Ngược, Tạm Dừng, Hy Sinh",
                "Giáo Dục, Hệ Thống Niềm Tin, Tiếp nhận, Nhận dạng nhóm",
                "Thay Đổi Đột Ngột, Giải Thoát, Sa Sút, Mặc Khải",
                "Nuôi Nấng, Sung Túc, Trù Phú, Tri Giác, Thiên Nhiên",
                1, Category.MajorArcana);
        insertQuestion(q13);

        Question q14= new Question("Ý nghĩa của lá bài Death ?",
                "Sự Khai Sáng, Sự Vĩ Đại, Sức Sống, Sự Tự Tin",
                "Kết Thúc, Thay Đổi, Loại Bỏ, Tác Động Tuyệt Đối",
                "Sự Phán Xét, Sự Tái Sinh, Tiếng Gọi Nội Tâm, Sự Rửa Tội",
                "Nội Quan (*tự xem xét nội tâm), Tìm Kiếm, Dẫn Dắt, Đơn Độc",
                2, Category.MajorArcana);
        insertQuestion(q14);

        Question q15= new Question("Ý nghĩa của lá bài Temperance ?",
                "Chừng Mực, Cân Bằng, Sức Khỏe, Kết Hợp",
                "Nỗi Sợ Hãi, Ảo Ảnh, Sự Tưởng Tượng, Sự Hoang Mang",
                "Sự Khai Sáng, Sự Vĩ Đại, Sức Sống, Sự Tự Tin",
                "Sự Phán Xét, Sự Tái Sinh, Tiếng Gọi Nội Tâm, Sự Rửa Tội",
                1, Category.MajorArcana);
        insertQuestion(q15);

        Question q16= new Question("Ý nghĩa của lá bài The Devil ?",
                "Cảnh Nô Lệ, Chủ Nghĩa Vật Chất, Sự Ngu Dốt, Sự Tuyệt Vọng",
                "Định Mệnh, Điểm Bùng Phát, Sự Biến Chuyển, Tầm Nhìn Cá Nhân",
                "Hành Động, Nhận Thức Có Ý Thức, Tập Trung, Quyền Lực",
                "Khởi Đầu, Tự Nguyện, Tự Phát, Niềm Tin, Điên Rồ",
                1, Category.MajorArcana);
        insertQuestion(q16);

        Question q17= new Question("Ý nghĩa của lá bài The Tower ?",
                "Hy Vọng, Cảm Hứng, Hào Phóng, Yên Bình",
                "Khởi Đầu, Tự Nguyện, Tự Phát, Niềm Tin, Điên Rồ",
                "Giáo Dục, Hệ Thống Niềm Tin, Tiếp nhận, Nhận dạng nhóm",
                "Thay Đổi Đột Ngột, Giải Thoát, Sa Sút, Mặc Khải",
                4, Category.MajorArcana);
        insertQuestion(q17);

        Question q18= new Question("Ý nghĩa của lá bài The Star ?",
                "Sự Hợp Nhất, Sự Hoàn Thành Trọn Vẹn, Sự Kết Nối, Sự Thỏa Mãn",
                "Nuôi Nấng, Sung Túc, Trù Phú, Tri Giác, Thiên Nhiên",
                "Hy Vọng, Cảm Hứng, Hào Phóng, Yên Bình",
                "Thụ Động, Vô Thức, Tiềm Năng, Bí Ẩn",
                3, Category.MajorArcana);
        insertQuestion(q18);

        Question q19= new Question("Ý nghĩa của lá bài The Moon ?",
                "Nỗi Sợ Hãi, Ảo Ảnh, Sự Tưởng Tượng, Sự Hoang Mang",
                "Định Mệnh, Điểm Bùng Phát, Sự Biến Chuyển, Tầm Nhìn Cá Nhân",
                "Hy Vọng, Cảm Hứng, Hào Phóng, Yên Bình",
                "Làm Cha, Cấu Trúc, Nắm Quyền, Luật Lệ",
                1, Category.MajorArcana);
        insertQuestion(q19);

        Question q20= new Question("Ý nghĩa của lá bài The Sun ?",
                "Nội Quan (*tự xem xét nội tâm), Tìm Kiếm, Dẫn Dắt, Đơn Độc",
                "Mối Quan Hệ, Bản Năng Giới Tính, Niềm Tin Cá Nhân, Giá Trị",
                "Sự Khai Sáng, Sự Vĩ Đại, Sức Sống, Sự Tự Tin",
                "Sức Mạnh, Nhẫn Nại, Lòng Trắc Ẩn, Kiểm Soát Mềm Mỏng",
                3, Category.MajorArcana);
        insertQuestion(q20);

        Question q21= new Question("Ý nghĩa của lá bài Judgement ?",
                "Kết Thúc, Thay Đổi, Loại Bỏ, Tác Động Tuyệt Đối",
                "Bỏ Qua, Đảo Ngược, Tạm Dừng, Hy Sinh",
                "Sự Phán Xét, Sự Tái Sinh, Tiếng Gọi Nội Tâm, Sự Rửa Tội",
                "Sức Mạnh, Nhẫn Nại, Lòng Trắc Ẩn, Kiểm Soát Mềm Mỏng",
                3, Category.MajorArcana);
        insertQuestion(q21);

        Question q22= new Question("Ý nghĩa của lá bài The World ?",
                "Giáo Dục, Hệ Thống Niềm Tin, Tiếp nhận, Nhận dạng nhóm",
                "Sự Phán Xét, Sự Tái Sinh, Tiếng Gọi Nội Tâm, Sự Rửa Tội",
                "Sức Mạnh, Nhẫn Nại, Lòng Trắc Ẩn, Kiểm Soát Mềm Mỏng",
                "Sự Hợp Nhất, Sự Hoàn Thành Trọn Vẹn, Sự Kết Nối, Sự Thỏa Mãn",
                4, Category.MajorArcana);
        insertQuestion(q22);

        //End MajorArcana
        
    }


    private void insertQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        //cv.put(QuestionTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionTable.TABLE_NAME, null, cv);
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(cursor.getInt(cursor.getColumnIndex(CategoriesTable._ID)));
                category.setName(cursor.getString(cursor.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categoryList;
    }

    public ArrayList<Question> getAllQuestion() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + QuestionTable.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(cursor.getInt(cursor.getColumnIndex(QuestionTable._ID)));
                question.setQuestion(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setOption4(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION4)));

                question.setAnswerNr(cursor.getInt(cursor.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));
                //question.setDifficulty(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(cursor.getInt(cursor.getColumnIndex(QuestionTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(int categoryID, String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        String selection = QuestionTable.COLUMN_CATEGORY_ID + "= ? " ;
                //+ " AND " + QuestionTable.COLUMN_DIFFICULTY + " = ?";

        String[] selectionArgs = new String[] {String.valueOf(categoryID), difficulty};

        Cursor cursor = db.query(QuestionTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,null,null);

        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(cursor.getInt(cursor.getColumnIndex(QuestionTable._ID)));
                question.setQuestion(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setOption4(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION4)));
                question.setAnswerNr(cursor.getInt(cursor.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));
                //question.setDifficulty(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(cursor.getInt(cursor.getColumnIndex(QuestionTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(int categoryID) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        String selection = QuestionTable.COLUMN_CATEGORY_ID + "= ? " ;
        //+ " AND " + QuestionTable.COLUMN_DIFFICULTY + " = ?";
        String rawquery = "SELECT * FROM " +QuestionTable.TABLE_NAME +
                " WHERE "
                + QuestionTable.COLUMN_CATEGORY_ID + "= ? ";

        String[] selectionArgs = new String[] {String.valueOf(categoryID)};
        Cursor cursor = db.query(QuestionTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,null,null);

        //Cursor cursor = db.rawQuery(rawquery,selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(cursor.getInt(cursor.getColumnIndex(QuestionTable._ID)));
                question.setQuestion(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                question.setOption4(cursor.getString(cursor.getColumnIndex(QuestionTable.COLUMN_OPTION4)));
                question.setAnswerNr(cursor.getInt(cursor.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));
                question.setCategoryID(cursor.getInt(cursor.getColumnIndex(QuestionTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questionList;
    }

}