package EBM_tool.gui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Utils {
	public static String getFileExtension(String name) {
		int pointIndex = name.lastIndexOf(".");
		
		if(pointIndex == - 1) {
			return null;
		}
		
		if(pointIndex == name.length() - 1) {
			return null;
		}
		
		return name.substring(pointIndex + 1, name.length());
	}
	
	public static InputStream byteToStream(byte[] myBytes) {
		InputStream stream = new ByteArrayInputStream(myBytes);
		//System.out.println(myBytes.length);
		return stream;
	}
}
