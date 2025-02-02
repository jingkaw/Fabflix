




import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class ParseFileActor {

	//No generics
	List myActors;
	Document dom;


	public ParseFileActor(){
		//create a list to hold the employee objects
		myActors = new ArrayList();
	}

	public void runExample() {
		
		//parse the xml file and get the dom object
		parseXmlFile();
		
		//get each employee element and create a Employee object
		parseDocument();
		
		//Iterate through the list and print the data
		printData();
		
	}
	
	
	private void parseXmlFile(){
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			//parse using builder to get DOM representation of the XML file
			dom = db.parse("actors63.xml");
			

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	
	private void parseDocument(){
		//get the root elememt
		Element docEle = dom.getDocumentElement();
		
		//get a nodelist of <employee> elements
		NodeList nl = docEle.getElementsByTagName("actor");
		System.out.println("nl1  length " + nl.getLength());
		if(nl != null && nl.getLength() > 0) 
		{
			
			//nl.getLength()
			for(int i = 0 ; i < 10;i++) 
			{
				System.out.println(" index is   "+i);
				//get the employee element
				Element el = (Element)nl.item(i);
				
				//get the Employee object
				Actor a = getActor(el);
				
				//add it to list
				myActors.add(a);
			}
			
			}
		}
	


	/**
	 * I take an employee element and read the values in, create
	 * an Employee object and return it
	 * @param empEl
	 * @return
	 */
	private Actor getActor(Element empEl) {
		
		//for each <employee> element get text or int values of 
		//name ,id, age and name
		String stagename = getTextValue(empEl,"stagename");
		
		int year = getIntValue(empEl,"dob");

		
		//Create a new Employee with the value read from the xml nodes
		Actor e = new Actor(stagename,year);
		
		return e;
	}
	


	/**
	 * I take a xml element and the tag name, look for the tag and get
	 * the text content 
	 * i.e for <employee><name>John</name></employee> xml snippet if
	 * the Element points to employee node and tagName is name I will return John  
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getTextContent();
		}
		//getTextContent()
		//getFirstChild().getNodeValue(); 本身的

		return textVal;
	}

	
	/**
	 * Calls getTextValue and returns a int value
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		try {
			return Integer.parseInt(getTextValue(ele,tagName));
	
		}
		catch(NumberFormatException e){
			
		}
		return 0;
	}
	
	/**
	 * Iterate through the list and print the 
	 * content to console
	 */
	private void printData(){
		
		System.out.println("No of Movies '" + myActors.size() + "'.");
		
		Iterator it = myActors.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
	}


	public static void main(String[] args){
		//create an instance
		ParseFileActor dpe = new ParseFileActor();
		
		//call run example
		dpe.runExample();
	}

}


