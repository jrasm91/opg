package edu.byu.cs.roots.opg.gui;

import java.awt.AWTError;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ProgressMonitor;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import org.apache.fop.fonts.FontInfo;
import org.apache.fop.svg.PDFFullPageDocumentGraphics2D;
import org.apache.fop.svg.PDFDocumentGraphics2D;
import org.apache.log4j.Logger;

import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.chart.ChartMaker;
import edu.byu.cs.roots.opg.chart.ChartOptions;
import edu.byu.cs.roots.opg.chart.ChartType;
import edu.byu.cs.roots.opg.chart.preset.templates.PresetChartOptions;
import edu.byu.cs.roots.opg.chart.preset.templates.PresetChartOptionsPanel;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBoxScheme;
import edu.byu.cs.roots.opg.color.ColorScheme;
import edu.byu.cs.roots.opg.conf.ConfigData;
import edu.byu.cs.roots.opg.conf.Encryption;
import edu.byu.cs.roots.opg.conf.OpgInterface;
import edu.byu.cs.roots.opg.exc.FailedToLoadException;
import edu.byu.cs.roots.opg.exc.FailedToSaveException;
import edu.byu.cs.roots.opg.fonts.OpgFont;
import edu.byu.cs.roots.opg.gui.tools.AutoCompletion;
import edu.byu.cs.roots.opg.gui.tools.ColorCellRenderer;
import edu.byu.cs.roots.opg.gui.tools.ColorTableModel;
import edu.byu.cs.roots.opg.gui.tools.FloatEditor;
import edu.byu.cs.roots.opg.gui.tools.FloatSpinnerModel;
import edu.byu.cs.roots.opg.gui.tools.Swatch;
import edu.byu.cs.roots.opg.gui.tools.SwatchArray;
import edu.byu.cs.roots.opg.io.AffineOnScreenChartWriter;
import edu.byu.cs.roots.opg.io.HTMLPostCreator;
import edu.byu.cs.roots.opg.io.PrintableChart;
import edu.byu.cs.roots.opg.model.Family;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.ImageFile;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgCursor;
import edu.byu.cs.roots.opg.model.OpgOptions;
import edu.byu.cs.roots.opg.model.OpgPage;
import edu.byu.cs.roots.opg.model.OpgSession;
import edu.byu.cs.roots.opg.model.PaperWidth;
import edu.byu.cs.roots.opg.model.SessionState;
import edu.byu.cs.roots.opg.nfs.DownloadProgress;
import edu.byu.cs.roots.opg.nfs.UsernamePasswordException;
import edu.byu.cs.roots.opg.nfs.newapi.NFSDownloadManager;
import edu.byu.cs.roots.opg.util.BrowserLauncher;
import edu.byu.cs.roots.opg.util.OpgFileFilter;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class OnePageMainGui {

	protected static final String helpUrl = "http://roots.cs.byu.edu/pedigree/help/beta";
	/**
	 * Added By: Spencer HOffa
	 * Added On: 2/5/2013
	 * 
	 * Added a variable for the URL of the donate page
	 * so that it can be easily changed later.
	 */
	private static final String donateURL = "https://secure3.convio.net/ldsp/site/Donation2?idb=51449929&df_id=5301&5301.donation=form1";
	//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	//			END ADDITION BY SPENCER HOFFA
	///////////////////////////////////////////////////////////////////////////////////
	public static String Version = "Version 3.0 Beta - Relapi-user-1254ease 1";
	public static boolean printEnabled = false;//changed to false by: spencer HOffa on 2/15/2013
	public static boolean TEXT_PIC_ENABLED = false;

	private String configPath;

	public static String versionId = "Version 3.489";
//	private ClassLoader loader = null;
	private JFrame jFrame = null; // @jve:decl-index=0:visual-constraint="27,19"
	private JPanel jContentPane = null;
	private JPanel optionsPanel = null;
	private JPanel leftPanel = null;
    /*
     * Edited By:Spencer Hoffa
     * Edited On: 11/13/2012
     * Removed this variable... don't need it anymore.
     */
	//private JPanel rootInfoPanel = null;
	private JPanel viewerPanel = null;
	private OpgPreviewPanel visualPreviewPanel = null;
	private JComboBox chartStyleComboBox = null;
	/*
	 * Removed By: Spencer HOffa
	 * Removed ON: 2/15/2013
	 */
	//private JMenuItem openMenuItem = null;
	///////////////////////////////////////////////////
	private JSlider ancestorSlider = null;
	private JSlider descendantSlider = null;
	private JPanel chartOptionsPanel = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JPanel generationPanel = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel7 = null;
	private JButton turnPageLeftButton = null;
	private JButton turnPageRightButton = null;
	private JLabel pageNumberLabel = null;
	private JPanel pageChangerPanel = null;

	protected OpgSession session;  //  @jve:decl-index=0:

	public static Logger log = Logger.getLogger(OnePageMainGui.class); // @jve:decl-index=0:

	/*
	 * Removed By: Spencer Hoffa
	 * Removed On: 2/15/2013 
	 */
	//private JMenuItem saveMenuItem = null;
	//////////////////////////////////////////////////
	private JMenuItem newMenuItem = null;
	private JMenuItem downloadMenuItem = null;
	private JMenuItem updateMenuItem = null;
	private JMenuItem quitMenuItem = null;
	//private JMenuItem saveAsMenuItem = null;//Removed by: spencer HOffa on 2/15/2013
	private JMenuItem saveAsPDFMenuItem = null;
	private JSeparator jSeparator = null;
	private JMenuItem closeMenuItem = null;
	private JSlider widthSlider = null;
	private JRadioButton PortraitRadioButton = null;
	private JRadioButton LandscapeRadioButton = null;
	private ButtonGroup orientationGroup = null;  //  @jve:decl-index=0:
	private JLabel paperSizeLabel = null;
	private JPanel sliderPanel = null;
	private JPanel paperLengthPanel = null;
	private JSpinner inchesSpinner = null;
	private JLabel inchLabel = null;
	private JTabbedPane TabPane = null;
	private JPanel styleOptionsPanel = null;  //  @jve:decl-index=0:visual-constraint="754,95"
	private JPanel stylePanel = null;
	private JPanel colorOptionsPanel = null;
	private JLabel fontLabel = null;
	private JComboBox fontComboBox = null;
	private JPanel advancedOptionsPanel = null;
	private JCheckBox boldBox = null;
	private JCheckBox italicBox = null;
	private JSpinner maxFontSpinner = null;
	private JSpinner minFontSpinner = null;
	private JLabel maxfontLabel = null;
	private JLabel minfontLabel = null;
	private JLabel styleLabel = null;
	private JLabel mptLabel1 = null;
	private JLabel mptLabel2 = null;
	private JTextArea minTextArea = null;
	private JTextArea maxTextArea = null;
	private JComboBox descSchemeBox = null;
	private JToolBar zoomToolBar = null;
	private JLabel zoomLabel = null;
	private JToggleButton arrowToggleButton = null;
	private JToggleButton handToggleButton = null;
	private JToggleButton zinToggleButton = null;
	private JToggleButton zoutToggleButton = null;
	private JPanel toolPanel = null;
	private JMenuBar menuBar = null; 
	private JMenu fileMenu = null;
	private JMenu optionsMenu = null;
	private JMenu viewMenu = null;
	private JMenu helpMenu = null;
	private JMenu NFSMenu = null;

	private JMenuItem marginsMenuItem = null;
	//private JMenuItem updatePurchasesMenuItem = null;//Removed by: spencer HOffa on 2/15/2013
	private JMenuItem aboutMenuItem = null;
	private JMenuItem TopicsMenuItem = null;
	private JSeparator helpMenuSeparator = null;
	private JPanel spacerPanel = null;
	private JPanel spacerPanel1 = null;
	private JTextField zoomField = null;
    /*
     * Edited By: Spencer Hoffa
     * Edited On: 11/13/2012
     * Removed this variable... don't need it anymore.
     */
	//private JToolBar jToolBar = null;
	private JPanel rootPanel = null;
	private JButton setRootButton = null;
	private JButton viewIndiInfoButton = null;
	private JComboBox rootComboBox = null;
	private JCheckBox includeSpouseCheckBox = null;
	private JCheckBox drawAncestorCheckBox = null;
	private JToolBar jToolBar1 = null;
	private JPanel gedComPanel = null;
	private JLabel jLabel2 = null;
	private JLabel gedcomPathTextBox = null;
	private JCheckBoxMenuItem rulerCheckBox = null;
	/**
	 * Added By: Spencer Hoffa
	 * Added On: 2/12/2013
	 */
	private JCheckBoxMenuItem showAdvancedOptionsCheckBox = null;
	//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	//			End additions by: Spencer HOffa
	//////////////////////////////////////////////////////////////////
	private JCheckBoxMenuItem advancedOptionsCheckBox = null;
	private JButton zoomFitButton = null;
	private JSeparator jSeparator1 = null;
	private JMenuItem printMenuItem = null;
	private SwatchArray swatchArray = null;
	SwatchArray customSwatchArray = null;
	private JScrollPane colorScrollPane = null;
	private JTable descColorTable = null;
	private JTable ancesColorTable = null;
	private JScrollPane ancesScrollPane = null;
	//private JTable ancesColorTable1 = null;
	private JTabbedPane colorTabbedPane = null;
	private JPanel descPanel = null;
	private JPanel ancesPanel = null;
	private JLabel colorSchemeLabel = null;
	private JLabel colorSchemeLabel1 = null;
	private JComboBox ancesSchemeBox = null;
	private JPopupMenu gedPopupMenu = null;  //  @jve:decl-index=0:visual-constraint="601,-19"
	private JMenuItem linkGedcomMenuItem = null;
	private JToolBar insertToolBar = null;
	private JButton addTextButton = null;
	private JButton addPictureButton = null;
	private JMenuItem browserMenuItem = null;
	private JSeparator jSeparator2 = null;
	private JMenuItem insertPictureMenuItem = null;
	private JComboBox styleChoiceComboBox = null;
	
	private DownloadProgress progressBar = null;


//	this is a hack, but I couldn't figure out another way to avoid the changelisteners being called
	//Idealy there would be support for action listeners, that would solve the problem
	public boolean maskEvents = true;
	private JMenuItem pageSetupMenuItem = null;
	private JPanel OrderPanel = null;
	private JButton orderButton = null;
	private JToolBar optionsToolBar = null;
	
	private String username = "";
	private String password = "";
	private final int DOWNLOAD = 0;
	private final int UPDATE = 1;
	
	private Individual root = null;

	public OnePageMainGui thisReference = this;
        
    /**
     * Added By: Spencer Hoffa
     * Added on: 10/31/2012
     * 
     * Variables needed for the new GUI
     */
    private JPanel simpleRootPanel = null;
    private JButton simpleRootSetButton = null;
    
    private JPanel simpleTurnPagePanel = null;
    private JLabel pageNumberLabel2 = null;
    private boolean simplifiedMode = false;
    
    private JFrame splashScreen = null;
    /**
     * Added By: Spencer HOffa
     * Added On: 2/5/2013
     * 
     * Variable for a donate button in the help menu.
     */
    JMenuItem donateMenuItem = null;
    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    //      END ADDED by SPENCER HOFFA
    ///////////////////////////////////////////////////////////////////////

    /**
     * Added By: Spencer hoffa
     * Added On: 1/15/2013
     * 
     * This is a splashscreen panel for this program.  It extends a
     * regular JPanel and overrides the drawing function.
     */
    private class SplashScreenPanel extends JPanel
    {
        Image splashImage = null;
        /**
         * This is the constructor for a SplashScreenPanel
         * 
         * @param imageFileName The filename of the image to display on the
         *      background of the SplashScreen Panel
         */
        public SplashScreenPanel(String imageFileName)
        {
            //load the Image
            URL splashURL = this.getClass().getResource(imageFileName);
            try 
            {
                    InputStream in = splashURL.openStream();
                    splashImage = ImageIO.read(in);
            } 
            catch (IOException e) 
            {
                    e.printStackTrace();
            }
            //--------------
        }
        
        @Override
        public void paintComponent(Graphics g)
        {
        	super.paintComponent(g);
        	
        	//draw my image
        	g.drawImage(splashImage, 0, 0, null);
        }
        
    }
    
    /**
     * This method will create the SplashScreen frame and return it.
     */
    JFrame getSplashScreen()
    {
        if (splashScreen == null)
        {
            splashScreen = new JFrame();
        
            Dimension frameSize = new Dimension(640, 480);

            splashScreen.setMinimumSize(frameSize);

            Dimension screenSize;
            try 
            {
                    Toolkit tk = Toolkit.getDefaultToolkit();
                    screenSize = tk.getScreenSize();
            } 
            catch (AWTError awe) 
            {
                    screenSize = new Dimension(640, 480);
            }
            int x = screenSize.width / 2 - frameSize.width / 2;
            int y = screenSize.height / 2 - frameSize.height / 2;
            splashScreen.setLocation(x, y);

            splashScreen.setAlwaysOnTop(true);

            //Create a panel with the splash as the background
            SplashScreenPanel splashPanel = new SplashScreenPanel("/edu/byu/cs/roots/opg/image/splash.jpg");
            
            splashPanel.setLayout( new BoxLayout( splashPanel, BoxLayout.PAGE_AXIS) );// new BorderLayout() );

            //Create the Close button for the splash screen
            JButton splashClose = new JButton("Close");
            splashClose.setOpaque(false);
            splashClose.addActionListener(
                    new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent e) 
                        {
                            splashScreen.dispose();
                            splashScreen = null;
                            //Added On 2/12/2013
                            //Removed on 2/26/2013
                            if (getJFrame().isVisible())
                            {
                            //	openSupportedFileTypes();
                				getSession().changeType(ChartType.MULTISHEET);
                	            //
                	            setSimplifiedGUI(true);
                            }
                            //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                        }
                    }
                    );
            //-------------------------------------------------
            //Create the donate button for the splash screen
            JButton splashDonate = new JButton("Donate");
            splashDonate.setOpaque(false);
            splashDonate.addActionListener(
            		new ActionListener()
            		{
            			@Override
            			public void actionPerformed(ActionEvent e)
            			{
            				try 
            				{
								java.awt.Desktop.getDesktop().browse(new URI(donateURL));
							} 
            				catch (IOException e1) 
            				{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} 
            				catch (URISyntaxException e1) 
            				{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
            				
            				splashScreen.dispose();
                            splashScreen = null;
                            //Added On 2/12/2013
                            //Removed on 2/26/2013
                            if (getJFrame().isVisible())
                            {
                            //	openSupportedFileTypes();
                				getSession().changeType(ChartType.MULTISHEET);
                	            //
                	            setSimplifiedGUI(true);
                            }
                            //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
            			}
            		}
            		);
            //-------------------------------------------------

            splashPanel.add( Box.createRigidArea( new Dimension(0, (int)(frameSize.height * 0.92f)) ) );
            
            JPanel splashAlignPanel = new JPanel();
            splashAlignPanel.setOpaque(false);
            splashAlignPanel.setLayout( new BoxLayout( splashAlignPanel, BoxLayout.LINE_AXIS) );
            splashAlignPanel.add( Box.createRigidArea( new Dimension(25, 0) ) );
            splashAlignPanel.add( splashClose );
            splashAlignPanel.add( Box.createHorizontalGlue() );
            splashAlignPanel.add( splashDonate );
            splashAlignPanel.add( Box.createRigidArea( new Dimension(25, 0) ) );
            
            
            splashPanel.add(splashAlignPanel);
            
            //Center the Donate button on the Screen
            /*JPanel splashDonatePanel = new JPanel();
            
            //make the panel see through
            splashDonatePanel.setOpaque(false);
            
            splashDonatePanel.setLayout( new BoxLayout( splashDonatePanel, BoxLayout.LINE_AXIS) );
            
            splashDonatePanel.add( Box.createHorizontalGlue() );
            splashDonatePanel.add( splashDonate );
            //splashDonatePanel.add( Box.createHorizontalGlue() );
            
            splashPanel.add(splashDonatePanel);
            //The space between the two buttons
            //splashPanel.add( Box.createRigidArea( new Dimension(0, 8) ) );
            //-------------------------------------------
            
            //Center Close Button On screen-----------------------------------
            JPanel splashClosePanel = new JPanel();
            
            //make the panel see through
            splashClosePanel.setOpaque(false);
            
            splashClosePanel.setLayout( new BoxLayout( splashClosePanel, BoxLayout.LINE_AXIS) );
            
            //splashClosePanel.add( Box.createHorizontalGlue() );
            splashClosePanel.add(splashClose);//, BorderLayout.SOUTH);
            //splashClosePanel.add( Box.createHorizontalGlue() );
            
            splashPanel.add(splashClosePanel);
            //----------------------------------------------------------------
            */
            splashScreen.add(splashPanel);
            
            splashScreen.setUndecorated(true);
            
            splashScreen.pack();
        }
        
        return splashScreen;
    }
    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    //      End Addtiontion By Spencer Hoffa
    /////////////////////////////////////////////////////////////////////
        
	/**
	 * This method initializes the jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	public OnePageMainGui() {
            /**
             * Added by: Spencer Hoffa
             * Added on: 1/15/2013
             * 
             * This code will add a splash screen to the program
             */
            getSplashScreen().setVisible(true);
            //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
            //          End addition by: Spencer HOffa
            //////////////////////////////////////////////////
            
		session = new OpgSession();
		
//		loader = ClassLoader.getSystemClassLoader();
		configPath = System.getProperty("user.home") +
			File.separatorChar + ".onePage3Config";
		try{
			
			//System.out.println("ConfigPath: " + configPath);
			 
			
			//load configuration data
			
//			session.config = Encryption.readConfigData(configPath);
//			session.config.showRuler = false;
//			restoreUserColors();
//			if(!session.config.checkId())
//				updateUserSubscription();
		}
		catch(Exception e){
			e.printStackTrace();
			session.config = new ConfigData();
			updateUserSubscription();
		}
		getJFrame().addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				quit();
			}
		});
		
		getJFrame().addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent arg0) {
				System.out.println("Ouch");
				
			}

			public void keyReleased(KeyEvent arg0) {
				System.out.println("Ah...");
				//  Auto-generated method stub
				
			}

			public void keyTyped(KeyEvent arg0) {
				//  Auto-generated method stub
				
			}
			
			
		});
		
		getJFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setOptionsEnabled(false);
		
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				getJFrame().setVisible(true);
				
				/**
				 * Added By: Spencer Hoffa
				 * Added On: 2/12/2013
				 * 
				 * Force load a file when start up
				 * Removed on 2/26/2013
				 */
				if (splashScreen == null)
				{
					//openSupportedFileTypes();
					getSession().changeType(ChartType.MULTISHEET);
		            //
		            setSimplifiedGUI(true);
				}
				//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
				//	End additions by: Spencer Hoffa
				///////////////////////////////////////////////////////
			}
			
		});
		
	}

	public void terminate(){
		//URL path = loader.getResource("edu/byu/cs/roots/opg/conf/config");
		
		
		//log.info(path);
		
		
		try{
			//save configuration data
			
			
			if(session.config.usercolors == null) session.config.usercolors 
				= new ArrayList<Color>();
			session.config.usercolors.clear();
			for(Swatch s:getCustomSwatchArray().getSwatches()){
				session.config.usercolors.add(s.getColor());
			}
			Encryption.writeConfigData(configPath, session.config);
//		}catch(URISyntaxException se){
//			log.debug("finding config data file", se);
//		
		}catch(Exception ioe){
			log.debug("Error saving config data", ioe);
		}
		System.exit(0);
	}

	private void restoreUserColors(){
		if(session.config.usercolors == null) return;
		Iterator<Swatch> itr = getCustomSwatchArray().getSwatches().iterator();
		for(Color c:session.config.usercolors){
			if(!itr.hasNext()) break;
			itr.next().setColor(c);
		}
	}

	private void updateUserSubscription(){
		System.out.println("You need to re-verify!");
		//TODO THIS IS TEMPORARY TESTING DATA
		session.config.addPurchase("ahe!kglw$02,");
		session.config.addPurchase("e,h2<734th?{");
		session.config.addPurchase("*hw]gn1~9)hw");
		session.config.addPurchase(".9&g2e%bmq)+");
		OpgInterface.getCodes();
		
	}

	/**
	 * connects to http://new.familysearch.org and grabs the user's pedigree from there
	 */
	public void getFamilySearch() {
		close();
		//TODO This is the old NFS API
//		try {
//			log.debug("attempting to connect to http://new.familysearch.com");
//			{
//				final JFrame frame = makeLoginFrame(DOWNLOAD);	
//				frame.pack();
//				frame.setVisible(true);
//				
//			}			
//		} catch (Exception e){
//			JOptionPane.showMessageDialog(this.jContentPane, e
//					.getLocalizedMessage(), e.getLocalizedMessage(),
//					JOptionPane.ERROR_MESSAGE);
//			log.error("Error opening file", e );
//		}
//		
		NFSDownloadManager dl = new NFSDownloadManager();
		dl.execute();
		
	}
	


	/**
	 * Makes the frame used for logging on to new FamiySearch
	 */
	public JFrame makeLoginFrame(final int type) {
		final OnePageMainGui gui = this;
		//TODO: it would be cool if we could add a new FamilySearch icon to the frame!
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		final JFrame info = new JFrame("Login to new FamilySearch");
		info.setLayout(gridbag);
		info.setLocationRelativeTo(null);

		final JTextField uField = new JTextField(20);
		uField.setText("");
		JLabel uLabel = new JLabel("Username: ");
		uLabel.setLabelFor(uField);
		JPanel uPanel = new JPanel();
		uPanel.add(uLabel);
		uPanel.add(uField);
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(uPanel, c);
		info.add(uPanel);

		final JPasswordField pField = new JPasswordField(20);
		pField.setText("");
		JLabel pLabel = new JLabel("Password: ");
		pLabel.setLabelFor(pField);
		JPanel pPanel = new JPanel();
		pPanel.add(pLabel);
		pPanel.add(pField);
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(pPanel, c);
		info.add(pPanel);

		JPanel bPanel = new JPanel();
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		okButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				username = uField.getText();
				password = new String(pField.getPassword());
				info.setVisible(false);
				log.debug("trying login information");
				getProgressBar().getTextArea().setText("");
				session.thread.prepareLogin(gui, username, password, getProgressBar());
				if (type == DOWNLOAD)
				{
					getProgressBar().setThread(session.thread);
					Thread thread = new Thread(session.thread);
					thread.start();
//					downloadFamilySearch();
				}
					
				else //if (type == UPDATE)
					updateFamilySearch();
			}
		});
		
		KeyListener keyListener = new KeyListener(){
			private int pressedCode;
			@Override
			public void keyPressed(KeyEvent e) {pressedCode = e.getKeyCode();}
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER && e.getKeyCode() == pressedCode)	{
				username = uField.getText();
				password = new String(pField.getPassword());
				info.setVisible(false);
				log.debug("trying login information");
				getProgressBar().getTextArea().setText("");
				session.thread.prepareLogin(gui, username, password, getProgressBar());
				if (type == DOWNLOAD)
				{
					getProgressBar().setThread(session.thread);
					Thread thread = new Thread(session.thread);
					thread.start();
//					downloadFamilySearch();
				}
					
				else //if (type == UPDATE)
					updateFamilySearch();
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
				info.setVisible(false);
				log.debug("connection canceled");
			}
		});
		bPanel.add(okButton);
		bPanel.add(cancelButton);
		c.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(bPanel, c);
		info.add(bPanel);

		return info;
	}

	public void downloadFamilySearch() {
//		try {
//			/*TODO: we will want to put a progress bar or some sort of window
//			 *      here to make sure that the user knows something is going
//			 *      on and that the program has not crashed, especially on the
//			 *      slow days when new.familysearch.org is going quite slow.
//			 */
//			session.download(username, password);
//		}
//		catch (UsernamePasswordException e) {
//			JOptionPane.showMessageDialog(null, 
//					e.getMessage(),
//					"Error!", JOptionPane.ERROR_MESSAGE);
//			log.debug("Error downloading file");
//		}
//		catch (IOException e) {
//			JOptionPane.showMessageDialog(null, 
//					"There was an error trying to connect to new.familysearch.org.\n" +
//					"If you tried to download a specific Personal Identifier, make\n" +
//					"sure you typed the Identifier correctly.",
//					"Error!", JOptionPane.ERROR_MESSAGE);
//			log.debug("Error downloading file");
//		}
//
//		if (session.state == SessionState.edit) 
//		{
//			Individual rootTemp = session.getBaseRoot();
//			int ances = session.getOptions().getAncesGens();
//			int desc = session.getOptions().getDescGens();
////			int ances = 0, desc = 0;
//			rootComboBox.removeAllItems();
//			for (Individual indi : session.names_dataProvider) {
//				//log.debug("Adding " + indi);
//				if(indi != null && indi.givenName == null)
//					System.out.println("yes");
//				rootComboBox.addItem(indi);
//			}
//			if(session.getBaseRoot() != null){
//
//				getRootComboBox().setSelectedIndex(session.names_dataProvider.indexOf(rootTemp));
//				getIncludeSpouseCheckBox().setSelected(session.getOptions().isIncludeSpouses());
//				log.debug("setting root");
//				getRootComboBox().repaint();
//				reloadDynAdvOptPanel();
//			}
//			try
//			{
//				session.getOptions().setAncesGens(ances, session);
//				session.getOptions().setDescGens(desc, session);
//			}
//			catch (IllegalArgumentException err)
//			{
//				System.err.println("Generations set to go back are more then this individual has.");
//			}
//
//		}
//
//		rootSelect();
//		reflectState(); 
////		log.debug("Updating SchemesList");
//		updateSchemesList();
//		fillColorTables();
//		refresh();
//		getVisualPreviewPanel().fitWidth();

	}
	
	public void updateFamilySearch() {
		//this is where we will call access.update() :)
		//at some point we might like a try/catch statement in here
		
		session.update(username, password);
		initializeChart();
		
	}
	
	public void initializeChart(){
		if (session.state == SessionState.edit) 
		{
			Individual rootTemp = session.getBaseRoot();
			int ances = session.getOptions().getAncesGens();
			int desc = session.getOptions().getDescGens();
//			int ances = 0, desc = 0;
			rootComboBox.removeAllItems();
			for (Individual indi : session.names_dataProvider) {
				//log.debug("Adding " + indi);
				if(indi != null && indi.givenName == null)
					System.out.println("yes");
				rootComboBox.addItem(indi);
			}
			if(rootTemp != null){

				getRootComboBox().setSelectedIndex(session.names_dataProvider.indexOf(rootTemp));
				getIncludeSpouseCheckBox().setSelected(session.getOptions().isIncludeSpouses());
				log.debug("setting root");
				getRootComboBox().repaint();
				reloadDynAdvOptPanel();
			}
			try
			{
				session.getOptions().setAncesGens(ances, session);
				session.getOptions().setDescGens(desc, session);
			}
			catch (IllegalArgumentException err)
			{
				System.err.println("Generations set to go back are more then this individual has.");
			}

		}

		rootSelect();
		reflectState(); 
//		log.debug("Updating SchemesList");
		updateSchemesList();
		fillColorTables();
		refresh();
		getVisualPreviewPanel().fitWidth();
	}
	
	/*
	 * This is called when the user selects update from the drop down menu. The
	 * method first checks to see if there is a username and password stored already,
	 * and if there is not, then the user must log in again. 
	 */
	public void loginForUpdateFamilySearch() {
		if (username.equals("") || password.equals("")) {
			try {
				log.debug("aquiring username and password");
				{
					final JFrame frame = makeLoginFrame(UPDATE);	
					frame.pack();
					frame.setVisible(true);
				}			
			} catch (Exception e){
				JOptionPane.showMessageDialog(this.jContentPane, e
						.getLocalizedMessage(), e.getLocalizedMessage(),
						JOptionPane.ERROR_MESSAGE);
				log.error("Error aquiring username and password", e );
			}
		}
		else 
			updateFamilySearch();
		
	}
	
	public void importGedcom() {
		try {
			File directory = new File((session.config.directory != null) ?
					session.config.directory : "");
			if(!isMac()){
				JFileChooser fileChooser = new JFileChooser(directory);
				//dialog.
				//fileChooser.setFileFilter(OpgFileFilter.PAFZIP);
				fileChooser.setFileFilter(OpgFileFilter.GED);
				fileChooser.setDialogTitle("New");
				int option = fileChooser.showOpenDialog(this.jContentPane);
				if (option == JFileChooser.APPROVE_OPTION) {
					File f = fileChooser.getSelectedFile();
					open(f);
					reloadDynAdvOptPanel();
					session.config.directory = f.getAbsolutePath();
					int pos = session.config.directory.lastIndexOf("\\");
					if(pos < 0)
						pos = session.config.directory.lastIndexOf("/");
					session.config.directory = session.config.directory.substring(0, pos);
				} else {
					log.debug("File open canceled");
				}
			}
			else {
				FileDialog fd = new FileDialog(jFrame,"Please select a file to open.",FileDialog.LOAD);
				fd.setDirectory(directory.getAbsolutePath());
				fd.setFilenameFilter(OpgFileFilter.GED);
				fd.setVisible(true);
				if(fd.getFile() != null)
					open(fd.getDirectory() + 
							System.getProperty("file.separator") + fd.getFile());
				reloadDynAdvOptPanel();
			}

		} catch (Exception e){
			JOptionPane.showMessageDialog(this.jContentPane, e
					.getLocalizedMessage(), e.getLocalizedMessage(),
					JOptionPane.ERROR_MESSAGE);
			log.error("Error opening file", e );
		}
	}
	
	public void openPaf() {
		try {
			File directory = new File((session.config.directory != null) ?
					session.config.directory : "");
			if(!isMac()){
				JFileChooser fileChooser = new JFileChooser(directory);
				//dialog.
				//fileChooser.setFileFilter(OpgFileFilter.PAFZIP);
				fileChooser.setFileFilter(OpgFileFilter.PAFZIP);
				fileChooser.setDialogTitle("New");
				int option = fileChooser.showOpenDialog(this.jContentPane);
				if (option == JFileChooser.APPROVE_OPTION) {
					File f = fileChooser.getSelectedFile();
					open(f);
					reloadDynAdvOptPanel();
					session.config.directory = f.getAbsolutePath();
					int pos = session.config.directory.lastIndexOf("\\");
					if(pos < 0)
						pos = session.config.directory.lastIndexOf("/");
					session.config.directory = session.config.directory.substring(0, pos);
				} else {
					log.debug("File open canceled");
				}
			}
			else {
				FileDialog fd = new FileDialog(jFrame,"Please select a file to open.",FileDialog.LOAD);
				fd.setDirectory(directory.getAbsolutePath());
				fd.setFilenameFilter(OpgFileFilter.PAFZIP);
				fd.setVisible(true);
				if(fd.getFile() != null)
					open(fd.getDirectory() + 
							System.getProperty("file.separator") + fd.getFile());
				reloadDynAdvOptPanel();
			}

		} catch (Exception e){
			JOptionPane.showMessageDialog(this.jContentPane, e
					.getLocalizedMessage(), e.getLocalizedMessage(),
					JOptionPane.ERROR_MESSAGE);
			log.error("Error opening file", e );
		}
	}

	public void newProject(){
		close();
		
		reloadDynAdvOptPanel();
		reflectState();
		refresh();
		getVisualPreviewPanel().fitWidth();
		// add new project wizard or something
		
		/**
		 * Added By: Spencer Hoffa
		 * Added On: 2/12/2013
		 * 
		 * Force load a file when New Project clicked
		 */
		openSupportedFileTypes();
		getSession().changeType(ChartType.MULTISHEET);
        setSimplifiedGUI(true);
		//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		//	End additions by: Spencer Hoffa
		///////////////////////////////////////////////////////
	}


	/**
	 * Opens a file choosing dialog box to decide which file to open.
	 *
	 */
	public void open() {
		close();
		log.debug("attempting to open file");
		File directory = new File((session.config.directory != null) ? session.config.directory : "");
		if(isMac()){
			System.out.println("open dialog");
			try{
				FileDialog fd= new FileDialog(jFrame,"Please select a file to open", FileDialog.LOAD);
				fd.setDirectory(directory.getAbsolutePath());
				fd.setFilenameFilter(OpgFileFilter.OPG);
				fd.setVisible(true);
				if(fd.getFile() != null)
					open(fd.getDirectory() + 
							System.getProperty("file.separator") + fd.getFile());
				
			}catch (Exception e){}
		}
		else {
			JFileChooser fileChooser = new JFileChooser(directory);
			//fileChooser.addChoosableFileFilter(OpgFileFilter.GED);
			fileChooser.addChoosableFileFilter(OpgFileFilter.OPG);
			//fileChooser.addChoosableFileFilter(OpgFileFilter.PAFZIP);
			//fileChooser.addChoosableFileFilter(OpgFileFilter.OPGGED);
			int option = fileChooser.showOpenDialog(this.jContentPane);
			if (option == JFileChooser.APPROVE_OPTION) {
				open(fileChooser.getSelectedFile());
			} else {
				log.debug("File open canceled");
			}
		}
	}

	public void open(File f){
		try{
			log.debug("opening " + f.getCanonicalPath());
			session.open(f);
			//save the directory
			session.config.directory = f.getAbsolutePath();
			int pos = session.config.directory.lastIndexOf("\\");
			if(pos < 0)
				pos = session.config.directory.lastIndexOf("/");
			session.config.directory = session.config.directory.substring(0, pos);
	//		log.debug("done opening");
			reloadDynAdvOptPanel();
		} catch (FailedToLoadException ftle) {
			System.out.println("Sting");
			JOptionPane.showMessageDialog(this.jContentPane, ftle
					.getLocalizedMessage(), ftle.getLocalizedMessage(),
					JOptionPane.ERROR_MESSAGE);
			log.error("File opening error", ftle);
		} catch (Exception e){
			JOptionPane.showMessageDialog(this.jContentPane, e
					.getLocalizedMessage(), e.getLocalizedMessage(),
					JOptionPane.ERROR_MESSAGE);
			log.error("Error opening file", e );
		}

		if (session.state == SessionState.edit) 
		{
			Individual rootTemp = session.getBaseRoot();
			int ances = session.getBaseOptions().getAncesGens();
			int desc = session.getBaseOptions().getDescGens();
//			int ances = 0, desc = 0;
			rootComboBox.removeAllItems();
			for (Individual indi : session.names_dataProvider) {
				//log.debug("Adding " + indi);
					rootComboBox.addItem(indi);
					
										
					if(root == null && indi.hasBirthDate())
						root = indi;
					
					if(indi.hasBirthDate() && indi.birth.yearInt > root.birth.yearInt)
						root = indi;
					
			}
			if(rootTemp != null){
				//this
				getRootComboBox().setSelectedIndex(session.names_dataProvider.indexOf(rootTemp));
				getIncludeSpouseCheckBox().setSelected(session.getBaseOptions().isIncludeSpouses());
				log.debug("setting root");
				getRootComboBox().repaint();
			}
			try
			{
				session.getBaseOptions().setAncesGens(ances, session);
				session.getBaseOptions().setDescGens(desc, session);
			}
			catch (IllegalArgumentException err)
			{
				System.err.println("Generations set to go back are more then this individual has.");
			}

		}
		//if (session.record.isNFS)
	
		setComboBoxSelection(root); //ANDREW
		rootSelect();
		reflectState(); 
//		log.debug("Updating SchemesList");
		updateSchemesList();
		fillColorTables();
		refresh();
		getVisualPreviewPanel().fitWidth();
	}

	/**
	 * Sets the rootComboBox to the individual passed to it.
	 * 
	 * @param indi the individual to be selected in the rootComboBox.
	 */
	
	public void setComboBoxSelection(Individual indi) {

		rootComboBox.setSelectedItem(indi);
		updateSelectionCheckboxes();
		
	}

	public void open(String filepath){
		try{
			File f = new File(filepath);
			open(f);
		} catch (Exception e){
			JOptionPane.showMessageDialog(this.jContentPane, e
					.getLocalizedMessage(), e.getLocalizedMessage(),
					JOptionPane.ERROR_MESSAGE);
			log.error("Error opening file", e );
		}
	}

	public void order(){

		//check for an Internet connection to the OnePage server
		if (!hasInternetConnection())
		{
			JOptionPane.showMessageDialog(this.jContentPane, "Unable to connect to OnePage Genealogy order server.\n" + 
					"Please ensure you are connected to the Internet and try again."
					, "Connection Failure", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Determine where the screen is right now
		Dimension screenSize;		
		try {
			Toolkit tk = Toolkit.getDefaultToolkit();
			screenSize = tk.getScreenSize();
		} catch (AWTError awe) {
			screenSize = new Dimension(640, 480);
		}
		
		if(session.state == SessionState.newsession){
			session.setTempFile(null);
			new OrderForm(session, getJFrame());
			return;
		}
		else if(session.state == SessionState.view){
			session.setTempFile(null);
		}
		else if(session.state == SessionState.edit){
			if(session.projfile != null)
			{
				save(new File(session.projfile));
				session.setTempFile(null);
			}
			else
			{
				try
				{
					File tempFile = File.createTempFile("Chart", ".opg");
					save(tempFile);
					session.projfile = null;
					session.setTempFile(tempFile.getAbsolutePath());
				}
				catch (IOException e)
				{
					return;
				}
			}
		}
		
		Dialog dlgPreOrder = new PreOrderDialog(session, getJFrame());
		int frameX = screenSize.width / 2 - dlgPreOrder.getWidth() / 2;
		int frameY = screenSize.height / 2 - dlgPreOrder.getHeight() / 2;
		dlgPreOrder.setBounds(frameX, frameY, dlgPreOrder.getWidth(), dlgPreOrder.getHeight());
		dlgPreOrder.setVisible(true);
		
		
//		//generate random numbers to make a unique file name for the file
//		File file = new File(fileName);
//		String tempFileName = getRandomLetters(8) + file.getName();
//		File tempFile = new File(tempFileName);
//		this.save(tempFile);
//		this.save(file);
//		
//		//use a seperate thread to transfer the file to the server
//		ftpTransferToServer(tempFileName);
		
	}
	
	private boolean hasInternetConnection() {
		return HTMLPostCreator.hasInternetConnection();
	}

	public String getRandomLetters(int numLetters)
	{
		Random random = new Random(System.currentTimeMillis());
		String result = "";
		for (int i = 0; i < numLetters; ++i)
		{
			result += (char)(random.nextInt(26) + (int)'A');
		}
		return result;
	}
	
	ProgressMonitor progressMonitor;
	private class FTPTransferThread extends Thread
	{
		String fileName;
		Component comp;
		
		public FTPTransferThread(String fileName, Component comp)
		{
			this.fileName = fileName;
			this.comp = comp;
		}
		
		public void run() {
			try {
				URL url = new URL("ftp://onepage3:charttransfer@titanic.cs.byu.edu:21/" + fileName);
				URLConnection urlc = url.openConnection();
				OutputStream os = urlc.getOutputStream();
				File file = new File(fileName);
				FileInputStream in = new FileInputStream(file);
				BufferedOutputStream bout = new BufferedOutputStream(os);
				progressMonitor = new ProgressMonitor(comp, "Uploading chart to server","",0, (int)file.length());
				System.out.println((int)file.length());
				progressMonitor.setProgress(1);	
				byte[] dataBuffer = new byte[1000];
				int bytesRead;
				//while ((data = in.read()) != -1)
				while ((bytesRead = in.read(dataBuffer)) != -1)
				{
					bout.write(dataBuffer, 0, bytesRead);
					progressMonitor.setProgress(++bytesRead);
					sleep(1);
				}
				bout.close();
				BrowserLauncher.openURL("https://roots.cs.byu.edu/pedigree/upload.php?filename=" + fileName);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(comp, "Error uploading file to server1" + e.getMessage()
						, "File transfer error" , JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	FTPTransferThread transferThread;
	
	public void ftpTransferToServer(String fileName)
	{
		transferThread = new FTPTransferThread(fileName, orderButton);
		transferThread.start();
		return;
		//return true;
	}
	
	public void reflectState(){
		if (session.state == SessionState.edit) {
			getVisualPreviewPanel().setIntroVisible(false);
			getOptionsToolBar().setVisible(true);
			setOptionsEnabled(true);
			//getSaveMenuItem().setEnabled(true);//Removed by: spencer HOffa on 2/15/2013
			//TODO: here!
			if (session != null && session.record != null && session.record.isNFS())
				getNFSMenu().setEnabled(true);
			else
				getNFSMenu().setEnabled(false);
			//getSaveAsMenuItem().setEnabled(true);//Removed by: spencer HOffa on 2/15/2013
			getSaveAsPDFMenuItem().setEnabled(true);
			getCloseMenuItem().setEnabled(true);
			getRulerCheckBox().setEnabled(true);
			/*
			 * Added By: Spencer HOffa
			 * Added On: 2/12/2013
			 */
			getShowAdvancedOptionsCheckBox().setEnabled(true);
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
			//		End additions by: Spencer HOffa
			/////////////////////////////////////////////////////
			getAdvancedOptionsCheckBox().setEnabled(true);
			
			getMarginsMenuItem().setEnabled(true);
			if(printEnabled){
				getPrintMenuItem().setEnabled(true);
				getPageSetupMenuItem().setEnabled(true);
			}
			if(session.projfile == null){
				getJFrame().setTitle("One Page Genealogy " + versionId + " - New Project");
			}else{
				getJFrame().setTitle("One Page Genealogy " + versionId + " - " +session.projfile);
			}
		} else if (session.state == SessionState.view) {
			getVisualPreviewPanel().setIntroVisible(false);
			getOptionsToolBar().setVisible(true);
			setOptionsEnabled(false);
			if (session != null && session.record != null && session.record.isNFS())
				getNFSMenu().setEnabled(true);
			else
				getNFSMenu().setEnabled(false);
			//getSaveMenuItem().setEnabled(true);//Removed by: spencer HOffa on 2/15/2013
			//getSaveAsMenuItem().setEnabled(true);//Removed by: spencer HOffa on 2/15/2013
			getSaveAsPDFMenuItem().setEnabled(true);
			getCloseMenuItem().setEnabled(true);
			getRulerCheckBox().setEnabled(true);
			/*
			 * Added By: Spencer HOffa
			 * Added On: 2/12/2013
			 */
			getShowAdvancedOptionsCheckBox().setEnabled(true);
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
			//		End additions by: Spencer HOffa
			/////////////////////////////////////////////////////
			getAdvancedOptionsCheckBox().setEnabled(true);
			getMarginsMenuItem().setEnabled(false);
			if(printEnabled){
				getPrintMenuItem().setEnabled(true);
				getPageSetupMenuItem().setEnabled(true);
			}
			if(session.projfile != null){
				getJFrame().setTitle("One Page Genealogy " + versionId +" - " +session.projfile);
			}else{
				getJFrame().setTitle("ERROR");
			}
		} else {
			getVisualPreviewPanel().setIntroVisible(true);
                        
                        /*
                         * Added by: Spencer Hoffa
                         * Added On: 11/1/2012
                         * Modifide On: 11/13/2012
                         */
                        //turn their visiblility off when Intro is visible
                        //we want the gui to be simple so turn off things
                        //that aren't going to be used to load a file.
                        getToolPanel().setVisible(false);
                        //root info panel has been merged into the tool panel
                        //getRootInfoPanel().setVisible(false);
                        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                        //      End Addition By Spencer Hoffa
                        ///////////////////////////////////////////////
                        
			getOptionsToolBar().setVisible(false);
			setOptionsEnabled(false);
			getNFSMenu().setEnabled(false);
			//getSaveMenuItem().setEnabled(false);//Removed by: spencer HOffa on 2/15/2013
			//getSaveAsMenuItem().setEnabled(false);//Removed by: spencer HOffa on 2/15/2013
			getSaveAsPDFMenuItem().setEnabled(false);
			getCloseMenuItem().setEnabled(false);
			getRulerCheckBox().setEnabled(false);
			/*
			 * Added By: Spencer HOffa
			 * Added On: 2/12/2013
			 */
			getShowAdvancedOptionsCheckBox().setEnabled(false);
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
			//		End additions by: Spencer HOffa
			/////////////////////////////////////////////////////
			getAdvancedOptionsCheckBox().setEnabled(false);
			getMarginsMenuItem().setEnabled(false);
			
			if(printEnabled){
				getPrintMenuItem().setEnabled(false);
				getPageSetupMenuItem().setEnabled(false);
			}
			getJFrame().setTitle("One Page Genealogy "+versionId);
		}
	}
	
	/**
	 * To be called when the user pushes save, it uses the presence of an associated filename
	 * to determine whether to "save" or "saveas"
	 *
	 */
	public boolean save(){
		boolean success = false;
		if(session.projfile == null){
			success = saveAs();
		}
		else{
			success = save(new File(session.projfile));
		}
		if(success){
			JOptionPane.showMessageDialog(this.jContentPane, "File Saved succesfuly",
					"File Saved", JOptionPane.INFORMATION_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(this.jContentPane, "Failed to Save!!!"
					, "Error loading", JOptionPane.ERROR_MESSAGE);
		}
		reflectState();
		return success;
	}
	
	/**
	 * Initiates the file save process with the given file as the target
	 * @param file The file which will be written to
	 */
	public boolean save(File file){
		boolean success = false;
		try{
			session.save(file, true);
			success = true;
		}
		catch(FailedToSaveException e){
			log.error("didn't save", e);
		}
		catch(IOException ioe){
			log.error("Error saving", ioe);
		}
		return success;
	}
	
	/**
	 * Prompts the user for a file name whether there is an associated file name or not
	 *
	 */
	public boolean saveAs() {
		File f = null;
		log.debug("attempting to saveAs");
		File directory = new File((session.config.directory != null) ? session.config.directory : "");
		JFileChooser fileChooser = new JFileChooser(directory);
		fileChooser.setFileFilter(OpgFileFilter.OPG);
		int option = fileChooser.showSaveDialog(this.jContentPane);
		if (option == JFileChooser.APPROVE_OPTION) {
			String name = fileChooser.getSelectedFile().getName();
			if (name.lastIndexOf(".opg") + 4 != name.length())
				name += ".opg";
			f = new File(name);
			session.config.directory = f.getAbsolutePath();
			int idx = session.config.directory.lastIndexOf('\\');
			if(idx == -1)
				idx = session.config.directory.lastIndexOf('/');
			session.config.directory = session.config.directory.substring(0, idx);
			save(f);
			reflectState();
			return true;
		} else {
			log.debug("File save canceled");
			return false;
		}	
	}
	
	/**
	 * Prompts the user for a file name for the new pdf file.
	 */
	public boolean saveAsPDF() {
		File directory = new File((session.config.directory != null) ? session.config.directory : "");
		JFileChooser fileChooser = new JFileChooser(directory);
		int option = fileChooser.showSaveDialog(this.jContentPane);
		if (option == JFileChooser.APPROVE_OPTION) {
			String name = fileChooser.getSelectedFile().getName();
			if(name.lastIndexOf(".pdf") + 4 != name.length())
				name += ".pdf";
			log.debug("the file that they chose: " + name);
			float length;
			float width;
			boolean isLandscape = session.getBaseOptions().isLandscape();
			float paperLength = (float)(session.getOpgOptions().isPreferredLength()?
					session.getOpgOptions().getPreferredLength():session.getBaseOptions().getPaperLength());
			float paperWidth = (float) session.getBaseOptions().getPaperWidth().width;
			
			if(isLandscape){
				length = paperWidth;
				width = paperLength;
			}else{
				length = paperLength;
				width = paperWidth;
			}
			System.out.println(length+"");
			try {
				FileOutputStream out = new FileOutputStream(name);
				int numPages = session.getPages();
				PDFFullPageDocumentGraphics2D g = new PDFFullPageDocumentGraphics2D(false, out, (int)width, (int)length, numPages+1);

				g.setGraphicContext(new org.apache.xmlgraphics.java2d.GraphicContext());
				FontInfo info = new FontInfo();
				

				
				int remainingPageLength = (int)length;
				int yOffset = 0;
				int prevOffset = 0;
				OpgPage page = session.currentPage();
				int curPage = 1;
				Map<Integer, String> pageReferenceMapping = new HashMap<Integer, String>();
				pageReferenceMapping.put(curPage, g.getPageReference());
				for (ChartDrawInfo draw : session.getAllCharts())
				{
					if (remainingPageLength - draw.getYExtent() >= 0)
					{
						yOffset += prevOffset;
						remainingPageLength -= draw.getYExtent();
					}
					else
					{
						g.nextPage();
						pageReferenceMapping.put(curPage, g.getPageReference());
						remainingPageLength = (int)length - draw.getYExtent();
						yOffset = 0;
					}
					visualPreviewPanel.writer.createChart(draw,g,0,yOffset);
					
					prevOffset = draw.getYExtent();
					System.out.println("Printed page: " + g.getPageReference());
				}
				
				g.finish();
				g.dispose();
				g.finalize();
				out.close();

				System.out.println("the document has completed!");
				JOptionPane.showMessageDialog(getJFrame(), "PDF has been saved");
				return true;
			} catch (FileNotFoundException e) {
				System.out.println("the file " + name + " could not be found when saving as a pdf");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}						
			return false; 	//this will be changed to true when it actually works!
		} else {
			log.debug("PDF file save cancelled");
			return false;
		}
	}

	public void close(){
		root = null;
		chartStyleComboBox.setSelectedIndex(0);
		session.clear();
		session.state = SessionState.newsession;
		rootComboBox.removeAllItems();
		username = "";
		password = "";
		refresh();
		reflectState();
	}
	
	public void quit(){
		if(session.state == SessionState.edit && session.isChanged()){
			/**
			 * Edited By Spencer Hoffa
			 * Edited on: 4/16/2013
			 * 
			 * Removed the ask to save call. and set the return
			 * value to -1.
			 */
			int asktosave = -1;//askToSave("Would you like to save before quiting?");
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
			//				End Addition by: Spencer Hoffa
			/////////////////////////////////////////////////////////////
			if(asktosave == -1){
//				the user said they don't want to save
				terminate();
			}else if(asktosave == 1){
				//the user said they want to save
				if(save())
					terminate();
			}else{
				//the user canceled;
			}
		}else{
			terminate();
		}
	}
	/**
	 * Prompts the user, asking if they want to save or not
	 * 
	 * @return Returns 1 for a yes, 0 for a cancel, and -1 for a no
	 */
	public int askToSave(String message){
		Object[] options = {"Yes",
			                "No",
			                "Cancel"};
		int n = JOptionPane.showOptionDialog(getJFrame(),
			message,
			"One Page Genealogy",
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options[2]);
		switch(n){
		case 0:
			return 1;
		case 1:
			return -1;
		default:
			return 0;
		}
	}
	
	public void refresh(){
		session.getCharts();
		getVisualPreviewPanel().repaint();
		maskEvents = true;
		//do all the refreshing of options here
		refreshBasicOptions();
		refreshTextPreview();
		refreshStyleOptions();
		refreshAdvancedOptions();
		if (session.getOpgOptions().getRefreshSchemeList())
			refreshStyleChoices();
		refreshPurchaseAbilities();
		
		
		maskEvents = false;
	}
	
	public void updateSchemesList(){
		ColorScheme desc = session.getBaseOptions().getDescScheme();
		ColorScheme ances = session.getBaseOptions().getAncesScheme();
		
		getAncesSchemeBox().removeAllItems();
		for(ColorScheme scheme:session.ancesSchemes){
			ancesSchemeBox.addItem(scheme);
		}
		ancesSchemeBox.setSelectedItem(ances);
//		log.debug("Setting ancesSchemeBox to " + session.options.getAncesScheme());
		getDescSchemeBox().removeAllItems();
		for(ColorScheme scheme:session.descSchemes){
			descSchemeBox.addItem(scheme);
		}
		descSchemeBox.setSelectedItem(desc);
//		log.debug("Setting descSchemeBox to " + session.options.getDescScheme());
	}
	
	public void refreshPurchaseAbilities(){
//		boolean purchased = session.config.isPurchased(session.getOpgOptions().getChartType());
//		JMenuItem print = getPrintMenuItem();
//		JMenuItem save = getSaveAsPDFMenuItem();
//		if (!purchased){
//			print.setEnabled(false);
//			print.setToolTipText("You need to purchase this chart!");
//			
//			save.setEnabled(false);
//			save.setToolTipText("You need to purchase this chart!");
//		}
//		else if(purchased){
//			print.setEnabled(true);
//			print.setToolTipText("");
//			
//			save.setEnabled(true);
//			save.setToolTipText("");
//		}
	}
	
	public void fillColorTables(){
		Object[][] data = null;
		ColorTableModel model = null;
		ColorTableModel newmodel = null;
		TableColumn colorColumn = null;
		int i = 0;
		ColorScheme ancesScheme = (ColorScheme) (getAncesSchemeBox().getSelectedItem());
		if(ancesScheme != null){	
			data = new Object[ancesScheme.coloroptions.size()][2];
			i = 0;
			for(String option:ancesScheme.coloroptions){
				try{
					data[i][1] = (ancesScheme.getClass().getDeclaredMethod("get"+option, (Class[])null)).invoke(ancesScheme, (Object[])null);
					data[i][0] = option;
//					log.debug(data[i][0] + " " + data[i][1]);
					i++;
				}
				catch(Exception e){
					log.debug("Error", e);
				}
			}
			model = (ColorTableModel) (getAncesColorTable().getModel());
			newmodel = new ColorTableModel(model.getColumnNames(), data);
			newmodel.addTableModelListener(new TableModelListener() {
				public void tableChanged(TableModelEvent arg0) {
					ColorTableModel model = (ColorTableModel) (getAncesColorTable().getModel());
					String option = (String) model.getValueAt(arg0.getFirstRow(), 0);
					ColorScheme ancesScheme = (ColorScheme) (getAncesSchemeBox().getSelectedItem());
					Class<?>[] colorclass = {Color.class};
					Object[] color = new Object[1];
					color[0] = (Color) model.getValueAt(arg0.getFirstRow(), 1);
					try{
						ancesScheme.getClass().getDeclaredMethod("set"+option, colorclass).invoke(ancesScheme, color);
						ancesScheme.clearTree();
						Individual tempRoot = session.getCurrentPageBaseRoot();
						if(tempRoot.fams.size() > 0){
							Individual spouse = (tempRoot.gender == Gender.MALE) ? tempRoot.fams.get(0).wife : tempRoot.fams.get(0).husband;
							if(session.currentPage().getFirstOptions().isIncludeSpouses() && spouse != null) 
								ancesScheme.colorTree(spouse, ColorScheme.colorup);
						}
						ancesScheme.colorTree(tempRoot, ColorScheme.colorup);
						session.currentPage().getFirstOptions().setAncesScheme(ancesScheme);
						refresh();
					}catch(Exception e){
						log.debug("Error", e);
					}
					
				}
			});
			getAncesColorTable().setModel(newmodel);
			colorColumn = ancesColorTable.getColumnModel().getColumn(1);
			colorColumn.setCellRenderer(new ColorCellRenderer());
		}	
		getAncesColorTable().repaint();
		
		
		ColorScheme descScheme = (ColorScheme) (getDescSchemeBox().getSelectedItem());
		if(descScheme != null){
			data = new Object[descScheme.coloroptions.size()][2];
			i = 0;
			for(String option:descScheme.coloroptions){
				try{
					data[i][1] = (descScheme.getClass().getDeclaredMethod("get"+option, (Class[])null)).invoke(descScheme, (Object[])null);
					data[i][0] = option;
//					log.debug(data[i][0] + " " + data[i][1]);
					i++;
				}
				catch(Exception e){
					log.debug("Error", e);
				}
			}
			model = (ColorTableModel) (getDescColorTable().getModel());
			newmodel = new ColorTableModel(model.getColumnNames(), data);
			newmodel.addTableModelListener(new TableModelListener() {
				public void tableChanged(TableModelEvent arg0) {
					ColorTableModel model = (ColorTableModel) (getDescColorTable().getModel());
					String option = (String) model.getValueAt(arg0.getFirstRow(), 0);
					ColorScheme descScheme = (ColorScheme) (getDescSchemeBox().getSelectedItem());
					Class<?>[] colorclass = {Color.class};
					Object[] color = new Object[1];
					color[0] = (Color) model.getValueAt(arg0.getFirstRow(), 1);
					try{
						descScheme.getClass().getDeclaredMethod("set"+option, colorclass).invoke(descScheme, color);
						descScheme.clearTree();
						Individual tempRoot = session.getBaseRoot();
						if(tempRoot.fams.size() > 0){
							Individual spouse = (tempRoot.gender == Gender.MALE) ? tempRoot.fams.get(0).wife : tempRoot.fams.get(0).husband;
							if(session.getBaseOptions().isIncludeSpouses() && spouse != null) descScheme.colorTree(spouse, ColorScheme.colorup);
						}
						descScheme.colorTree(tempRoot, ColorScheme.colordown);
						session.currentPage().getFirstOptions().setDescScheme(descScheme);
						refresh();
					}catch(Exception e){
						log.debug("Error", e);
					}
				}
			});
			getDescColorTable().setModel(newmodel);
			colorColumn = descColorTable.getColumnModel().getColumn(1);
			colorColumn.setCellRenderer(new ColorCellRenderer());
		}
		getDescColorTable().repaint();
//		refresh();
		
	}
	
	public void refreshTextPreview(){
		int style = 0;
		if(session.getOpgOptions().isBold()) style|=Font.BOLD;
		if(session.getOpgOptions().isItalic()) style|=Font.ITALIC;
		getMinTextArea().setFont(session.getOpgOptions().getFont().getFont(style, session.getOpgOptions().getMinFontSize()));
		getMaxTextArea().setFont(session.getOpgOptions().getFont().getFont(style, session.getOpgOptions().getMaxFontSize()));
	}
	
	public void refreshBasicOptions() {
		gedcomPathTextBox.setText(session.gedfile);
//		log.debug("setting inches spinner to " + session.options.getPaperLength()/72.0);
		inchesSpinner.setValue(session.currentPage().getPageHeight()/72.0);
		includeSpouseCheckBox.setSelected(session.getBaseOptions().isIncludeSpouses());
		getLandscapeRadioButton().setSelected(session.getBaseOptions().isLandscape());
		if (root != null)
			getIncludeSpouseCheckBox().setEnabled((!(root.fams == null || root.fams.size() == 0))&& session.getBaseOptions().getSpouseIncludedChoice());
		
		PaperWidth selected = session.getBaseOptions().getPaperWidth();
		PaperWidth[] widths = PaperWidth.values();
		int length = widths.length;
		for(int i = 0;i<length;i++){
			if(widths[i] == selected ){
				widthSlider.setValue(i);
				break;
			}
		}
		
		ancestorSlider.setMaximum(session.getOpgOptions().getMaxAncesSlider());		
		descendantSlider.setMaximum(session.getOpgOptions().getMaxDescSlider());
		
		//code for making the ticks look good no mater how many generations you have
		ancestorSlider.setMajorTickSpacing((session.getOpgOptions().getMaxAncesSlider() > 10) ? 5 : 1);
		descendantSlider.setMajorTickSpacing((session.getOpgOptions().getMaxDescSlider() > 10) ? 5 : 1);
		
		ancestorSlider.setLabelTable(ancestorSlider.createStandardLabels(ancestorSlider.getMajorTickSpacing(), 0));
		descendantSlider.setLabelTable(descendantSlider.createStandardLabels(descendantSlider.getMajorTickSpacing(), 0));
		
		
		/**
		 * Added By: Spencer Hoffa
		 * Added On: 2/26/2013
		 * 
		 * So that things are set to 6 again
		 */
		if (session.getOpgOptions().getChartType() == ChartType.MULTISHEET)
		{
			if (session.getOpgOptions().getMaxAncesSlider() >= 5)
			{
				session.getBaseOptions().setAncesGens(5, session);
			}
			else
			{
				session.getBaseOptions().setAncesGens(session.getOpgOptions().getMaxAncesSlider(), session);
			}
		}
		//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		//		End addition by: Spencer HOffa
		////////////////////////////////////////////////////
		
		ancestorSlider.setValue(session.getBaseOptions().getAncesGens());
		descendantSlider.setValue(session.getBaseOptions().getDescGens());
		
//		PortraitRadioButton.setSelected(!session.options.isLandscape());
//		LandscapeRadioButton.setSelected(session.options.isLandscape());
		
		PortraitRadioButton.setEnabled(session.getBaseOptions().getPaperOrientationChoice());
		LandscapeRadioButton.setEnabled(session.getBaseOptions().getPaperOrientationChoice());
		widthSlider.setEnabled(session.getBaseOptions().getPaperWidthChoice());
		inchesSpinner.setEnabled(session.getBaseOptions().getPaperHeightChoice());
		
		
	}
	
	public void refreshStyleOptions() {
		getFontComboBox().setSelectedItem(session.getOpgOptions().getFont());
		getBoldBox().setSelected(session.getOpgOptions().isBold());
		getItalicBox().setSelected(session.getOpgOptions().isItalic());
		getMaxFontSpinner().setValue(session.getOpgOptions().getMaxFontSize());
		getMinFontSpinner().setValue(session.getOpgOptions().getMinFontSize());	
		if(session.currentPage() == session.getPage(0))
			showStyleOptionsPanel();
		else
			hideStyleOptionsPanel();
	}
	
	public void refreshAdvancedOptions(){
//		getAncestorCheckBox().setVisible(session.config.advancedOptions && !(root == (Individual)rootComboBox.getSelectedItem()));
//		
//		for (int i = 0; i < getAdvancedOptionsPanel().getComponentCount(); i++){
//			getAdvancedOptionsPanel().getComponent(i).setVisible(session.config.advancedOptions);
//		}
	}
	
	public void refreshStyleChoices(){

		ArrayList<edu.byu.cs.roots.opg.chart.preset.templates.StylingBoxScheme> styles = session.getOpgOptions().getChartStyles();	
		styleChoiceComboBox.removeAllItems();
		for(edu.byu.cs.roots.opg.chart.preset.templates.StylingBoxScheme style : styles)
			styleChoiceComboBox.addItem(style);
		if(!styles.isEmpty())
			styleChoiceComboBox.setSelectedIndex(0);

		session.getOpgOptions().setRefreshSchemeList(false);
	}
	
	public void resetPresetPanel(){
		//Resets the fields in the preset options panel
		if(PresetChartOptions.class.isInstance(session.getBaseOptions())){
			PresetChartOptionsPanel opsPanel = (PresetChartOptionsPanel)getAdvancedOptionsPanel().getComponent(0);
			opsPanel.getStyleEdit().setStyle(0, false, null);
		}
	}
	
	public void reloadDynAdvOptPanel(){
		getAdvancedOptionsPanel().removeAll();
		//adds the chart specific Options panel
		ChartMaker maker = session.getBaseMaker() ;
		if(maker != null)
			getAdvancedOptionsPanel().add(maker.getSpecificOptionsPanel(session.getBaseOptions(), this), BorderLayout.CENTER);
		
		
	}
	
	
	

	private void setOptionsEnabled(boolean enabled) {
		//setEnabled(getColorContentPane(),enabled);
		setEnabled(getRootPanel(), enabled);
		setEnabled(getOptionsPanel(),enabled);
		setEnabled(getStyleOptionsPanel(), enabled);
		setEnabled(getAdvancedOptionsPanel(),enabled);
		setEnabled(getZoomToolBar(), enabled);
		setEnabled(getInsertToolBar(), enabled);
		getOrderButton().setEnabled(true);
	}
	
	private void setEnabled(JComponent com, boolean enabled){
		com.setEnabled(enabled);
		if(com.getComponents().length == 0)	return;
		Component[] coms = com.getComponents();
		int length = coms.length;
		for(int i = 0;i< length;i++){
			if(coms[i] instanceof JComponent)
				setEnabled((JComponent) coms[i], enabled);
			else
				coms[i].setEnabled(enabled);
		}
	}

	/* Called by refresh() and also works with refreshBasicOptions() 
	 *  so if you're having slider problems.. check there!
	 */
	private void rootSelect() 
	{
//		log.debug("root change detected");
		Individual indi = root;
	//	System.out.println("root changed to: " + indi.toString());
		if (indi != null) 
		{
			
			
			/** SET UP SPOUSE AND INIT SCHEMES **/
			if (indi.fams == null || indi.fams.size() == 0) 
			{
				includeSpouseCheckBox.setSelected(false);
				includeSpouseCheckBox.setEnabled(false);
				session.setRoot(indi, false);
			} 
			else 
			{
				if (session.getBaseOptions().getSpouseIncludedChoice())
					includeSpouseCheckBox.setEnabled(true);
				session.setRoot(indi, includeSpouseCheckBox.isSelected());
			}
			//set the spouse
			Individual spouse = null;
			if(indi.fams != null && session.getCurrentPageBaseRoot().fams.size() > 0) 
				spouse = (indi.gender == Gender.MALE) ? indi.fams.get(0).wife : indi.fams.get(0).husband;

			
			/**  COLOR SCHEMES SPREAD DOWN TREE**/
			if (session.getPageNumber() == 0){
				ColorScheme ancesScheme = session.getBaseOptions().getAncesScheme();
				ColorScheme descScheme = session.getBaseOptions().getDescScheme();
				descScheme.clearTree();
				if(session.getBaseOptions().isIncludeSpouses() && spouse != null) descScheme.colorTree(spouse, ColorScheme.colordown);
				descScheme.colorTree(indi, ColorScheme.colordown);
				ancesScheme.clearTree();
				//if(session.options.isIncludeSpouses() && spouse != null) ancesScheme.colorTree(spouse, ColorScheme.colorup);
				if (session.getBaseOptions().isIncludeSpouses())
				{
					for (Family fam: indi.fams)
					{
						Individual curSspouse = (indi.gender == Gender.MALE)? fam.wife: fam.husband;
						if(curSspouse != null)
							ancesScheme.colorTree(curSspouse, ColorScheme.colorup);
					}
				}
				ancesScheme.colorTree(indi, ColorScheme.colorup);
			}
			refresh();
			getVisualPreviewPanel().fitWidth();
		}
	}

	public void updatePaperLength(){		
		if(session.getBaseOptions().getPaperHeightChoice()){
			OpgPage curPage = session.currentPage();
			double val = 72*((Float) (inchesSpinner.getValue()));
			double increaseAmount = val - curPage.getPageHeight();
			double increasePerChart = increaseAmount/curPage.getChartCount();
			for(int i = 0; i < curPage.getChartCount(); i++){
				double newHeight = curPage.getMaker(i).getChart(curPage.getOptions(i), session).getYExtent()+increasePerChart;
				double minLength = curPage.getOptions(i).getMinPaperLength();
				if(newHeight < minLength){
					newHeight = minLength;
				}
				curPage.getOptions(i).setPaperLength(newHeight);
			}
			refresh();
		}
	}
	
	public void updatePaperLength(double newLength){
		if(session.getBaseOptions().getPaperHeightChoice()){
			OpgPage curPage = session.currentPage();
			double increaseAmount = newLength - curPage.getPageHeight();
			double increasePerChart = increaseAmount/curPage.getChartCount();
			for(int i = 0; i < curPage.getChartCount(); i++){
				double newHeight = curPage.getMaker(i).getChart(curPage.getOptions(i), session).getYExtent()+increasePerChart;
				double minLength = curPage.getOptions(i).getMinPaperLength();
				if(newHeight < minLength){
					newHeight = minLength;
				}
				curPage.getOptions(i).setPaperLength(newHeight);
			}
			refresh();
		}
	}
	
	public OpgSession getSession() {
		return session;
	}

	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(759, 552));
			jFrame.setMinimumSize(new Dimension(700, 500));
			//InputStream in = loader.getResourceAsStream("edu/byu/cs/roots/opg/image/OPGlogo.jpg");
			URL iconUrl = this.getClass().getResource("/edu/byu/cs/roots/opg/image/OPGlogo.jpg");
			//System.out.println("IconUrl: " + iconUrl);
			Image image = null;
			try {
				InputStream in = iconUrl.openStream();
				image = ImageIO.read(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
			jFrame.setIconImage(image);  

			jFrame.setTitle("One Page Genealogy "+versionId);
			jFrame.setContentPane(getJContentPane());
			jFrame.setJMenuBar(getMenuBar());
			if (isMac())
				System.setProperty("com.apple.macos.useScreenMenuBar", "true");
			// Place the application in the center of the screen
			Dimension screenSize;
			Dimension frameSize = jFrame.getSize();
			try {
				Toolkit tk = Toolkit.getDefaultToolkit();
				screenSize = tk.getScreenSize();
			} catch (AWTError awe) {
				screenSize = new Dimension(640, 480);
			}
			int x = screenSize.width / 2 - frameSize.width / 2;
			int y = screenSize.height / 2 - frameSize.height / 2;
			jFrame.setLocation(x, y);
			
			jFrame.addWindowFocusListener(new WindowFocusListener(){

				
				@Override
				public void windowGainedFocus(WindowEvent arg0) {
					
					DownloadProgress bar = thisReference.getProgressBar();
					if(bar.isVisible())
					{
						bar.requestFocus();
					}
					
				}

				@Override
				public void windowLostFocus(WindowEvent arg0) {
					
				}
				
			});
			
			reflectState();

		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setMinimumSize(new Dimension(700, 500));
			jContentPane.add(getLeftPanel(), BorderLayout.CENTER);
			jContentPane.add(getJToolBar1(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getOptionsPanel() {
		if (optionsPanel == null) {
			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
			gridBagConstraints33.gridx = 0;
			gridBagConstraints33.weightx = 1.0D;
			gridBagConstraints33.weighty = 1.0D;
			gridBagConstraints33.fill = GridBagConstraints.BOTH;
			gridBagConstraints33.gridy = 4;
			GridBagConstraints gridBagConstraints54 = new GridBagConstraints();
			gridBagConstraints54.gridx = 0;
			gridBagConstraints54.weighty = 0.0D;
			gridBagConstraints54.weightx = 0.0D;
			gridBagConstraints54.fill = GridBagConstraints.BOTH;
			gridBagConstraints54.gridy = 5;
			GridBagConstraints gridBagConstraints43 = new GridBagConstraints();
			gridBagConstraints43.gridx = 0;
			gridBagConstraints43.ipadx = 0;
			gridBagConstraints43.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints43.weightx = 1.0D;
			gridBagConstraints43.anchor = GridBagConstraints.NORTH;
			gridBagConstraints43.gridy = 0;
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.gridx = 0;
			gridBagConstraints41.ipadx = 5;
			gridBagConstraints41.ipady = 0;
			gridBagConstraints41.fill = GridBagConstraints.BOTH;
			gridBagConstraints41.weightx = 1.0D;
			gridBagConstraints41.weighty = 8.0D;
			gridBagConstraints41.gridy = 3;
			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
			gridBagConstraints42.gridx = 0;
			gridBagConstraints42.gridy = 1;
			gridBagConstraints42.weightx = 8.0D;
			gridBagConstraints42.weighty = 1.0D;
			gridBagConstraints42.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints44 = new GridBagConstraints();
			gridBagConstraints44.gridx = 0;
			gridBagConstraints44.gridy = 2;
			
			optionsPanel = new JPanel();
			optionsPanel.setLayout(new GridBagLayout());
			optionsPanel.setPreferredSize(new Dimension(210, 300));
			optionsPanel.add(getGenerationPanel(), gridBagConstraints41);
			optionsPanel.add(getChartOptionsPanel(), gridBagConstraints43);
			optionsPanel.add(getOrderPanel(), gridBagConstraints33);
			
			optionsPanel.add(getSpacerPanel(), gridBagConstraints54);
			optionsPanel.add(getStyleChoiceComboBox(), gridBagConstraints42);
			optionsPanel.add(getPageChangerPanel(), gridBagConstraints44);
		}
		return optionsPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getLeftPanel() {
		if (leftPanel == null) {
			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
			gridBagConstraints42.gridx = 0;
			gridBagConstraints42.fill = GridBagConstraints.BOTH;
			gridBagConstraints42.weighty = 1.0D;
			gridBagConstraints42.weightx = 1.0D;
			gridBagConstraints42.gridy = 1;
			leftPanel = new JPanel();
			leftPanel.setLayout(new GridBagLayout());
			leftPanel.add(getViewerPanel(), gridBagConstraints42);
		}
		return leftPanel;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
         * Edited By: spencer Hoffa
         * Edited On: 11/13/2012
         * Removed this method because it wasn't needed anymore.
	 */
	/*private JPanel getRootInfoPanel() {
		if (rootInfoPanel == null) {
			rootInfoPanel = new JPanel();
			rootInfoPanel.setLayout(new BoxLayout(getRootInfoPanel(), BoxLayout.X_AXIS));
			rootInfoPanel.add(getJToolBar(), null);
		}
		return rootInfoPanel;
	}*/

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getViewerPanel() {
		if (viewerPanel == null) {
			viewerPanel = new JPanel();
			viewerPanel.setLayout(new BorderLayout());
//			viewerPanel.setBorder(BorderFactory.createTitledBorder(null, "Viewer",
//					TitledBorder.DEFAULT_JUSTIFICATION,
//					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
//							Font.BOLD, 12), new Color(51, 51, 51)));
			viewerPanel.add(getVisualPreviewPanel(), BorderLayout.CENTER);
			viewerPanel.add(getToolPanel(), BorderLayout.NORTH);
                        /*
                         * Edited BY: Spencer Hoffa
                         * Edited On: 11/13/2012
                         * //Root info panel has been merged into Tool Panel
                         */
			//viewerPanel.add(getRootInfoPanel(), BorderLayout.SOUTH);
			viewerPanel.add(getOptionsToolBar(), BorderLayout.EAST);
                        
                        /*
                         * added By: Spencer Hoffa
                         * Added On: 11/1/2012
                         * Modified On: 11/13/2012
                         */
                        //rootInfo Panel and ToolPanel don't need to be visible
                        //when created
                        getToolPanel().setVisible(false);
                        //Root info panel has been merged into Tool Panel
                        //getRootInfoPanel().setVisible(false);
                        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                        //      END ADDITION BY SPENCER HOFFA
                        ////////////////////////////////////////////////////
		}
		return viewerPanel;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private OpgPreviewPanel getVisualPreviewPanel() {
		if (visualPreviewPanel == null) {
			visualPreviewPanel = new OpgPreviewPanel(this);
			visualPreviewPanel.setLayout(new GridBagLayout());
			//visualPreviewPanel
			//		.setBorder(BorderFactory
			//		.createLineBorder(Color.darkGray, 2));
			
		}
		return visualPreviewPanel;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getChartStyleComboBox() {
		if (chartStyleComboBox == null) {
			chartStyleComboBox = new JComboBox();
			chartStyleComboBox.setToolTipText("You can select from a variety of chart types");
			//fills in all the chart type options from the ChartType enum
			for(ChartType type:ChartType.values()){
				chartStyleComboBox.addItem(type);
			}
			ChartType type = (ChartType) chartStyleComboBox.getSelectedItem();
			session.changeType(type);
			chartStyleComboBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!maskEvents){
						ChartType type = (ChartType) chartStyleComboBox.getSelectedItem();
						if(type != session.getOpgOptions().getChartType()){
							session.changeType(type);
							reloadDynAdvOptPanel();
							refresh();
							getVisualPreviewPanel().fitWidth();
						}
					}
				}
			});
			
		}
		return chartStyleComboBox;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 * 
	 * Remvoed On: 2/15/2013
	 * Removed By: Spencer HOffa
	 */
	/*private JMenuItem getOpenMenuItem() {
		if (openMenuItem == null) {
			openMenuItem = new JMenuItem();
			openMenuItem.setText("Open");
			openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() ));
			openMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(session.state == SessionState.edit && session.isChanged()){
						int asktosave = askToSave("Would you like to save before opening a new file?");
						if(asktosave == -1){
//							the user said they don't want to save
							open();
						}else if(asktosave == 1){
							//the user said they want to save
							if(save())
								open();
						}else{
							//the user canceled;
						}
					}else{
						open();
					}
				}
			});
		}
		return openMenuItem;
	}
	*/

	/**
	 * This method initializes jSlider2
	 * 
	 * @return javax.swing.JSlider
	 */
	private JSlider getAncestorSlider() {
		if (ancestorSlider == null) {
			ancestorSlider = new JSlider();
			ancestorSlider.setMajorTickSpacing(1);
			ancestorSlider.setMinorTickSpacing(1);
			ancestorSlider.setMaximum(0);
			ancestorSlider.setPaintTicks(true);
			ancestorSlider.setEnabled(false);
			ancestorSlider.setSnapToTicks(true);
			ancestorSlider.setPaintLabels(true);
			ancestorSlider.setToolTipText("Use this slider to control how many generatons of ancestors you want to have on your chart");
			ancestorSlider.setInverted(false);
			ancestorSlider.setPreferredSize(new Dimension(50, 200));
			ancestorSlider.setOrientation(JSlider.VERTICAL);
			ancestorSlider.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (!maskEvents) {
						if (session.getBaseOptions().getAncesGens() != ancestorSlider.getValue()){
							session.getBaseOptions().setAncesGens(ancestorSlider.getValue(), session);
							refresh();
							getVisualPreviewPanel().fitWidth();
						}
					}
				}
			});
		}
		return ancestorSlider;
	}

	/**
	 * This method initializes jSlider3
	 * 
	 * @return javax.swing.JSlider
	 */
	private JSlider getDescendantSlider() {
		if (descendantSlider == null) {
			descendantSlider = new JSlider();
			descendantSlider.setMajorTickSpacing(1);
			descendantSlider.setMinorTickSpacing(1);
			descendantSlider.setPaintTicks(true);
			descendantSlider.setMaximum(0);
			descendantSlider.setSnapToTicks(true);
			descendantSlider.setPaintLabels(true);
			descendantSlider.setToolTipText("Use this slider to control how many generatons of descendants you want to have on your chart");
			descendantSlider.setInverted(false);
			descendantSlider.setPreferredSize(new Dimension(50, 200));
			descendantSlider.setOrientation(JSlider.VERTICAL);
			descendantSlider.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (!maskEvents) {
//						log.debug("Descendants adjusted to " + descendantSlider.getValue());
						if (session.getBaseOptions().getDescGens() != descendantSlider.getValue()){
							session.getBaseOptions().setDescGens(descendantSlider.getValue(), session);
							refresh();
							getVisualPreviewPanel().fitWidth();
						}
					}
				}
			});
		}
		return descendantSlider;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getChartOptionsPanel() {
		if (chartOptionsPanel == null) {
			GridBagConstraints gridBagConstraints53 = new GridBagConstraints();
			gridBagConstraints53.gridx = 2;
			gridBagConstraints53.gridwidth = 2;
			gridBagConstraints53.gridy = 3;
			GridBagConstraints gridBagConstraints52 = new GridBagConstraints();
			gridBagConstraints52.gridx = 0;
			gridBagConstraints52.gridwidth = 2;
			gridBagConstraints52.weighty = 1.0D;
			gridBagConstraints52.gridy = 3;
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.gridx = 1;
			gridBagConstraints51.fill = GridBagConstraints.BOTH;
			gridBagConstraints51.gridwidth = 3;
			gridBagConstraints51.gridy = 2;
			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
			gridBagConstraints50.gridx = 0;
			gridBagConstraints50.weighty = 1.0D;
			gridBagConstraints50.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints50.gridy = 2;
			GridBagConstraints gridBagConstraints49 = new GridBagConstraints();
			gridBagConstraints49.gridx = 2;
			gridBagConstraints49.gridwidth = 2;
			gridBagConstraints49.fill = GridBagConstraints.BOTH;
			gridBagConstraints49.gridy = 1;
			GridBagConstraints gridBagConstraints48 = new GridBagConstraints();
			gridBagConstraints48.gridx = 0;
			gridBagConstraints48.weighty = 1.0D;
			gridBagConstraints48.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints48.gridy = 1;
			GridBagConstraints gridBagConstraints46 = new GridBagConstraints();
			gridBagConstraints46.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints46.gridy = 0;
			gridBagConstraints46.weightx = 1.0;
			gridBagConstraints46.gridwidth = 2;
			gridBagConstraints46.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints46.gridx = 2;
			GridBagConstraints gridBagConstraints47 = new GridBagConstraints();
			gridBagConstraints47.gridx = 0;
			gridBagConstraints47.weighty = 1.0D;
			gridBagConstraints47.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints47.gridy = 0;
			paperSizeLabel = new JLabel();
			paperSizeLabel.setText(PaperWidth.values()[0].displayName);
			paperSizeLabel.setToolTipText("The current size of the paper");
			paperSizeLabel.setPreferredSize(new Dimension(25, 16));
			jLabel5 = new JLabel();
			jLabel5.setText("Width");
			jLabel4 = new JLabel();
			jLabel4.setText("Height");
			jLabel3 = new JLabel();
			jLabel3.setText("Style");
			chartOptionsPanel = new JPanel();
			chartOptionsPanel.setLayout(new GridBagLayout());
			chartOptionsPanel.setPreferredSize(new Dimension(170, 170));
			chartOptionsPanel.setBorder(BorderFactory.createTitledBorder(null, "Chart Layout", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			chartOptionsPanel.setMinimumSize(new Dimension(150, 150));
			chartOptionsPanel.add(jLabel3, gridBagConstraints47);
			chartOptionsPanel.add(getChartStyleComboBox(), gridBagConstraints46);
			chartOptionsPanel.add(jLabel4, gridBagConstraints48);
			chartOptionsPanel.add(getPaperLengthPanel(), gridBagConstraints49);
			chartOptionsPanel.add(jLabel5, gridBagConstraints50);
			chartOptionsPanel.add(getSliderPanel(), gridBagConstraints51);
			chartOptionsPanel.add(getPortraitRadioButton(), gridBagConstraints52);
			chartOptionsPanel.add(getLandscapeRadioButton(), gridBagConstraints53);
			chartOptionsPanel.setPreferredSize(new Dimension(230, 150));
			getOrientationGroup();
		}
		return chartOptionsPanel;
	}

	/**
	 * This method initializes generationPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getGenerationPanel() {
		if (generationPanel == null) {
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints18.gridy = 2;
			gridBagConstraints18.weightx = 1.0;
			gridBagConstraints18.weighty = 0.0D;
			gridBagConstraints18.gridx = 0;
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints17.gridy = 2;
			gridBagConstraints17.weightx = 1.0;
			gridBagConstraints17.weighty = 5.0D;
			gridBagConstraints17.gridx = 1;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints16.gridy = 1;
			gridBagConstraints16.ipadx = 0;
			gridBagConstraints16.anchor = GridBagConstraints.NORTH;
			gridBagConstraints16.weighty = 1.0D;
			gridBagConstraints16.gridx = 1;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints15.gridy = 1;
			gridBagConstraints15.ipadx = 0;
			gridBagConstraints15.anchor = GridBagConstraints.NORTH;
			gridBagConstraints15.weightx = 0.0D;
			gridBagConstraints15.weighty = 1.0D;
			gridBagConstraints15.gridx = 0;
			jLabel7 = new JLabel();
			jLabel7.setText("Descendants");
			jLabel7.setToolTipText("");
			jLabel6 = new JLabel();
			jLabel6.setText("Ancestors");
			jLabel6.setToolTipText("");
			generationPanel = new JPanel();
			generationPanel.setLayout(new GridBagLayout());
			generationPanel.setPreferredSize(new Dimension(205, 200));
			generationPanel.setBorder(BorderFactory.createTitledBorder(null, "Generations", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			generationPanel.setMaximumSize(new Dimension(210, 200));
			generationPanel.setMinimumSize(new Dimension(144, 50));
			generationPanel.add(jLabel6, gridBagConstraints15);
			generationPanel.add(jLabel7, gridBagConstraints16);
			generationPanel.add(getDescendantSlider(), gridBagConstraints17);
			generationPanel.add(getAncestorSlider(), gridBagConstraints18);
		}
		return generationPanel;
	}

	/**
	 * This method initializes jMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 * 
	 * Removed by: Spencer HOffa
	 * Removed On: 2/15/2013
	 */
	/*
	private JMenuItem getSaveMenuItem() {
		if (saveMenuItem == null) {
			saveMenuItem = new JMenuItem();
			saveMenuItem.setText("Save");
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() ));
			saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					save();
				}
			});
		}
		return saveMenuItem;
	}
	//*/
	
	/**
	 * This method initializes jMenuItem2
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getNewMenuItem() {
		if (newMenuItem == null) {
			newMenuItem = new JMenuItem();
			newMenuItem.setText("New");
			newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() ));
			newMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(session.state == SessionState.edit){
						int asktosave = askToSave("Would you like to save before creating a new project?");
						if(asktosave == -1){
//							the user said they don't want to save
							newProject();
						}else if(asktosave == 1){
							//the user said they want to save
							if(save())
								newProject();
						}else{
							//the user canceled;
						}
					}else{
						newProject();
					}
				}
			});
		}
		return newMenuItem;
	}

	/**
	 * This method initializes jMenuItem3
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getDownloadMenuItem() {
		if (downloadMenuItem == null) {
			downloadMenuItem = new JMenuItem();
			downloadMenuItem.setText("Download New");
			downloadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() ));
			downloadMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (session.state == SessionState.edit) {
						int asktosave = askToSave("Would you like to save before downloading a new project?");
						if (asktosave == -1) {
//							the user said they don't want to save
							getFamilySearch();
						}else if(asktosave == 1){
//							the user said they want to save
							if (save())
								getFamilySearch();
						}else{
							//the user cancelled;
						}
					}else{
						getFamilySearch();
					}
					
				}
			});
		}
		return downloadMenuItem;
	}	
	
	/**
	 * this method initializes jMenuItem4
	 * 
	 * @return javax.swing.JMenuItem
	 */
	/*
	 * TODO: this should only be available if the user downloaded their information
	 * off new.familysearch.org.  otherwise, it should not be allowed to be selected.
	 * this could pose a problem for records that are saved, reopened, and then want
	 * to update their download.
	 */	
	private JMenuItem getUpdateMenuItem() {
		if (updateMenuItem == null) {
			updateMenuItem = new JMenuItem();
			updateMenuItem.setText("Update");
			updateMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() ));
			updateMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (session.state == SessionState.edit) {
						loginForUpdateFamilySearch();
					}
				}
			});
		}
		return updateMenuItem;
	}
	
	/**
	 * This method initializes jMenuItem5
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getQuitMenuItem() {
		if (quitMenuItem == null) {
			quitMenuItem = new JMenuItem();
			quitMenuItem.setText("Quit");
			quitMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					quit();
				}
			});
		}
		return quitMenuItem;
	}

	/**
	 * This method initializes jMenuItem6	
	 * 	
	 * @return javax.swing.JMenuItem	
	 * 
	 * Removed By: Spencer HOffa
	 * Removed On: 2/15/2013
	 */
	/*
	private JMenuItem getSaveAsMenuItem() {
		if (saveAsMenuItem == null) {
			saveAsMenuItem = new JMenuItem();
			saveAsMenuItem.setText("Save As...");
			saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				KeyEvent.SHIFT_DOWN_MASK | Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() ));
			saveAsMenuItem.setEnabled(false);
			saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveAs();
				}
			});
		}
		return saveAsMenuItem;
	}
	//*/
	
	/**
	 * This method initializes jMenuItem(something)
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSaveAsPDFMenuItem() {
		if (saveAsPDFMenuItem == null) {
			saveAsPDFMenuItem = new JMenuItem();
			saveAsPDFMenuItem.setText("Save as PDF");
			saveAsPDFMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			saveAsPDFMenuItem.setEnabled(false);
			saveAsPDFMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveAsPDF();
				}
			});
		}
		return saveAsPDFMenuItem;
	}
	
	/**
	 * This method initializes jSeparator	
	 * 	
	 * @return javax.swing.JSeparator	
	 */
	private JSeparator getJSeparator() {
		if (jSeparator == null) {
			jSeparator = new JSeparator();
		}
		return jSeparator;
	}

	/**
	 * This method initializes jMenuItem5	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getCloseMenuItem() {
		if (closeMenuItem == null) {
			closeMenuItem = new JMenuItem();
			closeMenuItem.setText("Close");
			closeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() ));
			closeMenuItem.setEnabled(false);
			closeMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(session.state == SessionState.edit && session.isChanged()){
						int asktosave = askToSave("Would you like to save before closing this file?");
						if(asktosave == -1){
//							the user said they don't want to save
							close();
						}else if(asktosave == 1){
							//the user said they want to save
							if(save())
								close();
						}else{
							//the user cancelled;
						}
					}else{
						close();
					}
				}
			});
		}
		return closeMenuItem;
	}

	/**
	 * This method initializes widthSlider	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getWidthSlider() {
		if (widthSlider == null) {
			widthSlider = new JSlider();
			widthSlider.setPaintTicks(true);
			widthSlider.setMaximum(PaperWidth.values().length-1);
			widthSlider.setMinimum(0);
			widthSlider.setMajorTickSpacing(1);
			widthSlider.setMinorTickSpacing(1);
			widthSlider.setSnapToTicks(true);
			widthSlider.setPreferredSize(new Dimension(110, 25));
			widthSlider.setToolTipText("Use this slider to determine which paper size you want to use");
			widthSlider.setValue(0);
			widthSlider.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					updatePaperWidth();
				}
			});
		}
		return widthSlider;
	}
	
	public void updatePaperWidth(){
		if(session.getBaseOptions().getPaperWidthChoice()){
			PaperWidth width = PaperWidth.values()[widthSlider.getValue()];
			paperSizeLabel.setText(width.displayName);
			if (!maskEvents) {
				for(ChartOptions ops : session.currentPage().getOptionsList())
					ops.setPaperWidth(width);
			}
			refresh();
		}
	}
	
	public void updatePaperWidth(PaperWidth newWidth){
		if(session.getBaseOptions().getPaperWidthChoice()){
			paperSizeLabel.setText(newWidth.displayName);
			if (!maskEvents) {
				for(ChartOptions ops : session.currentPage().getOptionsList())
					ops.setPaperWidth(newWidth);
			}
			refresh();
		}
	}

	/**
	 * This method initializes PortraitRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getPortraitRadioButton() {
		if (PortraitRadioButton == null) {
			PortraitRadioButton = new JRadioButton();
			PortraitRadioButton.setSelected(true);
			PortraitRadioButton.setToolTipText("Click here to change the orientation so that the height is fully adjustable");
			PortraitRadioButton.setText("Portrait");
			PortraitRadioButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!maskEvents){
						if(!getLandscapeRadioButton().isSelected()){
							session.getOptions().setLandscape(false);
							GridBagLayout layout = (GridBagLayout) chartOptionsPanel.getLayout();
							GridBagConstraints constslider = layout.getConstraints(sliderPanel);
							GridBagConstraints constpaper = layout.getConstraints(paperLengthPanel);
							constslider.gridy = 2;
							constpaper.gridy = 1;
							layout.setConstraints(sliderPanel, constslider);
							layout.setConstraints(paperLengthPanel, constpaper);
							chartOptionsPanel.revalidate();
							refresh();
						}
					}
				}
			});
		}
		return PortraitRadioButton;
	}

	/**
	 * This method initializes LandscapeRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getLandscapeRadioButton() {
		if (LandscapeRadioButton == null) {
			LandscapeRadioButton = new JRadioButton();
			LandscapeRadioButton.setText("Landscape");
			LandscapeRadioButton.setToolTipText("Click here to change the orientation of the paper so that the width is fully adjustable");
			LandscapeRadioButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!maskEvents){
						if(getLandscapeRadioButton().isSelected()){
							session.getOptions().setLandscape(true);
							GridBagLayout layout = (GridBagLayout) chartOptionsPanel.getLayout();
							GridBagConstraints constslider = layout.getConstraints(sliderPanel);
							GridBagConstraints constpaper = layout.getConstraints(paperLengthPanel);
							constslider.gridy = 1;
							constpaper.gridy = 2;
							layout.setConstraints(sliderPanel, constslider);
							layout.setConstraints(paperLengthPanel, constpaper);
							chartOptionsPanel.revalidate();
							refresh();
						}
					}
				}
			});
		}
		return LandscapeRadioButton;
	}

	private ButtonGroup getOrientationGroup() {
		if(orientationGroup == null){
			orientationGroup = new ButtonGroup();
			orientationGroup.add(getPortraitRadioButton());
			orientationGroup.add(getLandscapeRadioButton());
		}
		return orientationGroup;
	}
	
	/**
	 * This method initializes sliderPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSliderPanel() {
		if (sliderPanel == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.gridx = 0;
			sliderPanel = new JPanel();
			sliderPanel.setLayout(new GridBagLayout());
			sliderPanel.setPreferredSize(new Dimension(105, 25));
			sliderPanel.setMaximumSize(new Dimension(105, 25));
			sliderPanel.add(getWidthSlider(), gridBagConstraints);
			sliderPanel.add(paperSizeLabel, gridBagConstraints1);
		}
		return sliderPanel;
	}

	/**
	 * This method initializes paperLengthPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPaperLengthPanel() {
		if (paperLengthPanel == null) {
			GridBagConstraints gridBagConstraints44 = new GridBagConstraints();
			gridBagConstraints44.gridx = 1;
			gridBagConstraints44.weightx = 1.0D;
			gridBagConstraints44.gridy = 0;
			GridBagConstraints gridBagConstraints45 = new GridBagConstraints();
			gridBagConstraints45.gridx = 0;
			gridBagConstraints45.ipadx = 0;
			gridBagConstraints45.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints45.gridy = 0;
			inchLabel = new JLabel();
			inchLabel.setText("inches");
			paperLengthPanel = new JPanel();
			paperLengthPanel.setLayout(new GridBagLayout());
			paperLengthPanel.add(getInchesSpinner(), gridBagConstraints45);
			paperLengthPanel.add(inchLabel, gridBagConstraints44);
		}
		return paperLengthPanel;
	}

	/**
	 * This method initializes inchesField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public static final int MAXINCHES=72, MININCHES=12;
	JSpinner getInchesSpinner() {
		if (inchesSpinner == null) {
			FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
			spinnerModel.setMaxvalue(10000);
			spinnerModel.setMinvalue(0);
			spinnerModel.setValue(36f);
			inchesSpinner = new JSpinner();
			inchesSpinner.setModel(spinnerModel);
			inchesSpinner.setEditor(new FloatEditor(inchesSpinner));
			inchesSpinner.setEnabled(false);
			inchesSpinner.setToolTipText("The current size of the paper");
			inchesSpinner.setPreferredSize(new Dimension(60, 20));
			inchesSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (!maskEvents) {
						updatePaperLength();
					}
				}
			});
		}
		return inchesSpinner;
	}

	/**
	 * This method initializes TabPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getTabPane() {
		if (TabPane == null) {
			TabPane = new JTabbedPane();
			TabPane.setToolTipText("");
			TabPane.setMinimumSize(new Dimension(250, 300));
			TabPane.addTab("Options", null, getOptionsPanel(), null);
			TabPane.addTab("Style", null, getStyleOptionsPanel(), null);
			TabPane.addTab("Advanced", null, getAdvancedOptionsPanel(), null);
		}
		return TabPane;
	}

	/**
	 * This method initializes styleOptionsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getStyleOptionsPanel() {
		if (styleOptionsPanel == null) {
			GridBagConstraints gridBagConstraints55 = new GridBagConstraints();
			gridBagConstraints55.gridx = 0;
			gridBagConstraints55.weighty = 1.0D;
			gridBagConstraints55.gridy = 2;
			GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
			gridBagConstraints27.gridx = 0;
			gridBagConstraints27.ipadx = 1;
			gridBagConstraints27.ipady = 1;
			gridBagConstraints27.fill = GridBagConstraints.BOTH;
			gridBagConstraints27.weighty = 1.0D;
			gridBagConstraints27.gridwidth = 1;
			gridBagConstraints27.gridy = 0;
			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
			gridBagConstraints28.gridx = 0;
			gridBagConstraints28.ipadx = 0;
			gridBagConstraints28.ipady = 150;
			gridBagConstraints28.weighty = 1.0D;
			gridBagConstraints28.fill = GridBagConstraints.BOTH;
			gridBagConstraints28.weightx = 1.0D;
			gridBagConstraints28.gridy = 1;
			styleOptionsPanel = new JPanel();
			styleOptionsPanel.setLayout(new GridBagLayout());
			//styleOptionsPanel.setSize(new Dimension(173, 321));
			styleOptionsPanel.setPreferredSize(new Dimension(210, 300));
			styleOptionsPanel.add(getColorOptionsPanel(), gridBagConstraints28);
			styleOptionsPanel.add(getStylePanel(), gridBagConstraints27);
			styleOptionsPanel.add(getSpacerPanel1(), gridBagConstraints55);
		}
		return styleOptionsPanel;
	}
	
	//TODO make these two methods better
	private void hideStyleOptionsPanel(){
		if(TabPane.getTabCount() == 3)
			TabPane.removeTabAt(1);
	}
	
	private void showStyleOptionsPanel(){
		TabPane.insertTab("Style", null, getStyleOptionsPanel(), null, 1);
	}

	/**
	 * This method initializes stylePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getStylePanel() {
		if (stylePanel == null) {
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.gridx = 0;
			gridBagConstraints24.gridwidth = 4;
			gridBagConstraints24.fill = GridBagConstraints.BOTH;
			gridBagConstraints24.weighty = 1.0D;
			gridBagConstraints24.gridheight = 1;
			gridBagConstraints24.weightx = 1.0D;
			gridBagConstraints24.gridy = 4;
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.gridx = 3;
			gridBagConstraints23.gridy = 3;
			mptLabel2 = new JLabel();
			mptLabel2.setText("pt");
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.gridx = 3;
			gridBagConstraints22.gridy = 2;
			mptLabel1 = new JLabel();
			mptLabel1.setText("pt");
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 0;
			gridBagConstraints21.anchor = GridBagConstraints.WEST;
			gridBagConstraints21.weightx = 0.0D;
			gridBagConstraints21.weighty = 0.0D;
			gridBagConstraints21.gridy = 1;
			styleLabel = new JLabel();
			styleLabel.setText("Font Style");
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.gridx = 0;
			gridBagConstraints20.anchor = GridBagConstraints.WEST;
			gridBagConstraints20.weighty = 0.0D;
			gridBagConstraints20.gridy = 3;
			minfontLabel = new JLabel();
			minfontLabel.setText("Min Font Size");
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.gridx = 0;
			gridBagConstraints19.anchor = GridBagConstraints.WEST;
			gridBagConstraints19.weighty = 0.0D;
			gridBagConstraints19.gridy = 2;
			maxfontLabel = new JLabel();
			maxfontLabel.setText("Max Font Size");
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = 2;
			gridBagConstraints14.gridwidth = 1;
			gridBagConstraints14.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints14.gridy = 3;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 2;
			gridBagConstraints13.gridwidth = 1;
			gridBagConstraints13.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints13.gridy = 2;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 2;
			gridBagConstraints12.gridy = 1;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.gridy = 1;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints10.gridy = 0;
			gridBagConstraints10.weightx = 1.0;
			gridBagConstraints10.gridwidth = 3;
			gridBagConstraints10.anchor = GridBagConstraints.CENTER;
			gridBagConstraints10.gridx = 1;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.anchor = GridBagConstraints.WEST;
			gridBagConstraints9.weightx = 0.0D;
			gridBagConstraints9.weighty = 0.0D;
			gridBagConstraints9.gridy = 0;
			fontLabel = new JLabel();
			fontLabel.setText("Font");
			stylePanel = new JPanel();
			stylePanel.setLayout(new GridBagLayout());
			stylePanel.setPreferredSize(new Dimension(180, 130));
			stylePanel.setBorder(BorderFactory.createTitledBorder(null, "Text Options", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			stylePanel.setVisible(true);
			stylePanel.setMinimumSize(new Dimension(180, 110));
			stylePanel.add(fontLabel, gridBagConstraints9);
			stylePanel.add(getFontComboBox(), gridBagConstraints10);
			stylePanel.add(getBoldBox(), gridBagConstraints11);
			stylePanel.add(getItalicBox(), gridBagConstraints12);
			stylePanel.add(getMaxFontSpinner(), gridBagConstraints13);
			stylePanel.add(getMinFontSpinner(), gridBagConstraints14);
			stylePanel.add(maxfontLabel, gridBagConstraints19);
			stylePanel.add(minfontLabel, gridBagConstraints20);
			stylePanel.add(styleLabel, gridBagConstraints21);
			stylePanel.add(mptLabel1, gridBagConstraints22);
			stylePanel.add(mptLabel2, gridBagConstraints23);
			//stylePanel.add(getFontPreviewPanel(), gridBagConstraints24);
		}
		return stylePanel;
	}

	/**
	 * This method initializes colorOptionsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getColorOptionsPanel() {
		if (colorOptionsPanel == null){
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.fill = GridBagConstraints.BOTH;
			gridBagConstraints8.gridy = 0;
			gridBagConstraints8.weightx = 1.0;
			gridBagConstraints8.weighty = 1.0;
			gridBagConstraints8.gridx = 3;
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();

			gridBagConstraints31.fill = GridBagConstraints.BOTH;
			gridBagConstraints31.gridy = 2;
			gridBagConstraints31.weightx = 1.0;
			gridBagConstraints31.weighty = 1.0;
			gridBagConstraints31.gridx = 2;

			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
			gridBagConstraints40.gridx = 1;
			gridBagConstraints40.gridwidth = 4;
			gridBagConstraints40.fill = GridBagConstraints.BOTH;
			gridBagConstraints40.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints40.weightx = 1.0D;
			gridBagConstraints40.ipady = 15;
			gridBagConstraints40.gridy = 2;
			GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
			gridBagConstraints35.gridx = 1;
			gridBagConstraints35.fill = GridBagConstraints.BOTH;
			gridBagConstraints35.gridwidth = 3;
			gridBagConstraints35.gridheight = 1;
			gridBagConstraints35.weightx = 1.0D;
			gridBagConstraints35.weighty = 1.0D;
			gridBagConstraints35.insets = new Insets(3, 0, 0, 0);
			gridBagConstraints35.gridy = 1;
			colorOptionsPanel = new JPanel();
			colorOptionsPanel.setLayout(new GridBagLayout());
			colorOptionsPanel.setBorder(BorderFactory.createTitledBorder(null, "Color Scheme Options", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			colorOptionsPanel.setPreferredSize(new Dimension(80, 120));
			colorOptionsPanel.setMinimumSize(new Dimension(245, 120));
			colorOptionsPanel.add(getSwatchArray(), gridBagConstraints35);
			colorOptionsPanel.add(getCustomSwatchArray(), gridBagConstraints40);
			colorOptionsPanel.add(getColorTabbedPane(), gridBagConstraints8);
		}
		return colorOptionsPanel;
	}

	/**
	 * This method initializes fontComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getFontComboBox() {
		if (fontComboBox == null) {
			fontComboBox = new JComboBox();
			for(OpgFont font:OpgFont.values()){
				fontComboBox.addItem(font);
			}
			fontComboBox.setPreferredSize(new Dimension(50, 25));
			fontComboBox.setToolTipText("Select the font you would like to use for your chart");
			fontComboBox.setSelectedItem(session.getOpgOptions().getFont());
			fontComboBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!maskEvents){
						session.getOpgOptions().setFont((OpgFont) fontComboBox.getSelectedItem());
						refresh();
					}
				}
			});
		}
		return fontComboBox;
	}

	/**
	 * This method initializes advancedOptionsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getAdvancedOptionsPanel() {
		if (advancedOptionsPanel == null) {
			advancedOptionsPanel = new JPanel();
			advancedOptionsPanel.setLayout(new BorderLayout());
		}
		return advancedOptionsPanel;
	}
	
	
	

	/**
	 * This method initializes boldBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getBoldBox() {
		if (boldBox == null) {
			boldBox = new JCheckBox();
			boldBox.setText("Bold");
			boldBox.setToolTipText("Select whether or not you want your text to be bold");
			boldBox.setSelected(session.getOpgOptions().isBold());
			boldBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!maskEvents){
						session.getOpgOptions().setBold(boldBox.isSelected());
						refresh();
					}
				}
			});
		}
		return boldBox;
	}

	/**
	 * This method initializes italicBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getItalicBox() {
		if (italicBox == null) {
			italicBox = new JCheckBox();
			italicBox.setText("Italic");
			italicBox.setToolTipText("Select whether or not you want your text to be italic");
			italicBox.setSelected(session.getOpgOptions().isItalic());
			italicBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!maskEvents){
						session.getOpgOptions().setItalic(italicBox.isSelected());
						refresh();
					}
				}
			});
		}
		return italicBox;
	}

	/**
	 * This method initializes maxFontSpinner	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	//TODO is this used anymore?
	private JSpinner getMaxFontSpinner() {
		if (maxFontSpinner == null) {
			FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
			spinnerModel.setMaxvalue(200);
			spinnerModel.setMinvalue(3);
			spinnerModel.setValue(12f);
			maxFontSpinner = new JSpinner(spinnerModel);
			maxFontSpinner.setPreferredSize(new Dimension(40, 20));
			maxFontSpinner.setToolTipText("Choose the maximum font size you would like to see on the chart");
			maxFontSpinner.setEditor(new FloatEditor(maxFontSpinner));
			maxFontSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (!maskEvents) {
						Float newval = (Float) maxFontSpinner.getValue();
						Float minval = (Float) minFontSpinner.getValue();
						if(newval.compareTo(minval) < 0) minFontSpinner.setValue(newval);
						session.getOpgOptions().setMaxFontSize(newval);
						refresh();
					}
				}
			});
		}
		return maxFontSpinner;
	}

	/**
	 * This method initializes minFontSpinner	
	 * 	
	 * @return javax.swing.JSpinner	
	 */
	//TODO is this used anymore?
	private JSpinner getMinFontSpinner() {
		if (minFontSpinner == null) {
			FloatSpinnerModel spinnerModel = new FloatSpinnerModel();
			spinnerModel.setMaxvalue(100);
			spinnerModel.setMinvalue(3);
			spinnerModel.setValue(3f);
			minFontSpinner = new JSpinner(spinnerModel);
			minFontSpinner.setValue(session.getOpgOptions().getMinFontSize());
			minFontSpinner.setPreferredSize(new Dimension(40, 20));
			minFontSpinner.setToolTipText("Choose the minimum font size you would like to see on the chart");
			minFontSpinner.setEditor(new FloatEditor(minFontSpinner));
			minFontSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (!maskEvents) {
						Float newval = (Float) minFontSpinner.getValue();
						Float maxval = (Float) maxFontSpinner.getValue();
						if(newval.compareTo(maxval) > 0) maxFontSpinner.setValue(newval);
						session.getOpgOptions().setMinFontSize(newval);
						refresh();
					}
				}
			});
		}
		return minFontSpinner;
	}


	/**
	 * This method initializes minTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getMinTextArea() {
		if (minTextArea == null) {
			minTextArea = new JTextArea();
			minTextArea.setPreferredSize(new Dimension(0, 10));
			minTextArea.setText("Here is the min size");
		}
		return minTextArea;
	}

	/**
	 * This method initializes maxTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getMaxTextArea() {
		if (maxTextArea == null) {
			maxTextArea = new JTextArea();
			maxTextArea.setText("Here is the max size");
			maxTextArea.setPreferredSize(new Dimension(0, 10));
		}
		return maxTextArea;
	}

	/**
	 * This method initializes descSchemeBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getDescSchemeBox() {
		if (descSchemeBox == null) {
			descSchemeBox = new JComboBox();
			descSchemeBox.setMinimumSize(new Dimension(31, 20));
			descSchemeBox.setPreferredSize(new Dimension(31, 20));
			descSchemeBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!maskEvents && descSchemeBox.getSelectedItem() != null){	
						for (int i = 0; i < session.currentPage().getChartCount(); i++){
							ChartOptions options = session.currentPage().getOptions(i);
							Individual tempRoot = options.getRoot();
							options.setDescScheme((ColorScheme) descSchemeBox.getSelectedItem());
							options.getDescScheme().clearTree();
							if(tempRoot.fams.size() != 0 && tempRoot.fams.get(0) != null){
								Individual spouse = (tempRoot.gender == Gender.MALE) ? tempRoot.fams.get(0).wife : tempRoot.fams.get(0).husband;
								if(options.isIncludeSpouses() && spouse != null) options.getDescScheme().colorTree(spouse, ColorScheme.colorup);
							}
							options.getDescScheme().colorTree(tempRoot, ColorScheme.colordown);
							fillColorTables();
							refresh();
						}
					}
				}
			});
			
		}
		return descSchemeBox;
	}

	/**
	 * This method initializes zoomToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getZoomToolBar() {
		if (zoomToolBar == null) {
			zoomLabel = new JLabel();
			zoomLabel.setText("Zoom:");
			zoomLabel.setMaximumSize(new Dimension(45, 16));
			zoomLabel.setMinimumSize(new Dimension(45, 16));
			zoomLabel.setPreferredSize(new Dimension(45, 16));
			zoomLabel.setHorizontalAlignment(SwingConstants.CENTER);
			zoomLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			zoomToolBar = new JToolBar();
			zoomToolBar.setMinimumSize(new Dimension(141, 30));
			zoomToolBar.setName("Zoom Tool Bar");
			zoomToolBar.setPreferredSize(new Dimension(200, 24));
			
			zoomToolBar.add(getArrowToggleButton());
			zoomToolBar.add(getHandToggleButton());
			zoomToolBar.add(getZinToggleButton());
			zoomToolBar.add(getZoutToggleButton());
			zoomToolBar.add(getZoomFitButton());
			zoomToolBar.add(zoomLabel);
			zoomToolBar.add(getZoomField());
			
		}
		return zoomToolBar;
	}

	/**
	 * This method initializes handToggleButton	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getHandToggleButton() {
		if (handToggleButton == null) {
			handToggleButton = new JToggleButton();
			handToggleButton.setIcon(new ImageIcon(getClass().getResource("/edu/byu/cs/roots/opg/image/hand.png")));
			handToggleButton.setMaximumSize(new Dimension(20, 20));
			handToggleButton.setPreferredSize(new Dimension(20, 20));
			handToggleButton.setSelected(true);
			handToggleButton.setToolTipText("Click here to switch to the hand cursor");
			handToggleButton.setMinimumSize(new Dimension(20, 20));
			handToggleButton.addMouseListener(new MouseListener() {
				public void mousePressed(MouseEvent e) {}
				public void mouseClicked(MouseEvent arg0) {
					click();
				}
				public void mouseEntered(MouseEvent arg0) {}
				public void mouseExited(MouseEvent arg0) {}
				public void mouseReleased(MouseEvent arg0) {
					click();
				}
				private void click(){
                                    /*
                                     * Edited By: spencer Hoffa
                                     * Edited On: 11/13/2012
                                     * 
                                     * Original code is commented out
                                     */
					//getArrowToggleButton().setSelected(false);
					//getZoutToggleButton().setSelected(false);
					//getZinToggleButton().setSelected(false);
					//getHandToggleButton().setSelected(true);
					//session.cursor = OpgCursor.MOVE;
                                        setMoveButton();
                                     //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                                     //     End Edit by Spencer Hoffa
                                     ///////////////////////////////////////////
				}
			});
		}
		return handToggleButton;
	}

	/**
	 * This method initializes zinToggleButton	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getZinToggleButton() {
		if (zinToggleButton == null) {
			zinToggleButton = new JToggleButton();
			zinToggleButton.setIcon(new ImageIcon(getClass().getResource("/edu/byu/cs/roots/opg/image/icon_zoom_in.png")));
			zinToggleButton.setMaximumSize(new Dimension(20, 20));
			zinToggleButton.setMinimumSize(new Dimension(20, 20));
			zinToggleButton.setToolTipText("Click here to switch to the zoom out cursor");
			zinToggleButton.setPreferredSize(new Dimension(20, 20));
			zinToggleButton.addMouseListener(new MouseListener() {
				public void mousePressed(MouseEvent e) {}
				public void mouseClicked(MouseEvent arg0) {
					click();
				}
				public void mouseEntered(MouseEvent arg0) {}
				public void mouseExited(MouseEvent arg0) {}
				public void mouseReleased(MouseEvent arg0) {
					click();
				}
				private void click(){
                                    /*
                                     * Edited By: spencer Hoffa
                                     * Edited On: 11/13/2012
                                     * 
                                     * Original code is commented out
                                     */
					//getArrowToggleButton().setSelected(false);
					//getZoutToggleButton().setSelected(false);
					//getZinToggleButton().setSelected(true);
					//getHandToggleButton().setSelected(false);
					//session.cursor = OpgCursor.Z_IN;
                                        if (simplifiedMode)
                                        {
                                            getVisualPreviewPanel().zoomCenter(1.2*getVisualPreviewPanel().zoom);
                                            setMoveButton();
                                            refresh();
                                        }
                                        else
                                        {
                                            setZoomInButton();
                                        }
                                     //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                                     //     End Edit by Spencer Hoffa
                                     ///////////////////////////////////////////
				}
			});
		}
		return zinToggleButton;
	}

	/**
	 * This method initializes zoutToggleButton	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getZoutToggleButton() {
		if (zoutToggleButton == null) {
			zoutToggleButton = new JToggleButton();
			zoutToggleButton.setIcon(new ImageIcon(getClass().getResource("/edu/byu/cs/roots/opg/image/icon_zoom_out.png")));
			zoutToggleButton.setMaximumSize(new Dimension(20, 20));
			zoutToggleButton.setPreferredSize(new Dimension(20, 20));
			zoutToggleButton.setToolTipText("Click here to switch to the zoom in cursor");
			zoutToggleButton.setMinimumSize(new Dimension(20, 20));
			zoutToggleButton.addMouseListener(new MouseListener() {
				public void mousePressed(MouseEvent e) {}
				public void mouseClicked(MouseEvent arg0) {
					click();
				}
				public void mouseEntered(MouseEvent arg0) {}
				public void mouseExited(MouseEvent arg0) {}
				public void mouseReleased(MouseEvent arg0) {
					click();
				}
				private void click(){
                                    /*
                                     * Edited By: spencer Hoffa
                                     * Edited On: 11/13/2012
                                     * 
                                     * Original code is commented out
                                     */
					//getArrowToggleButton().setSelected(false);
					//getZoutToggleButton().setSelected(true);
					//getZinToggleButton().setSelected(false);
					//getHandToggleButton().setSelected(false);
					//session.cursor = OpgCursor.Z_OUT;
                                        if (simplifiedMode)
                                        {
                                            getVisualPreviewPanel().zoomCenter(0.8*getVisualPreviewPanel().zoom);
                                            setMoveButton();
                                            refresh();
                                        }
                                        else
                                        {
                                            setZoomOutButton();
                                        }
                                     //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                                     //     End Edit by Spencer Hoffa
                                     ///////////////////////////////////////////
				}
			});
		}
		return zoutToggleButton;
	}

	/**
	 * This method initializes toolPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getToolPanel() {
		if (toolPanel == null) {
			toolPanel = new JPanel();
			toolPanel.setLayout(new BoxLayout(getToolPanel(), BoxLayout.X_AXIS));
			toolPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			//toolPanel.add(getMenuToolBar(), null);
			toolPanel.add(getZoomToolBar(), null);
                        
                        /*
                         * Edited By: SPENCER HOFFA
                         * Edited On: 11/13/2012
                         */
                        toolPanel.add(getRootPanel());
                        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                        //      END EDIT BY SPENCER HOFFA
                        ////////////////////////////////////////////////////////
                        
			//TODO picture support!
			if(TEXT_PIC_ENABLED) toolPanel.add(getInsertToolBar(), null);
		}
		return toolPanel;
	}



	/**
	 * This method initializes aboutMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("About");
			aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JOptionPane.showMessageDialog(getJFrame(),
						    "One Page Genealogy Project \n"+
						    "Brigham Young University\n"+
						    "Computer Science Dept\n"+
						    Version,
						    "About OnePage Genealogy",
						    JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getJFrame().getIconImage())
						    );

				}
			});
			
		}
		return aboutMenuItem;
	}

	/**
	 * This method initializes TopicsMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getTopicsMenuItem() {
		if (TopicsMenuItem == null) {
			TopicsMenuItem = new JMenuItem();
			TopicsMenuItem.setText("Online Help");
			TopicsMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					BrowserLauncher.openURL(helpUrl);
					/*
					JOptionPane.showMessageDialog(getJFrame(),
						    "Sorry the help topics section is still under developement",
						    "Topics",
						    JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getJFrame().getIconImage())
						    );
						    */
				}
			});
		}
		return TopicsMenuItem;
	}
	
	/**
	 * This method initializes aboutMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMarginsMenuItem() {
		if (marginsMenuItem == null) {
			marginsMenuItem = new JMenuItem();
			marginsMenuItem.setText("Margin Sizes");
			marginsMenuItem.addActionListener(new java.awt.event.ActionListener() {
				
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Dimension screenSize;		
					try {
						Toolkit tk = Toolkit.getDefaultToolkit();
						screenSize = tk.getScreenSize();
					} catch (AWTError awe) {
						screenSize = new Dimension(640, 480);
					}
					Dialog margins = new MarginEditorDialog(session.getOpgOptions().getChartMargins(), thisReference);
					int frameX = screenSize.width / 2 - margins.getWidth() / 2;
					int frameY = screenSize.height / 2 - margins.getHeight() / 2;
					margins.setBounds(frameX, frameY, margins.getWidth(), margins.getHeight());
					margins.setVisible(true);
					
				}
			});
			
		}
		return marginsMenuItem;
	}
	
	/**
	 * This method initializes aboutMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 * 
	 * Removed By: Spencer HOffa
	 * Removed On: 2/14/2014
	 */
	/*
	private JMenuItem getUpdatePurchasesMenuItem() {
		if (updatePurchasesMenuItem == null) {
			updatePurchasesMenuItem = new JMenuItem();
			updatePurchasesMenuItem.setText("Update Purchased Charts");
			updatePurchasesMenuItem.addActionListener(new java.awt.event.ActionListener() {
				
				public void actionPerformed(java.awt.event.ActionEvent e) {
					updateUserSubscription();
					refresh();
				}
			});
			
		}
		return updatePurchasesMenuItem;
	}
	//*/

	/**
	 * This method initializes helpMenuSeparator	
	 * 	
	 * @return javax.swing.JSeparator	
	 */
	private JSeparator getHelpMenuSeparator() {
		if (helpMenuSeparator == null) {
			helpMenuSeparator = new JSeparator();
		}
		return helpMenuSeparator;
	}
	
	/**
	 * This method initializes menuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getMenuBar() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getFileMenu());
			menuBar.add(getOptionsMenu());
			menuBar.add(getViewMenu());
			menuBar.add(getHelpMenu());
			menuBar.add(getNFSMenu());
		}		
		return menuBar;
	}
	
	
	/**
	 * This method initializes fileMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu("File");
			fileMenu.add(getNewMenuItem());
			/*
			 * Removed By: Spencer HOffa
			 * Removed ON: 2/15/2013
			 * 
			 * These options are no longer needed.
			 */
			//fileMenu.add(getOpenMenuItem());
			//fileMenu.add(getSaveMenuItem());
			//fileMenu.add(getSaveAsMenuItem());
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
			///////////////////////////////////////////////////
			fileMenu.add(getSaveAsPDFMenuItem());
			fileMenu.add(getCloseMenuItem());
			//fileMenu.add(getJSeparator());//Moved to print endabled
			//TODO removed order prints option
//			fileMenu.add(getBrowserMenuItem());
			if (session != null && session.record != null && !session.record.isNFS())
				fileMenu.add(getDownloadMenuItem());
			if(printEnabled){
				fileMenu.add(getJSeparator());
//				fileMenu.add(getJSeparator2());
				fileMenu.add(getPageSetupMenuItem());
				fileMenu.add(getPrintMenuItem());
			}
			if (!isMac())
			{
				fileMenu.add(getJSeparator1());
				fileMenu.add(getQuitMenuItem());
			}
		}
		return fileMenu;
	}
	
	/**
	 * This method initializes fileMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getViewMenu() {
		if (viewMenu == null) {
			viewMenu = new JMenu("View");
			viewMenu.add(getRulerCheckBox());
			/**
			 * Added By: Spencer Hoffa
			 * Added On: 2/12/2013
			 */
			viewMenu.add( getShowAdvancedOptionsCheckBox() );
			//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
			//		End Addition by: Spencer Hoffa
			/////////////////////////////////////////////////
			//viewMenu.add(getYardstickCheckBox());
		}
		return viewMenu;
	}
	
	/**
	 * This method initializes helpMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu("Help");
			helpMenu.add(getTopicsMenuItem());
			/**
			 * Added By: Spencer Hoffa
			 * Added On: 2/5/2013
			 * 
			 * Adding the Donate menu button.
			 */
			helpMenu.add( getDonateMenuItem() );
			/////////////////////////////////////////////////////
			helpMenu.add(getHelpMenuSeparator());
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}
	
	//TODO: finish this! (Get the update bar working)
	private JMenu getNFSMenu() {
		if (NFSMenu == null) {
			NFSMenu = new JMenu("FamilySearch");
			NFSMenu.add(getDownloadMenuItem());
			//NFSMenu.add(getUpdateMenuItem());
		}
		return NFSMenu;
	}
	
	private JMenu getOptionsMenu() {
		if (optionsMenu == null) {
			optionsMenu = new JMenu("Options");
			optionsMenu.add(getMarginsMenuItem());
			optionsMenu.add(getAdvancedOptionsCheckBox());
			optionsMenu.add(getInsertPictureMenuItem());
			//optionsMenu.add(getUpdatePurchasesMenuItem());//Removed by: spencer HOffa on 2/15/2013
		}
		return optionsMenu;
	}

	/**
	 * @return true if the OS is Mac OS X
	 */
	private boolean isMac() {
		return System.getProperty("mrj.version") != null;
	}

	/**
	 * This method initializes spacerPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSpacerPanel() {
		if (spacerPanel == null) {
			spacerPanel = new JPanel();
			spacerPanel.setLayout(new GridBagLayout());
		}
		return spacerPanel;
	}

	/**
	 * This method initializes spacerPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSpacerPanel1() {
		if (spacerPanel1 == null) {
			spacerPanel1 = new JPanel();
			spacerPanel1.setLayout(new GridBagLayout());
		}
		return spacerPanel1;
	}

	/**
	 * This method initializes zoomField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getZoomField() {
		if (zoomField == null) {
			zoomField = new JTextField();
			zoomField.setMaximumSize(new Dimension(70, 20));
			zoomField.setToolTipText("The current zoom factor");
			zoomField.setSize(new Dimension(70, 20));
			zoomField.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!maskEvents){
						double zoom;
						try {
//							zoom = (NumberFormat.getPercentInstance().parse(zoomField.getText())).doubleValue();
//							getVisualPreviewPanel().setZoom(zoom);
							zoom = Double.parseDouble(zoomField.getText())*.01;
							if (zoom < 0)
								throw new Exception("Illegal zoom value");
							getVisualPreviewPanel().setZoom(zoom);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						refresh();
					}
				}
			});
			getVisualPreviewPanel().updateZoomBox();
		}
		return zoomField;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
         * Edited By: Spencer Hoffa
         * Edited On: 11/13/2012
         * My edit was to remove this method.
	 */
	/*private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setPreferredSize(new Dimension(600, 28));
			jToolBar.setMinimumSize(new Dimension(0, 0));
			jToolBar.add(getRootPanel());
		}
		return jToolBar;
	}*/

        /**
         * This will initialize the ViewIndividualInfoButton
         * This method was added to keep consistency in the program.
         * Added By: Spencer Hoffa
         * Added on: 10/31/2012
         */
        private JButton getViewInfo()
        {
            if (viewIndiInfoButton == null)
            {
                viewIndiInfoButton = new JButton("View Info");
                viewIndiInfoButton.addActionListener(new ViewIndividualInfoActionListener());
                        /*new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Dimension screenSize;		
					try {
						Toolkit tk = Toolkit.getDefaultToolkit();
						screenSize = tk.getScreenSize();
					} catch (AWTError awe) {
						screenSize = new Dimension(640, 480);
					}
					
					//Dialog dlgPreOrder = new PreOrderDialog(session, getJFrame());
					Dialog indiInfo = new IndiInfo((Individual)rootComboBox.getSelectedItem());
					int frameX = screenSize.width / 2 - indiInfo.getWidth() / 2;
					int frameY = screenSize.height / 2 - indiInfo.getHeight() / 2;
					indiInfo.setBounds(frameX, frameY, indiInfo.getWidth(), indiInfo.getHeight());
					indiInfo.setVisible(true);
				}
			});*/
            }
            return viewIndiInfoButton;
        }
        
	/**
	 * This method initializes rootPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getRootPanel() {
            /*
             * Edited By Spencer Hoffa
             * Edited On: 10/31/2012
             * 
             * Original code is commented out.
             * Extracted the creation of the View Info Button
             */
		if (rootPanel == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			
			gridBagConstraints8.anchor = GridBagConstraints.CENTER;
			gridBagConstraints8.gridy = 0;
			gridBagConstraints8.gridx = 6;
			gridBagConstraints7.anchor = GridBagConstraints.CENTER;
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.gridx = 4;
			gridBagConstraints6.anchor = GridBagConstraints.CENTER;
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.gridx = 2;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.anchor = GridBagConstraints.CENTER;
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.anchor = GridBagConstraints.CENTER;
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.insets = new Insets(0, 10, 0, 0);
			setRootButton = new JButton();
			setRootButton.setText("Set Tree Beginning");
			//viewIndiInfoButton = new JButton("View Info");
			rootPanel = new JPanel();
			rootPanel.setLayout(new GridBagLayout());
			//rootPanel.setPreferredSize(new Dimension(400, 24));
			//rootPanel.setMinimumSize(new Dimension(120, 25));
			rootPanel.add(setRootButton, gridBagConstraints4);
            /*
             * Edited By: Spencer Hoffa
             * Edited On: 11/14/2012
             * 
             * Removed because we decided we don't need it.
             */
			//rootPanel.add(getViewInfo() /*viewIndiInfoButton*/, gridBagConstraints8);
			//rootPanel.add(getRootComboBox(), gridBagConstraints5);
			rootPanel.add(getIncludeSpouseCheckBox(), gridBagConstraints6);
			rootPanel.add(getAncestorCheckBox(), gridBagConstraints7);
			
			setRootButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!maskEvents){
//						log.debug("Root action performed");
                                            //ChooseRootDialog chooseRootThingy = new ChooseRootDialog(jFrame);
                                            getChooseRootDialog();
                                            updateSelectionCheckboxes(); //see if this works
						/*root = (Individual) rootComboBox.getSelectedItem();
						updateSelectionCheckboxes();
						resetPresetPanel();
						rootSelect();*/
					}
				}
			}
			);
			
			/*viewIndiInfoButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Dimension screenSize;		
					try {
						Toolkit tk = Toolkit.getDefaultToolkit();
						screenSize = tk.getScreenSize();
					} catch (AWTError awe) {
						screenSize = new Dimension(640, 480);
					}
					
					//Dialog dlgPreOrder = new PreOrderDialog(session, getJFrame());
					Dialog indiInfo = new IndiInfo((Individual)rootComboBox.getSelectedItem());
					int frameX = screenSize.width / 2 - indiInfo.getWidth() / 2;
					int frameY = screenSize.height / 2 - indiInfo.getHeight() / 2;
					indiInfo.setBounds(frameX, frameY, indiInfo.getWidth(), indiInfo.getHeight());
					indiInfo.setVisible(true);
				}
			});*/
			
                        /*
                         * Edited By Spencer Hoffa
                         * Edited On 11/7/2012
                         * 
                         * Original code commented out.
                         * 
                         * Here they assumed that the variable rootComboBox was
                         * already set.  With changes this became an invalid 
                         * assumption.  So I changed rootComboBox to 
                         * getRootComboBox which then initialized the rootComboBox
                         * variable.
                         */
			/*rootComboBox*/getRootComboBox().addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!maskEvents ){
						System.out.print(e);
						updateSelectionCheckboxes();
					}
				}
			});
		}
		return rootPanel;
            //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
            //      END EDIT by Spencer hoffa
            ///////////////////////////////////////////////////////////
	}
	
	private void updateSelectionCheckboxes(){
		OpgOptions opgOptions = session.getOpgOptions();
		if(root == (Individual)rootComboBox.getSelectedItem())
			drawAncestorCheckBox.setVisible(false);
		else
			drawAncestorCheckBox.setVisible(true && session.config.advancedOptions);
		if(rootComboBox.getSelectedItem() != null && opgOptions.getCollapsedList() != null) //if this isn't here, crashes on "New" or "Open"
			drawAncestorCheckBox.setSelected(!opgOptions.isCollapsed((Individual)rootComboBox.getSelectedItem()));
		if(((Individual)rootComboBox.getSelectedItem()) == root){
			
			includeSpouseCheckBox.setVisible(true);
		}
		else{
			
			includeSpouseCheckBox.setVisible(false);
		}
	}

	/**
	 * This method initializes rootComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getRootComboBox() {
		if (rootComboBox == null) {
			rootComboBox = new JComboBox();
			rootComboBox.setPreferredSize(new Dimension(20, 20));
			rootComboBox.setToolTipText("Select who you would select as the root individual");
			AutoCompletion.enable(rootComboBox);
			rootComboBox.setMinimumSize(new Dimension(40,20));
                        
		}
		return rootComboBox;
	}

	/**
	 * This method initializes includeSpouseCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getIncludeSpouseCheckBox() {
		if (includeSpouseCheckBox == null) {
			includeSpouseCheckBox = new JCheckBox();
			includeSpouseCheckBox.setEnabled(false);
			includeSpouseCheckBox.setToolTipText("Use this check box to indicate whether or not you want to include the spouse of the root individual in the chart.");
			includeSpouseCheckBox.setText("Draw Spouse Tree");
			includeSpouseCheckBox.setSelected(false);
			includeSpouseCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!maskEvents){
//						log.debug("Include Spouse");
                                            //JOptionPane.showMessageDialog(getJFrame(),"Include Spouse clicked");
						resetPresetPanel();
						rootSelect();
					}
				}
			});
			
		}
		return includeSpouseCheckBox;
	}
	
	private JCheckBox getAncestorCheckBox() {
		if (drawAncestorCheckBox == null) {
			drawAncestorCheckBox = new JCheckBox();
			drawAncestorCheckBox.setEnabled(false);
			drawAncestorCheckBox.setToolTipText("Use this check box to not draw certain ancestry lines.");
			drawAncestorCheckBox.setText("A");
			drawAncestorCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!maskEvents){
						session.getOptions().setDrawTreeHasChanged(true);
						OpgOptions opgOptions = session.getOpgOptions();
						Individual temp = ((Individual) rootComboBox.getSelectedItem());
						if(drawAncestorCheckBox.isSelected())
							opgOptions.removeCollapsed(temp);
						else
						{
							opgOptions.addCollapsed(temp);
							session.addMaker(session.getOpgOptions().getChartType(), temp, 0);
						}
						resetPresetPanel();
						rootSelect();
					}
				}
			});
			
		}
		return drawAncestorCheckBox;
	}
	
	
	

	/**
	 * This method initializes jToolBar1	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.setFloatable(false);
			jToolBar1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			jToolBar1.add(getGedComPanel());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes gedComPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getGedComPanel() {
		if (gedComPanel == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.anchor = GridBagConstraints.SOUTH;
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
			gedcomPathTextBox = new JLabel();
			gedcomPathTextBox.setPreferredSize(new Dimension(400, 16));
			gedcomPathTextBox.setToolTipText("This displays the currently loaded gedcom file");
			gedcomPathTextBox.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if(session.gedfile == null && session.state == SessionState.view) getGedPopupMenu().show(e.getComponent(), e.getX(), e.getY());
				}
				public void mousePressed(java.awt.event.MouseEvent e) {
				}
				public void mouseReleased(java.awt.event.MouseEvent e) {
				}
				public void mouseEntered(java.awt.event.MouseEvent e) {
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
				}
			});
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.anchor = GridBagConstraints.SOUTH;
			gridBagConstraints3.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.fill = GridBagConstraints.NONE;
			jLabel2 = new JLabel();
			jLabel2.setText("Gedcom File:  ");
			jLabel2.setVerticalTextPosition(SwingConstants.CENTER);
			jLabel2.setVerticalAlignment(SwingConstants.CENTER);
			gedComPanel = new JPanel();
			gedComPanel.setLayout(new GridBagLayout());
			gedComPanel.add(jLabel2, gridBagConstraints3);
			gedComPanel.add(gedcomPathTextBox, gridBagConstraints2);
		}
		return gedComPanel;
	}

	/**
	 * This method initializes rulerCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBoxMenuItem getRulerCheckBox() {
		if (rulerCheckBox == null) {
			rulerCheckBox = new JCheckBoxMenuItem("Show Rulers");
			//rulerCheckBox.setToolTipText("Check this box if you want the ruler to appear on the page");
//			rulerCheckBox.setSelected(session.config.showRuler);
			rulerCheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (!maskEvents) {
						session.config.showRuler = rulerCheckBox.isSelected();
						refresh();
					}
				}
			});
		}
		return rulerCheckBox;
	}
	
	/**
	 * Added By: Spencer HOffa
	 * Added On: 2/12/2013
	 * 
	 * Get the Show Advanced Options checkbox menu Item.
	 * 
	 * @return javax.swint.JCheckBox
	 */
	private JCheckBoxMenuItem getShowAdvancedOptionsCheckBox()
	{
		if (showAdvancedOptionsCheckBox == null)
		{
			showAdvancedOptionsCheckBox = new JCheckBoxMenuItem("Show Advanced Options");
			showAdvancedOptionsCheckBox.setSelected(false);
			showAdvancedOptionsCheckBox.addItemListener(
					new java.awt.event.ItemListener()
					{
						@Override
						public void itemStateChanged(ItemEvent e) 
						{	
							//Turn on Advanced Options
							setSimplifiedGUI( !showAdvancedOptionsCheckBox.isSelected() );
						}
					}
					);
		}
		return showAdvancedOptionsCheckBox;
	}
	
	/**
	 * This method initializes advancedOptionsCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBoxMenuItem getAdvancedOptionsCheckBox() {
		if (advancedOptionsCheckBox == null) {
			advancedOptionsCheckBox = new JCheckBoxMenuItem("Allow Advanced Options");
			//rulerCheckBox.setToolTipText("Check this box if you want the ruler to appear on the page");
//			advancedOptionsCheckBox.setSelected(session.config.advancedOptions);
			advancedOptionsCheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (!maskEvents) {
						session.config.advancedOptions = advancedOptionsCheckBox.isSelected();
						refresh();
					}
				}
			});
		}
		return advancedOptionsCheckBox;
	}

	/**
	 * This method initializes zoomFitButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getZoomFitButton() {
		if (zoomFitButton == null) {
			zoomFitButton = new JButton();
			zoomFitButton.setIcon(new ImageIcon(getClass().getResource("/edu/byu/cs/roots/opg/image/ftwin.png")));
			zoomFitButton.setPreferredSize(new Dimension(20, 20));
			zoomFitButton.setToolTipText("Click here to automaticaly zoom and center your chart");
			zoomFitButton.setMaximumSize(new Dimension(20, 20));
			zoomFitButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getVisualPreviewPanel().fitWidth();
				}
			});
		}
		return zoomFitButton;
	}

	/**
	 * This method initializes jSeparator1	
	 * 	
	 * @return javax.swing.JSeparator	
	 */
	private JSeparator getJSeparator1() {
		if (jSeparator1 == null) {
			jSeparator1 = new JSeparator();
		}
		return jSeparator1;
	}

	/**
	 * This method initializes printMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getPrintMenuItem() {
		if (printMenuItem == null) {
			printMenuItem = new JMenuItem();
			printMenuItem.setText("Print");
			printMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					PrinterJob printJob = PrinterJob.getPrinterJob ();
					Book book = new Book ();
					
					//PageFormat pageFormat = printJob.pageDialog(session.options.getFormat());
					//PageFormat pageFormat = printJob.defaultPage();
					//PageFormat pageFormat = session.options.getFormat();
					PageFormat pageFormat = session.getOptions().getFormat();
					
					
					if(pageFormat == null){ 
						setDefaultPageSetup();
						pageFormat = session.getOptions().getFormat();
					}
					
					System.out.println("Printing...\n Page setup:\n\t"+pageFormat.getWidth()+"x"+pageFormat.getHeight()+"--"+(pageFormat.getOrientation()==PageFormat.PORTRAIT?"Portrait":"Landscape"));
					
					//TODO make this process multiple pages
					OpgPage page = session.currentPage();
					
					Individual tempRoot = session.getBaseRoot();
					String name = tempRoot.surname + ", " + tempRoot.givenName;
					printJob.setJobName((session.projfile == null) ? "OPG Chart - " + name : "OpgChart - " + session.projfile);
					
					book.append (new PrintableChart(page, new AffineOnScreenChartWriter(), session), pageFormat);
					printJob.setPageable (book);
					
					//session.options.setFormat(pageFormat);
						
					if (printJob.printDialog())
					{
						try
						{
							log.debug("printing");
							printJob.print();
						}
						catch (Exception PrintException)
						{
							PrintException.printStackTrace();
						}
					}
				

				}
			});
		}
		return printMenuItem;
	}

	
	
	
	/**
	 * This method initializes swatchArray	
	 * 	
	 * @return edu.byu.cs.roots.opg.gui2.tools.SwatchArray	
	 */
	private SwatchArray getSwatchArray() {
		if (swatchArray == null) {
			swatchArray = new SwatchArray();
			swatchArray.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			swatchArray.setSwatchHeight(15);
			swatchArray.setSwatchWidth(15);
			swatchArray.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!maskEvents){
//						log.debug(swatchArray.getSelected());
						JTable cTable = null;
						if(getColorTabbedPane().getSelectedIndex() == 1) cTable = getDescColorTable();
						if(getColorTabbedPane().getSelectedIndex() == 0) cTable = getAncesColorTable();
						int r = cTable.getSelectedRow();
						int [] rows = (r < 0? new int [] {0}:cTable.getSelectedRows());
						for(int row:rows){
							cTable.setValueAt(swatchArray.getSelected().getColor(), row, 1);
						}
					}
				}
			});
			swatchArray.add(new Swatch(Color.white),null);
			swatchArray.add(new Swatch(new Color(230,230,230)), null);
			swatchArray.add(new Swatch(Color.lightGray), null);
			swatchArray.add(new Swatch(Color.gray), null);
			swatchArray.add(new Swatch(Color.darkGray), null);
			swatchArray.add(new Swatch(Color.black), null);
			swatchArray.add(new Swatch(new Color(126,164,186)), null);
			swatchArray.add(new Swatch(new Color(78,92,186)), null);
			swatchArray.add(new Swatch(Color.blue), null);
			swatchArray.add(new Swatch(new Color(84,176,255)), null);
			swatchArray.add(new Swatch(new Color(131,208,255)), null);
			swatchArray.add(new Swatch(Color.cyan), null);
			swatchArray.add(new Swatch(new Color(132,245,183)), null);
			swatchArray.add(new Swatch(Color.green), null);
			swatchArray.add(new Swatch(new Color(75,168,73)), null);
			swatchArray.add(new Swatch(new Color(86,164,79)), null);
			swatchArray.add(new Swatch(new Color(138,192,54)), null);
			swatchArray.add(new Swatch(Color.yellow), null);
			swatchArray.add(new Swatch(new Color(255,252,158)), null);
			swatchArray.add(new Swatch(new Color(233,231,0)), null);
			swatchArray.add(new Swatch(new Color(255,225,0)), null);
			swatchArray.add(new Swatch(new Color(156,117,56)), null);
			swatchArray.add(new Swatch(Color.orange), null);
			swatchArray.add(new Swatch(new Color(255,166,76)), null);
			swatchArray.add(new Swatch(new Color(255,211,32)), null);
			swatchArray.add(new Swatch(new Color(237,138,62)), null);
			swatchArray.add(new Swatch(new Color(237,118,90)), null);
			swatchArray.add(new Swatch(Color.red), null);
			swatchArray.add(new Swatch(new Color(228,76,109)), null);
			swatchArray.add(new Swatch(new Color(228,117,153)), null);
			swatchArray.add(new Swatch(new Color(237,82,181)), null);
			swatchArray.add(new Swatch(Color.magenta), null);
			swatchArray.add(new Swatch(new Color(225,74,237)), null);
			swatchArray.add(new Swatch(new Color(201,91,237)), null);
			swatchArray.add(new Swatch(new Color(156,14,237)), null);
			swatchArray.add(new Swatch(new Color(99,0,186)), null);
		}
		return swatchArray;
	}

	/**
	 * This method initializes customSwatchArray	
	 * 	
	 * @return edu.byu.cs.roots.opg.gui2.tools.SwatchArray	
	 */
	private SwatchArray getCustomSwatchArray() {
		if (customSwatchArray == null) {
			customSwatchArray = new SwatchArray();
			customSwatchArray.setSwatchHeight(15);
			customSwatchArray.setSwatchWidth(15);
			customSwatchArray.setPreferredSize(new Dimension(10, 10));
			customSwatchArray.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			customSwatchArray.setMinimumSize(new Dimension(10, 10));
			customSwatchArray.add(new Swatch(Color.white),null);
			customSwatchArray.add(new Swatch(Color.white),null);
			customSwatchArray.add(new Swatch(Color.white),null);
			customSwatchArray.add(new Swatch(Color.white),null);
			customSwatchArray.add(new Swatch(Color.white),null);
			customSwatchArray.add(new Swatch(Color.white),null);
			customSwatchArray.add(new Swatch(Color.white),null);
			customSwatchArray.add(new Swatch(Color.white),null);
			customSwatchArray.add(new Swatch(Color.white),null);
			customSwatchArray.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!maskEvents){
						log.debug("Action Performed");
						if(e.getID() == 1){
							JTable cTable = null;
							if(getColorTabbedPane().getSelectedIndex() == 1) cTable = getDescColorTable();
							if(getColorTabbedPane().getSelectedIndex() == 0) cTable = getAncesColorTable();
							int r = cTable.getSelectedRow();
							//if(r < 0) return;
							int [] rows = (r < 0? new int [] {0}:cTable.getSelectedRows());
							for(int row:rows){
								cTable.setValueAt(customSwatchArray.getSelected().getColor(), row, 1);
							}
						}
						else{
							Swatch sel = customSwatchArray.getSelected();
							opencolorchooser(sel.getColor(), sel);
						}
					}
				}
			});
		}
		return customSwatchArray;
	}

	private void opencolorchooser(Color c, Swatch sel){
		log.debug("opening color box");
		CustomColorChooser chooser = new CustomColorChooser();
		chooser.getSelectionModel().addChangeListener(chooser.new ColorListener(this, sel, chooser));
		final JDialog frame = new JDialog();
		frame.addWindowListener(new WindowListener(){
			public void windowActivated(WindowEvent arg0) {}
			public void windowClosed(WindowEvent arg0) {}
			public void windowClosing(WindowEvent arg0) {}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				frame.dispose();
				
			}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowOpened(WindowEvent arg0) {}
			
		});
		frame.add(chooser);
		frame.pack();
		Dimension screenSize;		
		try {
			Toolkit tk = Toolkit.getDefaultToolkit();
			screenSize = tk.getScreenSize();
		} catch (AWTError awe) {
			screenSize = new Dimension(640, 480);
		}
			
		//Dialog dlgPreOrder = new PreOrderDialog(session, getJFrame());
		int frameX = screenSize.width / 2 - frame.getWidth() / 2;
		int frameY = screenSize.height / 2 - frame.getHeight() / 2;
		frame.setBounds(frameX, frameY, frame.getWidth(), frame.getHeight());
		
		
		
		frame.setVisible(true);
	}

	/**
	 * This method initializes colorScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getColorScrollPane() {
		if (colorScrollPane == null) {
			colorScrollPane = new JScrollPane();
			colorScrollPane.setViewportView(getDescColorTable());
		}
		return colorScrollPane;
	}

	/**
	 * This method initializes descColorTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	JTable getDescColorTable() {
		if (descColorTable == null) {
			String[] columnNames = {"", ""};
			//Object[][] data = {{"rootColor", Color.green}, {"crazyColor", Color.red}, {"yuckColor", Color.orange}, {"someColor", Color.yellow}, {"extColor", Color.blue}};
			ColorTableModel model = new ColorTableModel(columnNames, null);
			descColorTable = new JTable(model);
			descColorTable.setColumnSelectionAllowed(false);
			//TableColumn labelColumn = descColorTable.getColumnModel().getColumn(0);
			TableColumn colorColumn = descColorTable.getColumnModel().getColumn(1);
			colorColumn.setPreferredWidth(10);
			colorColumn.setCellRenderer(new ColorCellRenderer());
		}
		return descColorTable;
	}

	/**
	 * This method initializes ancesScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getAncesScrollPane() {
		if (ancesScrollPane == null) {
			ancesScrollPane = new JScrollPane();
			ancesScrollPane.setViewportView(getAncesColorTable());
		}
		return ancesScrollPane;
	}

	/**
	 * This method initializes ancesColorTable1	
	 * 	
	 * @return javax.swing.JTable	
	 */
	JTable getAncesColorTable() {
		if (ancesColorTable == null) {
			String[] columnNames = {"", ""};
//			Object[][] data = {{"rootColor", Color.green}, {"crazyColor", Color.red}, {"yuckColor", Color.orange}, {"someColor", Color.yellow}, {"extColor", Color.blue}};
			ColorTableModel model = new ColorTableModel(columnNames, null);
			ancesColorTable = new JTable(model);
			ancesColorTable.setColumnSelectionAllowed(false);
			TableColumn colorColumn = ancesColorTable.getColumnModel().getColumn(1);
			colorColumn.setCellRenderer(new ColorCellRenderer());
			colorColumn.setPreferredWidth(10);
		}
		return ancesColorTable;
	}

	/**
	 * This method initializes colorTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	JTabbedPane getColorTabbedPane() {
		if (colorTabbedPane == null) {
			colorTabbedPane = new JTabbedPane();
			colorTabbedPane.setMinimumSize(new Dimension(120, 45));
			colorTabbedPane.addTab("Ancestors", null, getAncesPanel(), null);
			colorTabbedPane.addTab("Descendants", null, getDescPanel(), null);
			
		}
		return colorTabbedPane;
	}

	/**
	 * This method initializes descPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getDescPanel() {
		if (descPanel == null) {
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.BOTH;
			gridBagConstraints7.gridy = 1;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.weighty = 1.0;
			gridBagConstraints7.gridwidth = 2;
			gridBagConstraints7.gridx = 0;
			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			gridBagConstraints34.gridx = 0;
			gridBagConstraints34.gridy = 0;
			colorSchemeLabel = new JLabel();
			colorSchemeLabel.setText("Color Scheme");
			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints30.gridy = 0;
			gridBagConstraints30.weightx = 1.0;
			gridBagConstraints30.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints30.gridx = 1;
			descPanel = new JPanel();
			descPanel.setLayout(new GridBagLayout());
			descPanel.add(getDescSchemeBox(), gridBagConstraints30);
			descPanel.add(colorSchemeLabel, gridBagConstraints34);
			descPanel.add(getColorScrollPane(), gridBagConstraints7);
		}
		return descPanel;
	}

	/**
	 * This method initializes ancesPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getAncesPanel() {
		if (ancesPanel == null) {
			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			gridBagConstraints32.fill = GridBagConstraints.BOTH;
			gridBagConstraints32.gridy = 1;
			gridBagConstraints32.weightx = 1.0;
			gridBagConstraints32.weighty = 1.0;
			gridBagConstraints32.gridwidth = 2;
			gridBagConstraints32.gridx = 0;
			GridBagConstraints gridBagConstraints37 = new GridBagConstraints();
			gridBagConstraints37.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints37.gridy = 0;
			gridBagConstraints37.weightx = 1.0;
			gridBagConstraints37.insets = new Insets(2, 2, 2, 2);
			gridBagConstraints37.gridx = 1;
			GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
			gridBagConstraints36.gridx = 0;
			gridBagConstraints36.gridy = 0;
			colorSchemeLabel1 = new JLabel();
			colorSchemeLabel1.setText("Color Scheme");
			ancesPanel = new JPanel();
			ancesPanel.setLayout(new GridBagLayout());
			ancesPanel.add(colorSchemeLabel1, gridBagConstraints36);
			ancesPanel.add(getAncesSchemeBox(), gridBagConstraints37);
			ancesPanel.add(getAncesScrollPane(), gridBagConstraints32);
		}
		return ancesPanel;
	}

	/**
	 * This method initializes ancesSchemeBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getAncesSchemeBox() {
		if (ancesSchemeBox == null) {
			ancesSchemeBox = new JComboBox();
			ancesSchemeBox.setPreferredSize(new Dimension(31, 20));
			ancesSchemeBox.setMinimumSize(new Dimension(31, 20));
			ancesSchemeBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!maskEvents && (ancesSchemeBox.getSelectedItem() != null)){
						for (int i = 0; i < session.currentPage().getChartCount(); i++){
							ChartOptions options = session.currentPage().getOptions(i);
							options.setAncesScheme((ColorScheme) ancesSchemeBox.getSelectedItem());
							options.getAncesScheme().clearTree();
							Individual tempRoot = options.getRoot();
							if (tempRoot != null && tempRoot.fams != null)
							{
								if(tempRoot.fams.size() != 0 && tempRoot.fams.size() != 0){
									Individual spouse = (tempRoot.gender == Gender.MALE) ? tempRoot.fams.get(0).wife : tempRoot.fams.get(0).husband;
									if(options.isIncludeSpouses() && spouse != null) options.getAncesScheme().colorTree(spouse, ColorScheme.colorup);
								}
							}
							options.getAncesScheme().colorTree(tempRoot, ColorScheme.colorup);
							fillColorTables();
							refresh();
						}
					}
				}
			});
		}
		return ancesSchemeBox;
	}

	/**
	 * This method initializes gedPopupMenu	
	 * 	
	 * @return javax.swing.JPopupMenu	
	 */
	private JPopupMenu getGedPopupMenu() {
		if (gedPopupMenu == null) {
			gedPopupMenu = new JPopupMenu();
			gedPopupMenu.setSize(new Dimension(28, 22));
			gedPopupMenu.add(getLinkGedcomMenuItem());
		}
		return gedPopupMenu;
	}

	/**
	 * This method initializes linkGedcomMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getLinkGedcomMenuItem() {
		if (linkGedcomMenuItem == null) {
			linkGedcomMenuItem = new JMenuItem();
			linkGedcomMenuItem.setText("Link in a Gedcom");
			linkGedcomMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent ae) {
					try {
						log.debug("attempting to link in gedcom file");
						File directory = new File((session.config.directory != null) ? session.config.directory : "");
						JFileChooser fileChooser = new JFileChooser(directory);
						fileChooser.setFileFilter(OpgFileFilter.GED);
						int option = fileChooser.showOpenDialog(getJContentPane());
						if (option == JFileChooser.APPROVE_OPTION) {
							File f = fileChooser.getSelectedFile();
							session.loadGedcom(f);
							reloadDynAdvOptPanel();
							session.config.directory = f.getAbsolutePath();
							session.config.directory = session.config.directory.substring(0, session.config.directory.lastIndexOf("\\"));
						} else {
							log.debug("File open canceled");
						}
					} catch (Exception e){
						JOptionPane.showMessageDialog(getJContentPane(), e
								.getLocalizedMessage(), e.getLocalizedMessage(),
								JOptionPane.ERROR_MESSAGE);
						log.error("Error opening file", e );
					}
					if (session.state == SessionState.edit) {
						for (Individual indi : session.names_dataProvider) {
							rootComboBox.addItem(indi);
						}
					}
					reflectState();
					refresh();
					getVisualPreviewPanel().fitWidth();
					// add new project wizard or something
				}
			});
		}
		return linkGedcomMenuItem;
	}

	/**
	 * This method initializes insertToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getInsertToolBar() {
		if (insertToolBar == null) {
			insertToolBar = new JToolBar();
			insertToolBar.add(getAddTextButton());
			insertToolBar.add(getAddPictureButton());
		}
		return insertToolBar;
	}

	/**
	 * This method initializes addTextButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAddTextButton() {
		if (addTextButton == null) {
			addTextButton = new JButton();
			addTextButton.setIcon(new ImageIcon(getClass().getResource("/edu/byu/cs/roots/opg/image/text.gif")));
			addTextButton.setMaximumSize(new Dimension(20, 20));
			addTextButton.setMinimumSize(new Dimension(20, 20));
			addTextButton.setSize(new Dimension(20, 20));
			addTextButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
                                    
					//getArrowToggleButton().setSelected(true);
                                    /*
                                     * Edited By: spencer Hoffa
                                     * Edited On: 11/13/2012
                                     * 
                                     * Original code is commented out
                                     */
					//getZoutToggleButton().setSelected(false);
					//getZinToggleButton().setSelected(false);
					//getHandToggleButton().setSelected(false);
					//session.cursor = OpgCursor.ARROWTEXT;
                                        setArrowTextButton();
                                     //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                                     //     End Edit by Spencer Hoffa
                                     ///////////////////////////////////////////
				
//					System.out.println("actionPerformed()"); //  Auto-generated Event stub actionPerformed()
				}
			});
		}
		return addTextButton;
	}

	/**
	 * This method initializes addPictureButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAddPictureButton() {
		if (addPictureButton == null) {
			addPictureButton = new JButton();
			addPictureButton.setIcon(new ImageIcon(getClass().getResource("/edu/byu/cs/roots/opg/image/picture.gif")));
			addPictureButton.setMaximumSize(new Dimension(20, 20));
			addPictureButton.setMinimumSize(new Dimension(20, 20));
			addPictureButton.setSize(new Dimension(20, 20));
			addPictureButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); //  Auto-generated Event stub actionPerformed()
				}
			});
		}
		return addPictureButton;
	}

	/**
	 * This method initializes arrowToggleButton	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getArrowToggleButton() {
		if (arrowToggleButton == null) {
			arrowToggleButton = new JToggleButton();
			arrowToggleButton.setIcon(new ImageIcon(getClass().getResource("/edu/byu/cs/roots/opg/image/arrow.png")));
			arrowToggleButton.setMaximumSize(new Dimension(20, 20));
			arrowToggleButton.setPreferredSize(new Dimension(20, 20));
			arrowToggleButton.setSelected(false);
			arrowToggleButton.setToolTipText("Click here to switch to the selection cursor");
			arrowToggleButton.setMinimumSize(new Dimension(20, 20));
			arrowToggleButton.addMouseListener(new MouseListener() {
				public void mousePressed(MouseEvent e) {}
				public void mouseClicked(MouseEvent arg0) {
					click();
				}
				public void mouseEntered(MouseEvent arg0) {}
				public void mouseExited(MouseEvent arg0) {}
				public void mouseReleased(MouseEvent arg0) {
					click();
				}
				private void click(){
                                    /*
                                     * Edited By: Spencer Hoffa
                                     * Edited On: 11/13/2012
                                     * Commented Code is original
                                     */
					//getArrowToggleButton().setSelected(true);
					//getZoutToggleButton().setSelected(false);
					//getZinToggleButton().setSelected(false);
					//getHandToggleButton().setSelected(false);
					//session.cursor = OpgCursor.ARROW;
                                    setArrowButton();
                                     //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                                     // End Edit by Spencer Hoffa
                                     ///////////////////////////////////////////
				}
			});
		}
		return arrowToggleButton;
	}

	/**
	 * This method initializes browserMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getBrowserMenuItem() {
		if (browserMenuItem == null) {
			browserMenuItem = new JMenuItem();
			browserMenuItem.setText("Order Prints");
			browserMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					order();
				}
			});
		}
		return browserMenuItem;
	}

	/**
	 * This method initializes jSeparator2	
	 * 	
	 * @return javax.swing.JSeparator	
	 */
	private JSeparator getJSeparator2() {
		if (jSeparator2 == null) {
			jSeparator2 = new JSeparator();
		}
		return jSeparator2;
	}

	/**
	 * This method initializes pageSetupMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getPageSetupMenuItem() {
		if (pageSetupMenuItem == null) {
			pageSetupMenuItem = new JMenuItem();
			pageSetupMenuItem.setText("Page Setup");
			pageSetupMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					//set the page format to match the current page
					setDefaultPageSetup();					
					session.getOptions().setFormat(PrinterJob.getPrinterJob().pageDialog(session.getOptions().getFormat()));
				}
			});
		}
		return pageSetupMenuItem;
	}
	
	private JMenuItem getInsertPictureMenuItem(){
		if (insertPictureMenuItem == null){
			insertPictureMenuItem = new JMenuItem();
			insertPictureMenuItem.setText("Insert Picture");
			insertPictureMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					 JFileChooser chooser = new JFileChooser();
					    // Note: source for ExampleFileFilter can be found in FileChooserDemo,
					    // under the demo/jfc directory in the JDK.
//					    ImageFilter filter = new ImageFilter();
//					    filter.addExtension("jpg");
//					    filter.addExtension("gif");
//					    filter.setDescription("JPG & GIF Images");
//					    chooser.setFileFilter(filter);
					    int returnVal = chooser.showOpenDialog(null);
					    if(returnVal == JFileChooser.APPROVE_OPTION) 
					    	{
					    		BufferedImage img = null;
					    		try {
					    			img = ImageIO.read(new File(chooser.getSelectedFile().getAbsolutePath()));
					    		} catch (IOException ioe) {
					    		}
					    		session.currentPage().getImages().add(new ImageFile(0,0, img));
					    		session.setChanged(true);
					    		refresh();
					    		session.resetChanged();
					    	}
				}
			});
		}
		return insertPictureMenuItem;
	}
	
	private void setDefaultPageSetup()
	{
		PrinterJob printJob = PrinterJob.getPrinterJob ();
		PageFormat pageFormat = printJob.defaultPage();
		pageFormat.setOrientation((!session.getBaseOptions().isLandscape())? PageFormat.PORTRAIT : PageFormat.LANDSCAPE);
		OpgPage page = session.currentPage();
		double chartXSize = page.getPageWidth();
		double chartYSize = page.getPageHeight();
		Paper paper = new Paper();
		paper.setSize(chartXSize, chartYSize);		
		paper.setImageableArea(0,0,chartXSize, chartYSize);
		pageFormat.setPaper(paper);
		session.getBaseOptions().setFormat(pageFormat);
	}

	/**
	 * This method initializes OrderPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getOrderPanel() {
		if (OrderPanel == null) {
			GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
			gridBagConstraints29.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints29.gridy = 0;
			gridBagConstraints29.gridx = 0;
			OrderPanel = new JPanel();
			OrderPanel.setLayout(new GridBagLayout());
			OrderPanel.add(getOrderButton(), gridBagConstraints29);
		}
		return OrderPanel;
	}

	/**
	 * This method initializes orderButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOrderButton() {
		if (orderButton == null) {
			orderButton = new JButton();
			orderButton.setText("Click To Order");
			orderButton.setToolTipText("Click here to order a print of this chart online (requires Internet connection)");
			orderButton.setPreferredSize(new Dimension(130, 30));
			orderButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					order();
				}
			});
		}
		return orderButton;
	}

	/**
	 * This method initializes optionsToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getOptionsToolBar() {
		if (optionsToolBar == null) {
			optionsToolBar = new JToolBar();
			optionsToolBar.setOrientation(JToolBar.VERTICAL);
			optionsToolBar.add(getTabPane());
			optionsToolBar.setVisible(false);
		}
		return optionsToolBar;
	}
	
	/**
	 * This method initializes fontComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getStyleChoiceComboBox() {
		if (styleChoiceComboBox == null) {
			styleChoiceComboBox = new JComboBox();
			
			styleChoiceComboBox.setPreferredSize(new Dimension(50, 25));
			styleChoiceComboBox.setToolTipText("Select the look of your chart");
			styleChoiceComboBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!maskEvents){
						//if (session.options.getChartType() == ChartType.PORTRAIT){
							ChartMaker maker = session.getBaseMaker();
							StylingBoxScheme scheme = (StylingBoxScheme)styleChoiceComboBox.getSelectedItem();
							maker.setChartStyle(scheme);
							session.getOptions().setPaperWidth(scheme.preferredWidth);
//						}
//						if (session.options.getChartType() == ChartType.LANDSCAPE){
//							LandscapeChartMaker maker = (LandscapeChartMaker)session.getMaker();
//							maker.setChartStyle((edu.byu.cs.roots.opg.chart.landscape.StylingBoxScheme)styleChoiceComboBox.getSelectedItem());
//						}
						
						session.getOpgOptions().setNewChartScheme(true);
						session.getOptions().setStyleBoxChanged(true);
						refresh();
					}
				}
			});
		}
		return styleChoiceComboBox;
	}
	
	private JPanel getPageChangerPanel() {
		if (pageChangerPanel == null) {
			pageChangerPanel = new JPanel();
			pageChangerPanel.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;
			pageChangerPanel.add(getTurnPageLeftButton(), c);
			c.gridx = 1;
			pageNumberLabel = new JLabel("0");
			pageChangerPanel.add(pageNumberLabel, c);
			c.gridx = 2;
			pageChangerPanel.add(getTurnPageRightButton(), c);
		}
		return pageChangerPanel;
	}
	
	private JButton getTurnPageLeftButton() {
		if (turnPageLeftButton == null) {
			turnPageLeftButton = new JButton("Left");
			turnPageLeftButton.setMaximumSize(new Dimension(60, 20));
			turnPageLeftButton.setMinimumSize(new Dimension(60, 20));
			turnPageLeftButton.setSize(new Dimension(20, 20));
			turnPageLeftButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
						session.getDecMaker();
						updatePageTurn();
						refresh();
						session.resetChanged();
					}
			});
		}
		return turnPageLeftButton;
	}
	
	private JButton getTurnPageRightButton() {
		if (turnPageRightButton == null) {
			turnPageRightButton = new JButton("Right");
			turnPageRightButton.setMaximumSize(new Dimension(60, 20));
			turnPageRightButton.setMinimumSize(new Dimension(60, 20));
			turnPageRightButton.setSize(new Dimension(20, 20));
			turnPageRightButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
						session.getIncMaker();
						updatePageTurn();
						refresh();
						session.resetChanged();
					}
			});
		}
		return turnPageRightButton;
	}
	
	public void updatePageTurn(){
		Individual changedRoot = session.currentPage().getFirstOptions().getRoot();
		if (root != changedRoot){
			root = changedRoot;
			setComboBoxSelection(changedRoot);
			resetPresetPanel();
			rootSelect();
			
		}
		pageNumberLabel.setText(""+(session.getPageNumber()+1) + '/' + session.getPages());
                pageNumberLabel2.setText(pageNumberLabel.getText());
		
		if (session.getPageNumber() != 0){
			setRootButton.setEnabled(false);
			rootComboBox.setEnabled(false);
			getAncestorSlider().setEnabled(false);
			getDescendantSlider().setEnabled(false);
		}
		else{
			setRootButton.setEnabled(true);
			rootComboBox.setEnabled(true);
			getAncestorSlider().setEnabled(true);
			getDescendantSlider().setEnabled(true);
		}
		session.setChanged(true);

	}
	
	public DownloadProgress getProgressBar(){
		if (progressBar == null){
			progressBar = new DownloadProgress(0);
		}
		return progressBar;
	}

	/**
	 * Added By: Spencer HOffa
	 * Added On: 2/5/2013
	 * 
	 * Adding a function to create the donate menu item
	 * if it has not already been created.
	 */
	private JMenuItem getDonateMenuItem()
	{
		if (donateMenuItem == null)
		{
			donateMenuItem = new JMenuItem();
			donateMenuItem.setText("Donate");
			donateMenuItem.addActionListener(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent arg0) 
						{
							// TODO Auto-generated method stub
							try 
            				{
								java.awt.Desktop.getDesktop().browse(new URI(donateURL));
							} 
            				catch (IOException e1) 
            				{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} 
            				catch (URISyntaxException e1) 
            				{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
					);
		}
		return donateMenuItem;
	}
	//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	//		End Additions By Spencer Hoffa
	/////////////////////////////////////////////////////
	
        /**
         * This method initializes the simple root set button
         * 
         * Added By: Spencer Hoffa
         * Added On: 10/31/2012
         */
        private JButton getSimpleRootSetButton()
        {
            if (simpleRootSetButton == null)
            {
                simpleRootSetButton = new JButton();
                simpleRootSetButton.setText("Set Root");
                
                simpleRootSetButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                                if(!maskEvents){
//						log.debug("Root action performed");
                                        root = (Individual) rootComboBox.getSelectedItem();
                                        updateSelectionCheckboxes();
                                        resetPresetPanel();
                                        rootSelect();
                                }
                        }
                }
                );
            }
            return simpleRootSetButton;
        }
        
        /**
         * Added By Spencer Hoffa
         * Added on: 10/31/2012
         */
        /*private JPanel getSimpleRootPanel()
        {
            if (simpleRootPanel == null)
            {   
                GridBagConstraints spouseCheckboxConstraints = new GridBagConstraints();
                GridBagConstraints ancestorCheckboxConstraints = new GridBagConstraints();
                GridBagConstraints infoConstraints = new GridBagConstraints();

                infoConstraints.anchor = GridBagConstraints.CENTER;
                infoConstraints.gridy = 2;
                infoConstraints.gridx = 0;
                
                ancestorCheckboxConstraints.anchor = GridBagConstraints.CENTER;
                ancestorCheckboxConstraints.gridy = 1;
                ancestorCheckboxConstraints.gridx = 1;
                
                spouseCheckboxConstraints.anchor = GridBagConstraints.CENTER;
                spouseCheckboxConstraints.gridy = 1;
                spouseCheckboxConstraints.gridx = 0;
                
                GridBagConstraints rootComboBoxConstraints  = new GridBagConstraints();
                rootComboBoxConstraints.anchor = GridBagConstraints.CENTER;
                rootComboBoxConstraints.gridx = 1;
                rootComboBoxConstraints.gridy = 0;
                rootComboBoxConstraints.weightx = 1.0;
                rootComboBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
                
                GridBagConstraints setButtonConstraints  = new GridBagConstraints();
                setButtonConstraints .anchor = GridBagConstraints.CENTER;
                setButtonConstraints .gridx = 0;
                setButtonConstraints .gridy = 0;
                setButtonConstraints .insets = new Insets(0, 10, 0, 0);
                
                JButton simpleViewInfoButton = new JButton("View Info");
                simpleViewInfoButton.addActionListener(new ViewIndividualInfoActionListener());
                
                simpleRootPanel = new JPanel();
                simpleRootPanel.setLayout(new GridBagLayout());
                simpleRootPanel.setPreferredSize(new Dimension(400, 24));
                simpleRootPanel.setMinimumSize(new Dimension(120, 25));
                simpleRootPanel.add(getSimpleRootSetButton(), setButtonConstraints );
                simpleRootPanel.add(simpleViewInfoButton, infoConstraints);
                //simpleRootPanel.add(getRootComboBox(), rootComboBoxConstraints);
                simpleRootPanel.add(getIncludeSpouseCheckBox(), spouseCheckboxConstraints);
                simpleRootPanel.add(getAncestorCheckBox(), ancestorCheckboxConstraints);
                
                
                
                simpleIncludeSpouseCheckBox = new JCheckBox();
                simpleIncludeSpouseCheckBox.setEnabled(false);
                simpleIncludeSpouseCheckBox.setToolTipText("Use this check box to indicate whether or not you want to include the spouse of the root individual in the chart.");
                simpleIncludeSpouseCheckBox.setText("Draw Spouse Tree");
                simpleIncludeSpouseCheckBox.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                                if(!maskEvents){
//						log.debug("Include Spouse");
                                    JOptionPane.showMessageDialog(getJFrame(),"Include Spouse clicked");
                                        resetPresetPanel();
                                        rootSelect();
                                }
                        }
                });

                

                rootComboBox.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                                if(!maskEvents ){
                                        System.out.print(e);
                                        updateSelectionCheckboxes();
                                }
                        }
                });
                
            }
            
            return simpleRootPanel;
        }*/
        
        /**
         * Creates a duplicate page turn panel
         * Added By: Spencer Hoffa
         * Added on: 11/1/2012
         */
        private JPanel getSimpleTurnPagePanel()
        {
            if (simpleTurnPagePanel == null)
            {
                //Duplicate TurnPageLeftButton
                JButton simpleTurnPageLeftButton = new JButton("Left");
                simpleTurnPageLeftButton.setMaximumSize(new Dimension(60, 20));
                simpleTurnPageLeftButton.setMinimumSize(new Dimension(60, 20));
                simpleTurnPageLeftButton.setSize(new Dimension(20, 20));
                simpleTurnPageLeftButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                                        session.getDecMaker();
                                        updatePageTurn();
                                        refresh();
                                        session.resetChanged();
                                }
                });
                //-------------------------------------------
                
                //Duplicate TurnPageRightButton
                JButton simpleTurnPageRightButton = new JButton("Right");
                simpleTurnPageRightButton.setMaximumSize(new Dimension(60, 20));
                simpleTurnPageRightButton.setMinimumSize(new Dimension(60, 20));
                simpleTurnPageRightButton.setSize(new Dimension(20, 20));
                simpleTurnPageRightButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                                        session.getIncMaker();
                                        updatePageTurn();
                                        refresh();
                                        session.resetChanged();
                                }
                });
                //-------------------------------------------
                
                
                simpleTurnPagePanel = new JPanel();
                simpleTurnPagePanel.setLayout(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                simpleTurnPagePanel.add(simpleTurnPageLeftButton, c);
                c.gridx = 1;
                pageNumberLabel2 = new JLabel("0");
                simpleTurnPagePanel.add(pageNumberLabel2, c);
                c.gridx = 2;
                simpleTurnPagePanel.add(simpleTurnPageRightButton, c);
            }
            
            return simpleTurnPagePanel;
        }
        
        /**
         * This function turns simplified mode on or off.
         * 
         * Added By: Spencer Hoffa
         * Added On: 11/13/2012
         * 
         * @param simple Is simplified mode on or off.
         */
        public void setSimplifiedGUI(boolean simple)
        {
            setOptionsVisibility(!simple);
            simplifiedMode = simple;
            setMoveButton();
            
            //getVisualPreviewPanel().repaint();
            
            //getVisualPreviewPanel().fitWidth();
            getVisualPreviewPanel().firstLoaded();
        }
        
        public void downloadDone()
        {
            getAncestorSlider().setValue(6);
            getAncestorSlider().firePropertyChange("value", 0, 6);
            /*session.getBaseOptions().setAncesGens(6, session);
            refresh();
            getVisualPreviewPanel().fitWidth();*/
        }
        
        /**
         * Added By Spencer Hoffa
         * Added on: 10/31/2012
         */
        private void setOptionsVisibility(boolean isVisible)
        {
            //make sure these are visible
            getToolPanel().setVisible(true);
            //Root info panel has been merged into Tool Panel
            //getRootInfoPanel().setVisible(true);
            
            getTabPane().setVisible(isVisible);
            advancedOptionsCheckBox.setVisible(isVisible);
            getArrowToggleButton().setVisible(isVisible);
            
            if (isVisible == false)
            {
                //remove panels that need to move or not display
                getViewerPanel().remove(getToolPanel());
                //Root info panel has been merged into Tool Panel
                //getViewerPanel().remove(getRootInfoPanel());
                
                //Set the layout to vertical
                //getToolPanel().setLayout(new BoxLayout(getToolPanel(), BoxLayout.Y_AXIS));
                
                //add the new turn page panel to the tool panel
                getToolPanel().add(getSimpleTurnPagePanel());
                //add the rootInfoPanel to the toolPanel
//                getToolPanel().add(getRootInfoPanel());
                
                //add the options to the West
                //getViewerPanel().add(getToolPanel(), BorderLayout.WEST);
                getViewerPanel().add(getToolPanel(), BorderLayout.NORTH);
                
                //Change the comboBox to the right chart style
                getChartStyleComboBox().setSelectedItem(ChartType.MULTISHEET);
                
               /* 
                
                //not work right jContentPane ????
                getToolPanel().add( getPageChangerPanel());
                this.updatePageTurn();
                
                //remove stuff from north and south
                getViewerPanel().remove(getToolPanel());
                getViewerPanel().remove(getRootInfoPanel());
                
                getToolPanel().add(getSimpleRootPanel());
                
                */
            }
            else
            {
                //remove options from west
                getViewerPanel().remove(getToolPanel());
                
                //remove the new turn page panel to the tool panel
                getToolPanel().remove(getSimpleTurnPagePanel());
                //remove the rootInfoPanel to the toolPanel
                //Root info panel has been merged into Tool Panel
                //getToolPanel().remove(getRootInfoPanel());
                
                //set layout to horizontal
                //getToolPanel().setLayout(new BoxLayout(getToolPanel(), BoxLayout.X_AXIS));
                
                //add Panels that need to be displayed
                getViewerPanel().add(getToolPanel(), BorderLayout.NORTH);
                //Root info panel has been merged into Tool Panel
                //getViewerPanel().add(getRootInfoPanel(), BorderLayout.SOUTH);
                
                /*getToolPanel().remove(getPageChangerPanel());
                
                getToolPanel().remove(getSimpleRootPanel());
                
                
                
                //add it to north
                viewerPanel.add(getToolPanel(), BorderLayout.NORTH);
                //add root panel in south
		viewerPanel.add(getRootInfoPanel(), BorderLayout.SOUTH);*/
            }
            
        }
        
        /**
         * This method will open any file type that we are currently
         * supporting.
         * Added by Spencer HOffa
         * Added on: 10/31/2012
         */
        public void openSupportedFileTypes()
        {
            
            try {
			File directory = new File((session.config.directory != null) ?
					session.config.directory : "");
			if(!isMac()){
				JFileChooser fileChooser = new JFileChooser(directory);
				//dialog.
				//fileChooser.setFileFilter(OpgFileFilter.PAFZIP);
				fileChooser.setFileFilter(OpgFileFilter.ALL);
				fileChooser.setDialogTitle("New");
				int option = fileChooser.showOpenDialog(this.jContentPane);
				if (option == JFileChooser.APPROVE_OPTION) {
					File f = fileChooser.getSelectedFile();
					open(f);
					reloadDynAdvOptPanel();
					session.config.directory = f.getAbsolutePath();
					int pos = session.config.directory.lastIndexOf("\\");
					if(pos < 0)
						pos = session.config.directory.lastIndexOf("/");
					session.config.directory = session.config.directory.substring(0, pos);
				} else {
					log.debug("File open canceled");
				}
			}
			else {
				FileDialog fd = new FileDialog(jFrame,"Please select a file to open.",FileDialog.LOAD);
				fd.setDirectory(directory.getAbsolutePath());
				fd.setFilenameFilter(OpgFileFilter.ALL);
				fd.setVisible(true);
				if(fd.getFile() != null)
					open(fd.getDirectory() + File.separator + fd.getFile());
				reloadDynAdvOptPanel();
			}

		} catch (Exception e){
			JOptionPane.showMessageDialog(this.jContentPane, e
					.getLocalizedMessage(), e.getLocalizedMessage(),
					JOptionPane.ERROR_MESSAGE);
			log.error("Error opening file", e );
		}
            
        }
        
        /**
         * This creates an action listener for the View Individual info
         * button.
         * 
         * added by: Spencer Hoffa
         * Added on: 10/31/2012
         */
        private class ViewIndividualInfoActionListener implements java.awt.event.ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Dimension screenSize;		
                try {
                        Toolkit tk = Toolkit.getDefaultToolkit();
                        screenSize = tk.getScreenSize();
                } catch (AWTError awe) {
                        screenSize = new Dimension(640, 480);
                }

                //Dialog dlgPreOrder = new PreOrderDialog(session, getJFrame());
                Dialog indiInfo = new IndiInfo((Individual)rootComboBox.getSelectedItem());
                int frameX = screenSize.width / 2 - indiInfo.getWidth() / 2;
                int frameY = screenSize.height / 2 - indiInfo.getHeight() / 2;
                indiInfo.setBounds(frameX, frameY, indiInfo.getWidth(), indiInfo.getHeight());
                indiInfo.setVisible(true);
            }
        }
        
        /**
         * This function will hand back the most recent version of
         * the ChooseRootDialog.
         * Added By: Spencer Hoffa
         * Added ON: 11/1/2012
         */
        private ChooseRootDialog currChooseRootDialog = null;
        public ChooseRootDialog getChooseRootDialog()
        {
            if (currChooseRootDialog == null)
            {
                //JOptionPane.showMessageDialog(this.getJFrame(), "Creating new choose root Dialog");
                currChooseRootDialog = new ChooseRootDialog(jFrame);
            }
            return currChooseRootDialog;
        }
        
        /**
         * Is the gui in simplified Mode
         */
        public boolean isSimplifiedMode()
        {
            return this.simplifiedMode;
        }
        
        /**
         * This will set the current instance of the 
         * choose root dialog person to select.
         * 
         * @param toSelect This is the individual that was clicked on.
         */
        public void setSelectedIndividual(Individual toSelect)
        {
            if (this.currChooseRootDialog != null)
            {
                //this.currChooseRootDialog.chooseRootList.setSelectedValue(toSelect, true);
                this.currChooseRootDialog.setSelected(toSelect);
                setMoveButton();
                this.currChooseRootDialog.setVisible(true);
            }
        }
        
        /**
         * Added By: Spencer Hoffa
         * Added On: 11/13/2012
         * Extracted the method for choosing the 
         * arrows and the edit options
         */
        public void setArrowButton()
        {
            getArrowToggleButton().setSelected(true);
            getZoutToggleButton().setSelected(false);
            getZinToggleButton().setSelected(false);
            getHandToggleButton().setSelected(false);
            session.cursor = OpgCursor.ARROW;
        }
        
        public void setArrowTextButton()
        {
            //getArrowToggleButton().setSelected(true);
            getZoutToggleButton().setSelected(false);
            getZinToggleButton().setSelected(false);
            getHandToggleButton().setSelected(false);
            session.cursor = OpgCursor.ARROWTEXT;
        }
        
        public void setZoomOutButton()
        {
            getArrowToggleButton().setSelected(false);
            getZoutToggleButton().setSelected(true);
            getZinToggleButton().setSelected(false);
            getHandToggleButton().setSelected(false);
            session.cursor = OpgCursor.Z_OUT;
        }
        
        public void setZoomInButton()
        {
            getArrowToggleButton().setSelected(false);
            getZoutToggleButton().setSelected(false);
            getZinToggleButton().setSelected(true);
            getHandToggleButton().setSelected(false);
            session.cursor = OpgCursor.Z_IN;
        }
        
        public void setMoveButton()
        {
            getArrowToggleButton().setSelected(false);
            getZoutToggleButton().setSelected(false);
            getZinToggleButton().setSelected(false);
            getHandToggleButton().setSelected(true);
            session.cursor = OpgCursor.MOVE;
        }
        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        //          End Additions By Spencer Hoffa
        ////////////////////////////////////////////////////////////////////////
        
        /*
         * Added By:Spencer Hoffa
         * Added On: 11/1/2012
         * class for choosing the root dialog
         */
        private class ChooseRootDialog extends JDialog
        {
            private DefaultListModel individualList;
            
            JTextField rootSearchBox;
            JList chooseRootList;
            
            private JPanel searchPanel;
            JPanel buttonPanel;
            JButton chooseRootOK;
            
            ChooseRootDialog(Frame parent)
            {
                super(parent, "Choose Root Dialog");
                
                individualList = new DefaultListModel();
                
                //Get all the people from the session
                getIndividuals();
                        
                //set the properties for the window
                setSize(new Dimension(100,100));
                setLocation(200,200);
                setAlwaysOnTop(true);
                
                //Setup the List of names
                chooseRootList = new JList(individualList);
                chooseRootList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                chooseRootList.setLayoutOrientation(JList.VERTICAL);
                chooseRootList.addListSelectionListener(
                		new ListSelectionListener()
                		{
							@Override
							public void valueChanged(ListSelectionEvent arg0) 
							{
								if (!chooseRootList.isSelectionEmpty())
								{
									chooseRootOK.setEnabled(true);
								}
							}
                		}
                		);
                /*chooseRootList.addListSelectionListener(new ListSelectionListener()
                        {
                            public void valueChanged(ListSelectionEvent e) 
                            {
                                //rootSearchBox.setText(chooseRootList.getSelectedValue().toString());
                            }
                        }
                        );*/
                
                //put it in a scroll pane
                JScrollPane listScrollPane = new JScrollPane(chooseRootList);
                //--------------------------------------------------------------
                
                JPanel choosePanel = new JPanel();
                choosePanel.setLayout(new BorderLayout());
                choosePanel.add(getSearchPanel(), BorderLayout.NORTH);
                choosePanel.add(listScrollPane, BorderLayout.CENTER);
                choosePanel.add(getButtonPanel(), BorderLayout.SOUTH);
                
                getContentPane().add(choosePanel);
                
                setSelected(session.getBaseRoot());
                
                setModal(false);//true);
                pack();
                setLocationRelativeTo(parent);
                
                ChooseRootDialog.this.addWindowListener(
                        new java.awt.event.WindowAdapter()
                        {
                            public void windowClosing(WindowEvent e) 
                            {
                                currChooseRootDialog = null;
                                ChooseRootDialog.this.dispose();
                            }
                        }
                        );
                
                setVisible(true);
            }
            
            private JPanel getSearchPanel()
            {
                if (searchPanel == null)
                {
                    //Setup the textbox for search
                    JLabel rootSearchLabel = new JLabel("Search: ");
                    rootSearchBox = new JTextField(20);
                    /*rootSearchBox.addKeyListener(new KeyListener()
                    {
                        public void keyTyped(KeyEvent e) 
                        {
                            chooseRootList.setModel(searchList(rootSearchBox.getText()));
                        }

                        public void keyPressed(KeyEvent e) {}
                        
                        public void keyReleased(KeyEvent e) {}
                    }
                    );*/
                    rootSearchBox.getDocument().addDocumentListener(
                            new DocumentListener()
                            {
                                public void insertUpdate(DocumentEvent e) 
                                {
                                    //should be same as remove update
                                    updateTextField();
                                }


                                public void removeUpdate(DocumentEvent e) 
                                {
                                    //should be same as insertUpdate
                                    updateTextField();
                                }

                                public void changedUpdate(DocumentEvent e) 
                                {
                                    //not implemented in plain text
                                }
                                
                                private void updateTextField()
                                {
                                	//JOptionPane.showMessageDialog(ChooseRootDialog.this, "Text updated: " + rootSearchBox.getText());
                                	chooseRootList.setModel(searchList(rootSearchBox.getText()));
                                	chooseRootList.clearSelection();
                                	chooseRootOK.setEnabled(false);
                                	
                                }
                            }
                            );
                    
                    JButton selectFromChart = new JButton("Select From Chart");
                    selectFromChart.addActionListener(
                    new java.awt.event.ActionListener()
                    {
                        public void actionPerformed(ActionEvent e) 
                        {
                            //JOptionPane.showMessageDialog(ChooseRootDialog.this, "Choosing from Chart.");
                            rootSearchBox.setText("");
                            setArrowButton();
                            ChooseRootDialog.this.setVisible(false);
                        }
                        
                    }
                    );
                    
                    searchPanel = new JPanel();
                    
                    searchPanel.add(rootSearchLabel);
                    searchPanel.add(rootSearchBox);
                    searchPanel.add(selectFromChart);
                }
                
                return searchPanel;
            }
            
            private JPanel getButtonPanel()
            {
                if (buttonPanel == null)
                {
                    // Setup the OK button
                	chooseRootOK = new JButton("OK");
                    chooseRootOK.addActionListener(
                            new java.awt.event.ActionListener()
                            {   
                                public void actionPerformed(java.awt.event.ActionEvent e) 
                                {
                                    //JOptionPane.showMessageDialog(ChooseRootDialog.this, chooseRootList.getSelectedValue().toString());
                                    
                                    root = (Individual) chooseRootList.getSelectedValue();
                                    updateSelectionCheckboxes();
                                    resetPresetPanel();
                                    rootSelect();
                                    
                                    //ChooseRootDialog.this.setVisible(false);
                                    currChooseRootDialog = null;
                                    ChooseRootDialog.this.dispose();
                                }
                            }
                            );
                    //---------------------------------------------

                    //Setup the Cancel button
                    JButton chooseRootCancel = new JButton("Cancel");
                    chooseRootCancel.addActionListener(
                            new java.awt.event.ActionListener()
                            {   
                                public void actionPerformed(java.awt.event.ActionEvent e) 
                                {
                                    //ChooseRootDialog.this.setVisible(false);
                                    currChooseRootDialog = null;
                                    ChooseRootDialog.this.dispose();
                                }
                            }
                            );
                    //---------------------------------------------

                    //put the buttons in a panel
                    buttonPanel = new JPanel();
                    
                    buttonPanel.add(chooseRootOK);
                    buttonPanel.add(chooseRootCancel);
                    //buttonPanel.setPreferredSize(new Dimension(200, 40));
                }
                
                return buttonPanel;
            }
            
            private void getIndividuals()
            {
                /*individualList.addElement("Number 1");
                individualList.addElement("Number 2");
                individualList.addElement("Number 3");
                individualList.addElement("Number 4");
                individualList.addElement("Number 5");
                individualList.addElement("Number 6");
                individualList.addElement("Number 7");
                individualList.addElement("Number 8");
                individualList.addElement("Number 9");
                individualList.addElement("Number 10");
                individualList.addElement("Number 11");
                individualList.addElement("Number 12");
                individualList.addElement("Number 13");
                individualList.addElement("Number 14");
                individualList.addElement("Number 15");*/
                //rootComboBox.removeAllItems();
                for (Individual indi : session.names_dataProvider) {
                        //log.debug("Adding " + indi);
                        /*if(indi != null && indi.givenName == null)
                                System.out.println("yes");*/
                        individualList.addElement(indi);
                }
                
                /*
            	 * Edited By:Spencer Hoffa
            	 * Edited On: 2/12/2013
            	 * 
            	 * I added back in this Code 
            	 */
                Individual rootTemp = session.getBaseRoot();
                if(rootTemp != null){

                        getRootComboBox().setSelectedIndex(session.names_dataProvider.indexOf(rootTemp));
                        getIncludeSpouseCheckBox().setSelected(session.getOptions().isIncludeSpouses());
                        log.debug("setting root");
                        getRootComboBox().repaint();
                        reloadDynAdvOptPanel();
                }
                //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                //	End Edit by: Spencer HOffa
                ////////////////////////////////////////////////////////////////////
            }
            
            public void setSelected(Individual newSelected)
            {
                if(newSelected != null)
                {
                    chooseRootList.setSelectedValue(newSelected, true);
                        //getRootComboBox().setSelectedIndex(session.names_dataProvider.indexOf(newSelected));
                    	/*
                    	 * Edited By:Spencer Hoffa
                    	 * Edited On: 2/12/2013
                    	 * 
                    	 * I added back in this line 
                    	 */
                        getIncludeSpouseCheckBox().setSelected(session.getOptions().isIncludeSpouses());
                        log.debug("setting root");
                        getRootComboBox().repaint();
                        reloadDynAdvOptPanel();
                        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                        //	End Edit by: Spencer HOffa
                        ////////////////////////////////////////////////////////////////////
                }
            }
            
            private ListModel searchList(String toSearch)
            {
                if (toSearch.length() == 0) return individualList;
                
                DefaultListModel newList = new DefaultListModel();
                
                //Split words by spaces
                //JOptionPane.showMessageDialog(ChooseRootDialog.this, toSearch);
                //JOptionPane.showMessageDialog(ChooseRootDialog.this, 
                //		toSearch.split(" ").length);
                
                String itemsToSearch[] = toSearch.split(" ");
                //---------------------------
                
                for (int i = 0; i < individualList.size(); i++)
                {
                    Individual ind = (Individual) individualList.get(i);
                    String indName = ind.toString().toLowerCase();
                    boolean addToList = true;
                    /*
                    if (indName.contains(toSearch.toLowerCase()))
                    {
                        newList.addElement(ind);
                        break;
                    }
                    */
                    //see if each word is a part of the name
                    for (int j = 0; j < itemsToSearch.length; j++)
                    {
                    	//if not then break and don't add
	                    if (!(indName.contains(itemsToSearch[j].toLowerCase())))
	                    {
	                        //newList.addElement(ind);
	                    	addToList = false;
	                        break;
	                    }
                    }
                    
                    if (addToList)
                    {
                    	//This means that this individuals name had every word
                    	//or part of word we were searching for
                    	newList.addElement(ind);
                    }
                }
                
                return newList;
            }
        }
        
        /**
         * Added By: Spencer Hoffa
         * Added On: 12/29/2012
         * 
         * This function is added to give access to the the current
         * window.
         */
        public JFrame getCurrentWindow()
        {
            return jFrame;
        }
        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        //      End addition by: Spencer Hoffa
        ///////////////////////////////////////////////////////////////
}
