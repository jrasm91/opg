package edu.byu.cs.roots.opg.gui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.byu.cs.roots.opg.io.HTMLPostCreator;
import edu.byu.cs.roots.opg.model.OpgSession;
import edu.byu.cs.roots.opg.util.BrowserLauncher;

public class PreOrderDialog extends JDialog {

	/** Number to used for saving the form as a serial object */
	private static final long serialVersionUID = 1637054880081658140L;
	/** Width of the dialog box */
	private static final int DIALOG_WIDTH = 400;
	/** Height of the dialog box */
	private static final int DIALOG_HEIGHT = 100;
	
	// Static messages on the form
	/** Message displayed by the dialog */
	private static final String strMessage = "Your chart is ready to be ordered.";
	/** Title of the dialog box */
	private static final String DIALOG_TITLE = "Order Chart";
	/** Text of the Button for Single Order */
	private static final String strCheckout = "Proceed to checkout";
	/** Text of the Button for Multi Order */
	private static final String strMoreCharts = "Add Additional Saved Charts";
	/** Text of the Button for Canceling this order */
	private static final String strCancel = "Cancel";
	
	// Local dialog variables
	/** The path to the current chart that was ordered */
	private String currentChart;
	/** Pointer to the form used by the mouse handlers */
	private Dialog frmPreOrderDialog;
	/** The current session of the user */
	private OpgSession userSession;
	
	/**
	 * Create the dialog asking if they want to procede to checkout or
	 * add additional charts.
	 * @param currentSession - The current session information
	 */
	public PreOrderDialog(OpgSession currentSession, Frame parent)
	{
		super(parent,DIALOG_TITLE,true);
		// Set the static variables
		currentChart = currentSession.getTempFile() == null ? currentSession.projfile : currentSession.getTempFile();
		frmPreOrderDialog = this;
		userSession = currentSession;
		
		// Form up the form
		initialize();

	}
	
	private class btnProceedCheckout_MouseHandeler implements MouseListener
	{

		public void mouseClicked(MouseEvent e) 
		{
			HTMLPostCreator poster = new HTMLPostCreator();
			poster.AddFile(currentChart);
			BrowserLauncher.openURL(OrderForm.RETREVAL_SITE + poster.Send());
			frmPreOrderDialog.dispose();
		}

		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
	
	private class btnAddMultiple_MouseHandeler implements MouseListener
	{

		public void mouseClicked(MouseEvent e) 
		{
			frmPreOrderDialog.dispose();
			new OrderForm(userSession, frmPreOrderDialog);
		}

		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
	
	private class btnCancel_MouseHandeler implements MouseListener
	{

		public void mouseClicked(MouseEvent e) 
		{
			frmPreOrderDialog.dispose();
		}

		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}

	/**
	 * This method initializes the appearance of this dialog box
	 */
	private void initialize() {
		
		// Set the layout of the form
		JPanel pnlMain = new JPanel();
		pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
		this.add(pnlMain);
		this.setSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT));
        this.setTitle(DIALOG_TITLE);
        this.setResizable(false);
		
		// Add the message
        JPanel pnlMessage = new JPanel();
        pnlMain.add(pnlMessage);
		JLabel lblMessage = new JLabel(strMessage);
		pnlMessage.add(lblMessage);
		
		// Add the panel for the buttons
		JPanel pnlButtons = new JPanel();
		pnlMain.add(pnlButtons);
		
		// Add the buttons and there handelers
		JButton btnProceedCheckout = new JButton(strCheckout);
		JButton btnAddMultiple = new JButton(strMoreCharts);
		JButton btnCancel = new JButton(strCancel);
		pnlButtons.add(btnProceedCheckout);
		pnlButtons.add(btnAddMultiple);
		pnlButtons.add(btnCancel);
		btnProceedCheckout.addMouseListener(new btnProceedCheckout_MouseHandeler());
		btnAddMultiple.addMouseListener(new btnAddMultiple_MouseHandeler());
		btnCancel.addMouseListener(new btnCancel_MouseHandeler());

	}
}  
