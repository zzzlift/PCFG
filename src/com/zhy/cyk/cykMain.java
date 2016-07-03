package com.zhy.cyk;

import java.io.BufferedReader;
import java.io.IOException;

import com.zhy.dataStructure.BackList;
import com.zhy.dataStructure.NodeList;
import com.zhy.file.FileOperator;

public class cykMain {
	public static String rulePath = "data/rules/rules.txt";
	public static String inputTextPath = "data/corpus/example.txt";
	public static String matrixOut = "data/matrixOut/matrixOut.txt";
	public static String[][] parserMatrix(String sentence) throws IOException{
		BufferedReader br = FileOperator.readFile(inputTextPath);
		String line = sentence;
//		while((line = br.readLine())!=null){
			String tempLine = line.trim();
//			if((tempLine == null)|(tempLine.equals(""))) continue;
			String[][] matrixStr = cykImplement2.getMatrix(tempLine);
			NodeList[][] matrixNode = cykImplement2.generateFirstLayer(matrixStr, rulePath);
//System.out.println("matrix"+matrixNode[0][1].getLeftList().get(0));
			int row = matrixNode.length;
			int col = row;
			BackList[][] backMatrix = new BackList[row][col];
			
			for(int i=0;i<row;i++){
				for(int j = 0;j<col;j++){
					BackList backli = new BackList();
					backMatrix[i][j] = backli;
				}
			}
			NodeList[][] matrixEnd = cykImplement2.binaryRule(matrixNode, rulePath, backMatrix);
//			for(int i=0;i<matrixEnd[0][4].getLeftList().size();i++){
//System.out.println("lastOne "+matrixEnd[0][4].getLeftList().size()+" "+matrixEnd[0][4].getLeftList().get(i));
//			}
			BackList[][] back = cykImplement2.backTrace;
			String[][] indMatrix = backTrace.traceTree(matrixEnd);
			//find predicate
//			predicate(indMatrix,sentence);
			backTrace.predicate(matrixEnd,sentence);
			
			FileOperator.writeFile("-----String[][]------", matrixOut);
			printStrMat(indMatrix);
			
//		}
		return indMatrix;
	}
	
	/**
	 * output the node matrix to file
	 * @param matrix
	 */
	public static void printMatrix(NodeList[][] matrix){

		int count = matrix.length;
		int row = count;
		int col = row;
		for(int i=0;i<row; i++){
		StringBuffer rowBuffer = new StringBuffer();
			for(int j=0;j<col;j++){
				int trans = 0;
				if(matrix[i][j].getLeftList()!=null){
					trans = matrix[i][j].getLeftList().size();
				}
				rowBuffer.append(trans+"\t");
			}
			String rowString = rowBuffer.toString().trim();
			FileOperator.writeFile(rowString, matrixOut);

		}
	}
	/**
	 * print String matrix
	 * @param matrix
	 */
	public static void printStrMat(String[][] matrix){

		int count = matrix.length;
		int row = count;
		int col = row;
		for(int i=0;i<row; i++){
		StringBuffer rowBuffer = new StringBuffer();
			for(int j=0;j<col;j++){
				String trans = "0";
				if(matrix[i][j]!=null){
					trans = matrix[i][j];
				}
				rowBuffer.append(trans+"\t");
			}
			String rowString = rowBuffer.toString().trim();
			FileOperator.writeFile(rowString, matrixOut);

		}
	}
	/**
	 * find predicate of sentence
	 * @param matrix
	 * @param sentence
	 */
	public static void predicate(String[][] matrix,String sentence){
		int count = matrix.length;
		int row = count;
		int col = row;
		String[] senArr = sentence.split(" ");
		
		for(int i = 0;i<row-1;i++){
			String pos = matrix[i][i+1];
			if(pos.equals("V")|pos.equals("VP")){
System.out.println("\n*******************predicate*******************");
System.out.println("Input sentence: "+sentence);
int ind = i+1;
System.out.println("The predicate: "+senArr[i]);
System.out.println("The index: " + ind);
			}
		}
	}
}
