package com.zhy.cyk;

import com.zhy.dataStructure.BackList;
import com.zhy.dataStructure.MyStack;
import com.zhy.dataStructure.NodeList;
import com.zhy.file.FileOperator;

public class backTrace {
	public static BackList[][] backTrace1 = null;
	public static String[][] preIndex = null;
	public static MyStack stack = new MyStack();
	public static String[][] traceTree(NodeList[][] matrix){
		int row = matrix.length;
		int col = row;
		int count = 0;
		backTrace1 = cykImplement2.backTrace;
		preIndex = new String[row][col];
		preIndex[0][col-1] = "S";
		
		int index = matrix[0][col-1].getLeftList().indexOf("S");
		String rightRule = matrix[0][col-1].getRightList().get(index);
		String[] ruleArr = rightRule.split(",");
		String first = ruleArr[0];
		String second = ruleArr[1];
		
		String backIndex = backTrace1[0][col-1].getIndex().get(index);
		String[] backArr = backIndex.split(",");
		int begin = Integer.parseInt(backArr[0]);
		int mid = Integer.parseInt(backArr[1]);
		int end = Integer.parseInt(backArr[2]);
		
		for(int i=0;i<2;i++){
			String ind_Content = backArr[i]+","+backArr[i+1]+","+ruleArr[i];
			stack.push(ind_Content);
		}
		
		while(!stack.isEmpty()){
			String top = stack.pop();
System.out.println("pop "+top);
			String[] topArr = top.split(",");
			int index_x = Integer.parseInt(topArr[0]);
			int index_y = Integer.parseInt(topArr[1]);
			String tag = topArr[2];
			//add to matrix
			preIndex[index_x][index_y] = tag;
			
			//find next depth node
			int indextag = matrix[index_x][index_y].getLeftList().indexOf(tag);
			
			String tag_rightRule = matrix[index_x][index_y].getRightList().get(indextag);
			String[] tag_rightRuleArr = tag_rightRule.split(",");
			
			
			if(tag_rightRuleArr.length<2) continue;
			String tag_first = tag_rightRuleArr[0];
			//null here
			String tag_second = tag_rightRuleArr[1];
			
			String tag_back = backTrace1[index_x][index_y].getIndex().get(indextag);
			String[] tag_backArr = tag_back.split(",");
			int tagBegin = Integer.parseInt(tag_backArr[0]);
			int tagMid = Integer.parseInt(tag_backArr[1]);
			int tagEnd = Integer.parseInt(tag_backArr[2]);
			//push
			for(int j=0;j<2;j++){
				String tag_ind_Content = tag_backArr[j]+","+tag_backArr[j+1]+","+tag_rightRuleArr[j];
				stack.push(tag_ind_Content);
System.out.println("push "+tag_ind_Content);
			}
FileOperator.writeFile("-----String[][]------", cykMain.matrixOut);
cykMain.printStrMat(preIndex);
		}
		
	return preIndex;	
	}
	/**
	 * find predicate
	 * @param matrix
	 */
	public static void predicate(NodeList[][] matrix, String sentence){
		int row = matrix.length;
		int col = row;
		int count = 0;
		backTrace1 = cykImplement2.backTrace;
		preIndex = new String[row][col];
		preIndex[0][col-1] = "S";
		
		int index = matrix[0][col-1].getLeftList().indexOf("S");
		String rightRule = matrix[0][col-1].getRightList().get(index);
		String[] ruleArr = rightRule.split(",");
		String first = ruleArr[0];
		String second = ruleArr[1];
		
		String backIndex = backTrace1[0][col-1].getIndex().get(index);
		String[] backArr = backIndex.split(",");
		int begin = Integer.parseInt(backArr[0]);
		int mid = Integer.parseInt(backArr[1]);
		int end = Integer.parseInt(backArr[2]);
		
		int rowFinal = 0;
		int colFinal = 0;
		for(int i=0;i<row;i++){
			if(backTrace1[begin][mid]!=null){
			//just one situation can be choosed
			if(first.equals("V")){
				index = matrix[begin][mid].getLeftList().indexOf("V");
				rightRule = matrix[begin][mid].getRightList().get(index);
				ruleArr = rightRule.split(",");
				if(ruleArr.length==2){
					first = ruleArr[0];
					second = ruleArr[1];
					backIndex = backTrace1[begin][mid].getIndex().get(index);
					backArr = backIndex.split(",");
					begin = Integer.parseInt(backArr[0]);
					mid = Integer.parseInt(backArr[1]);
					end = Integer.parseInt(backArr[2]);
				}
				rowFinal = begin;
				colFinal = mid;
			}
			else {
				if (first.equals("VP")){
					index = matrix[begin][mid].getLeftList().indexOf("VP");
					rightRule = matrix[begin][mid].getRightList().get(index);
					ruleArr = rightRule.split(",");
					if(ruleArr.length==2){
						first = ruleArr[0];
						second = ruleArr[1];
						backIndex = backTrace1[begin][mid].getIndex().get(index);
						backArr = backIndex.split(",");
						begin = Integer.parseInt(backArr[0]);
						mid = Integer.parseInt(backArr[1]);
						end = Integer.parseInt(backArr[2]);
					}
					rowFinal = begin;
					colFinal = mid;
				} else {
					if (second.equals("V")){
						index = matrix[mid][end].getLeftList().indexOf("V");
						rightRule = matrix[mid][end].getRightList().get(index);
						ruleArr = rightRule.split(",");
						if(ruleArr.length==2){
							first = ruleArr[0];
							second = ruleArr[1];
							backIndex = backTrace1[mid][end].getIndex().get(index);
							backArr = backIndex.split(",");
							begin = Integer.parseInt(backArr[0]);
							mid = Integer.parseInt(backArr[1]);
							end = Integer.parseInt(backArr[2]);
						}
						rowFinal = mid;
						colFinal = end;
					} else {
						if(second.equals("VP")){
							index = matrix[mid][end].getLeftList().indexOf("VP");
							rightRule = matrix[mid][end].getRightList().get(index);
							ruleArr = rightRule.split(",");
							if(ruleArr.length==2){
								first = ruleArr[0];
								second = ruleArr[1];
								backIndex = backTrace1[mid][end].getIndex().get(index);
								backArr = backIndex.split(",");
								begin = Integer.parseInt(backArr[0]);
								mid = Integer.parseInt(backArr[1]);
								end = Integer.parseInt(backArr[2]);
							}
							rowFinal = mid;
							colFinal = end;
						}
					}
				}
			}
		}else{
			i=row;
		}
		}
System.out.println(rowFinal+","+colFinal);
System.out.println("\n*******************predicate*******************");
System.out.println("Input sentence: "+sentence);
String[] senArr = sentence.split(" ");
int predicateInd = colFinal - 1;
System.out.println("The predicate: "+senArr[predicateInd]);
System.out.println("The index: " + colFinal);
	}
}
