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

public class cykImplement {
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
		NodeList noli = new NodeList();
		for(int i = 0; i < row; i++){
			for(int j = 0;j < col; j++){
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
			nodeMatrix[i-1][i].setLeftList(leftList);
			nodeMatrix[i-1][i].setRightList(rightList);
			nodeMatrix[i-1][i].setProList(proList);
			//uniaryRule to update
			nodeMatrix[i-1][i] = uniaryRule(nodeMatrix[i-1][i], rulePath);
System.out.println(nodeMatrix[i-1][i].getLeftList().size());
		}
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
		for(Map.Entry<String, Integer> entry : map.entrySet()){
			String key = entry.getKey();
			int value = entry.getValue();
			//find the best pro, delete others
			if(value > 1){
				double proMax=0;
				for(int j=0;j<value;j++){
					for(int k=0;k<currentCount;k++){
						if(nodeList.getLeftList().get(k).equals(key)){
							double currentPro = nodeList.getProList().get(k);
							if(currentPro < proMax){
								leftList2.remove(k);
								rightList2.remove(k);
								proList2.remove(k);
							} else {
								proMax = currentPro;
							}
						}
					}
					
					
				}
			}
			
			
		}
		nodeList.setLeftList(leftList2);
		nodeList.setRightList(rightList2);
		nodeList.setProList(proList2);
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
						rightList1.add(ruleRight);
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
		for(Map.Entry<String, Integer> entry : map.entrySet()){
			String key = entry.getKey();
			int value = entry.getValue();
			//find the best pro, delete others
			if(value > 1){
				double proMax=0;
				for(int j=0;j<value;j++){
					for(int k=0;k<currentCount;k++){
						if(nodeList.getLeftList().get(k).equals(key)){
							double currentPro = nodeList.getProList().get(k);
							if(currentPro < proMax){
								leftList2.remove(k);
								rightList2.remove(k);
								proList2.remove(k);
								//remove the false back trace
								backList2.remove(k);
							} else {
								proMax = currentPro;
							}
						}
					}
				}
			}
		}
		nodeList.setLeftList(leftList2);
		nodeList.setRightList(rightList2);
		nodeList.setProList(proList2);
		backNodeList.setIndex(backList2);
		return nodeList;
	}
	/**
	 * binary rule
	 * @param matrix: deal with the matrix
	 * @param rulePath: text file
	 * @param backMatrix: back track matrix
	 * @return
	 */
	public static NodeList[][] binaryRule(NodeList[][] matrix, String rulePath, BackList[][] backMatrix){
		int row = matrix.length;
		List<String> rules = ruleList(rulePath);
		for(int layer=2;layer<row;layer++){
			for(int begin = 0;begin<row-layer;begin++){
				for(int end = layer;end<5;end++){
					List<String> leftList3 = new ArrayList<String>();
					List<String> rightList3 = new ArrayList<String>();
					List<Double> proList3 = new ArrayList<Double>();
					List<String> backList3 = new ArrayList<String>();
					for(int mid = begin+1;mid < end; mid++){
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
					matrix[begin][end] = uniaryRuleBackList(matrix[begin][end],rulePath,backMatrix[begin][end]);
System.out.println("the element: "+matrix[begin][end].getLeftList().size()+" index: "+begin+","+end);
				}

			}
		}
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
