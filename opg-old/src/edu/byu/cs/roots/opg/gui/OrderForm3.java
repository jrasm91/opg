package edu.byu.cs.roots.opg.gui;

import java.awt.AWTError;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import edu.byu.cs.roots.opg.io.HTMLPostCreator;
import edu.byu.cs.roots.opg.model.OpgSession;
import edu.byu.cs.roots.opg.util.BrowserLauncher;

public class OrderForm3 {
	private JDialog frmOrder;
	
	// Panels for the components
	private JPanel pnlForm;  //  @jve:decl-index=0:visual-constraint="10,10"
	private JPanel pnlButtons;
	private JPanel pnlOrder;
	// Buttons
	private JButton btnOrder;
	private JButton btnCancel;
	private JList orderList;
	private DefaultListModel chartlist = new DefaultListModel();
	
	public static String RETREVAL_SITE ="https://roots.cs.byu.edu/pedigree/opgretreive.php?SessionID=";

	private JPanel pnlPreview = null;

	private JLabel lblPreview = null;

	private JPanel pnlChart = null;

	private JPanel pnlPrevButtons = null;

	private JButton btnAddChart = null;

	private JButton btnRemoveChart = null;

	public OrderForm3(OpgSession sessionInfo, Dialog parent)
	{
		frmOrder = new JDialog(parent, "Order Charts", true);
		initialize();
	}
	public OrderForm3(OpgSession sessionInfo, Frame parent)
	{
		frmOrder = new JDialog(parent, "Order Charts", true);
		initialize();
	}
	
	private void initialize()
	{
		Dimension screenSize;
		int frameWidth = 600;
		int frameHeight = 480;
		try {
			lblPreview = new JLabel();
			lblPreview.setText("Preview of Chart");
			Toolkit tk = Toolkit.getDefaultToolkit();
			screenSize = tk.getScreenSize();
		} catch (AWTError awe) {
			screenSize = new Dimension(640, 480);
		}
		
		pnlOrder = new JPanel();
		pnlForm = new JPanel();
		pnlButtons = new JPanel();
		pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.LINE_AXIS));
		frmOrder.add(pnlForm);
		pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.PAGE_AXIS));
		pnlForm.setSize(new Dimension(549, 471));
		pnlForm.add(pnlOrder);
		pnlForm.add(pnlButtons);
		
		// Buttons
		btnOrder = new JButton("Proceed to Checkout");
		btnCancel = new JButton("Cancel");
		
		// List
		JLabel lblOrder = new JLabel("Charts to be ordered.");
		orderList = new JList(chartlist);
		//chartlist.addElement("Hello");
		JPanel pnlList = new JPanel(new BorderLayout());
		pnlList.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		pnlList.setMinimumSize(new Dimension(300,400));
		orderList.setMinimumSize(new Dimension(300,400));
		JScrollPane orderListScroll = new JScrollPane(orderList);
		pnlList.add(lblOrder, BorderLayout.NORTH);
		pnlList.add(orderListScroll, java.awt.BorderLayout.CENTER);
		pnlOrder.setLayout(new BorderLayout());
		pnlOrder.add(getPnlPreview(), BorderLayout.CENTER);
		pnlOrder.add(pnlList, BorderLayout.EAST);
		
		// Window control buttons
		pnlButtons.add(btnOrder);
		pnlButtons.add(btnCancel);
		
		btnOrder.addMouseListener(new OrderChartListener());
		btnCancel.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent arg0) {
				frmOrder.dispose();
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}});
		
		int frameX = screenSize.width / 2 - frameWidth / 2;
		int frameY = screenSize.height / 2 - frameHeight / 2;
		frmOrder.setBounds(frameX, frameY, frameWidth, frameHeight);
		frmOrder.setVisible(true);
	}
	
	private class OrderChartListener implements MouseListener
	{
		public void mouseClicked(MouseEvent e) {
			HTMLPostCreator toSend = new HTMLPostCreator();
			for(int index = 0; index < chartlist.getSize(); index++)
				toSend.AddFile((String)chartlist.get(index));
			String SessionID = toSend.Send();
			BrowserLauncher.openURL(RETREVAL_SITE + SessionID);
			frmOrder.dispose();
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}

	/**
	 * This method initializes pnlPreview	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlPreview() {
		if (pnlPreview == null) {
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = 0;
			gridBagConstraints31.fill = GridBagConstraints.BOTH;
			gridBagConstraints31.gridwidth = 200;
			gridBagConstraints31.gridheight = 200;
			gridBagConstraints31.ipadx = 200;
			gridBagConstraints31.ipady = 200;
			gridBagConstraints31.weightx = 1.0;
			gridBagConstraints31.weighty = 1.0;
			gridBagConstraints31.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints31.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints31.gridy = 1;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 3;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridwidth = 100;
			gridBagConstraints.gridheight = 200;
			pnlPreview = new JPanel();
			pnlPreview.setLayout(new GridBagLayout());
			pnlPreview.add(lblPreview, new GridBagConstraints());
			pnlPreview.add(getPnlPrevButtons(), gridBagConstraints11);
			pnlPreview.add(getPnlChart(), gridBagConstraints31);
		}
		return pnlPreview;
	}
	/**
	 * This method initializes pnlChart	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlChart() {
		if (pnlChart == null) {
			pnlChart = new JPanel();
			pnlChart.setLayout(new GridBagLayout());
		}
		return pnlChart;
	}
	/**
	 * This method initializes pnlPrevButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlPrevButtons() {
		if (pnlPrevButtons == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = -1;
			gridBagConstraints2.gridy = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = -1;
			gridBagConstraints1.gridy = 1;
			pnlPrevButtons = new JPanel();
			pnlPrevButtons.setLayout(new GridBagLayout());
			pnlPrevButtons.add(getBtnAddChart(), gridBagConstraints1);
			pnlPrevButtons.add(getBtnRemoveChart(), gridBagConstraints2);
		}
		return pnlPrevButtons;
	}
	/**
	 * This method initializes btnAddChart	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnAddChart() {
		if (btnAddChart == null) {
			btnAddChart = new JButton();
			btnAddChart.setText("Add Chart(s)");
		}
		return btnAddChart;
	}
	/**
	 * This method initializes btnRemoveChart	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRemoveChart() {
		if (btnRemoveChart == null) {
			btnRemoveChart = new JButton();
			btnRemoveChart.setText("Remove Chart");
		}
		return btnRemoveChart;
	}
	
	
	
}
