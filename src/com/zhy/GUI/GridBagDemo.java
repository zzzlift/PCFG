package com.zhy.GUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class GridBagDemo extends JFrame {
	
	
	//1. input components of the panel
	public static JTextField queryText = new JTextField("fish people fish tanks",30);
	public static JTextArea outputText = new JTextArea();//杈撳嚭SPARQL鏌ヨ缁撴灉
	public static JTextArea addRule = new JTextArea();
	
	public static JButton queryButton = new JButton("Parse");//杈撳叆鍦板潃鍚庣洿鎺ヨ浆鍒板搴旀枃浠�
	public static JButton aboutUs = new JButton("Tutorials");
	public static JButton addRuleBut = new JButton("Add Rules");
	//1.1 other paras
	static final int WIDTH = 600;//瀵硅瘽妗嗗ぇ灏�
	static final int HEIGHT = 600;	
	
	//
	//private static ClickButton clickButton = new ClickButton();
	
	
	
	public GridBagDemo(){
		init();
		this.setSize(WIDTH,HEIGHT);
//		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
		
	
	public void init(){
		this.setTitle("Natural language parser");
		Toolkit kit=Toolkit.getDefaultToolkit(); //瀵硅瘽妗嗙殑浣嶇疆
        Dimension screenSize=kit.getScreenSize();
        int width=screenSize.width;  
        int height=screenSize.height;  
        int x=(width-WIDTH)/2;  
        int y=(height-HEIGHT)/2;  
        this.setLocation(x,y); 
        
        /*杩欎竴閮ㄥ垎涓昏鏄粍浠剁殑瀹炰緥鍖�
         * 
         */
        
        //ActionSele is a class
		ActionListener queryAction = new ActionSele(this);
		queryButton.addActionListener(queryAction);
		//ActionAddRule
		ActionListener addRule1 = new AddRule(this);
		addRuleBut.addActionListener(addRule1);

		
		//鐢ㄦ潵鏀剧疆SPARQL缁撴灉鏂囨湰妗�
		JPanel j11 = new JPanel();
		j11.setLayout(new GridLayout(1,1));//鐢ㄦ潵鏀剧疆SPARQL鏂囨湰妗�
		j11.setBorder(BorderFactory.createTitledBorder("Output"));//鏍囬
		j11.add(new JScrollPane(outputText));//SPARQL鏂囨湰妗嗙殑婊氬姩鏉★紝褰撳唴瀹硅秴鍑烘椂浣跨敤				
		
		//鐢ㄦ潵鏀剧疆杈撳嚭缁撴灉鏂囨湰妗�		
		JPanel j2 = new JPanel();
//		GridBagConstraints gc2 = new GridBagConstraints();
		j2.setLayout(new GridLayout(1,1));//鐢ㄦ潵鏀剧疆SPARQL鏂囨湰妗�
		j2.setBorder(BorderFactory.createTitledBorder("Add Rules"));//鏍囬
		j2.add(new JScrollPane(addRule));
//		j2.add(addRuleBut);

		
		
		
//		gc2.gridx = 0;
//		gc2.gridy = 1;
		
		//clickButton.clickAboutUsButton(aboutUs,this);//娣诲姞go鎸夐挳浜嬩欢
		ActionListener aboutUsAction = new ActionAboutUs(this);
		aboutUs.addActionListener(aboutUsAction);
		GridBagLayout gr = new GridBagLayout();
		
		
//		JPanel j3 = new JPanel();
//		j3.add(aboutUs);
		
		this.setLayout(gr);//鍏冪礌娣诲姞杩涘幓
		this.add(queryText);
		this.add(queryButton);
		this.add(j11);
		this.add(j2);
		this.add(addRuleBut);
		this.add(aboutUs);
		
		/*杩欎竴閮ㄥ垎涓昏鏄帓鐗堝竷灞�
		 * 
		 */
		GridBagConstraints gc = new GridBagConstraints();
//		gc.fill = GridBagConstraints.BOTH;
		gc.gridwidth = 1;//缁勪欢鍗犳嵁鐨勬牸鏁�
		gc.weighty = 30;//绗竴琛岀殑绾靛悜鏉冮噸锛屽叾褰卞搷涓�鐩村瓨鍦ㄧ洿鍒颁笅娆′慨鏀�
		gc.weightx = 0;
//		gc.insets = new Insets(-20, 0, -60,0);//涓婂乏涓嬪彸锛屽叾褰卞搷涓�鐩村瓨鍦ㄧ洿鍒颁笅娆′慨鏀�

		gc.gridwidth = 8;
		gc.fill = GridBagConstraints.HORIZONTAL;//妯悜鎷変几
		gr.setConstraints(queryText,gc);
		gc.gridwidth = 0;
		gr.setConstraints(queryButton,gc);
	
		
		
		gc.gridwidth = 1;//缁勪欢鍗犳嵁鐨勬牸鏁�
		gc.weighty = 30;//绗竴琛岀殑绾靛悜鏉冮噸锛屽叾褰卞搷涓�鐩村瓨鍦ㄧ洿鍒颁笅娆′慨鏀�
		gc.weightx = 0;
		gc.gridwidth = 0;
		gc.weighty = 120;
		gc.weightx = 0;
		gc.fill = GridBagConstraints.BOTH;
		gr.setConstraints(j11,gc);
		gr.setConstraints(j2,gc);
		
		
		gc.fill = GridBagConstraints.NONE;
		gc.weighty = 10;
		gc.weightx = 0;
		gc.insets = new Insets(0, 0, 0,0);//涓婂乏涓嬪彸瑕佸洖褰�0锛屼笉鐒朵竴鐩村瓨鍦ㄤ細褰卞搷鍚庨潰
		gc.gridwidth = 1; 
//		gc.anchor = GridBagConstraints.NORTHWEST;
		gr.setConstraints(addRuleBut,gc);	
		gc.gridwidth = 0; 
		gc.weightx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gr.setConstraints(aboutUs,gc);	
	}
		

	public static JTextArea getAddRule() {
		return addRule;
	}


	public static void setAddRule(JTextArea addRule) {
		GridBagDemo.addRule = addRule;
	}


	// some getter and setter
	public static JTextField getPathInput() {
		return queryText;
	}

	public static void setPathInput(JTextField pathInput) {
		GridBagDemo.queryText = pathInput;
	}

	public static JTextArea getOutputText() {
		return outputText;
	}

	public static void setOutputText(JTextArea outputText) {
		GridBagDemo.outputText = outputText;
	}

	public static void main(String args[]){
		GridBagDemo demo = new GridBagDemo();
	}
	public static JButton getQueryButton() {
		return queryButton;
	}


	public static void setQueryButton(JButton queryButton) {
		GridBagDemo.queryButton = queryButton;
	}
	public static JTextField getQueryText() {
		return queryText;
	}

	public static void setQueryText(JTextField queryText) {
		GridBagDemo.queryText = queryText;
	}
	
}
