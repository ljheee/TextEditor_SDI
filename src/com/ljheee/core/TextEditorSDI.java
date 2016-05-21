package com.ljheee.core;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

/**
 * �ı��༭��--ʵ�ֲ˵����򿪼������ļ�
 * @author ljheee
 */
public class TextEditorSDI extends JFrame {
	public static final int  DefaultWidth=900;
	public static final int DefaultHeight=700;
	
    private JComboBox fontName;//ѡ����
    private JComboBox fontSize;//�ֺ�
    private JCheckBox fontStyle[];//���ζ�ѡ��
    private JCheckBoxMenuItem zxCheckboxMenuItem[]; //����  ��ѡ��˵����ӵ������Ρ������˵���
   
	private JMenuBar mainMenuBar;
	private  JTextArea contentArea;
	
	private JMenu myFile=new JMenu("�ļ�"); //���ļ����˵�
	private JMenuItem newFile=new JMenuItem("�½�");
	private JMenuItem open=new JMenuItem("��");
	private JMenuItem save=new JMenuItem("����");
	private JMenuItem printFile=new JMenuItem("��ӡ");
	private JMenuItem quitItem=new JMenuItem("�˳�");
	
	private JMenu Edition=new JMenu("�༭");//���༭���˵�
	CutAction cutAction=new CutAction();
	private JMenuItem cuts=new JMenuItem(cutAction);
	CopyAction copyAction=new CopyAction();
	private JMenuItem copy=new JMenuItem(copyAction);//Acyionʵ�ֹ���,���ݲ˵��еĹ���һ��
	PasteAction pasteAction=new PasteAction();
	private JMenuItem paste=new JMenuItem(pasteAction);
	private JMenuItem undoEdit=new JMenuItem("����");
	private JMenuItem delete=new JMenuItem("ɾ��");
	private JMenuItem findReplace=new JMenuItem("����/�滻");
	
	private JMenu style=new JMenu("��ʽ");     //����ʽ���˵�
	private JMenuItem font=new JMenuItem("����");
	private JMenu ziXing=new JMenu("����");     //���Ρ������˵�
	private JMenuItem color=new JMenuItem("��ɫ");
	
	private JMenu help=new JMenu("����");//���������˵�
	private JMenuItem helps=new JMenuItem("����");
	private JMenuItem about=new JMenuItem("����");
   
	private JMenuItem cut2=new JMenuItem(cutAction);//��ݲ˵�
	private JMenuItem copy2=new JMenuItem(copyAction);//Acyionʵ�ֹ���
	private JMenuItem paste2=new JMenuItem(pasteAction);
	
	private JFileChooser fileChooser = new JFileChooser();//�ļ�ѡ����
	private UndoManager undoManager = new UndoManager();
	
	private JLabel fileInfo = new JLabel("״̬��:");
	private JLabel pathInfo = new JLabel("  ");
	private JLabel timeInfo = new JLabel("  ");
	
	
	public  JTextArea getTextArea(){
		return contentArea;
	}
	
	public TextEditorSDI() throws IOException{     //���췽��
		super("TextEditor SDI");
		this.setSize(DefaultWidth, DefaultHeight);
		this.setBackground(Color.darkGray);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//�����ļ�����
		fileChooser.setFileFilter(new FileNameExtensionFilter("General", new String[]{"txt","java","html","class","xls","ppt","doc","docx","xml","exe"}));
		contentArea=new JTextArea();
		
		mainMenuBar=new JMenuBar();
		JToolBar toolBar=new JToolBar();
		this.setJMenuBar(mainMenuBar);//ΪJFrame��Ӳ˵���
		this.getContentPane().add(toolBar, BorderLayout.NORTH);//��ToolBar�ӵ�����
		
		FontClass fontset=new FontClass();
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		String fontNames[]=ge.getAvailableFontFamilyNames();//���ϵͳ����
		fontName=new JComboBox(fontNames);//��Ͽ� ��ʾ  ϵͳ����
		toolBar.add(fontName);
		
		Integer sizes[]={20,30,40,50,60,70};//�ֺ�����
		fontSize=new JComboBox(sizes);//�ֺ�
		fontSize.addActionListener(fontset);
		toolBar.add(fontSize);//���������  �ֺ���Ͽ�
		
		String stylestr[]={"����","б��"};
		fontStyle=new JCheckBox[stylestr.length];    //new ���飨������ռ�
		zxCheckboxMenuItem=new JCheckBoxMenuItem[stylestr.length];
		
		for(int i=0;i<stylestr.length;++i){
			fontStyle[i]=new JCheckBox(stylestr[i]);
			zxCheckboxMenuItem[i]=new JCheckBoxMenuItem(stylestr[i]);			
		    toolBar.add(fontStyle[i]);
		    fontStyle[i].addActionListener(fontset);
		    
		    ziXing.add(zxCheckboxMenuItem[i]);//���� �����˵�  ��ӡ���ѡ��˵��
		    zxCheckboxMenuItem[i].addActionListener(fontset);
		}
		
		mainMenuBar.add(myFile);  //���ļ����˵�
		myFile.add(newFile);
		myFile.add(open);
		myFile.add(save);
		myFile.add(printFile);
		myFile.addSeparator();//��� �ָ���
		myFile.add(quitItem);
		
		mainMenuBar.add(Edition);//���༭���˵�
		Edition.add(cuts);
		Edition.add(copy);
		Edition.add(paste);
		Edition.addSeparator();//��� �ָ���
		Edition.add(undoEdit);
		Edition.add(delete);
		Edition.add(findReplace);
		
		mainMenuBar.add(style);//����ʽ���˵�
		style.add(font);
		style.add(ziXing);
		style.add(color);
				
		mainMenuBar.add(help);//���������˵�
		help.add(helps);
		help.add(about);
		
		JPopupMenu popMenu=new JPopupMenu();//��ݲ˵�
		contentArea.setComponentPopupMenu(popMenu);//�� ��ӡ���ݲ˵��������new��ǰ
		popMenu.add(cut2);
		popMenu.add(copy2);
		popMenu.add(paste2);
        
		//���� ��ݼ�.....................................................
		quitItem.setAccelerator(KeyStroke.getKeyStroke("control Q"));
		newFile.setAccelerator(KeyStroke.getKeyStroke("control N"));
		open.setAccelerator(KeyStroke.getKeyStroke("control O"));
		save.setAccelerator(KeyStroke.getKeyStroke("control S"));
		
		cuts.setAccelerator(KeyStroke.getKeyStroke("control X"));
		copy.setAccelerator(KeyStroke.getKeyStroke("control C"));
		paste.setAccelerator(KeyStroke.getKeyStroke("control V"));
		undoEdit.setAccelerator(KeyStroke.getKeyStroke("control Z"));
		findReplace.setAccelerator(KeyStroke.getKeyStroke("control F"));
		
		//.��Ӧ����............................................................	
		about.addActionListener(new ActionListener() {   //ʵ�֡�ճ��������
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(about, "Text Editor SDI\n@Author:ljheee\n2016-00-00");				
			}
		});
		
		color.addActionListener(new ActionListener() {  //ѡ��ԭɫDialog
			public void actionPerformed(ActionEvent e) {
				Color c=JColorChooser.showDialog(contentArea, "ѡ����ɫ", Color.blue);
				contentArea.setCaretColor(c);//���ƹ�� ��ɫ
				contentArea.setForeground(c);//�ı� ������ɫ
				contentArea.repaint();
			}
		});
		
		quitItem.addActionListener(new ActionListener() {//ʵ�֡��˳�������
			@Override
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(quitItem, "��ֹ��ǰ����","ȷ��",JOptionPane.YES_NO_CANCEL_OPTION)==0)
				    System.exit(0);
			}
		});
		
		//....���ļ��½����򿪡����桱����.....................................................................	
		newFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contentArea.setText("");
				TextEditorSDI.this.setTitle("TextEditor SDI"+"   "+"�½��ļ�");
				fileInfo.setText("�½��ļ�");
				pathInfo.setText("");
			}
		});
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int select = fileChooser.showOpenDialog(TextEditorSDI.this);
				if(select==JFileChooser.CANCEL_OPTION) return;
				
				BufferedReader br =null;
				File tempFile = fileChooser.getSelectedFile();
				try {
					if(select==JFileChooser.APPROVE_OPTION&&tempFile!=null&&tempFile.exists()){  //���Ҫ�򿪵��ļ�����--�Ŵ�
						contentArea.setText("");
						br = new BufferedReader(new InputStreamReader(new FileInputStream(tempFile)));
						TextEditorSDI.this.setTitle("TextEditor SDI"+"   "+tempFile.getName());
						fileInfo.setText(tempFile.getName()+"   ");
						pathInfo.setText(tempFile.getAbsolutePath()+"      ");
						while(true){
							String content = br.readLine();//ÿ�ζ�ȡһ���ַ���
							if(content==null)  break;
							contentArea.append(content);//�˴�����JEditorPane,��append()����
							contentArea.append("\n");//����
						}
					}
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(TextEditorSDI.this, "�ļ�Not Found-��ʧ��");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(TextEditorSDI.this, "�ļ���ȡ�쳣");
				}finally{
					try {
						if(br!=null) br.close();
					} catch (IOException e1) {
					}
				}
			}
		});
		
		save.addActionListener(new ActionListener() {
			BufferedWriter bw = null;
			@Override
			public void actionPerformed(ActionEvent e) {
				int select = fileChooser.showSaveDialog(TextEditorSDI.this);
				if(select==JFileChooser.CANCEL_OPTION) return;
					
//				fileChooser.setSelectedFile(new File("�½�.txt")); 
				File file = null;
				
				String fileName = null;
				if(select==JFileChooser.APPROVE_OPTION){
					file =fileChooser.getSelectedFile();//������ﲢû��ѡȡ���κε��ļ��������fileChooser.getName(file)���᷵����������ļ��� 
				}
				fileName = fileChooser.getName(file);
				if(fileName==null|| fileName.trim().length()==0){
					JOptionPane.showMessageDialog(TextEditorSDI.this, "�ļ���Ϊ�գ�");
				}
				
				if(file!=null&&file.isFile()){
					fileName = file.getName();
				}
				//�����Ǹ��ļ���
				file = fileChooser.getCurrentDirectory();//��õ�ǰĿ¼
				
				String path = file.getPath()+java.io.File.separator+fileName;
				file =new File(path);
			
				if(file.exists()) {  //��ѡ�������ļ�----ѯ���Ƿ�Ҫ����   
					 int i = JOptionPane.showConfirmDialog(TextEditorSDI.this, "���ļ��Ѿ����ڣ�ȷ��Ҫ������");     
					 if(i == JOptionPane.YES_OPTION)  ;     
					 else   return;    
				} 
				
				try {
					bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
					bw.write(contentArea.getText());
					bw.flush();
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(TextEditorSDI.this, "�ļ��������"+e1.getMessage());
				} catch (IOException e1) {
					e1.printStackTrace();
				}finally{
					try {
						if(bw!=null) bw.close();
					} catch (IOException e1) {
					}
				}
			}
		});
		
		//�����༭��
		undoEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(undoManager.canUndo())  undoManager.undo();
			}
		});
		//�����ַ��� --����
		findReplace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FindReplaceFrame(contentArea);
			}
		});
		
		this.add(new JScrollPane(contentArea)); //����ı��� --���м�
		
		contentArea.getDocument().addUndoableEditListener(undoManager);//���ı�����ע��ɳ����༭
		
		//״̬��---ʹ��JToolBar����Ϊ�����϶�
		JToolBar  bottomToolBar = new JToolBar();
		bottomToolBar.setFloatable(false);//����JToolBar�����϶�
		
		bottomToolBar.setPreferredSize(new Dimension(this.getWidth(), 18));
		bottomToolBar.add(fileInfo);
		
//		bottomToolBar.addSeparator(); //�˷�����ӷָ���  ��Ч
		JSeparator  jsSeparator = new JSeparator(SwingConstants.VERTICAL);
		bottomToolBar.add(jsSeparator);//��ӷָ���
		
		fileInfo.setPreferredSize(new Dimension(150, 18));
		fileInfo.setHorizontalTextPosition(SwingConstants.LEFT);
		
		bottomToolBar.add(pathInfo);
		pathInfo.setHorizontalTextPosition(SwingConstants.LEFT);
		bottomToolBar.add(new JSeparator(SwingConstants.VERTICAL));//��ӷָ���
		
		bottomToolBar.add(timeInfo);
		timeInfo.setPreferredSize(new Dimension(70, 18));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		timeInfo.setText(sdf.format(new Date()));
		
		
		this.getContentPane().add(bottomToolBar, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
	/**
	 * ˽���ڲ�Action�࣬Ϊ�˵���� ��ݲ˵�ͳһʵ�ֹ���
	 * @author ljheee
	 */
	private class PasteAction extends AbstractAction{
		public PasteAction(){
			super("ճ��");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			contentArea.paste();
		}
	}
	private class CutAction extends AbstractAction{
		public CutAction(){
			super("����");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			contentArea.cut();
		}
	}
	private class CopyAction extends AbstractAction{
		public CopyAction(){
			super("����");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			contentArea.copy();
		}
	}
	//���塢�ֺš�����  ����
	class FontClass implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int size=0;
			String fontname0=(String)fontName.getSelectedItem();  //���������
			Object obj= fontSize.getSelectedItem();             //��� �ֺ�
			size=((Integer)obj).intValue();
			
			Font curFont=contentArea.getFont(); //��ȡ��ǰ����
			int curStyle=curFont.getStyle();   //��ȡ��ǰ������
			if(e.getActionCommand().equals("����"))      curStyle=curStyle^1;
			if(e.getActionCommand().equals("б��"))      curStyle=curStyle^2;
			contentArea.setFont(new Font(fontname0,curStyle,size));
		}
	}

	
	public static void main(String[] args) throws IOException {
          new TextEditorSDI();
	}

}

