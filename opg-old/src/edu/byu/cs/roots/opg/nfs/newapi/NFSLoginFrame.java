package edu.byu.cs.roots.opg.nfs.newapi;

import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NFSLoginFrame extends JFrame{

	private String username = "";
	private String password = "";
	
	private JTextField uField;
	private JPasswordField pField;
	
	private boolean cancelled = false;
	private Object keyLock;
	
	public NFSLoginFrame(Object lock){
		super("Login to new FamilySearch");
		
		keyLock = lock;
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		setLayout(gridbag);
		setLocationRelativeTo(null);

		uField = new JTextField(20);
		uField.setText("api-user-1254");
		JLabel uLabel = new JLabel("Username: ");
		uLabel.setLabelFor(uField);
		JPanel uPanel = new JPanel();
		uPanel.add(uLabel);
		uPanel.add(uField);
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(uPanel, c);
		add(uPanel);

		pField = new JPasswordField(20);
		pField.setText("18f7");
		JLabel pLabel = new JLabel("Password: ");
		pLabel.setLabelFor(pField);
		JPanel pPanel = new JPanel();
		pPanel.add(pLabel);
		pPanel.add(pField);
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(pPanel, c);
		add(pPanel);

		JPanel bPanel = new JPanel();
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		okButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				acceptClicked();
			}
		});
		
		KeyListener keyListener = new KeyListener(){
			private int pressedCode;
			@Override
			public void keyPressed(KeyEvent e) {pressedCode = e.getKeyCode();}
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER && e.getKeyCode() == pressedCode)	{
				acceptClicked();
			}}
			@Override
			public void keyTyped(KeyEvent e) {}
			
		};
		pField.addKeyListener(keyListener);
		uField.addKeyListener(keyListener);
		okButton.addKeyListener(keyListener);
		cancelButton.addKeyListener(keyListener);
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				cancelClicked();
			}
		});
		bPanel.add(okButton);
		bPanel.add(cancelButton);
		c.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(bPanel, c);
		add(bPanel);
		
	}
	
	public void acceptClicked(){
		username = uField.getText();
		password = new String(pField.getPassword());
		dispose();
		releaseLock();
	}
	
	public void cancelClicked(){
		dispose();
		cancelled = true;
		releaseLock();
	}
	
	public void releaseLock(){
		synchronized(keyLock){
			keyLock.notify();
		}
	}
	
	public String getUsername(){return username;}
	public String getPassword(){return password;}
	public boolean getCancelled(){return cancelled;}
}
