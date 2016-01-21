import java.io.Serializable;

public class Folder implements Serializable {
	
	private static final long serialVersionUID = 1L;
	String Passowrd;
	String Hint;
	
	Folder(String Password, String Hint){
		this.Passowrd = Password;
		this.Hint = Hint;
	}

}
