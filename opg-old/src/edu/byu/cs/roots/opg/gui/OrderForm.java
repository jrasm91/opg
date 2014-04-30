package edu.byu.cs.roots.opg.gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

import edu.byu.cs.roots.opg.exc.FailedToLoadException;
import edu.byu.cs.roots.opg.exc.NotAValidFileException;
import edu.byu.cs.roots.opg.gui.tools.OpgListItem;
import edu.byu.cs.roots.opg.io.HTMLPostCreator;
import edu.byu.cs.roots.opg.model.OpgSession;
import edu.byu.cs.roots.opg.util.BrowserLauncher;
import edu.byu.cs.roots.opg.util.OpgFileFilter;

public class OrderForm extends JDialog {

	public static String RETREVAL_SITE ="https://roots.cs.byu.edu/pedigree/opgretreive.php?SessionID=";

	/**
	 * 
	 */
	private static final long serialVersionUID = 8303886391194290634L;
	private JPanel pnlMain = null;
	private JPanel pnlPreview = null;
	private JScrollPane pnlChartList = null;
	private JPanel pnlChartListMain = null;
	private JPanel pnlListButtons = null;
	private JList lstCharts = null;
	private JButton btnAddChart = null;
	private JButton btnRemoveChart = null;
	private JLabel lblPreview = null;
	private JLabel lblCharts = null;
	private JPanel pnlWindowButtons = null;
	private JButton btnCheckout = null;
	private JButton btnCancel = null;
	private JPanel pnlLBLChart = null;
	private JPanel pnlLBLPreview = null;
	private Canvas cnvPreview = null;
	
	private OpgSession session;
	
	private JDialog frmOrder;
	
	private DefaultListModel chartlist = new DefaultListModel();

	/**
	 * This method initializes 
	 * 
	 */
	public OrderForm(OpgSession currentSessions, Frame parent) {
		super(parent, true);
		session = currentSessions;
		initialize();
		frmOrder = this;
		loadCurrentFile();
		this.setVisible(true);
	}
	
	private void loadCurrentFile()
	{
		try
		{
			if (session.getTempFile() != null)
				chartlist.addElement(new OpgListItem(session.getTempFile()));
			else if (session.projfile != null)
				chartlist.addElement(new OpgListItem(session.projfile));
		}
		catch (NotAValidFileException err)
		{
			System.err.println("Opened chart is not a valid file.");
		}
		catch (FailedToLoadException err) 
		{
			System.err.println("Failed to open the chart that the user is working on.");
		}
		catch (FileNotFoundException err) 
		{
			System.err.println("Could not find the chart that the user was working on.");
		}

	}

	public OrderForm(OpgSession currentSessions, Dialog parent) {
		super(parent, true);
		session = currentSessions;
		initialize();
		frmOrder = this;
		loadCurrentFile();
		this.setVisible(true);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(655, 486));
        this.setContentPane(getPnlMain());
		this.setTitle("Order Charts");
		this.setResizable(false);
		this.addWindowListener(new WindowListener(){
			public void windowActivated(WindowEvent arg0) {}
			public void windowClosed(WindowEvent arg0) {}
			public void windowClosing(WindowEvent arg0) {}
			public void windowDeactivated(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowOpened(WindowEvent arg0) {
				if (chartlist.getSize() > 0)
					lstCharts.setSelectedIndex(0);
			}});
	}

	/**
	 * This method initializes pnlMain	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlMain() {
		if (pnlMain == null) {
			pnlMain = new JPanel();
			pnlMain.setLayout(new BorderLayout());
			pnlMain.add(getPnlChartListMain(), BorderLayout.WEST);
			pnlMain.add(getPnlPreview(), BorderLayout.CENTER);
			pnlMain.add(getPnlWindowButtons(), BorderLayout.SOUTH);
		}
		return pnlMain;
	}
	
	/**
	 * This method initializes getCnvPreview	
	 * 	
	 * @return java.awt.Canvas	
	 */
	private Canvas getCnvPreview() {
		if (cnvPreview == null) {
			cnvPreview = new PreviewCanvas();
		}
		return cnvPreview;
	}

	/**
	 * This method initializes pnlPreview	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlPreview() {
		if (pnlPreview == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = -1;
			gridBagConstraints6.gridy = -1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = -1;
			gridBagConstraints5.gridy = -1;
			lblPreview = new JLabel();
			lblPreview.setText("Chart Preview");
			pnlPreview = new JPanel();
			pnlPreview.setLayout(new BorderLayout());
			pnlPreview.add(getCnvPreview(), BorderLayout.CENTER);
			pnlPreview.add(getPnlLBLPreview(), BorderLayout.NORTH);
		}
		return pnlPreview;
	}

	/**
	 * This method initializes pnlChartList	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getPnlChartList() {
		if (pnlChartList == null) {
			pnlChartList = new JScrollPane();
			pnlChartList.setLayout(new ScrollPaneLayout());
			pnlChartList.setViewportView(getLstCharts());
		}
		return pnlChartList;
	}

	/**
	 * This method initializes pnlChartListMain	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlChartListMain() {
		if (pnlChartListMain == null) {
			lblCharts = new JLabel();
			lblCharts.setText("Charts in your order");
			pnlChartListMain = new JPanel();
			pnlChartListMain.setLayout(new BorderLayout());
			pnlChartListMain.add(getPnlChartList(), BorderLayout.CENTER);
			pnlChartListMain.add(getPnlListButtons(), BorderLayout.SOUTH);
			pnlChartListMain.add(getPnlLBLChart(), BorderLayout.NORTH);
		}
		return pnlChartListMain;
	}

	/**
	 * This method initializes pnlListButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlListButtons() {
		if (pnlListButtons == null) {
			pnlListButtons = new JPanel();
			pnlListButtons.setLayout(new FlowLayout());
			pnlListButtons.add(getBtnAddChart(), null);
			pnlListButtons.add(getBtnRemoveChart(), null);
		}
		return pnlListButtons;
	}

	/**
	 * This method initializes lstCharts	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getLstCharts() {
		if (lstCharts == null) {
			lstCharts = new JList(chartlist);
			lstCharts
					.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
						public void valueChanged(javax.swing.event.ListSelectionEvent e) {
							OpgListItem newSelection = (OpgListItem)lstCharts.getSelectedValue();
							Image imgChart = new BufferedImage(cnvPreview.getWidth() <= 0 ? 450 : cnvPreview.getWidth(), cnvPreview.getHeight() <= 0 ? 390 : cnvPreview.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
							newSelection.DrawChart(imgChart.getGraphics(), new Rectangle(imgChart.getWidth(null),imgChart.getHeight(null)));
							((PreviewCanvas)cnvPreview).setImage(imgChart);
							cnvPreview.repaint();
						}
					});
		}
		return lstCharts;
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
			btnAddChart.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					File directory = new File((session.config.directory != null) ? session.config.directory : "");
					JFileChooser fileChooser = new JFileChooser(directory);
					fileChooser.setFileFilter(OpgFileFilter.OPG);
					fileChooser.setMultiSelectionEnabled(true);

					// Add the filenames
					int result = fileChooser.showOpenDialog(frmOrder);
					if (result == JFileChooser.APPROVE_OPTION)
					{
						File[] lstFiles = fileChooser.getSelectedFiles();
						for (int curFile = 0; curFile < lstFiles.length; curFile++)
						{
							try
							{
								chartlist.addElement(new OpgListItem(lstFiles[curFile].getAbsolutePath()));
							}
							catch (FileNotFoundException err)
							{
								System.err.append("Unable to add " + lstFiles[curFile].getAbsolutePath() + " to the list." + 
										"\nThe file does not exist.");
								
							}
							catch (NotAValidFileException err)
							{
								System.err.append("Unable to add " + lstFiles[curFile].getAbsolutePath() + " to the list." + 
										"\nThat is not an actual file.");
							}
							catch (FailedToLoadException err)
							{
								System.err.append("Unable to add " + lstFiles[curFile].getAbsolutePath() + " to the list." + 
										"\nFile failed to load. The file is corrupted.");
						
							}
						}
						if (lstCharts.getSelectedIndex() < 0 && !chartlist.isEmpty())
							lstCharts.setSelectedIndex(0);
					}
					
				}
			});
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
			btnRemoveChart.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					chartlist.removeElement(lstCharts.getSelectedValue());
				}
			});
		}
		return btnRemoveChart;
	}

	/**
	 * This method initializes pnlWindowButtons	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlWindowButtons() {
		if (pnlWindowButtons == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.RIGHT);
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = -1;
			gridBagConstraints4.gridy = -1;
			pnlWindowButtons = new JPanel();
			pnlWindowButtons.setLayout(flowLayout);
			pnlWindowButtons.add(getBtnCheckout(), null);
			pnlWindowButtons.add(getBtnCancel(), null);
		}
		return pnlWindowButtons;
	}

	/**
	 * This method initializes btnCheckout	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCheckout() {
		if (btnCheckout == null) {
			btnCheckout = new JButton();
			btnCheckout.setText("Go to Checkout");
			btnCheckout.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					String wids="",lens="";
					HTMLPostCreator toSend = new HTMLPostCreator();
					for(int index = 0; index < chartlist.getSize(); index++){
						toSend.AddFile(((OpgListItem)chartlist.get(index)).getPath());
						OpgListItem chart = (OpgListItem) chartlist.get(index);
						wids += chart.getWidth() + " ";
						lens += chart.getLength() + " ";
					}
					System.out.println(wids+"\n"+lens);
					String SessionID = toSend.Send();
					//String extraArgs="&wids="+wids+"&lens="+lens;
					BrowserLauncher.openURL(RETREVAL_SITE + SessionID);
					dispose();
				}
			});
		}
		return btnCheckout;
	}

	/**
	 * This method initializes btnCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("Cancel");
			btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes pnlLBLChart	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlLBLChart() {
		if (pnlLBLChart == null) {
			pnlLBLChart = new JPanel();
			pnlLBLChart.setLayout(new FlowLayout());
			pnlLBLChart.add(lblCharts, null);
		}
		return pnlLBLChart;
	}

	/**
	 * This method initializes pnlLBLPreview	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlLBLPreview() {
		if (pnlLBLPreview == null) {
			pnlLBLPreview = new JPanel();
			pnlLBLPreview.setLayout(new FlowLayout());
			pnlLBLPreview.add(lblPreview, null);
		}
		return pnlLBLPreview;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
