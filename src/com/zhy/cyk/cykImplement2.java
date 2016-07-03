package com.zhy.cyk;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhy.dataStructure.BackList;
import com.zhy.dataStructure.NodeList;
import com.zhy.file.FileOperator;

public class cykImplement2 {
	/**
	 * generate the matrix
	 * @param str original sentence
	 * @return String[][]
	 */
	public static String[][] getMatrix(String stence){
		String[] sentArray = stence.split(" ");
		int sentLength = sentArray.length;
		String[][] sentMatrix= new String[sentLength+1][sentLength+1];
		for(int i = 1;i < sentLength+1;i++){
			sentMatrix[i][i] = sentArray[i-1];
		}
		return sentMatrix;
	}
	/**
	 * generate the first layer in matrix
	 * @param matrix: the first layer matrix
	 * @param rulePath: the rule text file
	 * @return NodeList[][]
	 */
	public static NodeList[][] generateFirstLayer(String[][] matrix, String rulePath){
		int row = matrix.length;
		int col = row;
		
		List<String> rules = ruleList(rulePath);
		NodeList[][] nodeMatrix = new NodeList[row][col];
		//Initialize the element
		for(int i = 0; i < row; i++){
			for(int j = 0;j < col; j++){
				NodeList noli = new NodeList();
				nodeMatrix[i][j] = noli;
			}
		}
		for(int i = 1;i < row;i++){
			List<String> leftList = new ArrayList<String>();
			List<String> rightList = new ArrayList<String>();
			List<Double> proList = new ArrayList<Double>();
			for(int j = 0; j < rules.size();j++){
				//split every rule
				String[] rule_pro = rules.get(j).split("\t");
				String rule = rule_pro[0];
				String proStr = rule_pro[1];
				double pro = Double.parseDouble(proStr);
				String[] ruleBinary = rule.split("-->");
				String ruleLeft = ruleBinary[0].trim();
				String ruleRight = ruleBinary[1].trim();
				String[] ruleRightArr = ruleRight.split(",");
				
				String word = matrix[i][i];
				if(ruleRightArr.length==1){
					//
					if(word.equals(ruleRightArr[0])){
System.out.println(word);
						//save content
						leftList.add(ruleLeft);
						rightList.add(ruleRight);
						proList.add(pro);
					}
				}
			}
			//set list to NodeList
//System.out.println(nodeMatrix[i-1][i].getLeftList().size());
//System.out.println(nodeMatrix[i+2][i].getLeftList().size());
//			nodeMatrix[i-1][i].setLeftList(leftList);

			nodeMatrix[i-1][i].setLeftList(leftList);
//System.out.println(nodeMatrix[i-1][i].getLeftList().size());
//System.out.println(nodeMatrix[i+2][i].getLeftList().size());
			nodeMatrix[i-1][i].setRightList(rightList);
			nodeMatrix[i-1][i].setProList(proList);
//FileOperator.writeFile("2", cykMain.matrixOut);
//cykMain.printMatrix(nodeMatrix);
			//uniaryRule to update
			nodeMatrix[i-1][i] = uniaryRule(nodeMatrix[i-1][i], rulePath);
//FileOperator.writeFile("3", cykMain.matrixOut);
//cykMain.printMatrix(nodeMatrix);
System.out.println(nodeMatrix[i-1][i].getLeftList().size());
		}
//cykMain.printMatrix(nodeMatrix);
		return nodeMatrix;
	}
	/**
	 * uniaryRule
	 * 1.find the new rule
	 * 2.keep the most pro rule, removing those less and overlap
	 * @param nodeList: a nodeList
	 * @param rulePath: the rule text file
	 * @return NodeList
	 */
	public static NodeList uniaryRule(NodeList nodeList, String rulePath){
		int count = nodeList.getRightList().size();
		List<String> rules = ruleList(rulePath);
		List<String> leftList1 = nodeList.getLeftList();
		List<String> rightList1 = nodeList.getRightList();
		List<Double> proList1 = nodeList.getProList();
System.out.println("before uniary "+leftList1.size());
		//add new rule to nodeList
		for(int i = 0;i < count;i++){
			
			for(int j = 0; j < rules.size();j++){
				//split every rule
				String[] rule_pro = rules.get(j).split("\t");
				String rule = rule_pro[0];
				String proStr = rule_pro[1];
				double pro = Double.parseDouble(proStr);
				String[] ruleBinary = rule.split("-->");
				String ruleLeft = ruleBinary[0].trim();
				String ruleRight = ruleBinary[1].trim();
				String[] ruleRightArr = ruleRight.split(",");
				//add to list if exist new
				String leftList = nodeList.getLeftList().get(i);
				if(ruleRightArr.length==1){
					if(ruleRightArr[0].equals(leftList)){
						leftList1.add(ruleLeft);
						rightList1.add(ruleRight);
						double previousPro = nodeList.getProList().get(i);
						double currentPro = previousPro*pro;
						proList1.add(currentPro);
						
					}
				}
			}
		}
		nodeList.setLeftList(leftList1);
		nodeList.setRightList(rightList1);
		nodeList.setProList(proList1);
		// update nodeList
		Map<String,Integer> map = new HashMap<String,Integer>();
		int currentCount = nodeList.getLeftList().size();
		for(int i=0;i < currentCount;i++){
			String keyword = nodeList.getLeftList().get(i);
			if(map.containsKey(keyword)){
				map.put(keyword, map.get(keyword)+1);
			} else {
				map.put(keyword, 1);
			}
		}
		List<String> leftList2 = nodeList.getLeftList();
		List<String> rightList2 = nodeList.getRightList();
		List<Double> proList2 = nodeList.getProList();
		
		List<String> leftList21 = new ArrayList<String>();
		List<String> rightList21 = new ArrayList<String>();
		List<Double> proList21 = new ArrayList<Double>();
		
		for(Map.Entry<String, Integer> entry : map.entrySet()){
			String key = entry.getKey();
			int value = entry.getValue();
			//find the best pro, delete others
			
			if(value > 1){
				double proMax=0;
				int index_k = 0;
					for(int k=0;k<currentCount;k++){
						if(nodeList.getLeftList().get(k).equals(key)){
							double currentPro = nodeList.getProList().get(k);
							if(currentPro > proMax){
								index_k = k;
								proMax = currentPro;
							} 
							
						}
					}
					leftList21.add(leftList2.get(index_k));
					rightList21.add(rightList2.get(index_k));
					proList21.add(proList2.get(index_k));
			} else {
				for(int v1 = 0; v1<currentCount;v1++){
					if(nodeList.getLeftList().get(v1).equals(key)){
						leftList21.add(leftList2.get(v1));
						rightList21.add(rightList2.get(v1));
						proList21.add(proList2.get(v1));
					}
				}
			}
		}
		nodeList.setLeftList(leftList21);
		nodeList.setRightList(rightList21);
		nodeList.setProList(proList21);
System.out.println("after uniary "+nodeList.getLeftList().size());
		return nodeList;
	}
	/**
	 * uniary rule is used to deal with the back list problem
	 * @param nodeList: a nodeList
	 * @param rulePath: rule text
	 * @return NodeList
	 */
	public static NodeList uniaryRuleBackList(NodeList nodeList, String rulePath, BackList backNodeList){

		int count = nodeList.getRightList().size();
		List<String> rules = ruleList(rulePath);
		List<String> leftList1 = nodeList.getLeftList();
		List<String> rightList1 = nodeList.getRightList();
		List<Double> proList1 = nodeList.getProList();
		List<String> backList1 = backNodeList.getIndex();
		//add new rule to nodeList
		for(int i = 0;i < count;i++){
			for(int j = 0; j < rules.size();j++){
				//split every rule
				String[] rule_pro = rules.get(j).split("\t");
				String rule = rule_pro[0];
				String proStr = rule_pro[1];
				double pro = Double.parseDouble(proStr);
				String[] ruleBinary = rule.split("-->");
				String ruleLeft = ruleBinary[0].trim();
				String ruleRight = ruleBinary[1].trim();
				String[] ruleRightArr = ruleRight.split(",");
				//add to list if exist new
				String leftList = nodeList.getLeftList().get(i);
				if(ruleRightArr.length==1){
					if(ruleRightArr[0].equals(leftList)){
						leftList1.add(ruleLeft);
						//add the origal one not the new
//						rightList1.add(ruleRight);
						rightList1.add(nodeList.getRightList().get(i));//chage
						double previousPro = nodeList.getProList().get(i);
						double currentPro = previousPro*pro;
						nodeList.getProList().add(currentPro);
						proList1.add(currentPro);
						backList1 = backNodeList.getIndex();
						backList1.add(backList1.get(i));
					}
				}
				
			}
		}
		nodeList.setLeftList(leftList1);
		nodeList.setRightList(rightList1);
		nodeList.setProList(proList1);
		backNodeList.setIndex(backList1);
System.out.println("mid of change "+nodeList.getLeftList().size());
		// update nodeList
		Map<String,Integer> map = new HashMap<String,Integer>();
		int currentCount = nodeList.getLeftList().size();
		for(int i=0;i < currentCount;i++){
			String keyword = nodeList.getLeftList().get(i);
			if(map.containsKey(keyword)){
				map.put(keyword, map.get(keyword)+1);
			} else {
				map.put(keyword, 1);
			}
		}
		List<String> leftList2 = nodeList.getLeftList();
		List<String> rightList2 = nodeList.getRightList();
		List<Double> proList2 = nodeList.getProList();
		List<String> backList2 = backNodeList.getIndex();
		//select the right index
		List<String> leftList21 = new ArrayList<String>();
		List<String> rightList21 = new ArrayList<String>();
		List<Double> proList21 = new ArrayList<Double>();
		List<String> backList21 = new ArrayList<String>();
		
		for(Map.Entry<String, Integer> entry : map.entrySet()){
			String key = entry.getKey();
			int value = entry.getValue();
			//find the best pro, delete others
			
			if(value > 1){
				double proMax=0;
				int index_k = 0;
					for(int k=0;k<currentCount;k++){
						if(nodeList.getLeftList().get(k).equals(key)){
							double currentPro = nodeList.getProList().get(k);
							if(currentPro > proMax){
								index_k = k;
								proMax = currentPro;
							} 
							
						}
					}
					leftList21.add(leftList2.get(index_k));
					rightList21.add(rightList2.get(index_k));
					proList21.add(proList2.get(index_k));
					backList21.add(backList2.get(index_k));
			} else {
				for(int v1 = 0; v1<currentCount;v1++){
					if(nodeList.getLeftList().get(v1).equals(key)){
						leftList21.add(leftList2.get(v1));
						rightList21.add(rightList2.get(v1));
						proList21.add(proList2.get(v1));
						backList21.add(backList2.get(v1));
					}
				}
			}
		}
		nodeList.setLeftList(leftList21);
		nodeList.setRightList(rightList21);
		nodeList.setProList(proList21);
		backNodeList.setIndex(backList21);
		return nodeList;
	}
	/**
	 * binary rule
	 * @param matrix: deal with the matrix
	 * @param rulePath: text file
	 * @param backMatrix: back track matrix
	 * @return
	 */
	public static BackList[][] backTrace = null;
	public static NodeList[][] binaryRule(NodeList[][] matrix, String rulePath, BackList[][] backMatrix){
		int row = matrix.length;
		List<String> rules = ruleList(rulePath);
		for(int layer=2;layer<row;layer++){
			int end = layer;
			for(int begin = 0;begin<row-layer;begin++){
				if(end<row){
					List<String> leftList3 = new ArrayList<String>();
					List<String> rightList3 = new ArrayList<String>();
					List<Double> proList3 = new ArrayList<Double>();
					List<String> backList3 = new ArrayList<String>();
					for(int mid = begin+1;mid < end; mid++){
System.out.println("("+begin+","+mid+")"+"("+mid+","+end+")"+"="+"("+begin+","+end+")");
						//compare every rule
						for(int j = 0; j < rules.size();j++){
							//split every rule
							String[] rule_pro = rules.get(j).split("\t");
							String rule = rule_pro[0];
							String proStr = rule_pro[1];
							double pro = Double.parseDouble(proStr);
							String[] ruleBinary = rule.split("-->");
							String ruleLeft = ruleBinary[0].trim();
							String ruleRight = ruleBinary[1].trim();
							String[] ruleRightArr = ruleRight.split(",");
							//
							if(ruleRightArr.length==2){
								String first = ruleRightArr[0];
								String second = ruleRightArr[1];
								int listLeftSize = matrix[begin][mid].getLeftList().size();
								int listRightSize = matrix[mid][end].getLeftList().size();
								//left node
								for(int leftNext = 0; leftNext < listLeftSize; leftNext++){
									String listLeft = matrix[begin][mid].getLeftList().get(leftNext);
									double leftPro = matrix[begin][mid].getProList().get(leftNext);
									//right node
									if(listLeft.equals(first)){
										for(int rightNext = 0; rightNext < listRightSize; rightNext++){
											String listRight = matrix[mid][end].getLeftList().get(rightNext);
											double rightPro = matrix[mid][end].getProList().get(rightNext);
											if(listRight.equals(second)){
												leftList3.add(ruleLeft);
												rightList3.add(ruleRight);
												double currentPro = leftPro*rightPro;
												proList3.add(currentPro);
												//save the previous index
												String backIndex = begin+","+mid+","+end;
												backList3.add(backIndex);
												
											}
										}
									}
								}
							}
						}
					}
					matrix[begin][end].setLeftList(leftList3);
					matrix[begin][end].setRightList(rightList3);
					matrix[begin][end].setProList(proList3);
					backMatrix[begin][end].setIndex(backList3);
					//because I need to deal with the back list
System.out.println("before uniary"+matrix[begin][end].getLeftList().size());
					matrix[begin][end] = uniaryRuleBackList(matrix[begin][end],rulePath,backMatrix[begin][end]);
FileOperator.writeFile("***", cykMain.matrixOut);
cykMain.printMatrix(matrix);
System.out.println("after uniary"+matrix[begin][end].getLeftList().size());
System.out.println("the element: "+matrix[begin][end].getLeftList().size()+" index: "+begin+","+end);
				end++;
				}

			}
		}
		backTrace = backMatrix;
		return matrix;
	}
	/**
	 * add rules to list
	 * @param the rule text file
	 * @return List<String>
	 * @throws IOException 
	 */
	public static List<String> ruleList(String rulePath){
		List<String> rules = new ArrayList<String>();
		BufferedReader br = FileOperator.readFile(rulePath);
		String line = null;
		try {
			while((line = br.readLine())!=null){
				String tempLine = line.trim();
				if((tempLine == null)|(tempLine.equals(""))) continue;
				rules.add(tempLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rules;
	}
}
