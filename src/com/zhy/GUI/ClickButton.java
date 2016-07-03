package com.zhy.GUI;

import javax.swing.*;

import com.zhy.cyk.cykMain;
import com.zhy.file.FileOperator;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class ClickButton {
	
}

//
class ActionAboutUs implements ActionListener {
	GridBagDemo gridBagDemo;
	ActionAboutUs(GridBagDemo gridBagDemo){
		this.gridBagDemo = gridBagDemo;
	}

	public void actionPerformed(ActionEvent ae) {
		JOptionPane.showMessageDialog(gridBagDemo, "App Name: \n" +
				"Sentence Parsing Tool\n" +
				"Version: 1.0 (First created in 2015.05.02)\n" +
				"\n" +
				"Quick tutorial:\n" +
				"You first need to write down your sentence and click the parse button. \n"+
				"\n" +
				"Function:\n" +
				"1. Parsing the natural language sentences of any language, only if you add \n" +
				"the right rules, words and probability.\n" +
				"2. Adding new rules and new words in the parsing system.\n" +
				"\n" +
				"Author:\n" +
				"Hongyin Zhu (hongyin_zhu@163.com, Institute of Automation, UCAS )\n" +
				"","About Us",1);
				
	}

}
//add rule
class AddRule implements ActionListener{
	GridBagDemo gridBagDemo;
	AddRule(GridBagDemo gridBagDemo){
		this.gridBagDemo = gridBagDemo;
	}
	JTextArea ruleText = GridBagDemo.getAddRule();
	public void actionPerformed(ActionEvent ae) {
		GridBagDemo.getQueryButton().setEnabled(false);
		String rules = ruleText.getText().trim();
		rules = "\n"+rules;
		FileOperator.writeFile(rules, cykMain.rulePath);
System.out.print("Adding rules successfully");
JOptionPane.showMessageDialog(gridBagDemo,"Adding rules successfully");
		GridBagDemo.getQueryButton().setEnabled(true);
	}
}
	
	
// input and output
class ActionSele implements ActionListener {
	
	GridBagDemo gridBagDemo;
	ActionSele(GridBagDemo gridBagDemo){
		this.gridBagDemo = gridBagDemo;
	}
	JTextArea outputText = GridBagDemo.getOutputText();

	public void actionPerformed(ActionEvent ae) {
		GridBagDemo.getQueryButton().setEnabled(false);
		try{
			String sentence = GridBagDemo.getQueryText().getText().trim().toLowerCase();
			String res = null;
			if (sentence != null) {
				try {
					String[][] indMat = cykMain.parserMatrix(sentence);
					String resPar = matrix_String.mat_Str(indMat);
					String[] sentenceArr = sentence.split(" ");
					int sentSize = sentenceArr.length;
					StringBuffer original = new StringBuffer();
					for(int i=0;i<sentSize;i++){
						original.append(sentenceArr[i]+"\t");
					}
					String ori = original.toString().trim();
					String complete = "\n0\t"+ori;
					String res_sen = resPar+complete;
					
					outputText.setText(res_sen);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} 
		}
		finally{
			GridBagDemo.getQueryButton().setEnabled(true);
		}
	
	}
}