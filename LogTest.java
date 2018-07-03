package jp.ne.yec.seagullLC.stagia.logdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;

public class LogTest {
	/**
     * Hàm thực hiện ghi file
     * @param object nội dung cần ghi
     * @param directory đường dẫn thư mục chứ file ghi
     * @return true thành công, false thất bại
     */
	public static void write(Object packageObj, Object object, String name, String directory)
    {
		String nameFileJavaPackage = packageObj.getClass().getName().replaceAll("\\.", "/");

		if (directory == null)
		{
			directory = "C:/RunDemo/log/Parameter/" + new File(nameFileJavaPackage).getParent();
		}
    	Gson gson = new Gson();
		String content = gson.toJson(object);
    	StringBuilder stringBuilder = new StringBuilder();
    	stringBuilder.append(content);
        stringBuilder.append("\n");


            // Kiểm tra tạo file TraceLog_yyyyMMdd.csv trong directory,
            // Tạo mới nếu chưa có
            String filePath = directory + "/" + name;
            File fileWrite = new File(filePath);
            String jsonString = gson.toJson(object);

            writeLog(jsonString, fileWrite.getParent(), fileWrite.getName());

    }

	public static void logDatabase(Object object)
	{
		String pathFile = "C:/RunDemo/log/DB/";
		boolean isFunction = false;
		String nameFunc = object.getClass().getEnclosingMethod().getName();

		BufferedReader bufferedReader = null;
		String nameFileJavaPackage = object.getClass().getName().replaceAll("\\.", "/");
//		String nameFileJava = "D:/stagia2/stagia2_svn/module/src/java/"+nameFileJavaPackage;
		String nameFileJava = "E:/stagia/modules/src/java/"+nameFileJavaPackage;
		String nameFileJavaOut = nameFileJava.split("\\$")[0] + ".java";

//		StringBuilder stringBuilder = new StringBuilder();
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(nameFileJavaOut)));
			String line = bufferedReader.readLine();
//			stringBuilder.append(line);
			while (line != null) {
//				stringBuilder.append(line);
				line = bufferedReader.readLine();
				if(line.contains(nameFunc))
				{
					isFunction = true;
				}
				if(isFunction)
				{
					if(line.contains("return")){
						writeLog(line, pathFile, nameFunc);
						return;
					};
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		 finally {

			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
     * Hàm thực hiện ghi file
     * @param object nội dung cần ghi
     * @param directory đường dẫn thư mục chứ file ghi
     * @return true thành công, false thất bại
     */
	static void writeLog(String text, String path, String name)
    {
    	StringBuilder stringBuilder = new StringBuilder();
    	stringBuilder.append(text);
        stringBuilder.append("\n");

    	FileOutputStream fileOutputStream = null;

        try
        {
            // Kiểm tra tạo thư mục lưu ghi file,
            // Tạo mới nếu chưa có
            File fileDir = new File(path);
            if (fileDir.exists() == false)
            {
                fileDir.mkdirs();
            }
            // Kiểm tra tạo file TraceLog_yyyyMMdd.csv trong directory,
            // Tạo mới nếu chưa có
            String filePath = path + "/" + name;
            File fileWrite = new File(filePath);
            if (fileWrite.exists() == false)
            {
                fileWrite.createNewFile();
            }

            // Ghi file TraceLog với nội dung là content
            fileOutputStream = new FileOutputStream(filePath, true);
            fileOutputStream.write(stringBuilder.toString().getBytes("UTF-8"));
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
        }
        finally
        {
            try
            {
                if (fileOutputStream != null)
                {
                    fileOutputStream.close();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();;
            }
        }
    }
}
