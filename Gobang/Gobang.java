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
		//0602 �]�w�@�}�lmp requestFocus
		mp.requestFocus();
		this.setVisible(true);
	}
	
	//Steven add
	public void littlegame()//�ŤM���Y��
	{
		ImageIcon pic1=new ImageIcon("./img/sc.png");//�ŤM
        ImageIcon pic2=new ImageIcon("./img/st.png");//���Y
        ImageIcon pic3=new ImageIcon("./img/cl.jpg");//�B
      
        Object[] options = { pic1, pic2, pic3 };
        selectValue=JOptionPane.showOptionDialog(null,
                                      "Please choose one to determine who will get prority to play the game:",
                                     "Warning",
                                     JOptionPane.DEFAULT_OPTION,
                                     JOptionPane.WARNING_MESSAGE,
                                     null,
                                     options,
                                     options[0]);//���X��ܰŤM���Y������
		//System.out.println("hellogame"+selectValue);
		
		if(isServer==false)//�p�G�Oclient��
		{
			String s="C-"+String.valueOf(selectValue);//�s�X�[�e��C
			client.dataOutput(s);//�Ndata�ǰe�X�h
		}
	}
	
	//judy add
	public JMenuBar getMenubar(){
		if(menubar==null){
			menubar = new JMenuBar();
			m1 = new JMenu("Setting");
			m11 = new JMenuItem("Preferences(P)..");
			m1.setFont(new Font("�L�n������",Font.BOLD,14));
			m11.setFont(new Font("�L�n������",Font.BOLD,12));
			
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
	
	// JFrame��Listener
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
	
	
	
	public JPanel getTopContentPane(){//�Ψӳ]�w�I���C��
		if(topContentpane == null){
			topContentpane = new JPanel();
			topContentpane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2)," �I���C��]�w�G ",
								1,0,new Font("�L�n������", Font.BOLD, 16),Color.BLACK));
			// topContentpane.setPreferredSize(new Dimension(150,100));
			topContentpane.setLayout(new FlowLayout(FlowLayout.CENTER));
			topContentpane.setBackground(new Color(215, 214, 213));
			
			choosebkgColor = new JButton("����C��...");
			bkgdefault = new JButton("�٭�w�]��");
			bkgapply = new JButton("����");
			
			choosebkgColor.setFont(new Font("�L�n������",Font.PLAIN,14));
			choosebkgColor.setBackground(Color.GRAY);
			choosebkgColor.setForeground(Color.WHITE);
			bkgdefault.setFont(new Font("�L�n������",Font.PLAIN,14));
			bkgdefault.setBackground(Color.GRAY);
			bkgdefault.setForeground(Color.WHITE);
			bkgapply.setFont(new Font("�L�n������",Font.PLAIN,14));
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
	
	public JPanel getDownContentPane(){//�Ψӳ]�w�Ѥl�C��
		if(downContentpane == null){
			downContentpane = new JPanel();
			downContentpane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2)," �Ѥl�C��]�w�G ",
								1,0,new Font("�L�n������", Font.BOLD, 16),Color.BLACK));
			// downContentpane.setPreferredSize(new Dimension(150,100));
			
			downContentpane.setLayout(new FlowLayout(FlowLayout.CENTER));
			downContentpane.setBackground(new Color(215, 214, 213));
			
			choosepieceColor = new JButton("����C��...");
			piecedefault = new JButton("�٭�w�]��");
			pieceapply = new JButton("����");
			
			choosepieceColor.setFont(new Font("�L�n������",Font.PLAIN,14));
			choosepieceColor.setBackground(Color.GRAY);
			choosepieceColor.setForeground(Color.WHITE);
			piecedefault.setFont(new Font("�L�n������",Font.PLAIN,14));
			piecedefault.setBackground(Color.GRAY);
			piecedefault.setForeground(Color.WHITE);
			pieceapply.setFont(new Font("�L�n������",Font.PLAIN,14));
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
	
	//subframe��Listener
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
			choose = JColorChooser.showDialog(this, "��ܭI���C��", Color.GRAY);
			
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
			choose = JColorChooser.showDialog(this, "��ܴѤl�C��", Color.BLACK);
			
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
		int opt=JOptionPane.showOptionDialog(null,"Please choose what you want to be?","",0,3,null,options,"Server");//���X�e���տ�ܷ���@��
		//choose server
		if(opt==0){
			setToServer();
			isServer=true;
			mp.fileout(1);	 //�}�l�Ĥ@�L��Ū��
		}
		//choose client
		else{
			setToClient();
			isServer=false;
			mp.fileout(1);	 //�}�l�Ĥ@�L��Ū��
		}
											 
	}
	
	public void setToServer(){//��ܭn��server��
		String inputPort = JOptionPane.showInputDialog(null,"Please set port","",1);//�n�}�l��port
		int port;
		try{
			port = Integer. parseInt(inputPort);
			server = new GobangServer(port);
			server.setGUI(this);
			server.start();//�}�l�����
		}
		catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null,"Wrong input!","",2);
		}
		
	}
	public void setToClient(){
		String inputHost = JOptionPane.showInputDialog(null,"Please set host","",1);//client�ݭn��Jhost
		String inputPort = JOptionPane.showInputDialog(null,"Please set port","",1);//�}�l��port
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
	int x;// ���l�B�ѽL�y��
	int y;
	int[][] chess = new int[12][12];// �ѽL�j�p��12x12
	int ex;// �ǰe�ƹ��I��y��
	int ey;
	boolean me = true;// ��l�Ƭ��v������¤l
	boolean one = false;// �����p������C���u��@�ӤH�U�l
	//static boolean start = false;// �w�]�}�ҹC������U�l,�ݥ���ܹC���Ҧ�
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
		
		//invisible �u�O���F���ǰeJPanel(Container)�i�H��getComponent �ӳ]�w�Ѥl�C��
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
			musicList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE, 3)," �п�ܭI�����֡G ",
								2,0,new Font("�L�n������", Font.BOLD, 16),Color.WHITE));
			musicList.setFont(new Font("�L�n������", Font.PLAIN, 14));
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
	
	//JPanel��Listener���F�n���뤣�P���ӷ�
	public void actionPerformed(ActionEvent e){
		String cmd = e.getActionCommand();
		// System.out.println(cmd);
		switch(cmd){
			case "comboBoxChanged":       //�Ӧۭ��ֿ���combobox                                        
				String select = musicList.getSelectedItem().toString();
				backgroundMusic.stop();
				playSound(select);
				this.requestFocus();
			break;
			case "replay":               //�Ӧ۫��s����
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
			mylb= new JLabel("�ڤ�Ѥl��" , SwingConstants.CENTER);
			mylb.setBounds(980,200,80,30);
			mylb.setFont(new Font("�L�n������",Font.BOLD,16));
			mylb.setForeground(Color.WHITE);
			mylb.setFocusable(false);
		}
		return mylb;
	}
	
	public JLabel getotherlb()
	{
		if(otherlb == null)
		{
			otherlb= new JLabel("���Ѥl��", SwingConstants.CENTER);
			otherlb.setBounds(getmylb().getX(),300,getmylb().getWidth(),getmylb().getHeight());
			otherlb.setForeground(Color.WHITE);
			otherlb.setFont(new Font("�L�n������",Font.BOLD,16));
			otherlb.setFocusable(false);
		}
		return otherlb;
	}
	
	public JLabel getcurrentcoordlb(){
		if(currentcoordlb==null){
			currentcoordlb = new JLabel("�ثe�y��", SwingConstants.CENTER);
			currentcoordlb.setBounds(1080,getmylb().getY(),getmylb().getWidth(),getmylb().getHeight());
			currentcoordlb.setForeground(Color.BLACK);
			currentcoordlb.setBackground(new Color(215, 214, 213));
			currentcoordlb.setOpaque(true);
			currentcoordlb.setFont(new Font("�L�n������",Font.PLAIN,16));
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
			myfield.setFont(new Font("�L�n������",Font.PLAIN,16));
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
			otherfield.setFont(new Font("�L�n������",Font.PLAIN,16));
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
			mycoord.setFont(new Font("�L�n������",Font.BOLD,18));
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
			othercoord.setFont(new Font("�L�n������",Font.BOLD,18));
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
			if(Gobang.this.isServer==true)//�p�G�Oserver���p�A�s�ɬO��record_�L��.txt�өR�W
			record_out = new PrintWriter( new FileOutputStream(s) );
			else if(Gobang.this.isServer==false)//�p�Gclient���p�A�s�ɬO��record_�L��.txt�өR�W
			record_out = new PrintWriter( new FileOutputStream(s2) );
		   }
		catch(FileNotFoundException E)
			{
			System.out.println("Can not find button");
			System.exit(0);
			}
	}
	
	public JTextPane getArea()//����ܲ���J�إΪ�textArea
	{	
		if(outputArea==null)
		{	
			doc = new DefaultStyledDocument();
		    outputArea = new JTextPane(doc);
            outputArea.setFont(new Font("�L�n������",Font.PLAIN,16));
			// outputArea.setLineWrap(true);
			outputArea.setEditable(false);
			outputArea.setBackground(Color.BLACK);
			outputArea.setFocusable(false);
			// 0603
			outputArea.setMargin(new Insets(5,5,0,5));
			
			//add style
			Style sys = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
			Style s = doc.addStyle("me",sys); // �[�J
			StyleConstants.setForeground(s,Color.YELLOW); // �C��
			StyleConstants.setBold(s,true); // ����
			
			s = doc.addStyle("other",sys); // �[�J
			StyleConstants.setForeground(s,Color.WHITE); // �C��
			
	
			
		}
		return outputArea;
	}
	
	
	public JScrollPane getopa() //��ܲ��Ϊ�jscrollpane
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
			//�]�������i����(���@�����)
            opaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            opaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			opaScrollPane.setBounds(new Rectangle(650, 200, 300, 400));
			opaScrollPane.setBorder(BorderFactory.createEmptyBorder());
			
		}
		return opaScrollPane;
	}
	
	public JTextField getdialog()//����ܲ���J�إΪ�
	{
		if( dial == null)
		{
			dial = new JTextField();
			dial.setFont(new Font("�L�n������",Font.BOLD,16));
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

	
	public JButton getreplay() //�]�wrepaly���s
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
		// �e�ѽL
		for (int i = 50; i <= 600; i += 50) {
			g.setColor(Color.WHITE); //�e�սu
			g.drawLine(i, 50, i, 600);//(�q(i,50)�e��(i,600)
			g.drawLine(50, i, 600, i);//(�q(50,i)�e��(600,i)
		}
	}
	
	//Steven add
	public void drawChess(Graphics g) {
		// System.out.println("draw chess");
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				if (chess[i][j] == 1) {			//chess�_��謰1�A�¤l
				    System.out.println("draw black");
					g.setColor(Color.black);     //�]�w�C��O�¤l
					//0601
					if(!piececolor.getText().equals("-16777216")&&isblack) g.setColor(new Color(Integer.valueOf(piececolor.getText())));
					g.fillOval((i + 1) * 50 - 19, (j + 1) * 50 - 19, 38, 38); //�e�դl
				} else if (chess[i][j] == 2) {  //chess�謰2�A�դl
					System.out.println("draw WHITE");
					g.setColor(Color.white);    //�]�w�C��O�դl
					if(!piececolor.getText().equals("-16777216")&&(!isblack)) g.setColor(new Color(Integer.valueOf(piececolor.getText())));
					g.fillOval((i + 1) * 50 - 19, (j + 1) * 50 - 19, 38, 38); //�e�դl
				} else if (chess[i][j] == 11) {// �I�︨�¤l�ɥ~�[�Ӷ¥����
					g.setColor(Color.black);   //�]�w�C��O�¤l
					//0601
					if(!piececolor.getText().equals("-16777216")&&isblack) g.setColor(new Color(Integer.valueOf(piececolor.getText())));
					g.fillOval((i + 1) * 50 - 19, (j + 1) * 50 - 19, 38, 38);
					g.drawRect((i + 1) * 50 - 19, (j + 1) * 50 - 19, 38, 38); //�e�W�~��
				} else if (chess[i][j] == 22) {// �I�︨�դl�ɴѤl�~�[�ӥե����
					g.setColor(Color.white);//�]�w�C��O�դl
					if(!piececolor.getText().equals("-16777216")&&(!isblack)) g.setColor(new Color(Integer.valueOf(piececolor.getText())));
					g.fillOval((i + 1) * 50 - 19, (j + 1) * 50 - 19, 38, 38);
					g.drawRect((i + 1) * 50 - 19, (j + 1) * 50 - 19, 38, 38);//�e�W�~��
				}
			}
		}
	}
	//Steven add
	public void clearChess() {// ��l�ƴѽL
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				chess[i][j] = 0;
			}
		}
		me = true;// ��l�Ƭ��v�踨�¤l
		repaint();
	}
	//Steven add
	public void checkWiner() {// �P�_�Ӥ�
		int black_count = 0;
		int white_count = 0;
		for (int i = 0; i < 12; i++) {// ��V�P�_
			for (int j = 0; j < 12; j++) {
				if (chess[i][j] == 1 || chess[i][j] == 11) {
					black_count++;//�p�G�O�¤l�s��J��¤l�A�h�֥[
					if (black_count == 5) {//5�p�l
						black_win();
					}
				} else {
					black_count = 0;//�p�G�S���J�줭�s�l�A�hcount�]0
				}
				if (chess[i][j] == 2 || chess[i][j] == 22) {
					white_count++;//�p�G�O�դl�s��J��դl�A�h�֥[
					if (white_count == 5) {//5�s�l
						white_win();
					}
				} else {
					white_count = 0;
				}
			}
		}
		for (int i = 0; i < 12; i++) {// �ݦV�P�_
			for (int j = 0; j < 12; j++) {
				if (chess[j][i] == 1 || chess[j][i] == 11) {//�P��V�ۦP�A�u�Oi j ����
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
		for (int i = 0; i < 7; i++) {// ���V�k�קP�_(�W�L7�L�k���Q�s�����l)
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
		for (int i = 4; i < 12; i++) {// �k�V���קP�_ 11->12((�C��4�L�k���Q�s�����l))
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
	
	
	
	
	public void black_win()//�٭�Ҧ���ѽL�@�}�l���w�]��
	{
		mycount=0;
	    othercount=0;
		myfield.setText("0");
		otherfield.setText("0");
		mycoord.setText("(0,0)");
	    othercoord.setText("(0,0)");
		record_out.close();
		JOptionPane.showMessageDialog(this, "�´ѳӧQ");
		clearChess();
		set++;
		fileout(set);
		one=false;
		me=true;
		Gobang.this.selectValue=4;
		Gobang.this.littlegame();
		return;	
	}
	public void white_win()//�٭�Ҧ���ѽL�@�}�l���w�]��
	{
		mycount=0;
		othercount=0;
		myfield.setText("0");
		otherfield.setText("0");
		mycoord.setText("(0,0)");
	    othercoord.setText("(0,0)");
		record_out.close();
		JOptionPane.showMessageDialog(this, "�մѳӧQ");
		clearChess();
		set++;
		fileout(set);
	    one=false;
		me=true;
		Gobang.this.selectValue=4;
		Gobang.this.littlegame();
		return;
	}
	

	public void doit() {// �P�_���l�C��ΧP�_�Ӥ�
		x = (ex - 19) / 50;
		y = (ey - 19) / 50;
		if (chess[x][y] == 0) {// �I���m�L�Ѥl
			if (me == true) {
				System.out.println("black enter");
				chess[x][y] = 11;// �¤l
				for (int i = 0; i < 12; i++) {
					for (int j = 0; j < 12; j++) {
						if (chess[i][j] == 22) {
							chess[i][j] = 2;// ���~�U���[�ծت��դl�ഫ�����q�դl
						}
					}
				}
				me = false;// �����դl���l
			} else if (me == false) {
				System.out.println("white enter");
				chess[x][y] = 22;// �դl
				for (int i = 0; i < 12; i++) {
					for (int j = 0; j < 12; j++) {
						if (chess[i][j] == 11) {
							chess[i][j] = 1;// ���~�U���[�®ت��¤l�ഫ�����q�¤l
						}
					}
				}
				me = true;// �����¤l���l
			}
		}
		repaint();// ��ø�ѽL��P�_�Ӥ�
	}
//Steven add
	public void sendXY(int xSend, int ySend) {// �ǰe�ƹ��I�諸�y��
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
                // �M���U���r��줺�e
		
	}
	
	
	
	public void receive() {//�������o�Ӫ��T��
			
			String strReceive=null;
			 if(Gobang.isServer==true)
             {
				 strReceive=Gobang.server.getMessage();//�qsever�ݱ����T��
			 }
             else
			 {
				 strReceive=Gobang.client.getMessage();//�qclient�ݱ����T��
			 }
          
				 
				String[] strs = strReceive.split("-");// �ϥ�"-"���Φr��,�o��y��
				
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
				else if(strs[0].equals("A")){ //�����Ӧ۹�ܲ����T��
				 String s = "";
					if(strs.length==1)
					{
						s="";		  //���ץu���@���p�O�]���ƻ򳣨S���N��enter
					}
 					else          //�p�G�ϥΪ̥ΤF�ܦh-�A�n���_��
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
				else if(strs[0].equals("C"))//��client�ݵo�XC-header���T���A�åB��server�ݧPŪ
				{
					int othervalue=Integer.parseInt(strs[1]);//othervalue�O��client�ݦ��쪺�T��
					while(Gobang.this.selectValue==4)//�p�Gserver�ݪ�selectvalue�Ȥ��O4�A���server�ݡA�٦��S����h�A�]���٤���B�z�A�ҥH�@��¶�^�驵�X�B�z
					{
						System.out.println("test");
					}
					switch(Gobang.this.selectValue)//0:�ŤM 1:���Y  2:��
					{
						case 0: if(othervalue==0)//�p�Gserver�ݥX�ŤM0�Aclient�ݥX�ŤM
						        {
								   one=false;//����U��
								   Gobang.this.server.dataOutput("D-S");//server�ݶǥX����T��     
								   JOptionPane.showMessageDialog(null, "Even!Try again!");//�o�X����T��
								   Gobang.this.littlegame();//�~�򪱹C��
							    }
								else if(othervalue==1)//�p�Gserver�ݥX�ŤM0�Aclient�ݥX���Y
								{
									one=false;//����U��
									Gobang.this.server.dataOutput("D-L");//�o�X��F���T��     
									JOptionPane.showMessageDialog(null, "Service lose");//���X��F���T��
									isblack=false;//������U��
								}
								else if(othervalue==2)//�p�Gserver�ݥX�ŤM0�Aclient�ݥX��
								{
								   one=true;//��U��
								   Gobang.this.server.dataOutput("D-W"); //�o�XĹ�F���T��     
								   JOptionPane.showMessageDialog(null, "Service win");//���XĹ�F���T��
								   isblack=true;//����U��
								}
								break;
						case 1: if(othervalue==0)//�p�Gserver�ݥX���Y�Aclient�ݥX�ŤM
						        {
								   one=true;//��U��
								   Gobang.this.server.dataOutput("D-W"); //�o�XĹ�F���T��         
								   JOptionPane.showMessageDialog(null, "Service win");//���XĹ�F���T��
								   isblack=true;//����U��
							    }
								else if(othervalue==1)//�p�Gserver�ݥX���Y�Aclient�ݥX���Y
								{
									one=false;//����U��
									Gobang.this.server.dataOutput("D-S"); //�o�X����T��    
									JOptionPane.showMessageDialog(null, "Even!Try again!");
									Gobang.this.littlegame();//�~�򪱹C��
								}
								else if(othervalue==2)//�p�Gserver�ݥX���Y�Aclient�ݥX��
								{
								   one=false;//����U��
								   Gobang.this.server.dataOutput("D-L");//�o�X��F���T��     
								   JOptionPane.showMessageDialog(null, "Service lose");//���X��F���T��
								   isblack=false;//������U��
								}
								break;
						case 2: if(othervalue==0)//�p�Gserver�ݥX���Aclient�ݥX�ŤM
						        {
								   one=false;//����U��
								   Gobang.this.server.dataOutput("D-L");//�o�X��F���T��          
								   JOptionPane.showMessageDialog(null, "Service lose");//���X��F���T��
								   isblack=false;//������U��
							    }
								else if(othervalue==1)//�p�Gserver�ݥX���Aclient�ݥX���Y
								{
									one=true;//��U��
									Gobang.this.server.dataOutput("D-W");//�o�XĹ�F���T��     
									JOptionPane.showMessageDialog(null, "Service win");//���XĹ�F���T��
									isblack=true;//����U��
								}
								else if(othervalue==2)//�p�Gserver�ݥX���Aclient�ݥX��
								{
								   one=false;//����U��
								   Gobang.this.server.dataOutput("D-S");//�o�X���⪺�T��        
								   JOptionPane.showMessageDialog(null, "Even!Try again!");//���X���⪺�T��
								   Gobang.this.littlegame();//�~�򪱹C��
									
								}
								break;
											
					}
					
					
				}
				else if(strs[0].equals("D"))//client�ݥѱ����ݱo��P�w��Ĺ�T��(�e��D)
				{
					
					if(strs[1].equals("W"))//�p�Gsever���LĹ�F
				    {
						one=false;//client����U��(��F)
						JOptionPane.showMessageDialog(null, "Client lose");//���Xclient�ݿ�F�o�T��
						isblack=false;//client�ݫ�U
					}
					else if(strs[1].equals("L"))//�p�Gsever���L��F
					{
						 one=true;//client���U��(Ĺ�F)
						 JOptionPane.showMessageDialog(null, "Client win");//���Xclient��Ĺ�F�o�T��
						 isblack=true;////client�ݥ��U
							
					}
					else if(strs[1].equals("S"))//sever������
					{
						 one=false;//��賣����U
						 JOptionPane.showMessageDialog(null, "Even!Try again!");//�A���@��
						 Gobang.this.littlegame();//�u�X�p�C������					
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
			 System.out.println(e.getX() + "==" + e.getY());// �b�ѽL�W�I��ƹ��ɦb�����i��X�y�и�T,��K����
			 ex = e.getX();
			 ey = e.getY();
		
			if (ex >= 25 && ex <= 625 && ey >= 25 && ey <= 625) {// ����ƹ��I����ѽL����
				x = (ex - 19) / 50;// �ϴѤl�ƦC�b�ѽL���I�B
				y = (ey - 19) / 50;
				Toolkit.getDefaultToolkit().beep();
			}
			if (chess[x][y] != 0) {// ���I���m�w�g���Ѥl,�h�����I��L��,���~�򸨤l
				return;
			}
			if (one == true) {// ����@�踨�l��,���l�P�_�ӭt,�öǰe�ƹ��I�諸�y��
				doit();// �Y�ǰe�y�Ыe�P�_�ӭt,�h�|�ɭP�̫�@���Ѥl���l��T����,�P�_������A���ǰe,�G���B���P�_
				sendXY(ex, ey);// ���ǰe�y�ЦA�P�_�ӭt
		
				encircle();
				checkWiner();
				
				
			}
			one = false;// �����踨�l */
			
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
						
            // �N��Ƴz�L�s�u������e�X
			String s="A-"+dialcontent; //�N�n�ǥX�h����ƥ[�WheaderA
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


// �T������ (message type) :
// JOptionPane.ERROR_MESSAGE (=0)
// JOptionPane.INFORMATION_MESSAGE (=1)
// JOptionPane.WARNING_MESSAGE (=2)
// JOptionPane.QUESTION_MESSAGE (=3)
// JOptionPane.PLAIN_MESSAGE (= -1)

// �ﶵ���s���� (option type) :
// JOptionPane.DEFAULT_OPTION (= -1)
// JOptionPane.YES_NO_OPTION (=0)
// JOptionPane.YES_NO_CANCEL_OPTION (=1)
// JOptionPane.OK_CANCEL_OPTION (=2)