import java.io.File;
import javax.swing.JOptionPane;

public class StartingPoint {
	
	public static void main(String args[]){
		Operations op = new Operations();
		if(args.length == 2){
			File dat = new File(Operations.Bin + op.encode(args[1]) + ".dat");
			if(args[0].equals("lock")){
				if(dat.exists()){
					JOptionPane.showMessageDialog(null, "Folder You are trying to Lock is Already Locked.!", "ALert - 201 Folder already Locked", JOptionPane.WARNING_MESSAGE);
				}else{
					op.lock(args[1], false);
				}
			}else if(args[0].equals("delete")){
				op.unlock(args[1], false);
			}else if(args[0].equals("secure")){
				op.secure(args[1], false);
			}else if(args[0].equals("unsecure")){
				op.unsecure(args[1], false);
			}else{
				JOptionPane.showMessageDialog(null, "Invalid Arguements.", "Error-102", JOptionPane.ERROR_MESSAGE);
			}
		}else if(args.length == 0){
			op.window1();
		}else {
			JOptionPane.showMessageDialog(null, "Invalid Arguements.", "Error-101", JOptionPane.ERROR_MESSAGE);
		}
	}
}
