package LinearControl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.collections15.Transformer;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.visualization.VisualizationImageServer;

public class App {

	private JFrame frame;
	private JTextField fromtextfield;
	private JTextField numvtextfield;
	private JTextField numetextfield;
	private JTextField endtextfield;
	private JTextField starttextfield;
	private JTextField totextfield;
	private JTextField weighttextfield;
	private JTextField TF_textfield;
	private int numb = 0;
	private int numv;
	private int nume;
	private int start;
	private int end;
	private MyGraph sfg;
	private int from;
	private int to;
	private double weight;
	 private DirectedSparseMultigraph<String, String> sfgg = new DirectedSparseMultigraph<String, String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1135, 754);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	   // setStyle();
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setForeground(new Color(153, 0, 102));
		btnNewButton.setFont(new Font("Agency FB", Font.BOLD, 17));
		btnNewButton.setBackground(new Color(0, 0, 0));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fromtextfield.getText().equals("") || totextfield.getText().equals("")
						|| weighttextfield.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "please complete your edge info.", "Error!",
							JOptionPane.WARNING_MESSAGE);
					return;
				}		
				from = Integer.valueOf(fromtextfield.getText());
				to = Integer.valueOf(totextfield.getText());
				weight = Double.valueOf(weighttextfield.getText());
				if (numv != 0 && (from >= numv || from < 0 || to >= numv || to < 0)) {
					JOptionPane.showMessageDialog(null, "please write valid vertices.", "Error!",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (numb == 0) {
					if (numvtextfield.getText().equals("") || numetextfield.getText().equals("")
							|| starttextfield.getText().equals("") || endtextfield.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "please complete your graph info.", "Error!",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					numv = Integer.valueOf(numvtextfield.getText());
					if (from >= numv || from < 0 || to >= numv || to < 0) {
						JOptionPane.showMessageDialog(null, "please write valid vertices.", "Error!",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					for (int i = 0; i < numv; i++) {
						sfgg.addVertex(Integer.toString(i));
						//sfgg.getNode(Integer.toString(i)).addAttribute("ui.label", Integer.toString(i));
					}
					nume = Integer.valueOf(numetextfield.getText());
					start = Integer.valueOf(starttextfield.getText());
					end = Integer.valueOf(endtextfield.getText());
					sfg = new MyGraph(numv, start, end);
					boolean tmp = sfg.addEdge(from, to, weight);
					if (tmp) {
					sfgg.addEdge("w"+String.valueOf(numb)+": "+Double.toString(weight),Integer.toString(from),Integer.toString(to));
					numb++;
					} else {
						JOptionPane.showMessageDialog(null, "Edge is already in the graph, Try another one.", "Error!",
								JOptionPane.WARNING_MESSAGE);	
					}
				} else if (numb == nume) {
					return;
				} else {
					boolean tmp = sfg.addEdge(from, to, weight);
					if (tmp) {
					sfgg.addEdge("w"+String.valueOf(numb)+": "+Double.toString(weight),Integer.toString(from),Integer.toString(to));
					numb++;
					} else {
						JOptionPane.showMessageDialog(null, "Edge is already in the graph, Try another one.", "Error!",
								JOptionPane.WARNING_MESSAGE);	
					}
				}
				display();
			}
		});
		btnNewButton.setBounds(382, 217, 81, 38);
		frame.getContentPane().add(btnNewButton);
		frame.setTitle("Signal Flow Graph");
		JLabel lblAddAnEdge = new JLabel("Add edges to your graph");
		lblAddAnEdge.setForeground(new Color(153, 0, 102));
		lblAddAnEdge.setFont(new Font("Andalus", Font.BOLD, 40));
		lblAddAnEdge.setBounds(10, 140, 437, 40);
		frame.getContentPane().add(lblAddAnEdge);

		fromtextfield = new JTextField();
		fromtextfield.setFont(new Font("Andalus", Font.BOLD, 20));
		fromtextfield.setBounds(10, 215, 114, 40);
		frame.getContentPane().add(fromtextfield);
		fromtextfield.setColumns(10);

		JLabel lblNumOfVertices = new JLabel("Num of vertices:");
		lblNumOfVertices.setFont(new Font("Andalus", Font.BOLD, 26));
		lblNumOfVertices.setBounds(27, 28, 187, 34);
		frame.getContentPane().add(lblNumOfVertices);

		numvtextfield = new JTextField();
		numvtextfield.setFont(new Font("Andalus", Font.BOLD, 20));
		numvtextfield.setColumns(10);
		numvtextfield.setBounds(224, 28, 114, 32);
		frame.getContentPane().add(numvtextfield);

		numetextfield = new JTextField();
		numetextfield.setFont(new Font("Andalus", Font.BOLD, 20));
		numetextfield.setColumns(10);
		numetextfield.setBounds(224, 87, 114, 32);
		frame.getContentPane().add(numetextfield);

		JLabel lblNumOfEdges = new JLabel("Num of Edges:");
		lblNumOfEdges.setFont(new Font("Andalus", Font.BOLD, 26));
		lblNumOfEdges.setBounds(27, 87, 187, 34);
		frame.getContentPane().add(lblNumOfEdges);

		JLabel lblNumOfStart = new JLabel("Num of start vertex:");
		lblNumOfStart.setFont(new Font("Andalus", Font.BOLD, 26));
		lblNumOfStart.setBounds(412, 28, 242, 34);
		frame.getContentPane().add(lblNumOfStart);

		JLabel lblNumOfEnd = new JLabel("Num of end vertex:");
		lblNumOfEnd.setFont(new Font("Andalus", Font.BOLD, 26));
		lblNumOfEnd.setBounds(412, 87, 242, 34);
		frame.getContentPane().add(lblNumOfEnd);

		endtextfield = new JTextField();
		endtextfield.setFont(new Font("Andalus", Font.BOLD, 20));
		endtextfield.setColumns(10);
		endtextfield.setBounds(664, 87, 114, 32);
		frame.getContentPane().add(endtextfield);

		starttextfield = new JTextField();
		starttextfield.setFont(new Font("Andalus", Font.BOLD, 20));
		starttextfield.setColumns(10);
		starttextfield.setBounds(664, 35, 114, 32);
		frame.getContentPane().add(starttextfield);

		totextfield = new JTextField();
		totextfield.setFont(new Font("Andalus", Font.BOLD, 20));
		totextfield.setColumns(10);
		totextfield.setBounds(134, 215, 114, 40);
		frame.getContentPane().add(totextfield);

		weighttextfield = new JTextField();
		weighttextfield.setFont(new Font("Andalus", Font.BOLD, 20));
		weighttextfield.setColumns(10);
		weighttextfield.setBounds(258, 215, 114, 40);
		frame.getContentPane().add(weighttextfield);

		JLabel lblFrom = new JLabel("From");
		lblFrom.setFont(new Font("Andalus", Font.BOLD, 20));
		lblFrom.setBounds(10, 179, 99, 40);
		frame.getContentPane().add(lblFrom);

		JLabel lblTo = new JLabel("To");
		lblTo.setFont(new Font("Andalus", Font.BOLD, 20));
		lblTo.setBounds(134, 179, 99, 40);
		frame.getContentPane().add(lblTo);

		JLabel lblWeight = new JLabel("Weight");
		lblWeight.setFont(new Font("Andalus", Font.BOLD, 20));
		lblWeight.setBounds(258, 179, 99, 40);
		frame.getContentPane().add(lblWeight);

		JButton btnNewButton_1 = new JButton("Calculate the Transfer Function\r\n\r\n");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sfg == null) {
					JOptionPane.showMessageDialog(null, "please add a graph to calculate its TF.", "Error!",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				TF_textfield.setText(Double.toString(sfg.solveSFG()));
			}
		});
		btnNewButton_1.setBackground(new Color(0, 0, 0));
		btnNewButton_1.setForeground(new Color(153, 0, 102));
		btnNewButton_1.setFont(new Font("Andalus", Font.BOLD, 32));
		btnNewButton_1.setBounds(513, 163, 526, 66);
		frame.getContentPane().add(btnNewButton_1);

		JLabel lblNewLabel = new JLabel("Graph\r\n");
		lblNewLabel.setForeground(new Color(153, 0, 51));
		lblNewLabel.setFont(new Font("Andalus", Font.BOLD | Font.ITALIC, 47));
		lblNewLabel.setBounds(811, 87, 139, 92);
		frame.getContentPane().add(lblNewLabel);

		JLabel label = new JLabel("Signal ");
		label.setForeground(new Color(153, 0, 51));
		label.setFont(new Font("Andalus", Font.BOLD | Font.ITALIC, 47));
		label.setBounds(856, -23, 156, 109);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel("Flow\r\n");
		label_1.setForeground(new Color(153, 0, 51));
		label_1.setFont(new Font("Andalus", Font.BOLD | Font.ITALIC, 47));
		label_1.setBounds(842, 28, 156, 109);
		frame.getContentPane().add(label_1);

		TF_textfield = new JTextField();
		TF_textfield.setFont(new Font("Andalus", Font.BOLD, 20));
		TF_textfield.setColumns(10);
		TF_textfield.setBounds(664, 230, 375, 48);
		frame.getContentPane().add(TF_textfield);

		JButton btnNewGraph = new JButton("New graph");
		btnNewGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sfg = null;
				clearGraph();
				fromtextfield.setText("");
				totextfield.setText("");
				weighttextfield.setText("");
				numvtextfield.setText("");
				numetextfield.setText("");
				starttextfield.setText("");
				endtextfield.setText("");
				TF_textfield.setText("");
				numb = 0;
				JOptionPane.showMessageDialog(null, "Start writing your graph info.", "",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnNewGraph.setForeground(new Color(153, 0, 102));
		btnNewGraph.setFont(new Font("Agency FB", Font.BOLD, 30));
		btnNewGraph.setBackground(Color.BLACK);
		btnNewGraph.setBounds(946, 669, 175, 48);
		frame.getContentPane().add(btnNewGraph);
	}
	
	private void display(){
        VisualizationImageServer<String,String>vs=new VisualizationImageServer<String,String>(new  CircleLayout<String,String>(sfgg),new Dimension(930,415));
        Transformer<String, String> transformer = new Transformer<String, String>()
        {
            @Override
            public String transform(String s)
            {
                return s;
            }
        };
        Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {
			@Override
			public Paint transform(String arg0) {
				return Color.magenta;
			}
        	 }; 
        vs.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vs.getRenderContext().setVertexLabelTransformer(transformer);
        vs.getRenderContext().setEdgeLabelTransformer(transformer);
        vs.setLocation(0, 280);
        frame.getContentPane().add(vs);
        frame.setVisible(true);
    }
	
	private void clearGraph() {
		Collection<String> ed = sfgg.getEdges();
		Iterator<String> t = ed.iterator();
		ArrayList<String> toRemove = new ArrayList<>();
		while(t.hasNext())
			toRemove.add(t.next());
		for(String elem : toRemove)
			sfgg.removeEdge(elem);
		for (int i = 0; i < numv ; i++)
			sfgg.removeVertex(Integer.toString(i));
		display();
	}
}
