import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class loadWindow extends JFrame{

	private static final long serialVersionUID = 1L;

	public loadWindow(){
		super("Loading..");
		setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/imag.gif"));	
		JLabel jlabel = new JLabel("    Loading, Please Wait.!    ");
		jlabel.setIcon(icon);
		JPanel p = new JPanel();
		p.add(jlabel);
		add(p, BorderLayout.CENTER);
		setSize(225, 125);
		setResizable(false);	
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
}
