import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Operations {
	static String Names[];
	public static String Default = "Control.Panel{3214-5674-9870-1132}";
	public static String Bin = "C:\\ProgramData\\FolderLockBasic\\Bin\\";
	public static String Dir = "C:\\ProgramData\\FolderLockBasic\\";
	public static int ret = 0;

	public void lock(String Path, boolean openAfter) {
		loadWindow ld = new loadWindow();
		JFrame frame = new JFrame("Lock - " + Path);
		frame.setVisible(false);
		frame.setSize(300, 350);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel label1 = new JLabel("Enter your Password : ");
		JLabel label2 = new JLabel("Confirm your Password :");
		JLabel label3 = new JLabel("Enter Password Hint :");

		JButton lock = new JButton("Lock Folder");
		JButton cancel = new JButton("Cancel");

		JPasswordField pf1 = new JPasswordField(15);
		JPasswordField pf2 = new JPasswordField(15);
		JTextField h = new JTextField(15);

		JPanel p1 = new JPanel(new GridBagLayout());
		JPanel p2 = new JPanel(new GridBagLayout());
		JPanel p3 = new JPanel(new GridBagLayout());
		JPanel p4 = new JPanel(new GridBagLayout());
		JPanel p5 = new JPanel(new GridBagLayout());

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int resp = JOptionPane.showConfirmDialog(null, "Are You sure to Cancel and Exit?", "Confirm Action.",
						JOptionPane.YES_NO_OPTION);
				if (resp == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		//// Lock Action Listener Starts Here. Important Piece of Code.!
		lock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				char[] ipPwdA1 = pf1.getPassword();
				char[] ipPwdA2 = pf2.getPassword();

				String ipPwd1 = new String(ipPwdA1);
				String ipPwd2 = new String(ipPwdA2);
				String ipHint = h.getText();

				if (ipPwd1.length() == 0) {
					JOptionPane.showMessageDialog(null, "Password Field Can not be Empty.!", "Password Required",
							JOptionPane.ERROR_MESSAGE);
				} else if (ipPwd2.length() == 0) {
					JOptionPane.showMessageDialog(null, "Please confirm your password.!",
							"Password confirmation required.!", JOptionPane.ERROR_MESSAGE);
				} else if (ipPwd1.length() < 8) {
					JOptionPane.showMessageDialog(null, "Password must be atleat 8 characters.!",
							"Password too Short.!", JOptionPane.ERROR_MESSAGE);
				} else if (!(ipPwd1.equals(ipPwd2))) {
					JOptionPane.showMessageDialog(null, "Passwords you have entered Did not Match.!",
							"Passwords DisMatch.!", JOptionPane.ERROR_MESSAGE);
				} else {
					if (ipHint.length() == 0) {
						int resp = JOptionPane.showConfirmDialog(null,
								"Password Hint helps you to remember password," + " incase you forgot your password.\n"
										+ "Are sure to continue without password Hint?",
								"Password Hint is Empty.!", JOptionPane.YES_NO_OPTION);
						if (resp == JOptionPane.YES_OPTION) {
							frame.dispose();
							ld.setVisible(true);
							ret = state1(Path, ipPwd1, ipHint);
							if (openAfter && ret == 1) {
								try {
									File dir = new File(Path);
									File parentDir = new File(dir.getParent());
									File Error_Log = new File(Dir + "\\RuntimeLog.txt");
									ProcessBuilder pb = new ProcessBuilder();
									pb.directory(parentDir);
									pb.redirectErrorStream(true);
									pb.redirectOutput(Redirect.appendTo(Error_Log));
									pb.command("CMD", "/c", "explorer.exe " + Path);
									Process p = pb.start();
									if (p.waitFor() == 0) {
										Error_Log.delete();
									}
								} catch (Exception e) {
									errorHandler(116, e.getMessage(), "Lock(string, boolean)", "Operations",
											"Exception at processs (and\\or Builders)");
									JOptionPane.showMessageDialog(null, "Handled Exception", "Error - 116",
											JOptionPane.ERROR_MESSAGE);
								}
							}
							ld.dispose();
						}
					} else {
						frame.dispose();
						ld.setVisible(true);
						ret = state1(Path, ipPwd1, ipHint);
						if (openAfter && ret == 1) {
							try {
								File dir = new File(Path);
								File parentDir = new File(dir.getParent());
								File Error_Log = new File(Dir + "\\RuntimeLog.txt");
								ProcessBuilder pb = new ProcessBuilder();
								pb.directory(parentDir);
								pb.redirectErrorStream(true);
								pb.redirectOutput(Redirect.appendTo(Error_Log));
								pb.command("CMD", "/c", "explorer.exe " + Path);
								Process p = pb.start();
								if (p.waitFor() == 0) {
									Error_Log.delete();
								}
							} catch (Exception e) {
								errorHandler(116, e.getMessage(), "Lock(string, boolean)", "Operations",
										"Exception at processs (and\\or Builders)");
								JOptionPane.showMessageDialog(null, "Handled Exception", "Error - 116",
										JOptionPane.ERROR_MESSAGE);
							}
						}
						ld.dispose();
					}
				}
			}
		});

		//// Lock Action Listener Ends Here.

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		gbc.gridx = 0;
		gbc.gridy = 0;
		p2.add(label1, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		p2.add(pf1, gbc);

		gbc.gridx = 0;
		gbc.gridy = 0;
		p3.add(label2, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		p3.add(pf2, gbc);

		gbc.gridx = 0;
		gbc.gridy = 0;
		p4.add(label3, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		p4.add(h, gbc);

		gbc.gridx = 0;
		gbc.gridy = 0;
		p5.add(lock, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		p5.add(cancel, gbc);

		GridBagConstraints gb = new GridBagConstraints();
		gb.insets = new Insets(10, 10, 10, 10);

		gb.gridx = 0;
		gb.gridy = 0;
		p1.add(p2, gb);

		gb.gridx = 0;
		gb.gridy = 1;
		p1.add(p3, gb);

		gb.gridx = 0;
		gb.gridy = 2;
		p1.add(p4, gb);

		gb.gridx = 0;
		gb.gridy = 3;
		p1.add(p5, gb);

		frame.add(p1, BorderLayout.CENTER);
		frame.getRootPane().setDefaultButton(lock);
		frame.setVisible(true);
	}

	public void unlock(String Path, boolean openAfter) {
		loadWindow ld = new loadWindow();
		JFrame frame = new JFrame("Turn - off protection");
		frame.setVisible(false);
		frame.setSize(450, 250);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel label1 = new JLabel("Confirm you password : ");
		JPasswordField pf = new JPasswordField(15);
		JButton unlock = new JButton("Turn-Off Protection");
		JButton forgot = new JButton("Forgot Password");
		JButton cancel = new JButton("Cancel");

		JPanel p1 = new JPanel(new GridBagLayout());
		JPanel p2 = new JPanel(new GridBagLayout());
		JPanel p3 = new JPanel(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.insets = new Insets(10, 10, 10, 10);

		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.insets = new Insets(10, 10, 10, 10);

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int resp = JOptionPane.showConfirmDialog(null, "Are You sure to Cancel and Exit?", "Confirm Action.",
						JOptionPane.YES_NO_OPTION);
				if (resp == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		forgot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Your Password Hint is : \n " + getHint(Path), "Password Hint",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		//// unlock ActionListener starts here
		unlock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				char[] ipPwdA = pf.getPassword();
				String inPwd = new String(ipPwdA);
				if (inPwd.length() == 0) {
					JOptionPane.showMessageDialog(null, "PassWord Can not be empty", "Password Required.!",
							JOptionPane.ERROR_MESSAGE);
				} else if (!(inPwd.equals(getPassword(Path)))) {
					JOptionPane.showMessageDialog(null, "Password you have entered is Incorrect", "PassWord Incorrect",
							JOptionPane.ERROR_MESSAGE);
				} else {
					int resp = JOptionPane.showConfirmDialog(null,
							"Are you sure to turn - off Protection to this folder?", "Confirm Action.",
							JOptionPane.YES_NO_OPTION);
					if (resp == JOptionPane.YES_OPTION) {
						frame.dispose();
						ld.setVisible(true);
						ret = state2(Path);
						if (openAfter && ret == 1) {
							try {
								File dir = new File(Path);
								File parentDir = new File(dir.getParent());
								File Error_Log = new File(Dir + "\\RuntimeLog.txt");
								ProcessBuilder pb = new ProcessBuilder();
								pb.directory(parentDir);
								pb.redirectErrorStream(true);
								pb.redirectOutput(Redirect.appendTo(Error_Log));
								pb.command("CMD", "/c", "explorer.exe " + Path);
								Process p = pb.start();
								if (p.waitFor() == 0) {
									Error_Log.delete();
								}
							} catch (Exception e) {
								errorHandler(116, e.getMessage(), "unlock(string, boolean)", "Operations",
										"Exception at processs (and\\or Builders)");
								JOptionPane.showMessageDialog(null, "Handled Exception", "Error - 116",
										JOptionPane.ERROR_MESSAGE);
							}
						}
						ld.dispose();
					}
				}
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		p2.add(label1, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		p2.add(pf, gbc);

		gbc1.gridx = 0;
		gbc1.gridy = 0;
		p3.add(unlock, gbc1);

		gbc1.gridx = 1;
		gbc1.gridy = 0;
		p3.add(forgot, gbc1);

		gbc1.gridx = 2;
		gbc1.gridy = 0;
		p3.add(cancel, gbc1);

		gbc2.gridx = 0;
		gbc2.gridx = 0;
		p1.add(p2, gbc2);

		gbc2.gridx = 0;
		gbc2.gridy = 1;
		p1.add(p3, gbc2);

		frame.add(p1, BorderLayout.CENTER);
		frame.getRootPane().setDefaultButton(unlock);
		frame.setVisible(true);
	}

	public void secure(String Path, boolean openAfter) {
		loadWindow ld = new loadWindow();
		ld.setVisible(true);
		if (openAfter && ret == 1) {
			try {
				File dir = new File(Path);
				File parentDir = new File(dir.getParent());
				File Error_Log = new File(Dir + "\\RuntimeLog.txt");
				ProcessBuilder pb = new ProcessBuilder();
				pb.directory(parentDir);
				pb.redirectErrorStream(true);
				pb.redirectOutput(Redirect.appendTo(Error_Log));
				pb.command("CMD", "/c", "explorer.exe " + Path);
				Process p = pb.start();
				if (p.waitFor() == 0) {
					Error_Log.delete();
				}
			} catch (Exception e) {
				errorHandler(116, e.getMessage(), "secure(string, boolean)", "Operations",
						"Exception at processs (and\\or Builders)");
				JOptionPane.showMessageDialog(null, "Handled Exception", "Error - 116", JOptionPane.ERROR_MESSAGE);
			}
		}
		ret = state3(Path);
		ld.dispose();
	}

	public void unsecure(String Path, boolean openAfter) {
		loadWindow ld = new loadWindow();
		JFrame frame = new JFrame("Un-Lock Folder");
		frame.setVisible(false);
		frame.setSize(450, 250);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel label1 = new JLabel("Confirm you password : ");
		JPasswordField pf = new JPasswordField(15);
		JButton unlock = new JButton("Un-Lock Folder");
		JButton forgot = new JButton("Forgot Password");
		JButton cancel = new JButton("Cancel");

		JPanel p1 = new JPanel(new GridBagLayout());
		JPanel p2 = new JPanel(new GridBagLayout());
		JPanel p3 = new JPanel(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.insets = new Insets(10, 10, 10, 10);

		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.insets = new Insets(10, 10, 10, 10);

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int resp = JOptionPane.showConfirmDialog(null, "Are You sure to Cancel and Exit?", "Confirm Action.",
						JOptionPane.YES_NO_OPTION);
				if (resp == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		forgot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Your Password Hint is : \n " + getHint(Path), "Password Hint",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		//// unlock ActionListener starts here
		unlock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				char[] ipPwdA = pf.getPassword();
				String inPwd = new String(ipPwdA);
				if (inPwd.length() == 0) {
					JOptionPane.showMessageDialog(null, "PassWord Can not be empty", "Password Required.!",
							JOptionPane.ERROR_MESSAGE);
				} else if (!(inPwd.equals(getPassword(Path)))) {
					JOptionPane.showMessageDialog(null, "Password you have entered is Incorrect", "PassWord Incorrect",
							JOptionPane.ERROR_MESSAGE);
				} else {
					frame.dispose();
					ld.setVisible(true);
					ret = state4(Path);
					if (openAfter && ret == 1) {
						try {
							File dir = new File(Path);
							File parentDir = new File(dir.getParent());
							File Error_Log = new File(Dir + "\\RuntimeLog.txt");
							ProcessBuilder pb = new ProcessBuilder();
							pb.directory(parentDir);
							pb.redirectErrorStream(true);
							pb.redirectOutput(Redirect.appendTo(Error_Log));
							pb.command("CMD", "/c", "explorer.exe " + Path);
							Process p = pb.start();
							if (p.waitFor() == 0) {
								Error_Log.delete();
							}
						} catch (Exception e) {
							errorHandler(116, e.getMessage(), "unsecure(string, boolean)", "Operations",
									"Exception at processs (and\\or Builders)");
							JOptionPane.showMessageDialog(null, "Handled Exception", "Error - 116",
									JOptionPane.ERROR_MESSAGE);
						}
					}
					ld.dispose();
				}
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		p2.add(label1, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		p2.add(pf, gbc);

		gbc1.gridx = 0;
		gbc1.gridy = 0;
		p3.add(unlock, gbc1);

		gbc1.gridx = 1;
		gbc1.gridy = 0;
		p3.add(forgot, gbc1);

		gbc1.gridx = 2;
		gbc1.gridy = 0;
		p3.add(cancel, gbc1);

		gbc2.gridx = 0;
		gbc2.gridx = 0;
		p1.add(p2, gbc2);

		gbc2.gridx = 0;
		gbc2.gridy = 1;
		p1.add(p3, gbc2);

		frame.add(p1, BorderLayout.CENTER);
		frame.getRootPane().setDefaultButton(unlock);
		frame.setVisible(true);
	}

	public void moveAll(String src, String Dest) {

		String Default = "Control.Panel{3214-5674-9870-1132}";
		File source = new File(src);
		if (source.isDirectory()) {
			File[] list = source.listFiles();
			for (int i = 0; i < list.length; i++) {
				if (!(list[i].getName().equals(Default) || list[i].getName().equals("Helper"))) {
					File dest = new File(Dest + "\\" + list[i].getName());
					list[i].renameTo(dest);
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Handled Error", "Error - 115", JOptionPane.ERROR_MESSAGE);
			errorHandler(115, source.getPath() + " is not a Directory", "moveAll(String, String)", "Operations",
					"Wrong arguement is passed to moveAll.! Unlikely to Occur.!");
		}
	}

	public void serial(String Path, String Pass, String Hint) {
		try {
			File dat = new File(Bin + encode(Path) + ".dat");
			Folder p = new Folder(Pass, Hint);
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dat));
			oos.writeObject(p);
			oos.close();
		} catch (IOException e) {
			errorHandler(103, e.getMessage(), "Operations", "serial(String, String, String)",
					"Exception in Serialazation");
			JOptionPane.showMessageDialog(null, "Handled Exception.! ", "Error - 103", JOptionPane.ERROR_MESSAGE);
		}
	}

	public String encode(String Path) {
		Path = Path.replace(":", "%SB01%");
		Path = Path.replace("\\", "%SB02%");
		return Path;
	}

	public String decode(String Path) {
		Path = Path.replace("%SB01%", ":");
		Path = Path.replace("%SB02%", "\\");
		return Path;
	}

	public void errorHandler(int ErrorCode, String Msg, String Method, String clas, String customMessage) {
		try {
			Date date = new Date();
			File errorlog = new File("C:\\ProgramData\\FolderLockBasic\\Error Logs.txt");
			PrintWriter pw = new PrintWriter(new FileOutputStream(errorlog, true));
			pw.println("");
			pw.println("");
			pw.println(
					"--------------------------- BEGIN OF ERROR DESCRIPTION ----------------------------------------");
			pw.println(date);
			pw.println("    Error Code  :  " + ErrorCode);
			pw.println("     Error Msg  :  " + Msg);
			pw.println("        Class   :  " + clas);
			pw.println("        Method  :  " + Method);
			pw.println("Custom Message  :  " + customMessage);
			pw.println(
					"--------------------------- END OF ERROR DESCRIPTION ------------------------------------------");
			pw.println("");
			pw.println("");
			pw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "UnHandled Exception, Please Contact Software Support.!", "Error 104",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public Folder deserial(String Path) {
		Folder p = null;
		try {
			File dat = new File(Bin + encode(Path) + ".dat");
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dat));
			p = (Folder) ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			errorHandler(104, e.getMessage(), "deserial(int, String, String, String, String)", "Operations",
					"Exception in Deserial");
			JOptionPane.showMessageDialog(null, "Handles Exception", "Error - 105", JOptionPane.ERROR_MESSAGE);
		}

		return p;
	}

	public String getHint(String Path) {
		Folder P = deserial(Path);
		return (P.Hint);
	}

	public String getPassword(String Path) {
		Folder P = deserial(Path);
		return (P.Passowrd);
	}

	public int state1(String Path, String Password, String Hint) {
		int ret = 0;
		try {
			File default_dir = new File(Path + "\\" + Default);
			File Helper_dir = new File(Path + "\\Helper");
			default_dir.mkdir();
			Helper_dir.mkdir();
			String Ver = getVersion();
			File OnLock_reg = new File(Path + "\\Helper\\OnLock.reg");
			File OnUnsecure_reg = new File(Path + "\\Helper\\OnUnsecure.reg");
			File OnDelete_reg = new File(Path + "\\Helper\\OnDelete.reg");
			serial(Path, Password, Hint);
			PrintWriter pw1 = new PrintWriter(OnLock_reg, "UTF-8");
			pw1.println("Windows Registry Editor Version 5.00");
			pw1.println("");
			pw1.println("[-HKEY_CLASSES_ROOT\\Folder\\shell\\Lock" + Ver + "]");
			pw1.println("");
			pw1.println("[-HKEY_CLASSES_ROOT\\Folder\\shell\\Delete" + Ver + "]");
			pw1.println("");
			pw1.println("[HKEY_CLASSES_ROOT\\Folder\\shell\\Unlock" + Ver + "]");
			pw1.println("\"AppliesTo\"=\"System.ItemPathDisplay:\\\"" + Path.replace("\\", "\\\\") + "\\\"\"");
			pw1.println("@=\"Unlock This Folder\"");
			pw1.println("");
			pw1.println("[HKEY_CLASSES_ROOT\\Folder\\shell\\Unlock" + Ver + "\\command]");
			pw1.println(
					"@=\"\\\"C:\\\\ProgramData\\\\FolderLockBasic\\\\Folder Lock Basic.exe\\\" \\\"unsecure\\\" \\\"%1\\\"\"");
			pw1.close();

			PrintWriter pw2 = new PrintWriter(OnDelete_reg, "UTF-8");
			pw2.println("Windows Registry Editor Version 5.00");
			pw2.println("");
			pw2.println("[-HKEY_CLASSES_ROOT\\Folder\\shell\\Lock" + Ver + "]");
			pw2.println("");
			pw2.println("[-HKEY_CLASSES_ROOT\\Folder\\shell\\Delete" + Ver + "]");
			pw2.println("");
			pw2.println("[-HKEY_CLASSES_ROOT\\Folder\\shell\\Unlock" + Ver + "]");
			pw2.close();

			PrintWriter pw3 = new PrintWriter(OnUnsecure_reg, "UTF-8");
			pw3.println("Windows Registry Editor Version 5.00");
			pw3.println("");
			pw3.println("[-HKEY_CLASSES_ROOT\\Folder\\shell\\Unlock" + Ver + "]");
			pw3.println("");
			pw3.println("[HKEY_CLASSES_ROOT\\Folder\\shell\\Lock" + Ver + "]");
			pw3.println("\"AppliesTo\"=\"System.ItemPathDisplay:\\\"" + Path.replace("\\", "\\\\") + "\\\"\"");
			pw3.println("@=\"Lock This Folder\"");
			pw3.println("");
			pw3.println("[HKEY_CLASSES_ROOT\\Folder\\shell\\Lock" + Ver + "\\command]");
			pw3.println(
					"@=\"\\\"C:\\\\ProgramData\\\\FolderLockBasic\\\\Folder Lock Basic.exe\\\" \\\"secure\\\" \\\"%1\\\"\"");
			pw3.println("");
			pw3.println("[HKEY_CLASSES_ROOT\\Folder\\shell\\Delete" + Ver + "]");
			pw3.println("\"AppliesTo\"=\"System.ItemPathDisplay:\\\"" + Path.replace("\\", "\\\\") + "\\\"\"");
			pw3.println("@=\"Turn off Protection to This Folder\"");
			pw3.println("");
			pw3.println("[HKEY_CLASSES_ROOT\\Folder\\shell\\Delete" + Ver + "\\command]");
			pw3.println(
					"@=\"\\\"C:\\\\ProgramData\\\\FolderLockBasic\\\\Folder Lock Basic.exe\\\" \\\"delete\\\" \\\"%1\\\"\"");
			pw3.close();

			moveAll(Path, Path + "\\" + Default);

			File Error_Log = new File(Dir + "\\RuntimeLog.txt");
			File WorkingDir = new File(Path);
			ProcessBuilder pb = new ProcessBuilder();
			pb.directory(WorkingDir);
			pb.redirectErrorStream(true);
			pb.redirectOutput(Redirect.appendTo(Error_Log));
			boolean Success = true;

			pb.command("CMD", "/c", "attrib +s +h " + Default);
			Process p = pb.start();
			if (!(p.waitFor() == 0)) {
				Success = false;
			}
			pb.command("CMD", "/c", "attrib +s +h Helper");
			p = pb.start();
			if (!(p.waitFor() == 0)) {
				Success = false;
			}
			pb.command("CMD", "/c", "ECHO Y|cacls " + Default + " /p everyone:n");
			p = pb.start();
			if (!(p.waitFor() == 0)) {
				Success = false;
			}
			String cmd = "regedit /s \"" + Path + "\\Helper\\OnLock.reg\"";
			pb.command("CMD", "/c", cmd);
			p = pb.start();
			if (!(p.waitFor() == 0)) {
				Success = false;
			}
			if (Success == true) {
				ret = 1;
				Error_Log.delete();
			} else {
				ret = -1;
				JOptionPane.showMessageDialog(null, "Handled Error", "Error - 112", JOptionPane.ERROR_MESSAGE);
				errorHandler(112, "Processing Error", "state1(String, String, String)", "Operations",
						"Check RuntimeLog.txt for More Information");
			}
		} catch (IOException | InterruptedException e) {
			ret = -1;
			JOptionPane.showMessageDialog(null, "Hendled Exception", "Error - 107", JOptionPane.ERROR_MESSAGE);
			errorHandler(107, e.getMessage(), "State1(String, String, String)", "Operations",
					"Many Possibilites, One of the Complex Methods");
		}
		return ret;
	}

	public String getVersion() {
		File file = new File(Dir + "\\Versions.txt");
		String Val = null;
		try {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(file);
			if (sc.hasNextInt()) {
				int ver = sc.nextInt();
				file.delete();
				Val = "FLB" + String.valueOf(ver);
				ver++;
				PrintWriter pw1 = new PrintWriter(file, "UTF-8");
				pw1.println(ver);
				pw1.println("");
				pw1.close();
				return Val;
			} else {
				System.out.println("Input MisMatch");
				System.exit(0);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Handeled Exception", "Error - 106", JOptionPane.ERROR_MESSAGE);
			errorHandler(106, e.getMessage(), "getVersion(null)", "Operations",
					"Exception may be with converting int to string or something like that.!");
		}
		return null;
	}

	public int state2(String Path) {
		int ret = 0;
		try {
			File default_dir = new File(Path + "\\" + Default);
			File Helper_dir = new File(Path + "\\Helper");
			File OnLock_reg = new File(Path + "\\Helper\\OnLock.reg");
			File OnDelete_reg = new File(Path + "\\Helper\\OnDelete.reg");
			File OnUnsecure_reg = new File(Path + "\\Helper\\OnUnsecure.reg");
			File Error_Log = new File(Dir + "\\RuntimeLog.txt");
			File WorkingDir = new File(Path);
			ProcessBuilder pb = new ProcessBuilder();
			pb.directory(WorkingDir);
			pb.redirectErrorStream(true);
			pb.redirectOutput(Redirect.appendTo(Error_Log));
			Process p;
			boolean Success = true;

			pb.command("CMD", "/c", "ECHO Y|cacls " + Default + " /p everyone:f");
			p = pb.start();
			if (!(p.waitFor() == 0)) {
				Success = false;
			}

			moveAll(Path + "\\" + Default, Path);

			String cmd = "regedit /s \"" + Path + "\\Helper\\OnDelete.reg\"";
			pb.command("CMD", "/c", cmd);
			p = pb.start();
			if (!(p.waitFor() == 0)) {
				Success = false;
			}
			default_dir.delete();
			OnLock_reg.delete();
			OnDelete_reg.delete();
			OnUnsecure_reg.delete();
			Helper_dir.delete();

			File dat = new File(Bin + encode(Path) + ".dat");
			dat.delete();

			if (Success == true) {
				ret = 1;
				Error_Log.delete();
			} else {
				ret = -1;
				JOptionPane.showMessageDialog(null, "Handled Error", "Error - 111", JOptionPane.ERROR_MESSAGE);
				errorHandler(111, "Processing Error", "state2(String)", "Operations",
						"Check RuntimeLog.txt for More Information");
			}
		} catch (Exception e) {
			ret = -1;
			JOptionPane.showMessageDialog(null, "Handled Error", "Error - 108", JOptionPane.ERROR_MESSAGE);
			errorHandler(108, e.getMessage(), "state2(String)", "Operations",
					"Expected to be at Process and/or ProcessBuilder");
		}
		return ret;
	}

	public int state3(String Path) {
		int ret = 0;
		try {
			File Error_Log = new File(Dir + "\\RuntimeLog.txt");
			File WorkingDir = new File(Path);
			ProcessBuilder pb = new ProcessBuilder();
			pb.directory(WorkingDir);
			pb.redirectErrorStream(true);
			pb.redirectOutput(Redirect.appendTo(Error_Log));
			Process p;
			boolean Success = true;

			pb.command("CMD", "/c", "ECHO Y|cacls " + Default + " /p everyone:f");
			p = pb.start();
			if (!(p.waitFor() == 0)) {
				Success = false;
			}

			moveAll(Path, Path + "//" + Default);

			String cmd = "regedit /s \"" + Path + "\\Helper\\OnLock.reg\"";
			pb.command("CMD", "/c", cmd);
			p = pb.start();
			if (!(p.waitFor() == 0)) {
				Success = false;
			}

			pb.command("CMD", "/c", "ECHO Y|cacls " + Default + " /p everyone:n");
			p = pb.start();
			if (!(p.waitFor() == 0)) {
				Success = false;
			}

			if (Success == true) {
				Error_Log.delete();
				ret = 1;
			} else {
				ret = -1;
				JOptionPane.showMessageDialog(null, "Handled Error", "Error - 110", JOptionPane.ERROR_MESSAGE);
				errorHandler(110, "Processing Error", "state3(String)", "Operations",
						"Check RuntimeLog.txt for More Information");
			}

		} catch (Exception e) {
			ret = -1;
			JOptionPane.showMessageDialog(null, "Handled Error", "Error - 109", JOptionPane.ERROR_MESSAGE);
			errorHandler(109, e.getMessage(), "state3(String)", "Operations",
					"Expected to be at Process and/or ProcessBuilder");
		}
		return ret;
	}

	public int state4(String Path) {
		int ret = 0;
		try {
			File Error_Log = new File(Dir + "\\RuntimeLog.txt");
			File WorkingDir = new File(Path);
			ProcessBuilder pb = new ProcessBuilder();
			pb.directory(WorkingDir);
			pb.redirectErrorStream(true);
			pb.redirectOutput(Redirect.appendTo(Error_Log));
			Process p;
			boolean Success = true;

			pb.command("CMD", "/c", "ECHO Y|cacls " + Default + " /p everyone:f");
			p = pb.start();
			if (!(p.waitFor() == 0)) {
				Success = false;
			}

			moveAll(Path + "\\" + Default, Path);

			String cmd = "regedit /s \"" + Path + "\\Helper\\OnUnsecure.reg\"";
			pb.command("CMD", "/c", cmd);
			p = pb.start();
			if (!(p.waitFor() == 0)) {
				Success = false;
			}

			pb.command("CMD", "/c", "ECHO Y|cacls " + Default + " /p everyone:n");
			p = pb.start();
			if (!(p.waitFor() == 0)) {
				Success = false;
			}

			if (Success == true) {
				ret = 1;
				Error_Log.delete();
			} else {
				ret = -1;
				JOptionPane.showMessageDialog(null, "Handled Error", "Error - 114", JOptionPane.ERROR_MESSAGE);
				errorHandler(114, "Processing Error", "state3(String)", "Operations",
						"Expected to be at Process and/or ProcessBuilder");
			}
		} catch (Exception e) {
			ret = -1;
			JOptionPane.showMessageDialog(null, "Handled Error", "Error - 113", JOptionPane.ERROR_MESSAGE);
			errorHandler(113, e.getMessage(), "state3(String)", "Operations",
					"Expected to be at Process and/or ProcessBuilder");
		}
		return ret;
	}

	public void window1() {
		JFrame frame = new JFrame("Folder Lock Basic");
		frame.setVisible(false);
		frame.setResizable(false);
		frame.setSize(250, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		JLabel label = new JLabel("Select your Action");
		JButton lock = new JButton("Lock a Folder");
		JButton unlock = new JButton("Unlock a Folder");

		lock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser choose = new JFileChooser();
				choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				choose.setMultiSelectionEnabled(false);
				choose.setAcceptAllFileFilterUsed(false);
				choose.setCurrentDirectory(choose.getFileSystemView().getParentDirectory(new File("C:\\")));
				File folder;
				if (choose.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					folder = choose.getSelectedFile();
					File dat = new File(encode(folder.getPath()));
					if (dat.exists()) {
						JOptionPane.showMessageDialog(null, "Folder you are trying to lock is already Locked",
								"Alert - 201 Folder Already Locked", JOptionPane.WARNING_MESSAGE);
					} else {
						lock(folder.getPath(), true);
					}
				}
			}
		});

		unlock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				window2();
			}
		});

		JPanel p1 = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		gbc.gridx = 0;
		gbc.gridy = 0;
		p1.add(label, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		p1.add(lock, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		p1.add(unlock, gbc);

		frame.add(p1, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	public void window2() {
		JFrame frame = new JFrame("Folder Lock Basic | Unlock Folder");
		frame.setVisible(false);
		frame.setResizable(false);
		frame.setSize(400, 250);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String array[] = getLockedFoldrs();
		if (array.length > 0) {
			JButton unlock = new JButton("Unlock");
			unlock.setEnabled(false);
			JButton cancel = new JButton("Cancel");
			JButton back = new JButton("Back");
			@SuppressWarnings({ "rawtypes", "unchecked" })
			JList jlist = new JList(array);
			jlist.requestFocusInWindow();
			jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jlist.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent arg0) {
					unlock.setEnabled(true);
				}
			});
			cancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int resp = JOptionPane.showConfirmDialog(null, "Are you sure to Exit the Program?", "Confiem Exit.",
							JOptionPane.YES_NO_OPTION);
					if (resp == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				}
			});

			unlock.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					int index = jlist.getSelectedIndex();
					frame.dispose();
					unsecure(array[index], true);
				}
			});

			back.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					frame.dispose();
					window1();
				}
			});
			JScrollPane scroll = new JScrollPane(jlist);
			JPanel p3 = new JPanel(new GridBagLayout());

			GridBagConstraints gbc1 = new GridBagConstraints();
			gbc1.insets = new Insets(10, 10, 10, 10);
			
			gbc1.gridx = 0;
			gbc1.gridy = 0;
			p3.add(unlock, gbc1);

			gbc1.gridx = 1;
			gbc1.gridy = 0;
			p3.add(back, gbc1);

			gbc1.gridx = 3;
			gbc1.gridy = 0;
			p3.add(cancel, gbc1);
			frame.add(scroll, BorderLayout.NORTH);
			frame.add(p3, BorderLayout.AFTER_LAST_LINE);
			frame.getRootPane().setDefaultButton(unlock);
			frame.setVisible(true);
		}else{
			JOptionPane.showMessageDialog(null, "No Locked Folder Found.!", "List Empty", JOptionPane.ERROR_MESSAGE);
			frame.dispose();
			window1();
		}
	}

	private String[] getLockedFoldrs() {
		File dir = new File(Dir +"\\Bin");
		Names = dir.list();
		if(Names.length == 0){
			return null;
		}else{
			for(int i = 0; i < Names.length; i++){
				Names[i] = Names[i].replaceAll(".dat", "");
				Names[i] = decode(Names[i]);
			}
			return Names;
		}	
	}
}
