package com.zhy.test;

import java.util.ArrayList;
import java.util.List;

import com.zhy.dataStructure.NodeList;

public class testNodeList {
	public static void main(String[] args){
		List<String> list = new ArrayList<String>();
		list.add("hello");
//		NodeList noLi = new NodeList();
//		NodeList noLi1 = new NodeList();
		NodeList[][] noM = new NodeList[10][10];
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				NodeList noLi = new NodeList();
				noM[i][j]=noLi;
			}
		}
		
//		noLi.setLeftList(list);
//		noM[1][1] = noLi;
//		noM[1][2] = noLi1;
		noM[1][1].setLeftList(list);
System.out.println(noM[2][2].getLeftList().size());
		
	}
}
