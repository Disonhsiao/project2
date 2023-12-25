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
	
	private BufferedReader theInputStream;  // 讀取客戶端資料的緩衝區
    private PrintStream theOutputStream;    // 將資料丟出給客戶端的物件
    private String message=null;                  // 從客戶端讀到的資料
	
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
	
	public String getMessage() {//給gobang(GUI介面)使用要取得訊息用
        return message;
    }
	
	//Override Thread
	public void run(){
		
	   try{
          
            client = server.accept();//皆收來自client端的資料
 
            
            theInputStream = new BufferedReader(
            new InputStreamReader(client.getInputStream()));
            theOutputStream = new PrintStream(client.getOutputStream());
 
            // 讀取資料迴圈
            while((message = theInputStream.readLine()) != null) {
                
               gobang.mp.receive();//如果有很多行，會一直呼叫gobang的函式receiveb，讓gobang接收訊息
            }
 
        }
        catch (IOException e) {
           JOptionPane.showMessageDialog(null,"Cannot Connect","",2);
        }			
	}
	
	
	
	public void dataOutput(String data) { //server端要將資料傳出去
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