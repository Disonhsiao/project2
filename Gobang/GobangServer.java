import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class GobangServer extends Thread{
	
	private ServerSocket server = null;
	private Socket client = null;
	
	private Gobang gobang = null;
	
	private BufferedReader theInputStream;  // Ū���Ȥ�ݸ�ƪ��w�İ�
    private PrintStream theOutputStream;    // �N��ƥ�X���Ȥ�ݪ�����
    private String message=null;                  // �q�Ȥ��Ū�쪺���
	
	//use port to create Server object
	public GobangServer(int port){
		try {
            server = new ServerSocket(port);
			System.out.println("create success");
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Wrong input!","",2);
        }
	}
	
	public void setGUI(Gobang gobang){
		this.gobang = gobang;
	}
	
	public String getMessage() {//��gobang(GUI����)�ϥέn���o�T����
        return message;
    }
	
	//Override Thread
	public void run(){
		
	   try{
          
            client = server.accept();//�Ҧ��Ӧ�client�ݪ����
 
            
            theInputStream = new BufferedReader(
            new InputStreamReader(client.getInputStream()));
            theOutputStream = new PrintStream(client.getOutputStream());
 
            // Ū����ưj��
            while((message = theInputStream.readLine()) != null) {
                
               gobang.mp.receive();//�p�G���ܦh��A�|�@���I�sgobang���禡receiveb�A��gobang�����T��
            }
 
        }
        catch (IOException e) {
           JOptionPane.showMessageDialog(null,"Cannot Connect","",2);
        }			
	}
	
	
	
	public void dataOutput(String data) { //server�ݭn�N��ƶǥX�h
        theOutputStream.println(data);
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