import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class GobangClient extends Thread{
	
	private Socket client;        // �Ȥ�ݳs�uSocket����
	private InetAddress host = null;
	private int port;
	
	private Gobang gobang = null;
	private BufferedReader theInputStream;  // Ū���Ȥ�ݸ�ƪ��w�İ�
    private PrintStream theOutputStream;    // �N��ƥ�X���Ȥ�ݪ�����
    private String message=null;                  // �q�Ȥ��Ū�쪺���
	
	
	public GobangClient(String ip, int port){
		try{
			host = InetAddress.getByName(ip);
            this.port = port;
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(null,"Wrong input!","",2);
		}
	}
	
	public void setGUI(Gobang gobang){
		this.gobang = gobang;
	}
	
	public String getMessage() {
        return message;
    }
	
	//Override Thread
	public void run(){
		try {
            
            // �إ�Socket����ù��ճs�u
            client = new Socket(host, port);
           
 
            // �qInputStream�إ�Ū���w�İ�
            theInputStream = new BufferedReader(
                new InputStreamReader(client.getInputStream()));
            // �qOutputStream���إ�PrintStream����
            theOutputStream = new PrintStream(client.getOutputStream());
 
            // Ū����ưj��
            while((message = theInputStream.readLine()) != null) {
                
                gobang.mp.receive();
            }
 

        }
        catch (IOException e) {
             JOptionPane.showMessageDialog(null,"Cannot Connect","",2);
        }
	}
	public void dataOutput(String data) {
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