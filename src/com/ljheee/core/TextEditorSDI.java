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
 * 文本编辑器--实现菜单，打开及保存文件
 * @author ljheee
 */
public class TextEditorSDI extends JFrame {
	public static final int  DefaultWidth=900;
	public static final int DefaultHeight=700;
	
    private JComboBox fontName;//选字体
    private JComboBox fontSize;//字号
    private JCheckBox fontStyle[];//字形多选框
    private JCheckBoxMenuItem zxCheckboxMenuItem[]; //字形  复选框菜单项，添加到“字形”二级菜单里
   
	private JMenuBar mainMenuBar;
	private  JTextArea contentArea;
	
	private JMenu myFile=new JMenu("文件"); //“文件”菜单
	private JMenuItem newFile=new JMenuItem("新建");
	private JMenuItem open=new JMenuItem("打开");
	private JMenuItem save=new JMenuItem("保存");
	private JMenuItem printFile=new JMenuItem("打印");
	private JMenuItem quitItem=new JMenuItem("退出");
	
	private JMenu Edition=new JMenu("编辑");//“编辑”菜单
	CutAction cutAction=new CutAction();
	private JMenuItem cuts=new JMenuItem(cutAction);
	CopyAction copyAction=new CopyAction();
	private JMenuItem copy=new JMenuItem(copyAction);//Acyion实现功能,与快捷菜单中的功能一样
	PasteAction pasteAction=new PasteAction();
	private JMenuItem paste=new JMenuItem(pasteAction);
	private JMenuItem undoEdit=new JMenuItem("撤销");
	private JMenuItem delete=new JMenuItem("删除");
	private JMenuItem findReplace=new JMenuItem("查找/替换");
	
	private JMenu style=new JMenu("格式");     //“格式”菜单
	private JMenuItem font=new JMenuItem("字体");
	private JMenu ziXing=new JMenu("字形");     //字形”二级菜单
	private JMenuItem color=new JMenuItem("颜色");
	
	private JMenu help=new JMenu("帮助");//“帮助”菜单
	private JMenuItem helps=new JMenuItem("帮助");
	private JMenuItem about=new JMenuItem("关于");
   
	private JMenuItem cut2=new JMenuItem(cutAction);//快捷菜单
	private JMenuItem copy2=new JMenuItem(copyAction);//Acyion实现功能
	private JMenuItem paste2=new JMenuItem(pasteAction);
	
	private JFileChooser fileChooser = new JFileChooser();//文件选择器
	private UndoManager undoManager = new UndoManager();
	
	private JLabel fileInfo = new JLabel("状态栏:");
	private JLabel pathInfo = new JLabel("  ");
	private JLabel timeInfo = new JLabel("  ");
	
	
	public  JTextArea getTextArea(){
		return contentArea;
	}
	
	public TextEditorSDI() throws IOException{     //构造方法
		super("TextEditor SDI");
		this.setSize(DefaultWidth, DefaultHeight);
		this.setBackground(Color.darkGray);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//设置文件过滤
		fileChooser.setFileFilter(new FileNameExtensionFilter("General", new String[]{"txt","java","html","class","xls","ppt","doc","docx","xml","exe"}));
		contentArea=new JTextArea();
		
		mainMenuBar=new JMenuBar();
		JToolBar toolBar=new JToolBar();
		this.setJMenuBar(mainMenuBar);//为JFrame添加菜单栏
		this.getContentPane().add(toolBar, BorderLayout.NORTH);//把ToolBar加到顶部
		
		FontClass fontset=new FontClass();
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		String fontNames[]=ge.getAvailableFontFamilyNames();//获得系统字体
		fontName=new JComboBox(fontNames);//组合框 显示  系统字体
		toolBar.add(fontName);
		
		Integer sizes[]={20,30,40,50,60,70};//字号数组
		fontSize=new JComboBox(sizes);//字号
		fontSize.addActionListener(fontset);
		toolBar.add(fontSize);//工具栏添加  字号组合框
		
		String stylestr[]={"粗体","斜体"};
		fontStyle=new JCheckBox[stylestr.length];    //new 数组（组件）空间
		zxCheckboxMenuItem=new JCheckBoxMenuItem[stylestr.length];
		
		for(int i=0;i<stylestr.length;++i){
			fontStyle[i]=new JCheckBox(stylestr[i]);
			zxCheckboxMenuItem[i]=new JCheckBoxMenuItem(stylestr[i]);			
		    toolBar.add(fontStyle[i]);
		    fontStyle[i].addActionListener(fontset);
		    
		    ziXing.add(zxCheckboxMenuItem[i]);//字形 二级菜单  添加“复选框菜单项”
		    zxCheckboxMenuItem[i].addActionListener(fontset);
		}
		
		mainMenuBar.add(myFile);  //“文件”菜单
		myFile.add(newFile);
		myFile.add(open);
		myFile.add(save);
		myFile.add(printFile);
		myFile.addSeparator();//添加 分割线
		myFile.add(quitItem);
		
		mainMenuBar.add(Edition);//“编辑”菜单
		Edition.add(cuts);
		Edition.add(copy);
		Edition.add(paste);
		Edition.addSeparator();//添加 分割线
		Edition.add(undoEdit);
		Edition.add(delete);
		Edition.add(findReplace);
		
		mainMenuBar.add(style);//“格式”菜单
		style.add(font);
		style.add(ziXing);
		style.add(color);
				
		mainMenuBar.add(help);//”帮助“菜单
		help.add(helps);
		help.add(about);
		
		JPopupMenu popMenu=new JPopupMenu();//快捷菜单
		contentArea.setComponentPopupMenu(popMenu);//将 添加“快捷菜单”的组件new在前
		popMenu.add(cut2);
		popMenu.add(copy2);
		popMenu.add(paste2);
        
		//设置 快捷键.....................................................
		quitItem.setAccelerator(KeyStroke.getKeyStroke("control Q"));
		newFile.setAccelerator(KeyStroke.getKeyStroke("control N"));
		open.setAccelerator(KeyStroke.getKeyStroke("control O"));
		save.setAccelerator(KeyStroke.getKeyStroke("control S"));
		
		cuts.setAccelerator(KeyStroke.getKeyStroke("control X"));
		copy.setAccelerator(KeyStroke.getKeyStroke("control C"));
		paste.setAccelerator(KeyStroke.getKeyStroke("control V"));
		undoEdit.setAccelerator(KeyStroke.getKeyStroke("control Z"));
		findReplace.setAccelerator(KeyStroke.getKeyStroke("control F"));
		
		//.响应功能............................................................	
		about.addActionListener(new ActionListener() {   //实现“粘贴”功能
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(about, "Text Editor SDI\n@Author:ljheee\n2016-00-00");				
			}
		});
		
		color.addActionListener(new ActionListener() {  //选择原色Dialog
			public void actionPerformed(ActionEvent e) {
				Color c=JColorChooser.showDialog(contentArea, "选择颜色", Color.blue);
				contentArea.setCaretColor(c);//控制光标 颜色
				contentArea.setForeground(c);//文本 字体颜色
				contentArea.repaint();
			}
		});
		
		quitItem.addActionListener(new ActionListener() {//实现“退出”功能
			@Override
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(quitItem, "终止当前程序？","确认",JOptionPane.YES_NO_CANCEL_OPTION)==0)
				    System.exit(0);
			}
		});
		
		//....“文件新建、打开、保存”功能.....................................................................	
		newFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contentArea.setText("");
				TextEditorSDI.this.setTitle("TextEditor SDI"+"   "+"新建文件");
				fileInfo.setText("新建文件");
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
					if(select==JFileChooser.APPROVE_OPTION&&tempFile!=null&&tempFile.exists()){  //如果要打开的文件存在--才打开
						contentArea.setText("");
						br = new BufferedReader(new InputStreamReader(new FileInputStream(tempFile)));
						TextEditorSDI.this.setTitle("TextEditor SDI"+"   "+tempFile.getName());
						fileInfo.setText(tempFile.getName()+"   ");
						pathInfo.setText(tempFile.getAbsolutePath()+"      ");
						while(true){
							String content = br.readLine();//每次读取一行字符串
							if(content==null)  break;
							contentArea.append(content);//此处换用JEditorPane,无append()方法
							contentArea.append("\n");//换行
						}
					}
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(TextEditorSDI.this, "文件Not Found-打开失败");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(TextEditorSDI.this, "文件读取异常");
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
					
//				fileChooser.setSelectedFile(new File("新建.txt")); 
				File file = null;
				
				String fileName = null;
				if(select==JFileChooser.APPROVE_OPTION){
					file =fileChooser.getSelectedFile();//如果这里并没有选取中任何的文件，下面的fileChooser.getName(file)将会返回手输入的文件名 
				}
				fileName = fileChooser.getName(file);
				if(fileName==null|| fileName.trim().length()==0){
					JOptionPane.showMessageDialog(TextEditorSDI.this, "文件名为空！");
				}
				
				if(file!=null&&file.isFile()){
					fileName = file.getName();
				}
				//否则是个文件夹
				file = fileChooser.getCurrentDirectory();//获得当前目录
				
				String path = file.getPath()+java.io.File.separator+fileName;
				file =new File(path);
			
				if(file.exists()) {  //若选择已有文件----询问是否要覆盖   
					 int i = JOptionPane.showConfirmDialog(TextEditorSDI.this, "该文件已经存在，确定要覆盖吗？");     
					 if(i == JOptionPane.YES_OPTION)  ;     
					 else   return;    
				} 
				
				try {
					bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
					bw.write(contentArea.getText());
					bw.flush();
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(TextEditorSDI.this, "文件保存出错"+e1.getMessage());
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
		
		//撤销编辑，
		undoEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(undoManager.canUndo())  undoManager.undo();
			}
		});
		//查找字符串 --功能
		findReplace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FindReplaceFrame(contentArea);
			}
		});
		
		this.add(new JScrollPane(contentArea)); //添加文本区 --到中间
		
		contentArea.getDocument().addUndoableEditListener(undoManager);//给文本区，注册可撤销编辑
		
		//状态栏---使用JToolBar，设为不可拖动
		JToolBar  bottomToolBar = new JToolBar();
		bottomToolBar.setFloatable(false);//设置JToolBar不可拖动
		
		bottomToolBar.setPreferredSize(new Dimension(this.getWidth(), 18));
		bottomToolBar.add(fileInfo);
		
//		bottomToolBar.addSeparator(); //此方法添加分隔符  无效
		JSeparator  jsSeparator = new JSeparator(SwingConstants.VERTICAL);
		bottomToolBar.add(jsSeparator);//添加分隔符
		
		fileInfo.setPreferredSize(new Dimension(150, 18));
		fileInfo.setHorizontalTextPosition(SwingConstants.LEFT);
		
		bottomToolBar.add(pathInfo);
		pathInfo.setHorizontalTextPosition(SwingConstants.LEFT);
		bottomToolBar.add(new JSeparator(SwingConstants.VERTICAL));//添加分隔符
		
		bottomToolBar.add(timeInfo);
		timeInfo.setPreferredSize(new Dimension(70, 18));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		timeInfo.setText(sdf.format(new Date()));
		
		
		this.getContentPane().add(bottomToolBar, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
	/**
	 * 私有内部Action类，为菜单项和 快捷菜单统一实现功能
	 * @author ljheee
	 */
	private class PasteAction extends AbstractAction{
		public PasteAction(){
			super("粘贴");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			contentArea.paste();
		}
	}
	private class CutAction extends AbstractAction{
		public CutAction(){
			super("剪切");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			contentArea.cut();
		}
	}
	private class CopyAction extends AbstractAction{
		public CopyAction(){
			super("复制");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			contentArea.copy();
		}
	}
	//字体、字号、字形  设置
	class FontClass implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int size=0;
			String fontname0=(String)fontName.getSelectedItem();  //获得字体名
			Object obj= fontSize.getSelectedItem();             //获得 字号
			size=((Integer)obj).intValue();
			
			Font curFont=contentArea.getFont(); //获取当前字体
			int curStyle=curFont.getStyle();   //获取当前字体风格
			if(e.getActionCommand().equals("粗体"))      curStyle=curStyle^1;
			if(e.getActionCommand().equals("斜体"))      curStyle=curStyle^2;
			contentArea.setFont(new Font(fontname0,curStyle,size));
		}
	}

	
	public static void main(String[] args) throws IOException {
          new TextEditorSDI();
	}

}

