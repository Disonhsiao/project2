import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.sound.midi.*;
import java.io.*; 
import java.net.*;
import java.util.*;
import javax.swing.Timer;
import java.awt.geom.*;

public class Gobangreplay extends JFrame {
	
	// private JPanel contentpane = null;
	static private GobangServer server = null;
	static private GobangClient client = null;
	//Steven add
	public MyPanel mp=null;
	static private boolean isServer;
	private  Image  img = null; 
	//private Timer timer=null;
	
	public Gobangreplay(){
		super();
		initialize2();
	}
	public Gobangreplay(JLabel jpiece,boolean isbla){
		super();
		initialize(jpiece,isbla);
	}
	
	public void initialize(JLabel jpiece,boolean isbla){
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Gobang Replay");
		//Stevne add
		mp=new MyPanel(jpiece,isbla);
		//
		this.setContentPane(mp);
		this.pack();
		this.setVisible(true);
		img=getToolkit().getImage("./img/bg2.jpg");
		//timer=new Timer(500,this);
	}
	public void initialize2(){
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Gobang Replay");
		//Stevne add
		mp=new MyPanel();
		//
		this.setContentPane(mp);
		this.pack();
		this.setVisible(true);
		img=getToolkit().getImage("./img/bg2.jpg");
		//timer=new Timer(500,this);
	}
	

	
	public class MyPanel extends JPanel implements ActionListener{
	
	private JLabel Title = null;
	private ImageIcon TitleIcon = new ImageIcon("./img/titleIcon4.png");
	//Steven add
	int x;// ���l�B�ѽL�y��
	int y;
	int[][] chess = new int[12][12];// �ѽL�j�p��12x12
	int ex;// �ǰe�ƹ��I��y��
	int ey;
	boolean me = true;// ��l�Ƭ��v������¤l
	boolean one = true;// �����p������C���u��@�ӤH�U�l
	//static boolean start = false;// �w�]�}�ҹC������U�l,�ݥ���ܹC���Ҧ�
	
	private JFileChooser fileChooser=null;
	private JButton fileJB = null;
	private String tmp = null;
	private ArrayList<Integer> x_cord = new ArrayList<Integer>();
	private ArrayList<Integer> y_cord = new ArrayList<Integer>();
	private ArrayList<String> who=new ArrayList<String>();
	private Timer timer=null;
	private int index=0;
	private JLabel pcolor=null;
	private boolean isblack =true;
	
	public MyPanel(){
		initialize();
		fileChooser=new JFileChooser("record/");
	}
	public MyPanel(JLabel piececolor,boolean isbla){
		initialize();
		pcolor=new JLabel();
		pcolor=piececolor;
		isblack=isbla;
		fileChooser=new JFileChooser("record/");
	}
	
	public void initialize(){
		this.setPreferredSize(new Dimension(1000,700));
		this.setBackground(Color.GRAY);
		this.setLayout(null);
		
		Title = new JLabel();
		Title.setIcon(TitleIcon);
		Title.setBounds(new Rectangle(680, 50, TitleIcon.getIconWidth(), TitleIcon.getIconHeight()));
		this.add(Title);
		this.add(getfile());
		timer=new Timer(500,this);
			
	}
	///////Steven add 0601
	public JButton getfile()
	{
		if( fileJB == null)
		{
			fileJB = new JButton();
			fileJB.setBounds(new Rectangle(650, 600, 100, 30));		
			fileJB.setText("choose file");
			fileJB.addActionListener(
                new java.awt.event.ActionListener() {
					
                    public void actionPerformed(java.awt.event.ActionEvent e) {
				timer.stop();	
				index=0;
                clearChess();
				File file=null;
				int result;
				fileChooser.setApproveButtonText("make sure");
				fileChooser.setDialogTitle("Open file");
				result=fileChooser.showOpenDialog(Gobangreplay.this.mp);
				if(result==JFileChooser.APPROVE_OPTION)
				{
				   file=fileChooser.getSelectedFile();   
				}
			    else if(result==JFileChooser.CANCEL_OPTION)
				{
				}

                Scanner inputStream = null;
	        	String fileString = "";
				try{
					inputStream = new Scanner(new FileInputStream(file));
					}
				catch(FileNotFoundException E){
				System.out.println("File was not found or could not be opened.");
				System.exit(0);
				}
	    
				while(inputStream.hasNextLine()) {
					fileString += inputStream.nextLine();
					fileString +=" ";
					}  
					tmp=fileString;
          			System.out.println(tmp);   
					setxy();
					timer.restart();
                         
                    }
                }
            );
		}
		return fileJB;
	}
	
	public void setxy()
	{
		x_cord.clear();
		y_cord.clear();
		who.clear();
		String[] sp = tmp.trim().split(" +");
		System.out.println("sp l"+sp.length);
		for(int i=0; i<sp.length; i++ )
		{
		  System.out.println("sp "+sp[i]);
		}		
		int j=0;
		for(int i=0; i<sp.length; i++ )
		{
		  if(i%3==1)
          {
			  x_cord.add(Integer.valueOf(sp[i]));
		  }			  
		  else if(i%3==2)
		  {
			  y_cord.add(Integer.valueOf(sp[i]));
		  }
		  else if(i%3==0)
		  {
			  who.add(sp[i]);
		  }
		}
	}
	
	public void actionPerformed(ActionEvent e){
    
        if (e.getSource() == timer)
         {
             if(index < x_cord.size())
			 {
				int x=x_cord.get(index);//Ū�ɨӪ�x�y��
				int y=y_cord.get(index);//Ū�רӪ�y�y��
				String s=who.get(index);//���@����U��
				
				if(s.equals("I"))//I��ܬO�ڤ�
				{
					if(isblack)//�T�{�ڤ�O�_���U
					{
					   chess[x][y]=1;//�]�mchess1�[�ڤ��´�
					   repaint();//��ø�ѽL
					}
					else//�����U
					{
					   chess[x][y]=2;//�]�mchess2�[�ڤ��մ�
					   repaint();//����
					}
				}
				else if(s.equals("O"))//O��ܬO���
				{
					if(isblack)//�T�{�ڤ�O�_���U
					{
					   chess[x][y]=2;//�]�mchess2�[����մ�
					   repaint();//��ø�ѽL
					}
					else//�����U
					{
					   chess[x][y]=1;//�]�mchess1�[����´�
					   repaint();//��ø
					}	
				}
                else if(s.equals("R"))//O��ܬO���
				{
					if(isblack)//�T�{�ڤ�O�_���U
					{
					   chess[x][y]=3;//�]�mchess2�[����մ�
					   repaint();//��ø�ѽL
					}
					else//�����U
					{
					   chess[x][y]=3;//�]�mchess1�[����´�
					   repaint();//��ø
					}	
				}								
				index++;//indexg�O�B�J
				if(index==x_cord.size()) timer.stop();
			 }
         } 
    }

	
	
////////checkboard part	
		
	protected void paintComponent(Graphics g) {
        
		super.paintComponent(g);
		 //draw the baclground
		 Dimension d =getSize();
		 AffineTransform at = new AffineTransform();
		 double sc= Math.min( d.width/(double)img.getWidth(null),d.height/(double)img.getHeight(null)  );
		 at.scale(sc,sc);
		 Graphics2D g2 = (Graphics2D) g;
		 g2.drawImage(img,at,this);
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
					if(!pcolor.getText().equals("-16777216")&&isblack) g.setColor(new Color(Integer.valueOf(pcolor.getText())));
					g.fillOval((i + 1) * 50 - 19, (j + 1) * 50 - 19, 38, 38); //�e�դl
				} else if (chess[i][j] == 2) {  //chess�謰2�A�դl
					System.out.println("draw WHITE");
					g.setColor(Color.white);    //�]�w�C��O�դl
					if(!pcolor.getText().equals("-16777216")&&(!isblack)) g.setColor(new Color(Integer.valueOf(pcolor.getText())));
					g.fillOval((i + 1) * 50 - 19, (j + 1) * 50 - 19, 38, 38); //�e�դl
				} else if (chess[i][j] == 11) {// �I�︨�¤l�ɥ~�[�Ӷ¥����
					g.setColor(Color.black);   //�]�w�C��O�¤l
					//0601
					if(!pcolor.getText().equals("-16777216")&&isblack) g.setColor(new Color(Integer.valueOf(pcolor.getText())));
					g.fillOval((i + 1) * 50 - 19, (j + 1) * 50 - 19, 38, 38);
					g.drawRect((i + 1) * 50 - 19, (j + 1) * 50 - 19, 38, 38); //�e�W�~��
				} else if (chess[i][j] == 22) {// �I�︨�դl�ɴѤl�~�[�ӥե����
					g.setColor(Color.white);//�]�w�C��O�դl
					if(!pcolor.getText().equals("-16777216")&&(!isblack)) g.setColor(new Color(Integer.valueOf(pcolor.getText())));
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
	

}
	
	
	
	// Main
	
	public static void main(String[] args){
		Gobangreplay gobang = new Gobangreplay();
		
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