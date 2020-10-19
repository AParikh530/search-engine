import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author ayushparikh
 * Creates a Search Engine that is based on user input.
 */
public class SearchEngine 
{
	public static final String PAGES_FILE = "pages.txt";
	public static final String LINKS_FILE = "links.txt";
	private static WebGraph web = new WebGraph();
	
	public static void main(String[] args)
	{
		String menuOptions = "Menu: "
				+ "		(AP) Add a new page to the graph.\n"
				+ "		(RP) Remove a page from the graph.\n"
				+ "		(AL) Add a link between pages in the graph.\n"
				+ "		(RL) Remove a link between pages in the graph.\n"
				+ "		(P) Print to the graph.\n"
				+ "		(S) Search for pages with a keyword.\n"
				+ "		(Q) Quit."; 
		
		System.out.println("Loading WebGraph data...");
		web.buildFromFiles(PAGES_FILE, LINKS_FILE);
		System.out.println("Success");
		
		System.out.println();
		System.out.println(menuOptions);
		System.out.print("Please select an option: ");
		Scanner inputEntered = new Scanner(System.in);
		String userPick = inputEntered.nextLine();
		
		while(!(userPick.equalsIgnoreCase("Q")))
		{
			if(userPick.equalsIgnoreCase("AP"))
			{
				System.out.print("Enter a URL: ");
				inputEntered = new Scanner(System.in);
				String url = inputEntered.nextLine();
				
				System.out.print("Enter keywords (space-seperated): ");
				inputEntered = new Scanner(System.in);
				String keywords1 = inputEntered.nextLine();
				try {
					
					LinkedList<String> keywords2 = new LinkedList<String>();
					String[] words = keywords1.split(" ");
					for(int i = 0; i < words.length; i++)
					{
						keywords2.add(words[i]);
					}
					
					web.addPage(url, keywords2);
					System.out.println("\n" + url + " successfully added to the WebGraph!\n");
				}
				catch(IllegalArgumentException e)
				{
					System.out.println("\nError: " + url + " already exists in the WebGraph. Could not add new WepPage.\n");
				}
			}
			else if(userPick.equalsIgnoreCase("RP"))
			{
				System.out.print("Enter a URL: ");
				inputEntered = new Scanner(System.in);
				String url = inputEntered.nextLine();
				
				web.removePage(url);
				System.out.println("\n" + url + " successfully removed from the WebGraph!\n");
			}
			else if(userPick.equalsIgnoreCase("AL"))
			{
				try
				{
					System.out.print("Enter a source URL: ");
					inputEntered = new Scanner(System.in);
					String sourceURL = inputEntered.nextLine();
					
					System.out.print("Enter a destination URL: ");
					inputEntered = new Scanner(System.in);
					String destURL = inputEntered.nextLine();
					
					web.addLink(sourceURL, destURL);
					System.out.println("\nLink successfully added from " + sourceURL + " to " + destURL + "!\n");
				}
				catch(IllegalArgumentException e)
				{
					System.out.println("\nError: Site could not be found in the WebGraph.\n");
				}
			}
			else if(userPick.equalsIgnoreCase("RL"))
			{
				System.out.print("Enter a source URL: ");
				inputEntered = new Scanner(System.in);
				String sourceURL = inputEntered.nextLine();
				
				System.out.print("Enter a destination URL: ");
				inputEntered = new Scanner(System.in);
				String destURL = inputEntered.nextLine();
				
				web.removeLink(sourceURL, destURL);
				System.out.println("\nLink successfully removed from " + sourceURL + " to " + destURL + "!\n");
			}
			else if(userPick.equalsIgnoreCase("P"))
			{
				System.out.print("   (I) Sort based on index (ASC)\n" + 
						"   (U) Sort based on URL (ASC)\n" + 
						"   (R) Sort based on rank (DSC)\n"
						+ "\nPlease select an option: ");
				
				inputEntered = new Scanner(System.in);
				String sortPick = inputEntered.nextLine();
				
				if(sortPick.equalsIgnoreCase("I"))
					web.printTable(1);
				else if(sortPick.equalsIgnoreCase("U"))
					web.printTable(2);
				else if(sortPick.equalsIgnoreCase("R"))
					web.printTable(3); 
			}
			else if(userPick.equalsIgnoreCase("S"))
			{
				System.out.print("Search keyword: ");
				inputEntered = new Scanner(System.in);
				String keyword = inputEntered.nextLine();
				try
				{
					web.printBasedOnKeyword(keyword); 
				}
				catch(IllegalArgumentException e)
				{
					System.out.println("No search results found for the keyword " + keyword + ".");
				}
			}
			
			System.out.println();
			System.out.println(menuOptions);
			System.out.print("Please select an option: ");
			inputEntered = new Scanner(System.in);
			userPick = inputEntered.nextLine();
		}
		System.out.println("Goodbye.");
	}	
}
