package com.ljheee.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class FindReplaceFrame extends JFrame {
	private JTextField input_keyword;
	private JLabel  jlebal_Msg;
	
	private int curPos = 0; //��ǰλ��
	private String keyWord = "";
	
	public FindReplaceFrame(final JTextArea text){
		this.setTitle("Find/Replace");
		this.setSize(290, 250);
		this.setLocationRelativeTo(text);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		JLabel lblFind = new JLabel("Find : ");
		input_keyword = new JTextField(12);
		
		JButton btnFindnext = new JButton("Find/next");
		
		jlebal_Msg = new JLabel("");
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblFind)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(input_keyword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
							.addComponent(btnFindnext))
						.addComponent(jlebal_Msg))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFind)
						.addComponent(input_keyword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnFindnext))
					.addGap(42)
					.addComponent(jlebal_Msg)
					.addContainerGap(119, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
		
		btnFindnext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(input_keyword.getText().length()<1){
					JOptionPane.showMessageDialog(FindReplaceFrame.this , "����Ϊ�գ�����������ؼ���...");
					return;
				}
				String content = text.getText();
				
				if(!keyWord.equals(input_keyword.getText())){//˵��--��һ���� ��ѯ
					curPos = 0;
					keyWord = input_keyword.getText();
				}else{
					curPos += keyWord.length();
				}
				
				String nextStr = "";
				int beginIndex = curPos;
				int endIndex = content.length();
				if( beginIndex < endIndex ){  //�ж��Ƿ����һ��
				
				nextStr = content.substring(beginIndex, endIndex);//��ȡʣ�µ��ַ���
				jlebal_Msg.setText("���鵽�� " + countMatch(keyWord, content));
				int offset = nextStr.indexOf(keyWord);
					if(offset >= 0){
						curPos += offset;     //��ǰλ�ü���ƫ��λ��
						text.select(curPos, curPos+keyWord.length());//ѡ�в��ҵ����ı�---��������ʾ
					}else{
						JOptionPane.showMessageDialog(FindReplaceFrame.this, "�Ѳ鵽  ���һ��...");
					}
				}else{
					if(countMatch(keyWord, content)==0){
						jlebal_Msg.setText("String not found!");
					}
					
				}
			}
		});
		
		
		this.setVisible(true);
	}
	/**
	 * ��content�ı���    ����keyWord�����ز��ҵ��ĸ���
	 * @param keyword
	 * @param content
	 * @return  ���ز��ҵ�keyWord�ĸ���
	 */
	protected int countMatch(String keyword, String content) {
		int count = 0;
		int curIndex = 0;
		String nextStr = content;
		int offset = nextStr.indexOf(keyword); //�´β��ҵ���ʼλ������ڵ�ǰλ�õ�ƫ��
		
		while( offset >= 0 ){
//			count++;
			offset = nextStr.indexOf(keyword);    //��������ڵ�ǰλ�õ�ƫ��
			if(offset!=-1){
				count++;
			}else{
				break;
			}
			curIndex += offset + keyword.length();//���µ�ǰ��ʼλ��
			if(curIndex >= content.length()) { //�Ѿ����ҵ����һ��
				count--;//TUDO���Ѿ���1Ҫ��һ������׼ȷ��
				break;
			}
			nextStr = content.substring(curIndex);//����һ���ҵ����ַ������ȡʣ�µ��ַ���
		}
		
//		while(curIndex <= content.length()){
//			offset = content.indexOf(keyword);
//			
//			if(offset!=-1){
//				count++;
//			}else{
//				break;
//			}
//			curIndex += offset;
//		}
		return count;
	}
}
