import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.sound.midi.*;
import java.io.*; 
import java.net.*;
import javax.swing.text.*;

//judy
// import javax.sound.sampled;
// import javax.sound.sampled.spi;
import java.applet.*;
import java.net.URL;
import javax.swing.plaf.basic.BasicScrollBarUI;
// import java.applet.AudioClip;

public class Gobang extends JFrame implements ActionListener{
	
	// private JPanel contentpane = null;
	static private GobangServer server = null;
	static private GobangClient client = null;
	//Steven add
	public MyPanel mp=null;
	static private boolean isServer;
	
	//judy add
	private JMenuBar menubar = null;
	private JMenu m1 = null;
	private JMenuItem m11 = null;
	
	private JFrame subframe = null;
	
	private int  selectValue =4;
	
	
	public Gobang(){
		super();
		initialize();
		chooseMessage();
		littlegame();
		
	}
	
	public void initialize(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Gobang Demo");
		//Stevne add
		mp = new MyPanel(this);
		//
		this.setContentPane(mp);
		this.setJMenuBar(getMenubar());
		this.pack();
		 // JFrame set on Screen CENTER
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screensize.getWidth()-this.getWidth())/2,(int)(screensize.getHeight()-this.getHeight())/2);
		//0602 設定一開始mp requestFocus
		mp.requestFocus();
		this.setVisible(true);
	}
	
	//Steven add
	public void littlegame()//剪刀石頭布
	{
		ImageIcon pic1=new ImageIcon("./img/sc.png");//剪刀
        ImageIcon pic2=new ImageIcon("./img/st.png");//石頭
        ImageIcon pic3=new ImageIcon("./img/cl.jpg");//步
      
        Object[] options = { pic1, pic2, pic3 };
        selectValue=JOptionPane.showOptionDialog(null,
                                      "Please choose one to determine who will get prority to play the game:",
                                     "Warning",
                                     JOptionPane.DEFAULT_OPTION,
                                     JOptionPane.WARNING_MESSAGE,
                                     null,
                                     options,
                                     options[0]);//跳出選擇剪刀石頭布視窗
		//System.out.println("hellogame"+selectValue);
		
		if(isServer==false)//如果是client端
		{
			String s="C-"+String.valueOf(selectValue);//編碼加前綴C
			client.dataOutput(s);//將data傳送出去
		}
	}
	
	//judy add
	public JMenuBar getMenubar(){
		if(menubar==null){
			menubar = new JMenuBar();
			m1 = new JMenu("Setting");
			m11 = new JMenuItem("Preferences(P)..");
			m1.setFont(new Font("微軟正黑體",Font.BOLD,14));
			m11.setFont(new Font("微軟正黑體",Font.BOLD,12));
			
			// menubar.setMargin(new Insets(30,10,10,10)); // top,left,bottom,right
			menubar.setPreferredSize(new Dimension(mp.getWidth(), 40));
			menubar.setBackground(new Color(215, 214, 213));
			m11.addActionListener(this);
			
			//add
			m1.add(m11);
			menubar.add(m1);
		}
		return menubar;
	}
	
	// JFrame的Listener
	public void actionPerformed(ActionEvent e){
		String cmd = e.getActionCommand();
		// System.out.println(cmd);
		if(cmd.equals("Preferences(P)..")){
			subframe = new SubFrame(this);
		}
	}

public class SubFrame extends JFrame implements ActionListener{
	
	private JFrame parentFrame = null;
	private JPanel contentpane = null;
	private JPanel topContentpane = null;
	private JPanel downContentpane = null;
	
	private JButton choosebkgColor = null;
	private JButton bkgapply = null;
	private JButton bkgdefault = null;
	
	private JButton choosepieceColor = null;
	private JButton pieceapply = null;
	private JButton piecedefault = null;
	
	private Color choose = null;
	private JFrame subframe = null;
	
	
	public SubFrame(JFrame tmp){
		// this.setPreferredSize(new Dimension(300,250));
		this.setTitle("Preferences");
		this.setContentPane(getContentPane());
		this.pack();
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screensize.getWidth()-this.getWidth())/2,(int)(screensize.getHeight()-this.getHeight())/2);
		parentFrame = tmp;
		this.setVisible(true);
		

	}
	
	public JPanel getContentPane(){
		if(contentpane == null){
			contentpane = new JPanel();
			contentpane.setLayout(new BorderLayout());
			contentpane.add(getTopContentPane(), BorderLayout.NORTH);
			contentpane.add(getDownContentPane(), BorderLayout.CENTER);
			
		}
		return contentpane;
	}
	
	
	
	public JPanel getTopContentPane(){//用來設定背景顏色
		if(topContentpane == null){
			topContentpane = new JPanel();
			topContentpane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2)," 背景顏色設定： ",
								1,0,new Font("微軟正黑體", Font.BOLD, 16),Color.BLACK));
			// topContentpane.setPreferredSize(new Dimension(150,100));
			topContentpane.setLayout(new FlowLayout(FlowLayout.CENTER));
			topContentpane.setBackground(new Color(215, 214, 213));
			
			choosebkgColor = new JButton("選擇顏色...");
			bkgdefault = new JButton("還原預設值");
			bkgapply = new JButton("應用");
			
			choosebkgColor.setFont(new Font("微軟正黑體",Font.PLAIN,14));
			choosebkgColor.setBackground(Color.GRAY);
			choosebkgColor.setForeground(Color.WHITE);
			bkgdefault.setFont(new Font("微軟正黑體",Font.PLAIN,14));
			bkgdefault.setBackground(Color.GRAY);
			bkgdefault.setForeground(Color.WHITE);
			bkgapply.setFont(new Font("微軟正黑體",Font.PLAIN,14));
			bkgapply.setBackground(Color.GRAY);
			bkgapply.setForeground(Color.WHITE);
			
			choosebkgColor.addActionListener(this);
			bkgdefault.addActionListener(this);
			bkgapply.addActionListener(this);
			
			bkgdefault.setEnabled(false);
			bkgapply.setEnabled(false);
			
			topContentpane.add(choosebkgColor);
			// topContentpane.add(bkgdefault);
			topContentpane.add(bkgapply);
			
			
			
		}
		return  topContentpane;
	}
	
	public JPanel getDownContentPane(){//用來設定棋子顏色
		if(downContentpane == null){
			downContentpane = new JPanel();
			downContentpane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2)," 棋子顏色設定： ",
								1,0,new Font("微軟正黑體", Font.BOLD, 16),Color.BLACK));
			// downContentpane.setPreferredSize(new Dimension(150,100));
			
			downContentpane.setLayout(new FlowLayout(FlowLayout.CENTER));
			downContentpane.setBackground(new Color(215, 214, 213));
			
			choosepieceColor = new JButton("選擇顏色...");
			piecedefault = new JButton("還原預設值");
			pieceapply = new JButton("應用");
			
			choosepieceColor.setFont(new Font("微軟正黑體",Font.PLAIN,14));
			choosepieceColor.setBackground(Color.GRAY);
			choosepieceColor.setForeground(Color.WHITE);
			piecedefault.setFont(new Font("微軟正黑體",Font.PLAIN,14));
			piecedefault.setBackground(Color.GRAY);
			piecedefault.setForeground(Color.WHITE);
			pieceapply.setFont(new Font("微軟正黑體",Font.PLAIN,14));
			pieceapply.setBackground(Color.GRAY);
			pieceapply.setForeground(Color.WHITE);
			
			choosepieceColor.addActionListener(this);
			piecedefault.addActionListener(this);
			pieceapply.addActionListener(this);
			
			
			bkgdefault.setEnabled(false);
			pieceapply.setEnabled(false);
			
			downContentpane.add(choosepieceColor);
			// downContentpane.add(piecedefault);
			downContentpane.add(pieceapply);
		}
		return downContentpane;
	}
	
	//subframe的Listener
	public void actionPerformed(ActionEvent e){
		JButton tmp = (JButton) e.getSource();
		Component[] cmp = parentFrame.getContentPane().getComponents();
		int cmplength = parentFrame.getContentPane().getComponentCount();
		JLabel piececolor = null;
		for(int i=0;i< cmplength;i++){
			
			try{
				if(cmp[i].getName().equals("piececolor")){
					piececolor = (JLabel)parentFrame.getContentPane().getComponent(i);
					break;
				}
			}
			catch(NullPointerException ex2){
				
			}
		}
		
		
		
		if(tmp.equals(choosebkgColor)){
			bkgapply.setEnabled(true);
			choose = JColorChooser.showDialog(this, "選擇背景顏色", Color.GRAY);
			
		}
		// if(tmp.equals(bkgdefault)){
			// choose = Color.GRAY;
			// bkgapply.setEnabled(true);
			// bkgdefault.setEnabled(false);

		// }
		if(tmp.equals(bkgapply)){
			parentFrame.getContentPane().setBackground(choose);
			// bkgdefault.setEnabled(true);
			bkgapply.setEnabled(false);
			
		}
		
		if(tmp.equals(choosepieceColor)){
			pieceapply.setEnabled(true);
			choose = JColorChooser.showDialog(this, "選擇棋子顏色", Color.BLACK);
			
		}
		// if(tmp.equals(piecedefault)){
			// piececolor = (JLabel)parentFrame.getContentPane().getComponent(13);
			// piececolor.setText(Integer.toString(choose.getRGB()));
			// pieceapply.setEnabled(false);
			if(piececolor.getText().equals("-16777216")) piecedefault.setEnabled(false);
			
		// }
		if(tmp.equals(pieceapply)){
			piececolor.setText(Integer.toString(choose.getRGB()));
			piecedefault.setEnabled(true);
			pieceapply.setEnabled(false);
		}
	}
}
	
	

	

	
	public void chooseMessage(){
	
		String[] options={"Server","Client"}; //0 and 1
		int opt=JOptionPane.showOptionDialog(null,"Please choose what you want to be?","",0,3,null,options,"Server");//跳出畫面校選擇當哪一端
		//choose server
		if(opt==0){
			setToServer();
			isServer=true;
			mp.fileout(1);	 //開始第一盤的讀擋
		}
		//choose client
		else{
			setToClient();
			isServer=false;
			mp.fileout(1);	 //開始第一盤的讀擋
		}
											 
	}
	
	public void setToServer(){//選擇要當server端
		String inputPort = JOptionPane.showInputDialog(null,"Please set port","",1);//要開始選port
		int port;
		try{
			port = Integer. parseInt(inputPort);
			server = new GobangServer(port);
			server.setGUI(this);
			server.start();//開始執行緒
		}
		catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null,"Wrong input!","",2);
		}
		
	}
	public void setToClient(){
		String inputHost = JOptionPane.showInputDialog(null,"Please set host","",1);//client端要輸入host
		String inputPort = JOptionPane.showInputDialog(null,"Please set port","",1);//開始選port
		int port;
		
		try{
			port = Integer. parseInt(inputPort);
			client = new GobangClient(inputHost,port);
			client.setGUI(this);
			client.start();
		}
		catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null,"Wrong input!","",2);
		}
		
	}
	
	public class MyPanel extends JPanel implements MouseListener, KeyListener, ActionListener{
	
	private JLabel Title = null;
	//judy 
	private JLabel subTitle = null;
	
	private ImageIcon TitleIcon = new ImageIcon("./img/titleIcon2.png");
	
	//judy
	private ImageIcon subTitleIcon = new ImageIcon("./img/titleIcon3.png");
	private ImageIcon voiceIcon = new ImageIcon("./img/voiceIcon.png");
	private ImageIcon muteIcon = new ImageIcon("./img/muteIcon.png");
	
	//Steven add
	int x;// 落子處棋盤座標
	int y;
	int[][] chess = new int[12][12];// 棋盤大小為12x12
	int ex;// 傳送滑鼠點選座標
	int ey;
	boolean me = true;// 初始化為己方先落黑子
	boolean one = false;// 控制聯機雙方每次只能一個人下子
	//static boolean start = false;// 預設開啟遊戲不能下子,需先選擇遊戲模式
	private GobangServer server = null;
	private GobangClient client = null;
	private int mycount=0;
	private int othercount=0;			
	
	//
	
	//judy add
	AudioClip backgroundMusic;
	private JComboBox musicList = null;
	private JLabel musicIcon = null;
	
/////////Stevne part
	//Stevne add
	private JTextField myfield = null;
	private JTextField otherfield = null;
	private JLabel mycoord = null;
	private JLabel othercoord = null;
	private JLabel mylb = null;
	private JLabel otherlb = null;
	private JLabel currentcoordlb = null;
	private PrintWriter record_out= null;
	private int set=1;	
	private JScrollPane opaScrollPane = null;
	private JTextPane outputArea = null;
	private JTextField dial = null;
	private JButton	replay = null;
	private Gobangreplay gp = null;
	private boolean isblack = true;
	
	private JFrame parentFrame = null;
	
	//0601
	private JLabel piececolor = null;
	
	//0603
	StyledDocument doc = null;
	
	public MyPanel(JFrame tmp){
		initialize();
		parentFrame = tmp;
		
	}
	
	public void initialize(){
		this.setPreferredSize(new Dimension(1200,700));
		this.setBackground(Color.GRAY);
		this.setLayout(null);
		this.add(getTitle());
		this.add(getsubTitle());
		this.add(getmusicList());
		this.add(getmusicIcon());
		
		////Steven part
		this.add(getmefield());
		this.add(getotherfield());
		this.add(getcurrentcoordlb());
		this.add(getmycoord());
		this.add(getothercoord());
		this.add(getmylb());
		this.add(getotherlb());
		this.add(getopa());
		this.add(getdialog());
		this.add(getreplay());
		
		//invisible 只是為了讓傳送JPanel(Container)可以傳getComponent 來設定棋子顏色
		this.add(getPieceColor());
		
		////		 
		this.addMouseListener(this);
		this.addKeyListener(this);
		// dial.addKeyListener(new TFListener());
		
		
		//judy
		playSound("SunsetParadise");	
	}
	
	// component 13
	public JLabel getPieceColor(){
		piececolor = new JLabel("-16777216"); //default color is black
		piececolor.setVisible(false);
		piececolor.setName("piececolor");
		return piececolor;
	}
	
	
	// judy
	public JLabel getTitle(){
		if(Title == null){
			Title = new JLabel();
			Title.setIcon(TitleIcon);
			Title.setFocusable(false);
			Title.setBounds(new Rectangle(630, 50, (int)TitleIcon.getIconWidth(), (int)TitleIcon.getIconHeight()));
			
		}
		return Title;
	}
	
	public JLabel getsubTitle(){
		if(subTitle == null){
			subTitle = new JLabel();
			subTitle.setBounds(new Rectangle(Title.getX()+20,Title.getY()+80, (int)subTitleIcon.getIconWidth()/2+10, (int)TitleIcon.getIconHeight()/2));
			subTitleIcon.setImage(subTitleIcon.getImage().getScaledInstance(subTitle.getWidth(), subTitle.getHeight(), Image.SCALE_AREA_AVERAGING));
			
			subTitle.setIcon(subTitleIcon);
			subTitle.setFocusable(false);
		}
		return subTitle;
	}
	
	//judy
	public void playSound(String str){
		String s = "";
		str = "./audio/"+str+".wav";
		try {
		 URL myURL = new File(str).toURI().toURL();
		 s = myURL.getPath();
		 backgroundMusic = Applet.newAudioClip(myURL);
		 backgroundMusic.play(); 
		 backgroundMusic.loop(); 
		 
		}
	   catch (Throwable t) {
		 JOptionPane.showMessageDialog(null, "file open error"+s);
	   }
	}
	
	public JComboBox getmusicList(){
		if(musicList == null){
			musicList = new JComboBox();
			//set properties
			musicList.setBounds(Title.getX()+Title.getWidth()+10, subTitle.getY(),200,50);
			musicList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE, 3)," 請選擇背景音樂： ",
								2,0,new Font("微軟正黑體", Font.BOLD, 16),Color.WHITE));
			musicList.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
			musicList.setForeground(Color.WHITE);
			musicList.setBackground(Color.BLACK);
			musicList.setRenderer(new DefaultListCellRenderer(){
				@Override
				public Component getListCellRendererComponent(JList<?> list,
                                                   Object value,
                                                   int index,
                                                   boolean isSelected,
                                                   boolean cellHasFocus) {
				Component listCellRendererComponent = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				JLabel label = (JLabel)listCellRendererComponent;
				label.setHorizontalAlignment(SwingConstants.CENTER);
				list.setSelectionBackground(Color.WHITE);
				
				
				
				
				return this;
				}

			});
			
			
			musicList.addItem("SunsetParadise");
			musicList.addItem("Homura");
			musicList.addItem("Blizzards");
			
			musicList.addActionListener(this);
			musicList.setFocusable(false);
			
			
		}
		
		return musicList;
	}
	
	//JPanel的Listener為了要分辨不同的來源
	public void actionPerformed(ActionEvent e){
		String cmd = e.getActionCommand();
		// System.out.println(cmd);
		switch(cmd){
			case "comboBoxChanged":       //來自音樂選檔combobox                                        
				String select = musicList.getSelectedItem().toString();
				backgroundMusic.stop();
				playSound(select);
				this.requestFocus();
			break;
			case "replay":               //來自按鈕重播
				gp = new Gobangreplay(piececolor,isblack);
			break;
		}	
	}
	
	public JLabel getmusicIcon(){
		if(musicIcon == null){
			musicIcon = new JLabel();
			
			musicIcon.setBounds(musicList.getX()+musicList.getWidth()+10,musicList.getY()+10, (int)voiceIcon.getIconWidth()/4, (int)voiceIcon.getIconHeight()/4);
			voiceIcon.setImage(voiceIcon.getImage().getScaledInstance(musicIcon.getWidth(), musicIcon.getHeight(), Image.SCALE_AREA_AVERAGING));
			muteIcon.setImage(muteIcon.getImage().getScaledInstance(musicIcon.getWidth(), musicIcon.getHeight(), Image.SCALE_AREA_AVERAGING));
			musicIcon.setIcon(voiceIcon);
			musicIcon.addMouseListener(this);
			musicIcon.setFocusable(false);
			
		}
		return musicIcon;
	}
/////////////////////Steven part
	public JLabel getmylb()
	{
		if(mylb == null)
		{
			mylb= new JLabel("我方棋子數" , SwingConstants.CENTER);
			mylb.setBounds(980,200,80,30);
			mylb.setFont(new Font("微軟正黑體",Font.BOLD,16));
			mylb.setForeground(Color.WHITE);
			mylb.setFocusable(false);
		}
		return mylb;
	}
	
	public JLabel getotherlb()
	{
		if(otherlb == null)
		{
			otherlb= new JLabel("對手棋子數", SwingConstants.CENTER);
			otherlb.setBounds(getmylb().getX(),300,getmylb().getWidth(),getmylb().getHeight());
			otherlb.setForeground(Color.WHITE);
			otherlb.setFont(new Font("微軟正黑體",Font.BOLD,16));
			otherlb.setFocusable(false);
		}
		return otherlb;
	}
	
	public JLabel getcurrentcoordlb(){
		if(currentcoordlb==null){
			currentcoordlb = new JLabel("目前座標", SwingConstants.CENTER);
			currentcoordlb.setBounds(1080,getmylb().getY(),getmylb().getWidth(),getmylb().getHeight());
			currentcoordlb.setForeground(Color.BLACK);
			currentcoordlb.setBackground(new Color(215, 214, 213));
			currentcoordlb.setOpaque(true);
			currentcoordlb.setFont(new Font("微軟正黑體",Font.PLAIN,16));
			currentcoordlb.setFocusable(false);
		}
		return currentcoordlb;
		
	}
		
	public JTextField getmefield()
	{
		if(myfield == null)
		{
			myfield= new JTextField("0");
			myfield.setBounds(getmylb().getX(),250,getmylb().getWidth(),30);
			
			myfield.setHorizontalAlignment(SwingConstants.CENTER);
			myfield.setFont(new Font("微軟正黑體",Font.PLAIN,16));
			// myfield.setBorder(BorderFactory.createEtchedBorder());
			// myfield.setBorder(BorderFactory.createDashedBorder());
			myfield.setBackground(Color.BLACK);
			myfield.setForeground(Color.WHITE);
			myfield.setEditable(false);
			myfield.setFocusable(false);
		}
		return myfield;
	}
	
	public JTextField getotherfield()
	{
		if(otherfield == null)
		{
			otherfield= new JTextField("0");
			otherfield.setBounds(getmylb().getX(),350,getmefield().getWidth(),getmefield().getHeight());
			otherfield.setBackground(Color.BLACK);
			otherfield.setForeground(Color.WHITE);
			otherfield.setFont(new Font("微軟正黑體",Font.PLAIN,16));
			otherfield.setHorizontalAlignment(SwingConstants.CENTER);
			otherfield.setEditable(false);
			otherfield.setFocusable(false);
		}
		return otherfield;
	}
	
	public JLabel getmycoord()
	{
		if(mycoord == null)
		{
			mycoord= new JLabel();
			mycoord.setBounds(getcurrentcoordlb().getX(),getmefield().getY(),getcurrentcoordlb().getWidth(),getmefield().getHeight());
			mycoord.setText("(0,0)");
			mycoord.setVerticalAlignment(SwingConstants.CENTER);
			mycoord.setHorizontalAlignment(SwingConstants.CENTER);
			mycoord.setFont(new Font("微軟正黑體",Font.BOLD,18));
			mycoord.setForeground(new Color(215, 214, 213));
			// mycoord.setOpaque(true);
			// mycoord.setBackground(Color.BLACK);
			mycoord.setFocusable(false);
		}
		return mycoord;
	}
	
	public JLabel getothercoord()
	{
		if(othercoord == null)
		{
			othercoord= new JLabel();
			othercoord.setBounds(getcurrentcoordlb().getX(),getotherfield().getY(),getcurrentcoordlb().getWidth(),getotherfield().getHeight());
			othercoord.setText("(0,0)");
			othercoord.setVerticalAlignment(SwingConstants.CENTER);
			othercoord.setHorizontalAlignment(SwingConstants.CENTER);
			othercoord.setFont(new Font("微軟正黑體",Font.BOLD,18));
			othercoord.setForeground(new Color(215, 214, 213));
			othercoord.setFocusable(false);
		}
		return othercoord;
	}
	
	public void fileout(int i)
	{
		String s="./record/record_"+i+".txt";
		String s2="./record/record2_"+i+".txt";
		try{
			if(Gobang.this.isServer==true)//如果是server狀況，存檔是用record_盤數.txt來命名
			record_out = new PrintWriter( new FileOutputStream(s) );
			else if(Gobang.this.isServer==false)//如果client狀況，存檔是用record_盤數.txt來命名
			record_out = new PrintWriter( new FileOutputStream(s2) );
		   }
		catch(FileNotFoundException E)
			{
			System.out.println("Can not find button");
			System.exit(0);
			}
	}
	
	public JTextPane getArea()//給對話盒輸入框用的textArea
	{	
		if(outputArea==null)
		{	
			doc = new DefaultStyledDocument();
		    outputArea = new JTextPane(doc);
            outputArea.setFont(new Font("微軟正黑體",Font.PLAIN,16));
			// outputArea.setLineWrap(true);
			outputArea.setEditable(false);
			outputArea.setBackground(Color.BLACK);
			outputArea.setFocusable(false);
			// 0603
			outputArea.setMargin(new Insets(5,5,0,5));
			
			//add style
			Style sys = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
			Style s = doc.addStyle("me",sys); // 加入
			StyleConstants.setForeground(s,Color.YELLOW); // 顏色
			StyleConstants.setBold(s,true); // 粗體
			
			s = doc.addStyle("other",sys); // 加入
			StyleConstants.setForeground(s,Color.WHITE); // 顏色
			
	
			
		}
		return outputArea;
	}
	
	
	public JScrollPane getopa() //對話盒用的jscrollpane
	{
		if(opaScrollPane == null)
		{
			opaScrollPane = new JScrollPane(getArea());
			JScrollBar js = new JScrollBar();
			js.setBackground(Color.BLACK);
			js.setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = new Color(191,255,245);
				this.thumbDarkShadowColor  = Color.BLACK;
				
			}
			});
			opaScrollPane.setVerticalScrollBar(js);
			//設為垂直可捲動(不一值顯示)
            opaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            opaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			opaScrollPane.setBounds(new Rectangle(650, 200, 300, 400));
			opaScrollPane.setBorder(BorderFactory.createEmptyBorder());
			
		}
		return opaScrollPane;
	}
	
	public JTextField getdialog()//給對話盒輸入框用的
	{
		if( dial == null)
		{
			dial = new JTextField();
			dial.setFont(new Font("微軟正黑體",Font.BOLD,16));
			dial.setBounds(new Rectangle(getopa().getX(), 600, getopa().getWidth(), 30));
			dial.setBackground(Color.BLACK);
			dial.setForeground(Color.YELLOW);
			dial.setCaretColor(Color.YELLOW);
			dial.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.WHITE));
			// dial.setMargin(new Insets(0,10,0,0));
			dial.addKeyListener(this);
		}
		return dial;
	}

	
	public JButton getreplay() //設定repaly按鈕
	{
		if( replay == null)
		{
			replay = new JButton();
			replay.setBounds(new Rectangle(980, 600, 150, 30));		
			replay.setText("replay");
			replay.addActionListener(this);
			replay.setFocusable(false);
		}
		return replay;
	}
	

///////////////////////Chessboard part									 
	
	protected void paintComponent(Graphics g) {
        
		super.paintComponent(g);
		drawChessBoard(g);
		drawChess(g);
		
	}
	
	public void drawChessBoard(Graphics g){
		// 畫棋盤
		for (int i = 50; i <= 600; i += 50) {
			g.setColor(Color.WHITE); //畫白線
			g.drawLine(i, 50, i, 600);//(從(i,50)畫到(i,600)
			g.drawLine(50, i, 600, i);//(從(50,i)畫到(600,i)
		}
	}
	
	//Steven add
	public void drawChess(Graphics g) {
		// System.out.println("draw chess");
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				if (chess[i][j] == 1) {			//chess起手方為1，黑子
				    System.out.println("draw black");
					g.setColor(Color.black);     //設定顏色是黑子
					//0601
					if(!piececolor.getText().equals("-16777216")&&isblack) g.setColor(new Color(Integer.valueOf(piececolor.getText())));
					g.fillOval((i + 1) * 50 - 19, (j + 1) * 50 - 19, 38, 38); //畫白子
				} else if (chess[i][j] == 2) {  //chess方為2，白子
					System.out.println("draw WHITE");
					g.setColor(Color.white);    //設定顏色是白子
					if(!piececolor.getText().equals("-16777216")&&(!isblack)) g.setColor(new Color(Integer.valueOf(piececolor.getText())));
					g.fillOval((i + 1) * 50 - 19, (j + 1) * 50 - 19, 38, 38); //畫白子
				} else if (chess[i][j] == 11) {// 點選落黑子時外加個黑正方框
					g.setColor(Color.black);   //設定顏色是黑子
					//0601
					if(!piececolor.getText().equals("-16777216")&&isblack) g.setColor(new Color(Integer.valueOf(piececolor.getText())));
					g.fillOval((i + 1) * 50 - 19, (j + 1) * 50 - 19, 38, 38);
					g.drawRect((i + 1) * 50 - 19, (j + 1) * 50 - 19, 38, 38); //畫上外框
				} else if (chess[i][j] == 22) {// 點選落白子時棋子外加個白正方框
					g.setColor(Color.white);//設定顏色是白子
					if(!piececolor.getText().equals("-16777216")&&(!isblack)) g.setColor(new Color(Integer.valueOf(piececolor.getText())));
					g.fillOval((i + 1) * 50 - 19, (j + 1) * 50 - 19, 38, 38);
					g.drawRect((i + 1) * 50 - 19, (j + 1) * 50 - 19, 38, 38);//畫上外框
				}
			}
		}
	}
	//Steven add
	public void clearChess() {// 初始化棋盤
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				chess[i][j] = 0;
			}
		}
		me = true;// 初始化為己方落黑子
		repaint();
	}
	//Steven add
	public void checkWiner() {// 判斷勝方
		int black_count = 0;
		int white_count = 0;
		for (int i = 0; i < 12; i++) {// 橫向判斷
			for (int j = 0; j < 12; j++) {
				if (chess[i][j] == 1 || chess[i][j] == 11) {
					black_count++;//如果是黑子連續遇到黑子，則累加
					if (black_count == 5) {//5聯子
						black_win();
					}
				} else {
					black_count = 0;//如果沒有遇到五連子，則count設0
				}
				if (chess[i][j] == 2 || chess[i][j] == 22) {
					white_count++;//如果是白子連續遇到白子，則累加
					if (white_count == 5) {//5連子
						white_win();
					}
				} else {
					white_count = 0;
				}
			}
		}
		for (int i = 0; i < 12; i++) {// 豎向判斷
			for (int j = 0; j < 12; j++) {
				if (chess[j][i] == 1 || chess[j][i] == 11) {//與橫向相同，只是i j 互換
					black_count++;
					if (black_count == 5) {
						black_win();
					}
				} else {
					black_count = 0;
				}
				if (chess[j][i] == 2 || chess[j][i] == 22) {
					white_count++;
					if (white_count == 5) {
						white_win();
					}
				} else {
					white_count = 0;
				}
			}
		}
		for (int i = 0; i < 7; i++) {// 左向右斜判斷(超過7無法順利連成五子)
			for (int j = 0; j < 7; j++) {
				for (int k = 0; k < 5; k++) {
					if (chess[i + k][j + k] == 1 || chess[i + k][j + k] == 11) {
						black_count++;
						if (black_count == 5) {
							 black_win();
						}
					} else {
						black_count = 0;
					}
					if (chess[i + k][j + k] == 2 || chess[i + k][j + k] == 22) {
						white_count++;
						if (white_count == 5) {
							white_win();
						}
					} else {
						white_count = 0;
					}
				}
			}
		}
		for (int i = 4; i < 12; i++) {// 右向左斜判斷 11->12((低於4無法順利連成五子))
			for (int j = 6; j >= 0; j--) {
				for (int k = 0; k < 5; k++) {
					if (chess[i - k][j + k] == 1 || chess[i - k][j + k] == 11) {
						black_count++;
						if (black_count == 5) {
							black_win();
						}
					} else {
						black_count = 0;
					}
					if (chess[i - k][j + k] == 2 || chess[i - k][j + k] == 22) {
						white_count++;
						if (white_count == 5) {
							white_win();
							return;
						}
					} else {
						white_count = 0;
					}
				}
			}
		}
	}
	
	public void encircle()
	{
		for(int i=1;i<=10;i++)
		{
          for(int j=0;j<=10;j++)
		  {
               if(chess[i][j]==1)
			   {
				   if(blackcircle(i,j))
				   {
					   chess[i][j]=3;
					   repaint();
					   record_out.println("R "+i+" "+j);
					   
				   }
			   }	
               if(chess[i][j]==2)
			   {
				   if(whitecircle(i,j))
				   {
					   chess[i][j]=3;
					   repaint();
					   record_out.println("R "+i+" "+j);
				   } 
			   }
		  }
		}		
	}
	
	public boolean blackcircle(int i,int j)
	{
	   return 	(chess[i-1][j]==2||chess[i-1][j]==22)&&(chess[i+1][j]==2||chess[i+1][j]==22)&&(chess[i][j-1]==2||chess[i][j-1]==22)&&(chess[i][j+1]==2||chess[i][j+1]==22);
	}
	
	public boolean whitecircle(int i,int j)
	{
	   return 	(chess[i-1][j]==1||chess[i-1][j]==11)&&(chess[i+1][j]==1||chess[i+1][j]==11)&&(chess[i][j-1]==1||chess[i][j-1]==11)&&(chess[i][j+1]==1||chess[i][j+1]==11);
	}
	
	
	
	
	public void black_win()//還原所有到棋盤一開始的預設值
	{
		mycount=0;
	    othercount=0;
		myfield.setText("0");
		otherfield.setText("0");
		mycoord.setText("(0,0)");
	    othercoord.setText("(0,0)");
		record_out.close();
		JOptionPane.showMessageDialog(this, "黑棋勝利");
		clearChess();
		set++;
		fileout(set);
		one=false;
		me=true;
		Gobang.this.selectValue=4;
		Gobang.this.littlegame();
		return;	
	}
	public void white_win()//還原所有到棋盤一開始的預設值
	{
		mycount=0;
		othercount=0;
		myfield.setText("0");
		otherfield.setText("0");
		mycoord.setText("(0,0)");
	    othercoord.setText("(0,0)");
		record_out.close();
		JOptionPane.showMessageDialog(this, "白棋勝利");
		clearChess();
		set++;
		fileout(set);
	    one=false;
		me=true;
		Gobang.this.selectValue=4;
		Gobang.this.littlegame();
		return;
	}
	

	public void doit() {// 判斷落子顏色及判斷勝方
		x = (ex - 19) / 50;
		y = (ey - 19) / 50;
		if (chess[x][y] == 0) {// 點選位置無棋子
			if (me == true) {
				System.out.println("black enter");
				chess[x][y] = 11;// 黑子
				for (int i = 0; i < 12; i++) {
					for (int j = 0; j < 12; j++) {
						if (chess[i][j] == 22) {
							chess[i][j] = 2;// 把剛才下的加白框的白子轉換為普通白子
						}
					}
				}
				me = false;// 換為白子落子
			} else if (me == false) {
				System.out.println("white enter");
				chess[x][y] = 22;// 白子
				for (int i = 0; i < 12; i++) {
					for (int j = 0; j < 12; j++) {
						if (chess[i][j] == 11) {
							chess[i][j] = 1;// 把剛才下的加黑框的黑子轉換為普通黑子
						}
					}
				}
				me = true;// 換為黑子落子
			}
		}
		repaint();// 重繪棋盤後判斷勝方
	}
//Steven add
	public void sendXY(int xSend, int ySend) {// 傳送滑鼠點選的座標
			//Steven add0531
		mycount++;
		myfield.setText(String.valueOf(mycount));
		String coord="("+x+" , "+y+" )";
		mycoord.setText(coord);		
		record_out.println("I "+x+" "+y);
		//	  
		
			String strr = "B-"+xSend + "-" + ySend;
		     if(Gobang.this.isServer==true)
                    Gobang.this.server.dataOutput(strr);
             else
                    Gobang.this.client.dataOutput(strr);
                // 清除下方文字欄位內容
		
	}
	
	
	
	public void receive() {//接收對方發來的訊息
			
			String strReceive=null;
			 if(Gobang.isServer==true)
             {
				 strReceive=Gobang.server.getMessage();//從sever端接收訊息
			 }
             else
			 {
				 strReceive=Gobang.client.getMessage();//從client端接收訊息
			 }
          
				 
				String[] strs = strReceive.split("-");// 使用"-"分割字串,得到座標
				
				if(strs[0].equals("B"))
				{
				ex = Integer.parseInt(strs[1]);
				ey = Integer.parseInt(strs[2]);
				//Stevne add0531
				othercount++;
				otherfield.setText(String.valueOf(othercount));
				//
				int x_cord=(ex-19)/50;
				int y_cord=(ey-19)/50;
				String coord="("+x_cord+" , "+y_cord+" )";
				othercoord.setText(coord);
				record_out.println("O "+x_cord+" "+y_cord);
				//	
				doit();
				encircle();
				checkWiner();
				one = true;
				}
				else if(strs[0].equals("A")){ //接收來自對話盒的訊息
				 String s = "";
					if(strs.length==1)
					{
						s="";		  //長度只有一狀況是因為甚麼都沒按就按enter
					}
 					else          //如果使用者用了很多-，要接起來
					{
						for(int i=1; i<strs.length;i++)
						{
							if(i>1)
								s=s+"-"+strs[i];
						    else
								s=s+strs[i];
						}
					}
				  // 0603
					try{
						// System.out.println(doc.getLength());
						doc.insertString(doc.getLength(),s+"\n",doc.getStyle("other"));
						// System.out.println(doc.getLength());
					} 
					catch (BadLocationException a) {
						
					}
				  // outputArea.append(s);	
				}
				else if(strs[0].equals("C"))//由client端發出C-header的訊息，並且由server端判讀
				{
					int othervalue=Integer.parseInt(strs[1]);//othervalue是由client端收到的訊息
					while(Gobang.this.selectValue==4)//如果server端的selectvalue值仍是4，表示server端，還有沒有選則，因此還不能處理，所以一直繞回圈延宕處理
					{
						System.out.println("test");
					}
					switch(Gobang.this.selectValue)//0:剪刀 1:石頭  2:布
					{
						case 0: if(othervalue==0)//如果server端出剪刀0，client端出剪刀
						        {
								   one=false;//不能下棋
								   Gobang.this.server.dataOutput("D-S");//server端傳出平手訊息     
								   JOptionPane.showMessageDialog(null, "Even!Try again!");//發出平手訊息
								   Gobang.this.littlegame();//繼續玩遊戲
							    }
								else if(othervalue==1)//如果server端出剪刀0，client端出石頭
								{
									one=false;//不能下棋
									Gobang.this.server.dataOutput("D-L");//發出輸了的訊息     
									JOptionPane.showMessageDialog(null, "Service lose");//跳出輸了的訊息
									isblack=false;//不能先下棋
								}
								else if(othervalue==2)//如果server端出剪刀0，client端出布
								{
								   one=true;//能下棋
								   Gobang.this.server.dataOutput("D-W"); //發出贏了的訊息     
								   JOptionPane.showMessageDialog(null, "Service win");//跳出贏了的訊息
								   isblack=true;//能先下棋
								}
								break;
						case 1: if(othervalue==0)//如果server端出石頭，client端出剪刀
						        {
								   one=true;//能下棋
								   Gobang.this.server.dataOutput("D-W"); //發出贏了的訊息         
								   JOptionPane.showMessageDialog(null, "Service win");//跳出贏了的訊息
								   isblack=true;//能先下棋
							    }
								else if(othervalue==1)//如果server端出石頭，client端出石頭
								{
									one=false;//不能下棋
									Gobang.this.server.dataOutput("D-S"); //發出平手訊息    
									JOptionPane.showMessageDialog(null, "Even!Try again!");
									Gobang.this.littlegame();//繼續玩遊戲
								}
								else if(othervalue==2)//如果server端出石頭，client端出布
								{
								   one=false;//不能下棋
								   Gobang.this.server.dataOutput("D-L");//發出輸了的訊息     
								   JOptionPane.showMessageDialog(null, "Service lose");//跳出輸了的訊息
								   isblack=false;//不能先下棋
								}
								break;
						case 2: if(othervalue==0)//如果server端出布，client端出剪刀
						        {
								   one=false;//不能下棋
								   Gobang.this.server.dataOutput("D-L");//發出輸了的訊息          
								   JOptionPane.showMessageDialog(null, "Service lose");//跳出輸了的訊息
								   isblack=false;//不能先下棋
							    }
								else if(othervalue==1)//如果server端出布，client端出石頭
								{
									one=true;//能下棋
									Gobang.this.server.dataOutput("D-W");//發出贏了的訊息     
									JOptionPane.showMessageDialog(null, "Service win");//跳出贏了的訊息
									isblack=true;//能先下棋
								}
								else if(othervalue==2)//如果server端出布，client端出布
								{
								   one=false;//不能下棋
								   Gobang.this.server.dataOutput("D-S");//發出平手的訊息        
								   JOptionPane.showMessageDialog(null, "Even!Try again!");//跳出平手的訊息
								   Gobang.this.littlegame();//繼續玩遊戲
									
								}
								break;
											
					}
					
					
				}
				else if(strs[0].equals("D"))//client端由接收端得到判定輸贏訊息(前綴為D)
				{
					
					if(strs[1].equals("W"))//如果sever說他贏了
				    {
						one=false;//client不能下棋(輸了)
						JOptionPane.showMessageDialog(null, "Client lose");//跳出client端輸了得訊息
						isblack=false;//client端後下
					}
					else if(strs[1].equals("L"))//如果sever說他輸了
					{
						 one=true;//client先下棋(贏了)
						 JOptionPane.showMessageDialog(null, "Client win");//跳出client端贏了得訊息
						 isblack=true;////client端先下
							
					}
					else if(strs[1].equals("S"))//sever說平手
					{
						 one=false;//兩方都不能下
						 JOptionPane.showMessageDialog(null, "Even!Try again!");//再玩一次
						 Gobang.this.littlegame();//彈出小遊戲視窗					
					}
					
				}
			
	}
	
	//Steven add
	public void mouseClicked(MouseEvent e){
		
		Class tmp = e.getSource().getClass();
		System.out.println(e.getSource().getClass()==JLabel.class);
		if(tmp == JLabel.class){
			JLabel jb = (JLabel)e.getSource();
			if(jb.getIcon().equals(voiceIcon)){
				jb.setIcon(muteIcon);
				backgroundMusic.stop();
			}
			else{
				jb.setIcon(voiceIcon);
				backgroundMusic.play();
			}
		}
		//because e.getX() is get component relative position, so the JLabel position will overlap the checkBoard
		else{
			 System.out.println(e.getX() + "==" + e.getY());// 在棋盤上點選滑鼠時在控制檯輸出座標資訊,方便測試
			 ex = e.getX();
			 ey = e.getY();
		
			if (ex >= 25 && ex <= 625 && ey >= 25 && ey <= 625) {// 控制滑鼠點選位於棋盤內部
				x = (ex - 19) / 50;// 使棋子排列在棋盤交點處
				y = (ey - 19) / 50;
				Toolkit.getDefaultToolkit().beep();
			}
			if (chess[x][y] != 0) {// 當點選位置已經有棋子,則此次點選無效,並繼續落子
				return;
			}
			if (one == true) {// 輪到一方落子時,落子判斷勝負,並傳送滑鼠點選的座標
				doit();// 若傳送座標前判斷勝負,則會導致最後一顆棋子落子資訊阻塞,判斷完畢後再次傳送,故此處不判斷
				sendXY(ex, ey);// 先傳送座標再判斷勝負
		
				encircle();
				checkWiner();
				
				
			}
			one = false;// 換到對方落子 */
			
		}
		
	}
	public void mouseReleased(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
        // System.out.println("keyTyped");
    }
     
    public void keyPressed(KeyEvent e) {
        // System.out.println(e.getSource());
		if(e.getKeyCode()==KeyEvent.VK_P&& e.isControlDown()){
			subframe = new SubFrame(parentFrame);
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			String dialcontent = dial.getText();
			dial.setText("");	
			// 0603
			try {
				// System.out.println(doc.getLength());
				
				doc.insertString(doc.getLength(),":"+dialcontent+"\n",doc.getStyle("me"));
				// System.out.println(doc.getLength());
			} 
			catch (BadLocationException ex) {
				ex.printStackTrace();
			}
						
            // 將資料透過連線執行緒送出
			String s="A-"+dialcontent; //將要傳出去的資料加上headerA
            if(Gobang.this.isServer) Gobang.this.server.dataOutput(s);     
            else Gobang.this.client.dataOutput(s);
                           
		}
    }
     
    public void keyReleased(KeyEvent e) {
        // System.out.println("keyReleased");
    }


}
	
	
	
	// Main
	public static void main(String[] args){
		Gobang gobang = new Gobang();
		
	}
	
}


// 訊息類型 (message type) :
// JOptionPane.ERROR_MESSAGE (=0)
// JOptionPane.INFORMATION_MESSAGE (=1)
// JOptionPane.WARNING_MESSAGE (=2)
// JOptionPane.QUESTION_MESSAGE (=3)
// JOptionPane.PLAIN_MESSAGE (= -1)

// 選項按鈕類型 (option type) :
// JOptionPane.DEFAULT_OPTION (= -1)
// JOptionPane.YES_NO_OPTION (=0)
// JOptionPane.YES_NO_CANCEL_OPTION (=1)
// JOptionPane.OK_CANCEL_OPTION (=2)