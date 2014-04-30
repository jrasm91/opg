package opg.other;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import javax.imageio.ImageIO;

import opg.spacer.Node;
import opg.spacer.Node.NodeGender;
import opg.spacer.Node.NodeType;

import org.gedcom4j.model.FamilyChild;
import org.gedcom4j.model.FamilySpouse;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.parser.GedcomParser;
import org.gedcom4j.parser.GedcomParserException;
import org.gedcom4j.query.Finder;

public class Utility {

	public static Node makeSimpleDescTree() {
		int children = 5;
		int softMax = 100;
		Random r = new Random(5);
		Queue<Node> q = new LinkedList<Node>();
		Node root = Node.makeRoot(NodeType.Descendent, NodeGender.Male, new Dimension(100, 300));
		q.add(root);
		int count = 1;
		while (!q.isEmpty()) {
			Node next = q.poll();
			if (count > softMax) {
				break;
			}
			for (int i = 0; i < r.nextInt(children + 1); i++) {
				q.add(next.makeNode(NodeGender.Undefined, new Dimension(75, 150)));
				count++;
			}
		}
		return root;
	}

	public static Node makeSimpleAncTree() {
		Node root = Node.makeRoot(NodeType.Ancestor, NodeGender.Male, new Dimension(50, 200));
		Node r1 = root.makeNode(NodeGender.Female, new Dimension(50, 200));
		Node r2 = root.makeNode(NodeGender.Male, new Dimension(50, 200));
		Node r11 = r1.makeNode(NodeGender.Female, new Dimension(50, 200));
		Node r21 = r2.makeNode(NodeGender.Female, new Dimension(50, 200));
		Node r22 = r2.makeNode(NodeGender.Male, new Dimension(50, 200));
		Node r211 = r21.makeNode(NodeGender.Female, new Dimension(50, 200));
		Node r212 = r21.makeNode(NodeGender.Male, new Dimension(50, 200));
		Node r2121 = r212.makeNode(NodeGender.Female, new Dimension(50, 200));
		Node r21211 = r2121.makeNode(NodeGender.Female, new Dimension(50, 200));
		Node r212111 = r21211.makeNode(NodeGender.Female, new Dimension(50, 200));
		Node r2121111 = r212111.makeNode(NodeGender.Male, new Dimension(50, 200));
		Node r2111 = r211.makeNode(NodeGender.Male, new Dimension(50, 200));
		Node r21111 = r2111.makeNode(NodeGender.Female, new Dimension(50, 200));

		return root;
	}

	public static Node makeDescTree2() {

		Node root = Node.makeRoot(NodeType.Descendent);
		Node r1 = root.makeNode();
		Node r2 = root.makeNode();
		Node r121 = r1.makeNode();
		Node r211 = r2.makeNode();
		Node r2121 = r2.makeNode();
		Node r12111 = r121.makeNode().rotate();
		Node r21211 = r2121.makeNode().rotate();
		Node r21212 = r2121.makeNode().rotate();
		Node r121111 = r12111.makeNode().rotate();
		Node r212111 = r21211.makeNode().rotate();
		Node r212112 = r21211.makeNode().rotate();
		Node r212113 = r21211.makeNode().rotate();
		return root;
	}

	public static Node makeGedcomTree() {
		GedcomParser gp = new GedcomParser();
		Gedcom g = gp.gedcom;
		try {
			// gp.load("wigington.ged");
			gp.load("src/josephposterity.ged");
		} catch (IOException | GedcomParserException e) {
			e.printStackTrace();
		}

		Finder f = new Finder(g);
		// List<Individual> found = f.findByName("Wiggonton", "Henry");
		List<Individual> found = f.findByName("Smith", "Joseph");
		Individual wasParent = found.iterator().next();

		// for (FamilyChild fc : wasParent.familiesWhereChild) {
		// if (fc.family.husband != null) {
		// System.out.println(fc.family.husband);
		// }
		// if (fc.family.wife != null) {
		// System.out.println(fc.family.wife);
		// }
		// }

		while (true) {
			Individual husband = null;
			Individual wife = null;
			if (wasParent.familiesWhereChild.iterator().hasNext()) {
				FamilyChild fc = wasParent.familiesWhereChild.iterator().next();
				husband = fc.family.husband;
				wife = fc.family.wife;
			}
			if (husband != null) {
				wasParent = husband;
			} else if (wife != null) {
				wasParent = wife;
			} else
				break;
		}
		Node root = Node.makeRoot(NodeType.Descendent, NodeGender.Undefined, new Dimension(300, 400));
		makeGEDperson(root, wasParent, 0);
		return root;
	}
	
	private static void makeGEDperson(Node parent, Individual i, int generation) {
		
		Dimension[] sizes = { 
				new Dimension(200, 300), 
				new Dimension(175, 200), 
				new Dimension(150, 100), 
				new Dimension(125, 100), 
				new Dimension(100, 100)  };
		
		parent.id = i.formattedName().replaceAll("/", "");
		parent.gender = i.sex.toString().equals("M")? NodeGender.Male : NodeGender.Female;
		if (i.familiesWhereSpouse.iterator().hasNext()) {
			 FamilySpouse fs = i.familiesWhereSpouse.iterator().next();
//			for (FamilySpouse fs : i.familiesWhereSpouse) {
				for (Individual child : fs.family.children) {
					Node c = parent.makeNode(NodeGender.Male, sizes[Math.min(generation, sizes.length - 1)]);
					makeGEDperson(c, child, generation + 1);
//				}
			}
		}
	}

	
	public static void openURL(String url){
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public static Image loadImage(String path){
		Image image = null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			image = null;
			e.printStackTrace();
		}
		return image;
	}

	public static String getFileExtension(String filename){
		String extension = null;
		if(filename.indexOf(".") != -1){
			String[] parts = filename.split("\\.");
			extension = parts[parts.length - 1].toUpperCase();
		}
		return extension;
	}

	public static void createPDF(List<BufferedImage> images, String filename){
//
//		try {
//			PDDocument document = new PDDocument();
//			Chart chart = Controller.singleton().getChart();
//			for(BufferedImage image: images){
//				PDPage page = new PDPage(new PDRectangle(chart.getWidth(), chart.getHeight()));
//				document.addPage(page);
//				PDJpeg jpg = new PDJpeg(document, image);
//				PDPageContentStream content = new PDPageContentStream(document, page);
//				content.drawImage(jpg, chart.getMargin().getLeft(), chart.getMargin().getTop());
//				content.close();
//			}
//			document.save(filename);
//			document.close();
//		} catch(IOException | COSVisitorException e){
//			e.printStackTrace();
//		}
	}

	public static Chart parse(File file) {
		//		String extension = Utility.getFileExtension(file.getName());
		//		if (extension == null)
		//			throw new IllegalArgumentException(Const.ERR_NO_FILE_EXT);
		//
		//		List<Person> individuals = null;
		//		List<Family> families = null;
		//		switch (extension) {
		//		case "GED":
		//			Gedcom gedcom = new ModelParser().parseGedcom(file);
		//			individuals = gedcom.getPeople();
		//			families = gedcom.getFamilies();
		//			break;
		//			// case "paf": return parser.parsePAF();
		//			// case "zip": return parser.parsePAF();
		//			// case "opg": return parser.parseOPG();
		//		default:
		//			throw new IllegalArgumentException(Const.ERR_BAD_EXT + extension);
		//		}
		//
		//		return new Chart(Chart.Types.MULTI_SHEET, individuals, families, null, Const.CHART_HEIGHT, Const.CHART_WIDTH);
		return null;
	}


	public static void writeBytes(byte[] bytes, String filename){
		FileOutputStream file;
		try {
			file = new FileOutputStream(filename);
			file.write(bytes);
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}













