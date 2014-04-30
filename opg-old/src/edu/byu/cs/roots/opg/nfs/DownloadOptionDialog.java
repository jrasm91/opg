package edu.byu.cs.roots.opg.nfs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class DownloadOptionDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	private JLabel genLabel;
	private JTextField genText, idText, warningLabel;
	private JRadioButton descendantSelect, rootDescendantSelect, noDescendantSelect;
	private JCheckBox downloadAllAncestors;
	private JPanel panel1, panel2;
	private JTabbedPane tabs;
	private JComboBox root;
	private JTextArea choiceLabel;
	private JButton okButton;
	private boolean downloadAccepted;
	private KeyListener listener;
	DownloadOptionDialog parent;
	String[] possibleValues = { "Self", "Enter Person Identifier" };
	
	public DownloadOptionDialog(){
		parent = this;
		downloadAccepted = false;
		initializeGui();
		
	}
	
	
	
	
	public int getChoiceIndex(){
		return root.getSelectedIndex();
	}
	
	public String getChoiceID(){
		if (root.getSelectedIndex() == 0 || 
				(root.getSelectedIndex() == 1 && idText.getText().contentEquals("Person Identifier")))
			return "";
		else
			return idText.getText();
	}
	
	public int getChoiceGens(){
		int retVal; 
		try {
			retVal = Integer.parseInt(genText.getText());
			if (retVal < 0)
				retVal = 9;
		}
		catch (NumberFormatException e){
			retVal = 9;
		}
		return retVal;
	}
	
	public boolean getChoiceRootDescendants(){
		return rootDescendantSelect.isSelected();
	}
	
	public boolean getChoiceAllDescendants(){
		return descendantSelect.isSelected();
	}
	
	public boolean getAllAncestors(){
		return downloadAllAncestors.isSelected();
	}
	
	public boolean getDownload(){
		return downloadAccepted;
	}
	
	public void updateWarningLabel(){
		if ((downloadAllAncestors.isSelected() || getChoiceGens() > 18) && descendantSelect.isSelected())
			warningLabel.setText("Warning, your current settings could cause the download to take an extremely long time!");
		else if (downloadAllAncestors.isSelected() || getChoiceGens() > 18)
			warningLabel.setText("Warning, your current settings could cause the download to take a very long time.");
		else if (getChoiceGens() > 9 && descendantSelect.isSelected())
			warningLabel.setText("Warning, your current settings could cause the download to take a very long time.");
		else if (descendantSelect.isSelected())
			warningLabel.setText("Warning, your current settings could cause the download to take longer.");
		else if (rootDescendantSelect.isSelected())
			warningLabel.setText("Warning, your current settings could cause the download to take slightly longer");
		else
			warningLabel.setText("");
		
		
	}
	
	
	
	
	public void initializeGui(){			
		setPreferredSize(new Dimension(450, 200));
		setModalityType(ModalityType.APPLICATION_MODAL);
		listener = new KeyListener(){
			private int down=0;
			@Override
			public void keyPressed(KeyEvent e) {down=e.getKeyCode();}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER && e.getKeyCode() == down){
					downloadAccepted = true;
					parent.dispose();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {}
		};
		add(getTabPane());
		pack();	
	}
	
	public JTabbedPane getTabPane(){
		
		
		tabs = new JTabbedPane();
		tabs.addKeyListener(listener);
		tabs.addTab("Information", getIdFrame());
		tabs.add("Options", getOptionsFrame());
		
		return tabs;
	}
	
	public JPanel getIdFrame(){
		//First Tab
		panel1 = new JPanel();
		choiceLabel = new JTextArea("Do you want to download a personal pedigree\n" +
				"with your information or enter a Person Identifier\n" +
				"of another individual on new FamilySearch?");
		choiceLabel.setEditable(false);
		choiceLabel.setOpaque(false);
		
		root = new JComboBox();
		root.addItem(possibleValues[0]);
		root.addItem(possibleValues[1]);
		
		root.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (root.getSelectedIndex() == 0)
					idText.setEnabled(false);
				else
					idText.setEnabled(true);
				
			}
		});
		
		idText = new JTextField("Person Identifier");
		idText.addKeyListener(listener);
		idText.setEnabled(false);
		okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				downloadAccepted = true;
				parent.dispose();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(okButton);
		
		
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel1.add(choiceLabel);
		panel1.add(root);
		panel1.add(idText);
		panel1.add(buttonPanel);
		
		return panel1;
	}
	
	public JPanel getOptionsFrame(){
		ActionListener warningListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				updateWarningLabel();
			}
		};
		
		DocumentListener docWarningListener = new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				updateWarningLabel();
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				updateWarningLabel();
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				updateWarningLabel();
				
			}

			
		};
		
		//Second Panel
		panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		JPanel genPanel = new JPanel();
		genPanel.setLayout(new BoxLayout(genPanel, BoxLayout.X_AXIS));
		genLabel = new JLabel("Enter desired amount of generations: ");
		genText = new JTextField();
		genText.setText("9");
		genText.setPreferredSize(new Dimension(50,50));
		genText.getDocument().addDocumentListener(docWarningListener);
		genText.addKeyListener(listener);
		downloadAllAncestors = new JCheckBox();
		downloadAllAncestors.setText("Download All Ancestors");
		downloadAllAncestors.addActionListener(warningListener);
		downloadAllAncestors.setVisible(false);
		downloadAllAncestors.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (downloadAllAncestors.isSelected())
					genText.setEnabled(false);
				else
					genText.setEnabled(true);
			}
			
		});
		downloadAllAncestors.addKeyListener(listener);
		
		JPanel checkPanel = new JPanel();
		checkPanel.setLayout(new BoxLayout(checkPanel, BoxLayout.X_AXIS));
		descendantSelect = new JRadioButton();
		descendantSelect.setText("All");
		descendantSelect.addActionListener(warningListener);
		descendantSelect.addKeyListener(listener);
		rootDescendantSelect = new JRadioButton();
		rootDescendantSelect.setText("Root Only");
		rootDescendantSelect.addActionListener(warningListener);
		rootDescendantSelect.addKeyListener(listener);
		noDescendantSelect = new JRadioButton();
		noDescendantSelect.setText("None");
		noDescendantSelect.addActionListener(warningListener);
		noDescendantSelect.addKeyListener(listener);
		ButtonGroup group = new ButtonGroup();
		group.add(descendantSelect);
		group.add(rootDescendantSelect);
		group.add(noDescendantSelect);
		noDescendantSelect.setSelected(true);
		TitledBorder title = BorderFactory.createTitledBorder("Descendant Options");
		title.setBorder(BorderFactory.createEtchedBorder());
		title.setTitleJustification(TitledBorder.LEFT);
		title.setTitlePosition(TitledBorder.ABOVE_TOP);
		checkPanel.setBorder(title);
		
		genPanel.add(genLabel);
		genPanel.add(genText);
		genPanel.add(downloadAllAncestors);
		panel2.add(genPanel);
		checkPanel.add(noDescendantSelect);
		checkPanel.add(rootDescendantSelect);
		checkPanel.add(descendantSelect);
		
		warningLabel = new JTextField();
		warningLabel.setForeground(Color.red);
		warningLabel.setOpaque(false);
		warningLabel.setEditable(false);
		warningLabel.setBorder(null);
		
		panel2.add(checkPanel);
		panel2.add(Box.createVerticalStrut(25));
		panel2.add(warningLabel);
		panel2.add(Box.createVerticalStrut(25));
		
		return panel2;
	}
	
	
}
