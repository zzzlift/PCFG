package com.zhy.GUI;


public class matrix_String {
	public static String mat_Str(String[][] matrix){
	
		
		int row = matrix.length;
		int col = row;
		StringBuffer mat = new StringBuffer();
		for(int i = 0;i<row;i++){
			mat.append("\n");
			for(int j = 0;j<col;j++){
				String ele = "0";
				if(matrix[i][j]!=null){
					ele = matrix[i][j];
				}
				mat.append(ele+"\t");
			}
		}
		String matStr = mat.toString();
		
		return matStr;
	}
}
