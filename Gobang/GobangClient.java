import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class GobangClient extends Thread{
	
	private Socket client;        // 客戶端連線Socket物件
	private InetAddress host = null;
	private int port;
	
	private Gobang gobang = null;
	private BufferedReader theInputStream;  // 讀取客戶端資料的緩衝區
    private PrintStream theOutputStream;    // 將資料丟出給客戶端的物件
    private String message=null;                  // 從客戶端讀到的資料
	
	
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
            
            // 建立Socket物件並嘗試連線
            client = new Socket(host, port);
           
 
            // 從InputStream建立讀取緩衝區
            theInputStream = new BufferedReader(
                new InputStreamReader(client.getInputStream()));
            // 從OutputStream中建立PrintStream物件
            theOutputStream = new PrintStream(client.getOutputStream());
 
            // 讀取資料迴圈
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